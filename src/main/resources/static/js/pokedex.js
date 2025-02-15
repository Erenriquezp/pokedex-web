const apiUrl = "http://localhost:8080/api/pokemon/name"; // URL para obtener detalles del Pokémon
const statsUrl = "http://localhost:8080/api/stats"; // URL para obtener estadísticas

/**
 * Obtiene los parámetros de la URL y realiza la búsqueda automáticamente.
 */
document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const pokemonName = urlParams.get("name");

    if (pokemonName) {
        document.getElementById("searchInput").value = pokemonName;
        searchPokemon();
    }
});


/**
 * Realiza la búsqueda de un Pokémon por su nombre.
 */
function searchPokemon() {
    const pokemonName = document.getElementById("searchInput").value.trim().toLowerCase();
    if (!pokemonName) {
        alert("Ingrese un nombre de Pokémon");
        return;
    }

    fetch(`${apiUrl}/${pokemonName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Pokémon no encontrado.");
            }
            return response.json();
        })
        .then(pokemon => {
            displayPokemonDetails(pokemon);
            loadPokemonStats(pokemonName);
        })
        .catch(error => {
            console.error(error);
            document.getElementById("pokemonDetails").innerHTML = `<p class="error-message">${error.message}</p>`;
            document.getElementById("statsBody").innerHTML = "";
        });
}

/**
 * Muestra los detalles del Pokémon.
 * @param {Object} pokemon - Datos del Pokémon.
 */
function displayPokemonDetails(pokemon) {
    const detailsContainer = document.getElementById("pokemonDetails");
    detailsContainer.innerHTML = `
        <div class="pokemon-card">
            <img src="${pokemon.sprites?.frontDefault}" alt="${pokemon.name}" class="pokemon-image">
            <h2>${pokemon.name.toUpperCase()}</h2>
            <p><strong>Id:</strong> ${pokemon.id}</p>
            <p><strong>Altura:</strong> ${pokemon.height} dm</p>
            <p><strong>Peso:</strong> ${pokemon.weight} hg</p>
            <p><strong>Orden:</strong> ${pokemon.orderIndex}</p>
            <p><strong>Experiencia base:</strong> ${pokemon.baseExperience}</p>
            
            <div class="pokemon-info">
                <h4>Tipos</h4>
                <div class="pokemon-types">
                    ${pokemon.types.map(type => `<span class="type-label">${type.name.toUpperCase()}</span>`).join("")}
                </div>

                <h4 style="margin-top: 10px">Habilidades</h4>
                <div class="pokemon-types" style="background-color: #1f1f1f; padding: 5px 10px">
                    ${pokemon.abilities.map(ability => `<span class="type-label">${ability.name.toUpperCase()}</span>`).join("")}
                </div>

            </div>
        </div>
    `;
}

/**
 * Obtiene y muestra las estadísticas del Pokémon.
 * @param {string} pokemonName - Nombre del Pokémon.
 */
function loadPokemonStats(pokemonName) {
    fetch(`${statsUrl}/${pokemonName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No se encontraron estadísticas.");
            }
            return response.json();
        })
        .then(stats => displayStats(stats, pokemonName))
        .catch(error => {
            console.error(error);
            document.getElementById("statsBody").innerHTML = `<tr><td colspan="2" class="error-message">${error.message}</td></tr>`;
        });
}

/**
 * Muestra las estadísticas en la tabla.
 * @param {Array} stats - Lista de estadísticas del Pokémon.
 * @param {string} pokemonName - Nombre del Pokémon.
 */
function displayStats(stats, pokemonName) {
    const title = document.getElementById("statsTitle");
    title.innerText = `Estadísticas de ${pokemonName.toUpperCase()}`;

    const tableBody = document.getElementById("statsBody");
    tableBody.innerHTML = "";

    if (stats.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="2">No se encontraron estadísticas.</td></tr>`;
        return;
    }

    stats.forEach(stat => {
        const row = document.createElement("tr");
        row.innerHTML = `<td>${stat.name}</td><td>${stat.baseStat}</td>`;
        tableBody.appendChild(row);
    });
}

// Asignar evento al botón de búsqueda
document.getElementById("searchBtn").addEventListener("click", searchPokemon);
