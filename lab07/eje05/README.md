# 📌 README – Logger Singleton Thread-Safe

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**
- **Suasaca Pacompia Alvaro Gustavo**
- **Valdiviezo Tovar Alexander**

---

## 🧩 Descripción general

Este ejercicio implementa un Logger basado en el patrón Singleton, pero modificado para ser **seguro en entornos multihilo (thread-safe)**. En aplicaciones concurrentes, dos o más hilos podrían intentar crear la instancia del Singleton al mismo tiempo, generando múltiples objetos y rompiendo el patrón. Para evitar esto, se usa la técnica **Double-Checked Locking**, combinada con un `std::mutex`.

---

## 🔧 Funciones principales del sistema

### **1. getInstancia() – Double Checked Locking**
Evita la creación simultánea de múltiples instancias.  
Primero revisa si la instancia existe; si no, bloquea el acceso con un mutex y vuelve a verificar antes de crearla.

### **2. log()**
Escribe mensajes en el archivo de bitácora.  
Cada escritura se protege con un `std::lock_guard<std::mutex>` para evitar que dos hilos escriban al mismo tiempo y generen líneas corruptas.

### **3. escribirDesdeHilo()**
Simula el comportamiento de múltiples hilos escribiendo en el Logger al mismo tiempo, demostrando que el sistema mantiene la coherencia del archivo.

---

## 🚀 Simulación multihilo
En el `main()`, tres hilos realizan múltiples llamadas al Logger de manera simultánea.  
El archivo de salida muestra que todas las líneas se registraron correctamente y sin sobreescritura, validando el funcionamiento thread-safe.

---

## 🧠 Conclusión
Este ejercicio demuestra cómo convertir un Singleton tradicional en un Singleton seguro para hilos usando **doble verificación y mutex**. Es una implementación fundamental en aplicaciones concurrentes donde múltiples procesos deben compartir un único recurso global como un manejador de logs.
