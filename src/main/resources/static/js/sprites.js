const apiUrl = "http://localhost:8080/api/sprites"; // URL de la API de sprites

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const pokemonName = urlParams.get("name");

    if (pokemonName) {
        fetchSprites(pokemonName); // Llama a la función para obtener los sprites
    }
});

/**
 * Obtiene y muestra los sprites del Pokémon.
 * @param {string} pokemonName - Nombre del Pokémon.
 */
function fetchSprites(pokemonName) {
    fetch(`${apiUrl}/${pokemonName.toLowerCase()}`)
        .then(response => {
            if (!response.ok) throw new Error("No se encontraron sprites para este Pokémon.");
            return response.json();
        })
        .then(sprites => displaySprites(sprites, pokemonName))
        .catch(error => {
            console.error(error);
            document.getElementById("spritesContainer").innerHTML = `<p class="error-message">${error.message}</p>`;
        });
}

/**
 * Muestra los sprites obtenidos en la interfaz.
 * @param {Object} sprites - Datos de los sprites.
 * @param {string} pokemonName - Nombre del Pokémon.
 */
function displaySprites(sprites, pokemonName) {
    const container = document.getElementById("spritesContainer");
    container.innerHTML = ""; // Limpiar contenido anterior

    const spriteUrls = [
        { label: "Frente", url: sprites.frontDefault },
        { label: "Espalda", url: sprites.backDefault },
        { label: "Frente Shiny", url: sprites.frontShiny },
        { label: "Espalda Shiny", url: sprites.backShiny },
        { label: "Frente Femenino", url: sprites.frontFemale },
        { label: "Espalda Femenino", url: sprites.backFemale },
        { label: "Frente Shiny Femenino", url: sprites.frontShinyFemale },
        { label: "Espalda Shiny Femenino", url: sprites.backShinyFemale },
        { label: "Dream World - Frente", url: sprites.dreamWorldFront },
        { label: "Dream World - Frente Femenino", url: sprites.dreamWorldFrontFemale },
        { label: "Home - Frente", url: sprites.homeFront },
        { label: "Home - Frente Shiny", url: sprites.homeFrontShiny },
        { label: "Home - Frente Femenino", url: sprites.homeFrontFemale },
        { label: "Home - Frente Shiny Femenino", url: sprites.homeFrontShinyFemale },
        { label: "Artwork Oficial - Frente", url: sprites.officialArtworkFront },
        { label: "Artwork Oficial - Shiny", url: sprites.officialArtworkShiny }
    ];

    let hasSprites = false;

    spriteUrls.forEach(sprite => {
        if (sprite.url && sprite.url.trim() !== "") { // Validar si la URL existe
            const card = document.createElement("div");
            card.className = "pokemon-card";
            card.innerHTML = `
                <img src="${sprite.url}" alt="${sprite.label}" class="pokemon-image">
                <p>${sprite.label}</p>
            `;
            container.appendChild(card);
            hasSprites = true;
        }
    });

    // Mostrar mensaje si no hay sprites disponibles
    if (!hasSprites) {
        container.innerHTML = `<p class="error-message">No hay sprites disponibles para ${pokemonName}.</p>`;
    }
}

// Asignar evento de búsqueda
document.getElementById("searchBtn").addEventListener("click", () => {
    const pokemonName = document.getElementById("searchInput").value.trim().toLowerCase();
    if (pokemonName) {
        fetchSprites(pokemonName);
    }
});
