# 📌 README – Implementación del patrón Singleton en un Logger en C++

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**  
- **Suasaca Pacompia Alvaro Gustavo**  
- **Valdiviezo Tovar Alexander**

---

Este ejercicio implementa un **sistema de registro de eventos (Logger)** utilizando el **patrón de diseño Singleton**. El Logger se encarga de escribir mensajes en un archivo llamado `bitacora.log`, agregando la fecha y hora actual a cada entrada. Gracias al patrón Singleton, todo el programa utiliza la **misma instancia** del Logger, asegurando que todos los mensajes se registren de manera centralizada.

El código incluye varios módulos (`moduloRed`, `moduloUI`, `moduloNegocio`) que envían mensajes al Logger. En el `main`, se crean dos punteros (`l1` y `l2`) usando `getInstancia()`, y al imprimir sus direcciones de memoria se evidencia que ambos apuntan a la misma instancia, lo cual confirma que el patrón Singleton está correctamente implementado.
