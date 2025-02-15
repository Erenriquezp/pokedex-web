document.addEventListener("DOMContentLoaded", () => {
    const themeToggle = document.getElementById("theme-toggle");

    console.log("DOM cargado");
    // Cargar el tema guardado en localStorage
    if (localStorage.getItem("theme") === "light") {
        document.body.classList.add("light-theme");
    }

    // Alternar entre temas
    themeToggle.addEventListener("click", () => {
        document.body.classList.toggle("light-theme");
        localStorage.setItem("theme", document.body.classList.contains("light-theme") ? "light" : "dark");
    });
});
