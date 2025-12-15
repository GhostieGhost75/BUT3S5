import folium
import psycopg2
conn = psycopg2.connect(host='192.168.24.250', user='Lucas', password='10dc27696928')
cur = conn.cursor()
cur.execute("""
SELECT name, type, summary, ST_X(geom), ST_Y(geom)
FROM locations;
""")
colors = {"City" : "gray", "Castle" : "purple", "Town" : "black",
          "Ruin" : "beige", "Landmark" : "red", "Region" : "green"}
rows = cur.fetchall()
m = folium.Map(zoom_start=13)
for name, type, summary, lon, lat in rows:
    folium.Marker( [lat, lon],
    tooltip=f"{name} ({type})",
    popup=summary,
    icon=folium.Icon(color=colors[type]),).add_to(m),
m.save("map_wot_postgis.html")