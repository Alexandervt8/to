#include <iostream>
#include <string>

class ConexionBD {
private:
    static ConexionBD* instancia;
    bool conectada;
    std::string nombreBD;

    ConexionBD() : conectada(false), nombreBD("mi_basedatos") {}

    ConexionBD(const ConexionBD&) = delete;
    ConexionBD& operator=(const ConexionBD&) = delete;

public:
    static ConexionBD* getInstancia() {
        if (instancia == nullptr) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    void conectar() {
        if (!conectada) {
            std::cout << "Conectando a la BD: " << nombreBD << "...\n";
            conectada = true;
            std::cout << "Conexion establecida.\n";
        } else {
            std::cout << "Ya existe una conexion activa.\n";
        }
    }

    void desconectar() {
        if (conectada) {
            std::cout << "Desconectando de la BD...\n";
            conectada = false;
            std::cout << "Conexion cerrada.\n";
        } else {
            std::cout << "No hay conexion activa para cerrar.\n";
        }
    }

    void estado() const {
        std::cout << "Estado de la conexion: "
                  << (conectada ? "Conectada" : "Desconectada") << "\n";
    }
};

ConexionBD* ConexionBD::instancia = nullptr;

int main() {
    ConexionBD* c1 = ConexionBD::getInstancia();
    ConexionBD* c2 = ConexionBD::getInstancia();

    c1->estado();
    c1->conectar();
    c2->estado(); 

    std::cout << "Direcciones de memoria:\n";
    std::cout << "c1: " << c1 << "\n";
    std::cout << "c2: " << c2 << "\n";

    c2->desconectar();
    c1->estado();

    return 0;
}
