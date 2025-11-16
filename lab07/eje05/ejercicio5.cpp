#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <ctime>
#include <iomanip>
#include <mutex>
#include <thread>

class Logger {
private:
    static Logger* instancia;
    static std::mutex mtx; 
    std::string nombreArchivo;
    Logger() : nombreArchivo("bitacora_threadsafe.log") {}
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
            std::lock_guard<std::mutex> lock(mtx);
            if (instancia == nullptr) {      
                instancia = new Logger();
            }
        }
        return instancia;
    }

    void log(const std::string& mensaje) {
        std::lock_guard<std::mutex> lock(mtx);  
        std::ofstream archivo(nombreArchivo, std::ios::app);
        if (archivo.is_open()) {
            archivo << "[" << obtenerHoraActual() << "] " << mensaje << "\n";
        }
    }
};

Logger* Logger::instancia = nullptr;
std::mutex Logger::mtx;
// --- Funcion usada por multiples hilos ---
void escribirDesdeHilo(const std::string& nombreHilo) {
    for (int i = 0; i < 5; i++) {
        Logger::getInstancia()->log(nombreHilo + " escribiendo mensaje " + std::to_string(i));
    }
}

int main() {
    std::thread t1(escribirDesdeHilo, "Hilo 1");
    std::thread t2(escribirDesdeHilo, "Hilo 2");
    std::thread t3(escribirDesdeHilo, "Hilo 3");
    t1.join();
    t2.join();
    t3.join();
    std::cout << "Mensajes escritos en bitacora_threadsafe.log\n";
    return 0;
}
