# 📌 README – Ejemplo de patrón Singleton en C++

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**  
- **Suasaca Pacompia Alvaro Gustavo**  
- **Valdiviezo Tovar Alexander**

---

Este ejercicio demuestra cómo implementar el **patrón de diseño Singleton** en C++ mediante una clase llamada `Configuracion`. El objetivo del patrón Singleton es **asegurar que solo exista una única instancia** de una clase durante toda la ejecución del programa.

---

## 🧩 ¿Qué hace este programa?

- Define una clase `Configuracion` con dos parámetros:
  - **Idioma** (valor por defecto: `es-PE`)
  - **Zona horaria** (valor por defecto: `America/Lima`)
- Utiliza el patrón Singleton para asegurar que solo exista **una instancia global** de esta clase.
- En `main()`, se solicitan dos punteros (`c1` y `c2`) hacia esa única instancia.
- Se demuestra que ambos punteros representan **el mismo objeto**, mostrando:
  - Los valores configurados  
  - Las direcciones de memoria

---

## 📐 Funcionamiento del patrón Singleton

### ✔️ Constructor privado  
Evita la creación de instancias fuera de la clase.

```cpp
Configuracion() : idioma("es-PE"), zonaHoraria("America/Lima") {}
