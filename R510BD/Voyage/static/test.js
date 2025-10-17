var museum = L.icon({
    iconUrl: 'https://upload.wikimedia.org/wikipedia/donate/4/4f/Museum_Icon_local.png',

    iconSize:     [25, 25], // size of the icon
    iconAnchor:   [12, 1], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -35] // point from which the popup should open relative to the iconAnchor
});

var cinema = L.icon({
    iconUrl: 'https://cdn-icons-png.flaticon.com/512/73/73960.png',

    iconSize:     [25, 25], // size of the icon
    iconAnchor:   [12, 24], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -35] // point from which the popup should open relative to the iconAnchor
});

var types = {
    "museum" : [],
    "cinema" : []
}

var map = L.map('map');
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const overpassUrl = "https://overpass-api.de/api/interpreter";
const input = document.getElementById("search");
const resultsDiv = document.getElementById("results");
let timeout = null;


input.addEventListener("input", () => {
    const query = input.value.trim();

    clearTimeout(timeout);
    if (query.length < 3) {
        resultsDiv.innerHTML = "";
        return;
    }

    // petite pause avant la requête pour éviter de spammer l’API
    timeout = setTimeout(() => {
        fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&addressdetails=1&limit=20`)
            .then(res => res.json())
            .then(data => {
                if (!data.length) {
                    resultsDiv.innerHTML = "<p>Aucun résultat</p>";
                    return;
                }

                resultsDiv.innerHTML = data.map(place => `
                    <div class="result-item" 
                         data-lat="${place.lat}" 
                         data-lon="${place.lon}">
                        <strong>${place.display_name}</strong>
                    </div>
                `).join("");
            })
            .catch(err => {
                console.error("Erreur Nominatim :", err);
            });
    }, 300);
});

// Quand on clique sur un résultat
resultsDiv.addEventListener("click", e => {
    const target = e.target.closest(".result-item");
    if (!target) return;

    const lat = parseFloat(target.dataset.lat);
    const lon = parseFloat(target.dataset.lon);
    const name = target.textContent;
    refreshmap(lat, lon, 10000);

    console.log("Lieu choisi :", name, lat, lon);
    alert(`Vous avez choisi : ${name}\nLat: ${lat}\nLon: ${lon}`);

    // Exemple : tu peux envoyer au serveur Flask
    fetch(`/lieu_selectionne?lat=${lat}&lon=${lon}&nom=${encodeURIComponent(name)}`);
});

function buildQuery(lat, lon, radius) {
    return `
[out:json][timeout:90];
(
  node["tourism"="museum"](around:${radius},${lat},${lon});

  node["amenity"="cinema"](around:${radius},${lat},${lon});
);
out center;
`;
}

function refreshmap(lat, lon, radius) {
    query = buildQuery(lat, lon, radius);
    map.setView([lat, lon], 13);
    fetch(overpassUrl, {
        method: "POST",
        body: query,
    })
    .then(response => response.json())
    .then(data => {
        console.log(data)
        data.elements.forEach(element => {
            if (element.lat && element.lon) {
                const name = element.tags && element.tags.name ? element.tags.name : "Musée sans nom";
                const popupContent = `
                    <strong>${name}</strong><br>
                    <em>ID OSM :</em> ${element.id}
                `;
                if (element.tags.amenity == 'cinema') {
                    let marker = L.marker([element.lat, element.lon], {icon: cinema});
                    marker.addTo(map).bindPopup(popupContent);
                    types.cinema.push(marker)
                }
                else {
                    let marker = L.marker([element.lat, element.lon], {icon: museum});
                    marker.addTo(map).bindPopup(popupContent);
                    types.museum.push(marker)
                }

            }
        });
    })
    .catch(err => {
        console.error("Erreur Overpass:", err);
    });
}



function filtermap(checkbox) {
    let category = checkbox.value;
    if (category in types) {
        if (checkbox.checked) {
            types[category].forEach(marker => marker.addTo(map));
        } else {
            types[category].forEach(marker => map.removeLayer(marker));
        }
    }
}

refreshmap(51.5074456, -0.1277653, 10000)