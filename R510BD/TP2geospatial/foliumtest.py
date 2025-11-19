import folium
import psycopg2
conn = psycopg2.connect(host='192.168.24.250', user='Lucas', password='10dc27696928')
cur = conn.cursor()
cur.execute("""
SELECT name, day, ST_X(loc), ST_Y(loc)
FROM london_points;
""")
rows = cur.fetchall()
m = folium.Map(location=[51.5074, -0.1278], zoom_start=13)
for name, day, lon, lat in rows:
    folium.Marker( [lat, lon],
    tooltip=f"{name} (Jour {day})",
    icon=folium.Icon(color="blue" if day == 1 else "green")).add_to(m)
m.save("map_london_postgis.html")