# ☕ Sistema de Cafetería Híbrida — Cliente / Servidor (Java + Sockets)

Este proyecto implementa un sistema de **Cafetería Híbrida** con arquitectura **Cliente–Servidor**, desarrollado en Java utilizando **Sockets TCP**, **MySQL** y el patrón **MVC**.

El sistema permite:

- Registrar pedidos desde estaciones cliente.
- Registrar nuevos clientes.
- Guardar los pedidos con sus ítems en la base de datos.
- Manejar múltiples clientes conectados a un servidor central.

---

## 📌 Características principales

### 🖥 Servidor
- Conexión a MySQL usando JDBC.
- Manejo de múltiples clientes (multihilo).
- Recepción de objetos `Pedido` y `Cliente`.
- Persistencia de datos mediante DAOs.
- Control de transacciones al registrar pedidos.
- Validación de llaves foráneas.

### 📱 Cliente
- Menú interactivo por consola.
- Opción para generar pedidos.
- Opción para registrar nuevos clientes.
- Comunicación con el servidor mediante objetos serializados.
- Cálculo automático del total del pedido.
- Envío de fecha, método de pago y estado del pedido.

---

## 📂 Estructura del Proyecto

