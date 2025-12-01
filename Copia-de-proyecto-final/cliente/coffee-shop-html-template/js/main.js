(function ($) {
    "use strict";
    
    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }
        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });
    

    // Date and time picker
    $('.date').datetimepicker({
        format: 'L'
    });
    $('.time').datetimepicker({
        format: 'LT'
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        margin: 30,
        dots: true,
        loop: true,
        center: true,
        responsive: {
            0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });

    document.addEventListener("DOMContentLoaded", function() {
    // Verificar si estamos en la página del menú
    if(document.getElementById('menu-container-hot')) {
        fetchProducts();
    }
});

function fetchProducts() {
    // Llama a la API Java que creamos en el paso 2
    fetch('/api/productos')
        .then(response => response.json())
        .then(data => {
            renderMenu(data);
        })
        .catch(error => console.error('Error cargando productos:', error));
}

function renderMenu(productos) {
    const containerHot = document.getElementById('menu-container-hot');
    const containerCold = document.getElementById('menu-container-cold');

    productos.forEach((prod, index) => {
        // Creamos el HTML dinámico basado en tu template
        const htmlItem = `
            <div class="row align-items-center mb-5">
                <div class="col-4 col-sm-3">
                    <img class="w-100 rounded-circle mb-3 mb-sm-0" src="${prod.img || 'img/menu-1.jpg'}" alt="">
                    <h5 class="menu-price">S/ ${prod.precio}</h5>
                </div>
                <div class="col-8 col-sm-9">
                    <h4>${prod.nombre}</h4>
                    <p class="m-0">${prod.desc || 'Delicioso café recién hecho'}</p>
                    <button class="btn btn-sm btn-primary mt-2" onclick="agregarAlCarrito(${index})">Añadir</button>
                </div>
            </div>
        `;

        // Distribuir en dos columnas (simple lógica par/impar para el ejemplo)
        if (index % 2 === 0) {
            containerHot.innerHTML += htmlItem;
        } else {
            containerCold.innerHTML += htmlItem;
        }
    });
}

function agregarAlCarrito(index) {
    alert("Producto añadido (Lógica de carrito pendiente)");
    // Aquí guardarías en localStorage o enviarías al servidor
}
    
})(jQuery);

