#!/usr/bin/env python3
# coding: utf-8
"""
Script robuste pour :
 - récupérer les musées d'une ville via Overpass
 - insérer en SQLite seulement si la ville n'est pas déjà dans la table
Conçu pour mieux fonctionner avec Tokyo / Osaka.
"""

import requests
import sqlite3
import time

DB_PATH = "database/museums.db"

# ---------------------------
# Helpers Nominatim -> area_id
# ---------------------------
def nominatim_get_osm(city, country=None):
    """Interroge Nominatim et renvoie le meilleur résultat (osm_type, osm_id, display_name, lat, lon)."""
    params = {"q": city, "format": "jsonv2", "limit": 3}
    if country:
        params["countrycodes"] = country  # ex "jp"
    try:
        r = requests.get("https://nominatim.openstreetmap.org/search", params=params, timeout=15)
        r.raise_for_status()
        j = r.json()
        if not j:
            return None
        # Choisir le meilleur match : privilégier type=relation ou way
        for item in j:
            if item.get("osm_type") in ("relation", "way"):
                return item
        # fallback : retourne le premier si pas de relation/way
        return j[0]
    except Exception as e:
        print("Nominatim error:", e)
        return None

def osm_to_area_id(osm_type, osm_id):
    """Convertit osm_type/osm_id en area_id pour Overpass si possible."""
    if osm_type == "relation":
        return 3600000000 + int(osm_id)
    if osm_type == "way":
        return 2400000000 + int(osm_id)
    return None  # node -> pas d'area directe

# ---------------------------
# Test existence area dans Overpass
# ---------------------------
def overpass_area_exists(area_id):
    """Teste si area(id:...) existe."""
    q = f"[out:json][timeout:10]; area({area_id}); out ids;"
    try:
        r = requests.post("https://overpass-api.de/api/interpreter", data=q.encode("utf-8"), timeout=30)
        if r.status_code != 200:
            return False
        j = r.json()
        # Si elements non vide -> existe
        return bool(j.get("elements"))
    except Exception:
        return False

# ---------------------------
# Requête Overpass pour musées à partir d'un area (id) ou par nom (fallback)
# ---------------------------
def fetch_museums_from_area_id(area_id):
    """Récupère musées via area(id:...)"""
    q = f"""
    [out:json][timeout:60];
    area({area_id})->.searchArea;
    nwr["tourism"="museum"](area.searchArea);
    out tags center;
    """
    try:
        r = requests.post("https://overpass-api.de/api/interpreter", data=q.encode("utf-8"), timeout=90)
        r.raise_for_status()
        return r.json().get("elements", [])
    except Exception as e:
        print("Overpass error (area_id):", e)
        return []

def fetch_museums_by_name(city, country="JP"):
    """
    Fallback : cherche des areas par nom (admin boundary) et récupère musées.
    On ajoute country code pour réduire les faux positifs.
    """
    # On tente admin_level 4 puis 6, etc. (varie selon pays)
    q = f"""
    [out:json][timeout:60];
    area["name"="{city}"]["boundary"="administrative"]["is_in:country"="{country}"];
    // si pas trouvé, on cherche sans is_in
    area["name"="{city}"]["boundary"="administrative"];
    // prendre la première area trouvée
    (._;)->.areas;
    nwr["tourism"="museum"](area.areas);
    out tags center;
    """
    try:
        r = requests.post("https://overpass-api.de/api/interpreter", data=q.encode("utf-8"), timeout=90)
        r.raise_for_status()
        return r.json().get("elements", [])
    except Exception as e:
        print("Overpass error (by name):", e)
        return []

# ---------------------------
# Conversion des éléments Overpass en tuples DB
# ---------------------------
def elements_to_museum_tuples(elements, city_name):
    """Transforme la liste d'éléments Overpass en tuples pour SQLite:
       (lat, lon, name, address, city, opening_hours, wikidata)
    """
    rows = []
    for el in elements:
        tags = el.get("tags", {})
        lat = el.get("lat") or el.get("center", {}).get("lat")
        lon = el.get("lon") or el.get("center", {}).get("lon")
        name = tags.get("name")
        # construire une adresse raisonnable
        address = tags.get("addr:full") or tags.get("addr:street") or tags.get("addr:housenumber") or None
        opening = tags.get("opening_hours")
        wikidata = tags.get("wikidata")
        if lat and lon and name:
            rows.append((float(lat), float(lon), name, address, city_name, opening, wikidata))
    return rows

# ---------------------------
# DB: insertion et logique principale
# ---------------------------
def ensure_table(conn):
    cur = conn.cursor()
    cur.execute("""
    CREATE TABLE IF NOT EXISTS museums (
        lat REAL,
        lon REAL,
        name TEXT,
        address TEXT,
        city TEXT,
        opening_hours TEXT,
        genre TEXT,
        wikidata TEXT,
        UNIQUE(name, lat, lon) ON CONFLICT IGNORE
    )
    """)
    conn.commit()

def city_present(conn, city):
    cur = conn.cursor()
    cur.execute("SELECT COUNT(*) FROM museums WHERE city = ?", (city,))
    return cur.fetchone()[0] > 0

def insert_museums(conn, rows):
    cur = conn.cursor()
    cur.executemany("""
        INSERT INTO museums(lat, lon, name, address, city, opening_hours, wikidata)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, rows)
    conn.commit()
    return len(rows)

def add_city_museums_if_missing(city, country_code="jp"):
    conn = sqlite3.connect(DB_PATH)
    ensure_table(conn)

    if city_present(conn, city):
        print(f"Ville '{city}' déjà présente dans la BD. Rien à faire.")
        conn.close()
        return

    # 1) tenter Nominatim pour obtenir osm_type/osm_id
    nom = nominatim_get_osm(city, country=country_code)
    elements = []
    if nom:
        osm_type = nom.get("osm_type")
        osm_id = nom.get("osm_id")
        area_id = osm_to_area_id(osm_type, osm_id)
        if area_id:
            print(f"Nominatim -> osm_type={osm_type}, osm_id={osm_id}, testing area_id={area_id}")
            if overpass_area_exists(area_id):
                elements = fetch_museums_from_area_id(area_id)
            else:
                print("Area_id calculé non trouvé dans Overpass, fallback par nom.")
        else:
            print("Nominatim renvoyé un node (pas d'area direct) ou type inconnu, fallback par nom.")
    else:
        print("Nominatim n'a rien retourné, fallback par nom.")

    # 2) fallback : chercher area par nom via Overpass (utile pour Tokyo/Osaka)
    if not elements:
        print("Recherche fallback Overpass par nom (peut prendre quelques secondes)...")
        elements = fetch_museums_by_name(city, country=country_code.upper())

    museums = elements_to_museum_tuples(elements, city)
    if not museums:
        print(f"Aucun musée trouvé pour '{city}' (Overpass renvoyé rien).")
        conn.close()
        return

    # Insert rows (UNIQUE constraint évite les doublons)
    inserted = insert_museums(conn, museums)
    print(f"{inserted} musées insérés pour la ville '{city}'.")
    conn.close()

# ---------------------------
# Exemple d'utilisation
# ---------------------------
if __name__ == "__main__":
    city = input("Ville (ex: Tokyo, Osaka) : ").strip()
    # pour le Japon, on force country_code=jp pour réduire ambiguïtés
    add_city_museums_if_missing(city, country_code="jp")
