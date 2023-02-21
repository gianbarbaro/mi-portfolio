//efecto de scroll para el navbar
window.addEventListener("scroll", function(){
    let header = document.querySelector("header");
    header.classList.toggle("active", window.scrollY > 0);
})
