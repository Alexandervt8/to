# 📌 README – Patrón Singleton aplicado a una conexión de Base de Datos en C++

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**  
- **Suasaca Pacompia Alvaro Gustavo**  
- **Valdiviezo Tovar Alexander**

---

Este ejercicio implementa una simulación de conexión a una Base de Datos utilizando el **patrón de diseño Singleton**. La clase `ConexionBD` controla la creación, gestión y estado de la conexión. Gracias al Singleton, la aplicación garantiza que solo exista **una única instancia de la conexión**, evitando múltiples accesos conflictivos a la base de datos. La clase maneja acciones como conectar, desconectar y mostrar el estado actual de la conexión.

En el `main()`, se obtienen dos punteros (`c1` y `c2`) mediante el método `getInstancia()`. Ambos apuntan a la misma instancia de la clase, lo cual se demuestra al imprimir sus direcciones de memoria. Cualquier acción hecha desde uno de ellos afecta automáticamente al otro, validando que solo existe una instancia global y coherente de la conexión a la base de datos durante toda la ejecución del programa.
