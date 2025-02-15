const evolutionApiUrl = "http://localhost:8080/api/evolution"; // URL de la API de evolución

/**
 * Realiza la búsqueda de la cadena evolutiva de un Pokémon.
 */
function searchEvolution() {
    const speciesName = document.getElementById("evolutionInput").value.trim().toLowerCase();
    if (!speciesName) {
        alert("Ingrese un nombre de Pokémon");
        return;
    }

    fetch(`${evolutionApiUrl}/${speciesName}`)
        .then(response => {
            if (!response.ok) throw new Error("No se encontró evolución.");
            return response.json();
        })
        .then(evolutionChain => displayEvolutionChain(evolutionChain))
        .catch(error => {
            console.error(error);
            document.getElementById("evolutionContainer").innerHTML = `<p class="error-message">${error.message}</p>`;
        });
}

/**
 * Muestra la cadena evolutiva en la vista.
 * @param {Array} evolutionChain - Lista con la evolución del Pokémon.
 */
function displayEvolutionChain(evolutionChain) {
    const container = document.getElementById("evolutionContainer");
    container.innerHTML = "";

    if (evolutionChain.length === 0) {
        container.innerHTML = "<p>No hay evolución para este Pokémon.</p>";
        return;
    }

    const evolutionFlow = document.createElement("div");
    evolutionFlow.className = "evolution-flow";

    evolutionChain.forEach(stage => {
        const species = stage.species;
        const name = species.name;
        const spriteUrl = `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${extractPokemonId(species.url)}.png`;

        const evolutionCard = document.createElement("div");
        evolutionCard.className = "evolution-card";
        evolutionCard.innerHTML = `
            <img src="${spriteUrl}" alt="${name}" class="evolution-image">
            <p>${name.toUpperCase()}</p>
        `;

        evolutionFlow.appendChild(evolutionCard);
    });

    container.appendChild(evolutionFlow);
}

/**
 * Extrae el ID del Pokémon desde la URL de la especie.
 * @param {string} url - URL de la especie.
 * @returns {string} ID del Pokémon.
 */
function extractPokemonId(url) {
    const parts = url.split("/");
    return parts[parts.length - 2]; // Último número en la URL
}

// Asignar evento al botón de búsqueda
document.getElementById("searchEvolutionBtn").addEventListener("click", searchEvolution);
