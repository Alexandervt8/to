#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <sstream>

// =============== Persona (Base) ===============
class Persona {
private:
    std::string nombre;
    int edad;

public:
    // Constructores
    Persona() : nombre(""), edad(0) {}
    Persona(const std::string& nombre, int edad) : nombre(nombre), edad(edad) {}

    // Getters / Setters estilo JavaBeans
    std::string getNombre() const { return nombre; }
    void setNombre(const std::string& n) { nombre = n; }

    int getEdad() const { return edad; }
    void setEdad(int e) { edad = e; }

    // toString
    virtual std::string toString() const {
        std::ostringstream oss;
        oss << "Persona{nombre=" << nombre << ", edad=" << edad << "}";
        return oss.str();
    }

    // Destructor virtual para herencia
    virtual ~Persona() = default;
};

// =============== Profesor (Hereda de Persona) ===============
class Profesor : public Persona {
private:
    std::string especialidad;

public:
    Profesor() : Persona(), especialidad("") {}
    Profesor(const std::string& nombre, int edad, const std::string& especialidad)
        : Persona(nombre, edad), especialidad(especialidad) {}

    std::string getEspecialidad() const { return especialidad; }
    void setEspecialidad(const std::string& esp) { especialidad = esp; }

    std::string toString() const override {
        std::ostringstream oss;
        oss << "Profesor{nombre=" << getNombre()
            << ", edad=" << getEdad()
            << ", especialidad=" << especialidad << "}";
        return oss.str();
    }
};

// =============== Estudiante (Hereda de Persona) ===============
class Estudiante : public Persona {
private:
    std::string matricula; // código o número de matrícula

public:
    Estudiante() : Persona(), matricula("") {}
    Estudiante(const std::string& nombre, int edad, const std::string& matricula)
        : Persona(nombre, edad), matricula(matricula) {}

    std::string getMatricula() const { return matricula; }
    void setMatricula(const std::string& m) { matricula = m; }

    std::string toString() const override {
        std::ostringstream oss;
        oss << "Estudiante{nombre=" << getNombre()
            << ", edad=" << getEdad()
            << ", matricula=" << matricula << "}";
        return oss.str();
    }
};

// =============== Horario (Parte de Curso) ===============
class Horario {
private:
    std::string dia;
    std::string horaInicio;
    std::string horaFin;

public:
    Horario() : dia(""), horaInicio(""), horaFin("") {}
    Horario(const std::string& dia, const std::string& inicio, const std::string& fin)
        : dia(dia), horaInicio(inicio), horaFin(fin) {}

    std::string getDia() const { return dia; }
    void setDia(const std::string& d) { dia = d; }

    std::string getHoraInicio() const { return horaInicio; }
    void setHoraInicio(const std::string& hi) { horaInicio = hi; }

    std::string getHoraFin() const { return horaFin; }
    void setHoraFin(const std::string& hf) { horaFin = hf; }

    std::string toString() const {
        std::ostringstream oss;
        oss << "Horario{dia=" << dia
            << ", inicio=" << horaInicio
            << ", fin=" << horaFin << "}";
        return oss.str();
    }
};

// =============== Curso (Compuesto por Horario) ===============
class Curso {
private:
    std::string nombre;
    Horario horario; // Composición: el horario pertenece al curso

public:
    Curso() : nombre(""), horario() {}
    Curso(const std::string& nombre, const Horario& horario)
        : nombre(nombre), horario(horario) {}

    std::string getNombre() const { return nombre; }
    void setNombre(const std::string& n) { nombre = n; }

    Horario getHorario() const { return horario; }
    void setHorario(const Horario& h) { horario = h; }

    std::string toString() const {
        std::ostringstream oss;
        oss << "Curso{nombre=" << nombre
            << ", " << horario.toString() << "}";
        return oss.str();
    }
};

// =============== Universidad (Agrega Cursos existentes) ===============
class Universidad {
private:
    std::string nombre;
    // Agregación: guarda referencias compartidas a cursos que pueden existir fuera
    std::vector<std::shared_ptr<Curso>> cursos;

public:
    Universidad() : nombre("") {}
    explicit Universidad(const std::string& nombre) : nombre(nombre) {}

    std::string getNombre() const { return nombre; }
    void setNombre(const std::string& n) { nombre = n; }

    void agregarCurso(const std::shared_ptr<Curso>& curso) {
        cursos.push_back(curso);
    }

    const std::vector<std::shared_ptr<Curso>>& getCursos() const { return cursos; }

    std::string toString() const {
        std::ostringstream oss;
        oss << "Universidad{nombre=" << nombre << ", cursos=[";
        for (size_t i = 0; i < cursos.size(); ++i) {
            oss << cursos[i]->getNombre();
            if (i + 1 < cursos.size()) oss << ", ";
        }
        oss << "]}";
        return oss.str();
    }
};

// =============== Reporte (Depende de Estudiante) ===============
class Reporte {
public:
    // Dependencia: usa Estudiante temporalmente para generar un informe
    std::string generar(const Estudiante& est) const {
        std::ostringstream oss;
        oss << "=== REPORTE DE ESTUDIANTE ===\n";
        oss << "Nombre: " << est.getNombre() << "\n";
        oss << "Edad: " << est.getEdad() << "\n";
        oss << "Matrícula: " << est.getMatricula() << "\n";
        oss << "Resumen: " << est.toString() << "\n";
        oss << "=============================\n";
        return oss.str();
    }
};

// =============== Programa Principal ===============
int main() {
    // 2 Profesores
    Profesor p1("Ana Torres", 45, "Programación");
    Profesor p2("Luis Gómez", 50, "Matemática");

    // 3 Estudiantes
    Estudiante e1("Carla Pérez", 20, "2025-001");
    Estudiante e2("Jorge Ramírez", 22, "2025-002");
    Estudiante e3("María López", 19, "2025-003");

    // 2 Cursos (cada uno con su Horario) - Composición
    Horario h1("Lunes", "08:00", "10:00");
    Horario h2("Miércoles", "10:00", "12:00");

    auto c1 = std::make_shared<Curso>("Estructuras de Datos", h1);
    auto c2 = std::make_shared<Curso>("Cálculo II", h2);

    // Universidad (Agregación de cursos)
    Universidad uni("Universidad Nacional de Arequipa");
    uni.agregarCurso(c1);
    uni.agregarCurso(c2);

    // Mostrar entidades
    std::cout << p1.toString() << "\n";
    std::cout << p2.toString() << "\n\n";

    std::cout << e1.toString() << "\n";
    std::cout << e2.toString() << "\n";
    std::cout << e3.toString() << "\n\n";

    std::cout << c1->toString() << "\n";
    std::cout << c2->toString() << "\n\n";

    std::cout << uni.toString() << "\n\n";

    // Dependencia: generar un reporte temporal de un estudiante
    Reporte rep;
    std::cout << rep.generar(e2) << std::endl;

    return 0;
}
