# ☕ Sistema de Cafetería Híbrida — Cliente / Servidor (Java + Sockets + HTTP + HTML)

Este proyecto implementa un sistema de **Cafetería Híbrida** con arquitectura **Cliente–Servidor**, combinando dos tecnologías:

- **Sockets TCP** para clientes de consola.
- **HTTP + REST** para clientes web.

Usa Java, MySQL y el patrón **MVC**, y permite registrar clientes, pedidos y gestionar productos tanto desde consola como desde páginas HTML servidas por el mismo servidor.

---

## 🚀 Funcionalidades del Sistema

### ✔️ Funciones principales

- Registro de clientes (por consola y vía HTML).
- Registro de pedidos con items.
- Persistencia en MySQL mediante DAOs.
- Manejo de múltiples conexiones por socket (multihilo).
- Servidor HTTP embebido para cargar HTML, CSS y JS.
- API REST para clientes externos (navegador, apps).
- Manejo de errores SQL (incluye correos duplicados).

---

## 🖥 Servidor (Sockets + HTTP + REST)

El servidor combina dos módulos:

### 🔹 1. Servidor TCP (Puerto 5000)
- Manejo de múltiples clientes con hilos.
- Recibe objetos `Cliente` y `Pedido`.
- Inserción de datos en MySQL.
- Uso de `ObjectInputStream` y `ObjectOutputStream`.

### 🔹 2. Servidor HTTP (Puerto 8080)
Implementado con `com.sun.net.httpserver.HttpServer`.

Incluye:

- Servidor web estático que sirve la carpeta:

## 📱 Clientes del Sistema

El sistema cuenta con **dos tipos de clientes independientes**, cada uno usando un tipo distinto de comunicación con el servidor.

Ambos clientes pueden operar simultáneamente contra el mismo servidor híbrido.

### 🔹 1. Cliente por Consola (Socket TCP – Puerto 5000)

Este cliente es una aplicación en Java que funciona directamente desde consola y se comunica con el servidor mediante **Sockets TCP**, enviando objetos Java serializados (`Cliente`, `Pedido`).

**Características:**

- Menú interactivo por consola.
- Registro de nuevos clientes enviando un objeto `Cliente`.
- Generación de pedidos con múltiples ítems.
- Cálculo automático del total del pedido.
- Envío completo del pedido: fecha, estado, método de pago, monto final.
- Comunicación binaria con el servidor:
  - `ObjectInputStream`
  - `ObjectOutputStream`
- Manejo automático de la conexión al puerto **5000**.

**Uso típico:**
1. El usuario abre la aplicación de consola.
2. Selecciona registrar cliente o generar pedido.
3. El sistema envía objetos serializados al servidor.
4. El servidor procesa y responde con mensajes de confirmación.

### 🔹 2. Cliente Web (HTML, CSS, JS – HTTP REST – Puerto 8080)

Este cliente es un frontend accesible desde cualquier navegador dentro de la red local.  
Es servido directamente por el **servidor HTTP embebido en Java**, sin necesidad de Apache, XAMPP u otro servidor externo.

**Características principales:**

- Interfaz gráfica moderna con HTML5, Bootstrap y JavaScript.
- Registro de clientes mediante:

---

## 📂 Estructura del Proyecto

```
pedidos/
 ├── servidor/
 │     ├── app/
 │     │     ├── MainServer.java
 │     │     ├── ClientHandler.java
 │     │     ├── HttpServerApp.java
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
 ├── web/  <-- Carpeta servida por el servidor Java
 │     ├── menu.html
 │     ├── reservation.html
 │     ├── js/
 │     ├── css/
 │     └── img/
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

CREATE TABLE categorias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150)
);

CREATE TABLE productos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150),
  precio DOUBLE,
  categoria_id INT,
  activo TINYINT DEFAULT 1,
  FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cliente_id INT,
  fecha DATETIME,
  estado VARCHAR(20),
  metodo_pago VARCHAR(20),
  total DOUBLE,
  FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE pedido_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT,
  producto_id INT,
  cantidad INT,
  precio_unitario DOUBLE,
  subtotal DOUBLE,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
  FOREIGN KEY (producto_id) REFERENCES productos(id)
);
...
```

## ▶️ Cómo COMPILAR y EJECUTAR

### Servidor

```
cd servidor
javac -cp ".;..\lib\mysql-connector-j-9.4.0.jar;..\lib\gson-2.10.1.jar" ^-d out app\*.java controller\*.java dao\*.java db\*.java model\*.java
java -cp "out;..\lib\mysql-connector-j-9.4.0.jar;..\lib\gson-2.10.1.jar" com.cafe.app.MainServer
```

### Cliente

```
cd cliente
javac -cp ".;" -d out app\*.java controller\*.java view\*.java model\*.java
java -cp "out" com.cafe.app.MainClient
```

### Acceder al sitio web desde cualquier PC:

```
http://192.168.0.7:8080/

```

## Autor

Proyecto desarrollado para arquitectura Cliente/Servidor en Java.
