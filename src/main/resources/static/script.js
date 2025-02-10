const apiUrl = "http://localhost:8080/api/pokemon"; // URL de la API
const loadDataUrl = "http://localhost:8080/api/pokemon/load"; // Endpoint para cargar datos
let currentPage = 0;
const pageSize = 18;

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
            <img src="${pokemon.spriteUrl}" alt="${pokemon.name}">
            <h3>${pokemon.name.toUpperCase()}</h3>
        `;
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
 * Busca un Pokémon por nombre.
 */
function searchPokemon() {
    const name = document.getElementById("searchInput").value.trim().toLowerCase();
    if (!name) return;

    fetch(`${apiUrl}/${name}`)
        .then(response => {
            if (!response.ok) throw new Error("Pokémon no encontrado");
            return response.json();
        })
        .then(pokemon => displayPokemon([pokemon]))
        .catch(error => {
            console.error(error);
            document.getElementById("pokemonContainer").innerHTML = "<p>Pokémon no encontrado.</p>";
        });
}

/**
 * Carga todos los Pokémon desde la API externa y los guarda en la base de datos.
 */
function loadAllPokemon() {
    const loadButton = document.getElementById("loadDataBtn");
    loadButton.disabled = true;
    loadButton.innerText = "Cargando...";

    fetch(loadDataUrl, { method: "POST" })
        .then(response => {
            if (!response.ok) throw new Error("Error al cargar los datos");
            return response.text();
        })
        .then(message => {
            console.log(message);
            alert("¡Datos cargados correctamente!");
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

// Asignar eventos a los botones
document.getElementById("loadDataBtn").addEventListener("click", loadAllPokemon);
document.getElementById("prevPage").addEventListener("click", () => changePage(-1));
document.getElementById("nextPage").addEventListener("click", () => changePage(1));

// Cargar Pokémon al iniciar
loadPokemon();
