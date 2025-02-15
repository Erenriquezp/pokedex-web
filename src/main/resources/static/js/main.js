/**
 * Carga dinámicamente un archivo HTML en un contenedor específico.
 * @param {string} file - Ruta del archivo HTML.
 * @param {string} containerId - ID del contenedor donde se insertará el contenido.
 */
function loadComponent(file, containerId) {
    fetch(file)
        .then(response => response.text())
        .then(html => {
            document.getElementById(containerId).innerHTML = html;
        })
        .catch(error => console.error(`Error cargando ${file}:`, error));
}

// Cargar Navbar y Footer en todas las vistas
document.addEventListener("DOMContentLoaded", () => {
    loadComponent("/components/navbar.html", "navbar-container");
    loadComponent("/components/footer.html", "footer-container");
});

function toggleMenu() {
    document.querySelector(".nav-links").classList.toggle("active");
}
