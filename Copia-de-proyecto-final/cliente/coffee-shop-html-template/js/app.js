// Variable global para el carrito
let carrito = [];

document.addEventListener("DOMContentLoaded", function() {
    
    // Cargar productos si estamos en el menú
    if(document.getElementById('lista-calientes')) {
        cargarProductos();
    }

    // Activar formulario de registro si estamos en esa página
    const formCliente = document.getElementById('form-cliente');
    if(formCliente) {
        formCliente.addEventListener('submit', function(e) {
            e.preventDefault();
            registrarCliente();
        });
    }
});

function cargarProductos() {
    fetch('/api/productos')
    .then(response => response.json())
    .then(productos => {
        const divCalientes = document.getElementById('lista-calientes');
        const divFrios = document.getElementById('lista-frios');
        divCalientes.innerHTML = "";
        divFrios.innerHTML = "";

        productos.forEach(prod => {
            // Pasamos el objeto producto completo como string al botón
            const prodString = encodeURIComponent(JSON.stringify(prod));

            const html = `
            <div class="row align-items-center mb-5">
                <div class="col-4 col-sm-3">
                    <img class="w-100 rounded-circle mb-3 mb-sm-0" src="img/menu-1.jpg" alt="">
                    <h5 class="menu-price">S/ ${prod.precio.toFixed(2)}</h5>
                </div>
                <div class="col-8 col-sm-9">
                    <h4>${prod.nombre}</h4>
                    <p class="m-0 text-muted" style="font-size: 0.9em;">Delicioso y fresco</p>
                    <div class="mt-2">
                        <button class="btn btn-sm btn-outline-primary" onclick="agregarAlCarrito('${prodString}')">
                            <i class="fa fa-plus"></i> Añadir al Pedido
                        </button>
                    </div>
                </div>
            </div>
            `;

            if (prod.categoriaId === 1) {
                divCalientes.innerHTML += html;
            } else {
                divFrios.innerHTML += html;
            }
        });
    })
    .catch(err => console.error(err));
}

function agregarAlCarrito(prodEncoded) {
    const prod = JSON.parse(decodeURIComponent(prodEncoded));
    
    // Verificar si ya existe en el carrito
    const existe = carrito.find(item => item.id === prod.id);
    
    if (existe) {
        existe.cantidad++;
    } else {
        carrito.push({
            id: prod.id,
            nombre: prod.nombre,
            precio: prod.precio,
            cantidad: 1
        });
    }
    actualizarCarritoUI();
    // Efecto visual simple
    alert("¡" + prod.nombre + " añadido al carrito!");
}

function actualizarCarritoUI() {
    const cartCount = document.getElementById('cart-count');
    const cartItemsDiv = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');
    
    // Actualizar contador flotante
    const totalItems = carrito.reduce((sum, item) => sum + item.cantidad, 0);
    cartCount.innerText = totalItems;

    // Actualizar lista en el modal
    if (carrito.length === 0) {
        cartItemsDiv.innerHTML = "<p class='text-muted text-center'>Tu carrito está vacío.</p>";
        cartTotal.innerText = "S/ 0.00";
        return;
    }

    let html = "<ul class='list-group'>";
    let totalPrecio = 0;

    carrito.forEach((item, index) => {
        const subtotal = item.precio * item.cantidad;
        totalPrecio += subtotal;
        
        html += `
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <div>
                <b>${item.nombre}</b> <br>
                <small>S/ ${item.precio} x ${item.cantidad}</small>
            </div>
            <div class="d-flex align-items-center">
                <span class="badge badge-primary badge-pill mr-2">S/ ${subtotal.toFixed(2)}</span>
                <button class="btn btn-sm btn-danger" onclick="eliminarDelCarrito(${index})">&times;</button>
            </div>
        </li>
        `;
    });
    html += "</ul>";
    
    cartItemsDiv.innerHTML = html;
    cartTotal.innerText = "S/ " + totalPrecio.toFixed(2);
}

function eliminarDelCarrito(index) {
    carrito.splice(index, 1);
    actualizarCarritoUI();
}

function finalizarPedido() {
    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const clienteId = document.getElementById('cliente-id-input').value;
    if (!clienteId) {
        alert("Por favor, ingresa tu ID de cliente para continuar.");
        return;
    }

    // Calcular total final
    const total = carrito.reduce((sum, item) => sum + (item.precio * item.cantidad), 0);

    // Estructura de datos para el servidor
    const pedidoData = {
        clienteId: parseInt(clienteId),
        total: total,
        items: carrito.map(item => ({
            productoId: item.id,
            cantidad: item.cantidad,
            precio: item.precio
        }))
    };

    // Enviar al Backend
    fetch('/api/realizar-pedido', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedidoData)
    })
    .then(response => response.text())
    .then(msg => {
        alert("Respuesta del Servidor: " + msg);
        if (msg.includes("correctamente")) {
            carrito = []; // Limpiar carrito
            actualizarCarritoUI();
            $('#cartModal').modal('hide'); // Cerrar modal (jQuery)
            document.getElementById('cliente-id-input').value = "";
        }
    })
    .catch(err => {
        alert("Error al procesar pedido: " + err);
    });
}

function registrarCliente() {
    const data = {
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value
    };

    fetch('/api/crear-cliente', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => response.text())
    .then(msg => {
        document.getElementById('mensaje-respuesta').innerText = msg;
        document.getElementById('form-cliente').reset();
    })
    .catch(err => console.error(err));
}