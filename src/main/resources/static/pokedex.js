const apiUrl = "http://localhost:8080/api/pokemon";

/**
 * Busca un Pokémon por nombre y muestra sus detalles.
 */
function searchPokemon() {
    const name = document.getElementById("searchInput").value.trim().toLowerCase();
    if (!name) {
        alert("Por favor ingresa el nombre de un Pokémon.");
        return;
    }

    fetch(`${apiUrl}/${name}`)
        .then(response => {
            if (!response.ok) throw new Error("Pokémon no encontrado");
            return response.json();
        })
        .then(pokemon => displayPokemonDetails(pokemon))
        .catch(error => {
            console.error(error);
            document.getElementById("pokemonDetails").innerHTML = "<p>Pokémon no encontrado.</p>";
        });
}

/**
 * Muestra los detalles del Pokémon en la tarjeta.
 * @param {Object} pokemon - Datos del Pokémon.
 */
function displayPokemonDetails(pokemon) {
    const container = document.getElementById("pokemonDetails");
    container.innerHTML = `
        <div class="pokemon-card">
            <img src="${pokemon.spriteUrl}" alt="${pokemon.name}">
            <div class="pokemon-info">
                <h3>${pokemon.name.toUpperCase()}</h3>
                <p><span>Tipo:</span> ${pokemon.types.map(t => t.name).join(", ")}</p>
                <p><span>Habilidades:</span> ${pokemon.abilities.map(a => a.name).join(", ")}</p>
            </div>
        </div>
    `;
}

// Asignar evento al botón de búsqueda
document.getElementById("searchBtn").addEventListener("click", searchPokemon);
