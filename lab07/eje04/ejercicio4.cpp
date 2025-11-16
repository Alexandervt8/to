#include <iostream>
#include <string>

class ControlJuego {
private:
    static ControlJuego* instancia;
    int nivel;
    int puntaje;
    int vidas;

    ControlJuego() : nivel(1), puntaje(0), vidas(3) {}
    ControlJuego(const ControlJuego&) = delete;
    ControlJuego& operator=(const ControlJuego&) = delete;

public:
    static ControlJuego* getInstancia() {
        if (instancia == nullptr) {
            instancia = new ControlJuego();
        }
        return instancia;
    }

    void aumentarPuntaje(int p) { puntaje += p; }
    void perderVida() { if (vidas > 0) vidas--; }
    void siguienteNivel() { nivel++; }

    void mostrarEstado() const {
        std::cout << "=== Estado del Juego ===\n";
        std::cout << "Nivel: " << nivel << "\n";
        std::cout << "Puntaje: " << puntaje << "\n";
        std::cout << "Vidas: " << vidas << "\n";
        std::cout << "========================\n";
    }
};


ControlJuego* ControlJuego::instancia = nullptr;


// ---------------- MODULOS DEL JUEGO ----------------

void moduloJugador() {
    ControlJuego::getInstancia()->aumentarPuntaje(50);
    std::cout << "[Jugador] Gana 50 puntos.\n";
}

void moduloEnemigo() {
    ControlJuego::getInstancia()->perderVida();
    std::cout << "[Enemigo] El jugador pierde una vida.\n";
}

void moduloInterfaz() {
    std::cout << "[UI] Mostrando estado en pantalla...\n";
    ControlJuego::getInstancia()->mostrarEstado();
}


// ---------------- MAIN ----------------

int main() {
    ControlJuego* c1 = ControlJuego::getInstancia();
    ControlJuego* c2 = ControlJuego::getInstancia();

    std::cout << "Instancia c1: " << c1 << "\n";
    std::cout << "Instancia c2: " << c2 << "\n\n";

    moduloJugador();
    moduloInterfaz();

    moduloEnemigo();
    moduloInterfaz();

    std::cout << "[Sistema] Pasando al siguiente nivel...\n";
    c1->siguienteNivel();
    moduloInterfaz();

    return 0;
}
