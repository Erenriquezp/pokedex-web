const apiUrl = "http://localhost:8080/api/sprites"; // URL de la API de sprites

/**
 * Realiza la búsqueda de sprites de un Pokémon.
 */
function searchSprites() {
    const pokemonName = document.getElementById("searchInput").value.trim().toLowerCase();
    if (!pokemonName) return;

    fetch(`${apiUrl}/${pokemonName}`)
        .then(response => {
            if (!response.ok) throw new Error("No se encontraron sprites");
            console.log(response);
            return response.json();
        })
        .then(displaySprites)
        .catch(error => {
            console.error(error);
            document.getElementById("spritesContainer").innerHTML = "<p>Pokémon no encontrado.</p>";
        });
}

/**
 * Muestra los sprites obtenidos en la interfaz.
 */
function displaySprites(sprites) {
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
        { label: "Espalda Shiny Femenino", url: sprites.backShinyFemale }
    ];

    spriteUrls.forEach(sprite => {
        if (sprite.url) {
            const card = document.createElement("div");
            card.className = "pokemon-card";
            card.innerHTML = `
                <img src="${sprite.url}" alt="${sprite.label}">
                <p>${sprite.label}</p>
            `;
            container.appendChild(card);
        }
    });
}

// Asignar evento de búsqueda
document.getElementById("searchBtn").addEventListener("click", searchSprites);
