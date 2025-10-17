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

    const lat = target.dataset.lat;
    const lon = target.dataset.lon;
    const name = target.textContent;
    refreshmap(lat, lon);

    console.log("Lieu choisi :", name, lat, lon);
    alert(`Vous avez choisi : ${name}\nLat: ${lat}\nLon: ${lon}`);

    // Exemple : tu peux envoyer au serveur Flask
    fetch(`/lieu_selectionne?lat=${lat}&lon=${lon}&nom=${encodeURIComponent(name)}`);
});