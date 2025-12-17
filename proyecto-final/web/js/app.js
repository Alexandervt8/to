// ===============================
// Variable global para el carrito
// ===============================
let carrito = [];

document.addEventListener("DOMContentLoaded", function() {

    if (document.getElementById('lista-calientes')) {
        cargarProductos();
    }

    const formCliente = document.getElementById('form-cliente');
    if (formCliente) {
        formCliente.addEventListener('submit', function(e) {
            e.preventDefault();
            registrarCliente();
        });
    }
});

// ===============================
// CARGAR PRODUCTOS
// ===============================
function cargarProductos() {
    fetch('/api/productos')
        .then(response => response.json())
        .then(productos => {

            const divCalientes = document.getElementById('lista-calientes');
            const divFrios = document.getElementById('lista-frios');
            divCalientes.innerHTML = "";
            divFrios.innerHTML = "";

            productos.forEach(prod => {

                // ❗ Mostrar solo productos activos
                if (prod.activo === false) return;

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
                            <button class="btn btn-sm btn-outline-primary"
                                onclick="agregarAlCarrito('${prodString}')">
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

// ===============================
// AGREGAR AL CARRITO
// ===============================
function agregarAlCarrito(prodEncoded) {
    const prod = JSON.parse(decodeURIComponent(prodEncoded));

    const existe = carrito.find(item => item.productoId === prod.id);

    if (existe) {
        existe.cantidad++;
    } else {
        carrito.push({
            productoId: prod.id,
            nombre: prod.nombre,
            cantidad: 1,
            estado: "pendiente" // 🔥 importante para cocina
        });
    }

    actualizarCarritoUI();
    alert("¡" + prod.nombre + " añadido al carrito!");
}

// ===============================
// ACTUALIZAR UI DEL CARRITO
// (solo visual, NO backend)
// ===============================
function actualizarCarritoUI() {

    const cartCount = document.getElementById('cart-count');
    const cartItemsDiv = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');

    const totalItems = carrito.reduce((sum, item) => sum + item.cantidad, 0);
    cartCount.innerText = totalItems;

    if (carrito.length === 0) {
        cartItemsDiv.innerHTML = "<p class='text-muted text-center'>Tu carrito está vacío.</p>";
        cartTotal.innerText = "S/ 0.00";
        return;
    }

    let html = "<ul class='list-group'>";
    let totalPrecio = 0;

    carrito.forEach((item, index) => {

        // ⚠️ precio solo para UI (no se envía)
        const precioMock = 0; // opcional
        const subtotal = precioMock * item.cantidad;

        html += `
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <div>
                <b>${item.nombre}</b> <br>
                <small>Cantidad: ${item.cantidad}</small>
            </div>
            <div>
                <button class="btn btn-sm btn-danger"
                    onclick="eliminarDelCarrito(${index})">&times;</button>
            </div>
        </li>
        `;
    });

    html += "</ul>";

    cartItemsDiv.innerHTML = html;
    cartTotal.innerText = "Calculado en el servidor";
}

// ===============================
// ELIMINAR ITEM
// ===============================
function eliminarDelCarrito(index) {
    carrito.splice(index, 1);
    actualizarCarritoUI();
}

// ===============================
// FINALIZAR PEDIDO
// ===============================
function finalizarPedido() {

    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const clienteId = document.getElementById('cliente-id-input').value;
    if (!clienteId) {
        alert("Por favor, ingresa tu ID de cliente.");
        return;
    }

    const pedidoData = {
        clienteId: parseInt(clienteId),
        tipo: "mesa",
        estado: "abierto",
        items: carrito.map(item => ({
            productoId: item.productoId,
            cantidad: item.cantidad,
            estado: item.estado
        }))
    };

    fetch('/api/realizar-pedido', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedidoData)
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        if (msg.includes("correctamente")) {
            carrito = [];
            actualizarCarritoUI();
            $('#cartModal').modal('hide');
            document.getElementById('cliente-id-input').value = "";
        }
    })
    .catch(err => alert("Error: " + err));
}

// ===============================
// REGISTRAR CLIENTE
// ===============================
function registrarCliente() {

    const data = {
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value
    };

    fetch('/cliente/registrar', {
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
