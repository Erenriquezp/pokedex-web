const apiUrl = "http://localhost:8080/api/types"; // URL de la API para filtrar por tipo

/**
 * Busca Pokémon por tipo.
 */
function searchByType() {
    const typeName = document.getElementById("typeInput").value.trim().toLowerCase();
    if (!typeName) return;

    fetch(`${apiUrl}/${typeName}`)
        .then(response => {
            if (!response.ok) throw new Error("No se encontraron Pokémon de este tipo");
            return response.json();
        })
        .then(displayPokemonByType)
        .catch(error => {
            console.error(error);
            document.getElementById("typeResults").innerHTML = "<p>No se encontraron Pokémon de este tipo.</p>";
        });
}

/**
 * Muestra los Pokémon filtrados por tipo.
 */
function displayPokemonByType(pokemons) {
    const container = document.getElementById("typeResults");
    container.innerHTML = ""; // Limpiar resultados anteriores

    if (pokemons.length === 0) {
        container.innerHTML = "<p>No se encontraron Pokémon de este tipo.</p>";
        return;
    }

    pokemons.forEach(pokemon => {
        const card = document.createElement("div");
        card.className = "pokemon-card";
        card.innerHTML = `
            <img src="${pokemon.sprites.frontDefault}" alt="${pokemon.name}">
            <h3>${pokemon.name.toUpperCase()}</h3>
        `;
        container.appendChild(card);
    });
}

// Asignar evento al botón de búsqueda
document.getElementById("searchTypeBtn").addEventListener("click", searchByType);
