# ☕ Sistema de Cafetería Híbrida — Cliente / Servidor (Java + Sockets)

Este proyecto implementa un sistema de **Cafetería Híbrida** con arquitectura **Cliente–Servidor**, desarrollado en Java utilizando **Sockets TCP**, **MySQL** y el patrón **MVC**.

El sistema permite:

- Registrar pedidos desde estaciones cliente.
- Registrar nuevos clientes.
- Guardar los pedidos con sus ítems en la base de datos.
- Manejar múltiples clientes conectados a un servidor central.

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

## 📂 Estructura del Proyecto

```
pedidos/
 ├── servidor/
 │     ├── app/
 │     │     ├── MainServer.java
 │     │     ├── ClientHandler.java
 │     ├── controller/
 │     │     └── PedidoController.java
 │     ├── dao/
 │     │     ├── CategoriaDAO.java
 │     │     ├── ClienteDAO.java
 │     │     ├── ProductoDAO.java
 │     │     └── PedidoDAO.java
 │     ├── db/
 │     │     └── DB.java
 │     └── model/
 │           ├── Cliente.java
 │           ├── Categoria.java
 │           ├── Producto.java
 │           ├── Pedido.java
 │           └── PedidoItem.java
 │
 ├── cliente/
 │     ├── app/
 │     │     └── MainClient.java
 │     ├── controller/
 │     │     └── PedidoClienteController.java
 │     ├── view/
 │     │     ├── MenuPrincipal.java
 │     │     ├── VistaPedido.java
 │     │     └── VistaCliente.java
 │     └── model/
 │           ├── Cliente.java
 │           ├── Categoria.java
 │           ├── Producto.java
 │           ├── Pedido.java
 │           └── PedidoItem.java
 │
 ├── lib/
 │     └── mysql-connector-j-9.4.0.jar
```

## 🛢 Base de Datos (MySQL)

Se utiliza la BD:

```
cafe_db
```

### Tablas requeridas:

```sql
CREATE TABLE clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150),
  email VARCHAR(150),
  telefono VARCHAR(50),
  activo TINYINT DEFAULT 1,
  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);
...
```

## ▶️ Cómo COMPILAR y EJECUTAR

### Servidor

```
cd servidor
javac -cp ".;..\lib\mysql-connector-j-9.4.0.jar" -d out app\*.java controller\*.java dao\*.java db\*.java model\*.java
java -cp "out;..\lib\mysql-connector-j-9.4.0.jar" com.cafe.app.MainServer
```

### Cliente

```
cd cliente
javac -cp ".;" -d out app\*.java controller\*.java view\*.java model\*.java
java -cp "out" com.cafe.app.MainClient
```

## Autor

Proyecto desarrollado para arquitectura Cliente/Servidor en Java.
