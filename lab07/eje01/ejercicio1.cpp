#include <iostream>
#include <string>

class Configuracion {
private:
    std::string idioma;
    std::string zonaHoraria;

    static Configuracion* instancia;

    Configuracion() : idioma("es-PE"), zonaHoraria("America/Lima") {}

    Configuracion(const Configuracion&) = delete;
    Configuracion& operator=(const Configuracion&) = delete;

public:
    static Configuracion* getInstancia() {
        if (instancia == nullptr) {
            instancia = new Configuracion();
        }
        return instancia;
    }

    void setIdioma(const std::string& i) { idioma = i; }
    void setZonaHoraria(const std::string& z) { zonaHoraria = z; }

    void mostrar_configuracion() const {
        std::cout << "Idioma: " << idioma << "\n";
        std::cout << "Zona horaria: " << zonaHoraria << "\n";
    }
};

Configuracion* Configuracion::instancia = nullptr;

int main() {
    Configuracion* c1 = Configuracion::getInstancia();
    Configuracion* c2 = Configuracion::getInstancia();

    c1->setIdioma("es-PE");
    c1->setZonaHoraria("America/Lima");

    std::cout << "Configuracion desde c1:\n";
    c1->mostrar_configuracion();

    std::cout << "\nConfiguracion desde c2 :\n";
    c2->mostrar_configuracion();

    std::cout << "\nDirecciones de memoria:\n";
    std::cout << "c1: " << c1 << "\n";
    std::cout << "c2: " << c2 << "\n";

    return 0;
}
