# 📘 Cuestionario – Patrón Singleton

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**
- **Suasaca Pacompia Alvaro Gustavo**
- **Valdiviezo Tovar Alexander**

---

## ❓ Cuestionario sobre el patrón Singleton

### **1. ¿Qué desventajas tiene el patrón Singleton en pruebas unitarias?**
El patrón Singleton dificulta las pruebas unitarias porque mantiene un estado global que persiste durante toda la ejecución del programa. Esto hace que una prueba pueda afectar a otra si ambas usan la misma instancia compartida, generando resultados inconsistentes. Además, es difícil reemplazar el Singleton por objetos simulados (*mocks*) durante las pruebas, ya que su ciclo de vida y creación están completamente controlados desde dentro de la clase.

---

### **2. ¿Cuándo no es recomendable usar Singleton?**
No es recomendable usar Singleton cuando los datos deben ser independientes entre módulos, cuando se necesita escalabilidad, o cuando se quiere evitar acoplamiento fuerte entre componentes. Tampoco debe utilizarse cuando el estado global puede generar efectos secundarios difíciles de rastrear o cuando la aplicación requiere múltiples instancias para manejar diferentes contextos (por ejemplo, conexiones a varias bases de datos).

---

### **3. ¿Cómo se diferencia de una clase estática?**
Un Singleton permite crear exactamente una instancia, mientras que una clase estática **no crea instancias**: solo ofrece métodos y atributos accesibles directamente. El Singleton admite *polimorfismo*, interfaces y puede implementarse de forma “perezosa” (*lazy*), mientras que una clase estática no puede heredarse ni implementa flexibilidad orientada a objetos. En resumen, una clase estática es más limitada, mientras que un Singleton sigue siendo un objeto real, solo que único.
