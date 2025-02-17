const apiUrl = "http://localhost:8080/api/pokemon"; // URL de la API
const loadDataUrl = "http://localhost:8080/api/pokemon/load"; // Endpoint para cargar datos
let currentPage = 0;
const pageSize = 20;

/**
 * Carga y muestra Pokémon en la página actual.
 */
function loadPokemon() {
    fetch(`${apiUrl}?page=${currentPage}&size=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                displayPokemon(data);
                document.getElementById("loadDataBtn").style.display = "none"; // Oculta el botón si ya hay datos
            } else {
                showNoDataMessage();
            }
        })
        .catch(error => console.error("Error fetching Pokémon:", error));
}

/**
 * Muestra los Pokémon en la página.
 * @param {Array} pokemons - Lista de Pokémon.
 */
function displayPokemon(pokemons) {
    const container = document.getElementById("pokemonContainer");
    container.innerHTML = "";

    pokemons.forEach(pokemon => {
        const card = document.createElement("div");
        card.className = "pokemon-card";
        card.innerHTML = `
            <img src="${pokemon.spriteUrl}" alt="${pokemon.name}" class="pokemon-image">
            <h2>${pokemon.name.toUpperCase()}</h2>

            <div class="pokemon-info">
                <h5>Tipos</h5>
                <div class="pokemon-types">
                    ${pokemon.types.map(type => `<span class="type-label">${type.name.toUpperCase()}</span>`).join("")}
                </div>
            </div>
        `;

        // 🔹 Agregar evento de clic para redirigir a la vista de búsqueda con detalles
        card.addEventListener("click", () => {
            window.location.href = `/pages/pokedex.html?name=${pokemon.name}`;
        });

        container.appendChild(card);
    });

    // Actualizar la paginación
    document.getElementById("pageInfo").innerText = `Página ${currentPage + 1}`;
    document.getElementById("prevPage").disabled = currentPage === 0;
    document.getElementById("nextPage").disabled = pokemons.length < pageSize;
}

/**
 * Muestra un mensaje cuando no hay datos en la base de datos.
 */
function showNoDataMessage() {
    const container = document.getElementById("pokemonContainer");
    container.innerHTML = `
        <p>No hay Pokémon en la base de datos.</p>
        <p>Presiona "Cargar Datos de la API" para obtener la información.</p>
    `;
    document.getElementById("loadDataBtn").style.display = "block"; // Muestra el botón si no hay datos
    document.getElementById("prevPage").disabled = true;
    document.getElementById("nextPage").disabled = true;
}

/**
 * Cambia la página (siguiente o anterior).
 * @param {number} direction - Dirección del cambio de página (-1: atrás, 1: adelante).
 */
function changePage(direction) {
    currentPage += direction;
    loadPokemon();
}

/**
 * Carga todos los Pokémon desde la API externa y los guarda en la base de datos.
 * Muestra una barra de progreso mientras carga los datos.
 */
function loadAllPokemon() {
    const loadButton = document.getElementById("loadDataBtn");
    const progressBar = document.getElementById("progressBar");
    const progressContainer = document.getElementById("progressContainer");

    // Mostrar barra de progreso
    progressContainer.style.display = "block";
    progressBar.style.width = "0%";

    loadButton.disabled = true;
    loadButton.innerText = "Cargando...";

    fetch(loadDataUrl, { method: "POST" })
        .then(response => {
            if (!response.ok) throw new Error("Error al cargar los datos");
            return response.text();
        })
        .then(message => {
            console.log(message);
            alert("¡Cargando datos correctamente!");
            let progress = 0;
            const interval = setInterval(() => {
                progress += 3; // Incremento del progreso
                progressBar.style.width = `${progress}%`;

                if (progress >= 300) {
                    clearInterval(interval);
                    progressContainer.style.display = "none";
                }
            }, 400); // Actualiza cada 300ms
            loadPokemon(); // Recargar Pokémon después de la carga
        })
        .catch(error => {
            console.error(error);
            alert("Error al cargar los datos.");
        })
        .finally(() => {
            loadButton.disabled = false;
            loadButton.innerText = "Cargar Datos de la API";
        });
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("loadDataBtn")?.addEventListener("click", loadAllPokemon);
    document.getElementById("prevPage")?.addEventListener("click", () => changePage(-1));
    document.getElementById("nextPage")?.addEventListener("click", () => changePage(1));

    loadPokemon();
});

/**
 * Redirige a la vista de búsqueda con el nombre del Pokémon ingresado.
 */
function handleSearch() {
    const pokemonName = document.getElementById("searchInput").value.trim().toLowerCase();
    if (!pokemonName) {
        alert("Ingrese el nombre de un Pokémon");
        return;
    }
    window.location.href = `/pages/pokedex.html?name=${pokemonName}`;
}

// 🔹 Agregar evento al botón de búsqueda
document.getElementById("searchBtn").addEventListener("click", handleSearch);

