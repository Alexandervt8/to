# 📌 README – Control Global del Juego con Singleton

## 👥 Integrantes del equipo
- **Anco Aymara Jean Pierre**  
- **Suasaca Pacompia Alvaro Gustavo**  
- **Valdiviezo Tovar Alexander**

---

## 🎮 Descripción general

Este ejercicio implementa la clase `ControlJuego`, encargada de manejar el estado global de un videojuego utilizando el **patrón Singleton**. Gracias a este patrón, todos los módulos del juego (jugador, enemigos y la interfaz) acceden y modifican la misma instancia del estado, garantizando coherencia durante toda la ejecución.

---

## 🧩 Funciones principales

### **1. getInstancia()**
Garantiza que solo exista una única instancia de `ControlJuego`.  
Cada vez que un módulo necesita acceder al estado del juego, llama a esta función, que devuelve siempre el mismo objeto.

### **2. aumentarPuntaje(int puntos)**
Incrementa el puntaje global del jugador.  
Se usa cuando el jugador realiza acciones que le otorgan puntos, como derrotar enemigos o recoger objetos.

### **3. perderVida()**
Reduce en uno el número de vidas.  
Se activa cuando el jugador recibe daño o comete un error dentro del juego.  
Si las vidas llegan a cero, el juego podría pasar a un estado de "game over".

### **4. siguienteNivel()**
Avanza al siguiente nivel del juego.  
Permite simular progresión en la partida cuando el jugador supera un escenario o jefe.

### **5. mostrarEstado()**
Imprime en pantalla el estado global del juego:  
- Nivel actual  
- Puntaje acumulado  
- Vidas restantes  

Es utilizada principalmente por el módulo de interfaz para mostrar información en tiempo real.

---

## 🕹️ Simulación en el programa

El programa principal utiliza tres módulos ficticios:  
- **Jugador:** gana puntos.  
- **Enemigo:** quita vidas.  
- **Interfaz:** muestra el estado del juego.

Todos acceden al mismo `ControlJuego`, demostrando que el patrón Singleton funciona correctamente al mantener un único estado global.

---

## 🧠 Conclusión

Este ejercicio demuestra cómo el patrón Singleton puede controlar el estado de un videojuego de forma centralizada, evitando inconsistencias entre módulos y garantizando que toda la lógica del juego utilice la misma información. Es un ejemplo clave de cómo manejar datos globales en sistemas interactivos.
