#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <ctime>
#include <iomanip>

class Logger {
private:
    static Logger* instancia;
    std::string nombreArchivo;

    Logger() : nombreArchivo("bitacora.log") {}

    Logger(const Logger&) = delete;
    Logger& operator=(const Logger&) = delete;

    std::string obtenerHoraActual() {
        using namespace std::chrono;
        auto ahora = system_clock::now();
        std::time_t t = system_clock::to_time_t(ahora);
        std::tm tmLocal = *std::localtime(&t);

        std::ostringstream oss;
        oss << std::put_time(&tmLocal, "%Y-%m-%d %H:%M:%S");
        return oss.str();
    }

public:
    static Logger* getInstancia() {
        if (instancia == nullptr) {
            instancia = new Logger();
        }
        return instancia;
    }

    void log(const std::string& mensaje) {
        std::ofstream archivo(nombreArchivo, std::ios::app);
        if (archivo.is_open()) {
            archivo << "[" << obtenerHoraActual() << "] " << mensaje << "\n";
        }
    }
};

Logger* Logger::instancia = nullptr;

void moduloRed() {
    Logger::getInstancia()->log("Mensaje desde moduloRed");
}

void moduloUI() {
    Logger::getInstancia()->log("Mensaje desde moduloUI");
}

void moduloNegocio() {
    Logger::getInstancia()->log("Mensaje desde moduloNegocio");
}

int main() {
    Logger* l1 = Logger::getInstancia();
    Logger* l2 = Logger::getInstancia();

    l1->log("Inicio del programa");
    moduloRed();
    moduloUI();
    moduloNegocio();
    l2->log("Fin del programa");

    std::cout << "Se han escrito mensajes en bitacora.log\n";
    std::cout << "Logger l1: " << l1 << "\n";
    std::cout << "Logger l2: " << l2 << "\n";

    return 0;
}
