#include <iostream>
#include <string>
using namespace std;
template <typename T>
class Nodo {
    public:
    T dato;
    Nodo<T>* siguiente;
    Nodo(T valor) : dato(valor), siguiente(nullptr) {}
};

template <typename T>
class Lista {
    private:
    Nodo<T>* cabeza;
    public:
    Lista() : cabeza(nullptr) {}
    // Agregar elemento al final
    void agregar(T valor) {
        Nodo<T>* nuevo = new Nodo<T>(valor);
        if (!cabeza) {
            cabeza = nuevo;
        } else {
            Nodo<T>* actual = cabeza;
            while (actual->siguiente) {
                actual = actual->siguiente;
            }
            actual->siguiente = nuevo;
        }
    }
    // Mostrar la lista
    void mostrar() const {
        Nodo<T>* actual = cabeza;
        while (actual) {
            cout << actual->dato << " -> ";
            actual = actual->siguiente;
        }
        cout << "NULL" << endl;
    }
    // Liberar memoria
    ~Lista() {
        Nodo<T>* actual = cabeza;
        while (actual) {
            Nodo<T>* temp = actual;
            actual = actual->siguiente;
            delete temp;
        }
    }
};
int main() {
    // Lista de edades (int)
    Lista<int> edades;
    edades.agregar(18);
    edades.agregar(25);
    edades.agregar(30);

    cout << "Secuencia de edades:" << endl;
    edades.mostrar();
    // Lista de palabras (string)
    Lista<string> palabras;
    palabras.agregar("Hola");
    palabras.agregar("mundo");
    palabras.agregar("Qt");
    cout << "\nSecuencia de palabras:" << endl;
    palabras.mostrar();
    return 0;
}