import java.util.ArrayList;
import java.util.List;


class Persona {
    private String nombre;
    private int edad;

    // Constructor
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    // toString
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Edad: " + edad;
    }
}

class Profesor extends Persona {
    public Profesor(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public String toString() {
        return "Profesor -> " + super.toString();
    }
}


class Estudiante extends Persona {
    public Estudiante(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public String toString() {
        return "Estudiante -> " + super.toString();
    }
}


class Horario {
    private String dia;
    private String hora;

    public Horario(String dia, String hora) {
        this.dia = dia;
        this.hora = hora;
    }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    @Override
    public String toString() {
        return "Horario: " + dia + " a las " + hora;
    }
}


class Curso {
    private String nombre;
    private Horario horario; // composición

    public Curso(String nombre, Horario horario) {
        this.nombre = nombre;
        this.horario = horario;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    @Override
    public String toString() {
        return "Curso: " + nombre + " | " + horario.toString();
    }
}


class Universidad {
    private String nombre;
    private List<Curso> cursos; // agregación

    public Universidad(String nombre) {
        this.nombre = nombre;
        this.cursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {
        cursos.add(curso);
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Universidad: " + nombre + "\nCursos:\n");
        for (Curso c : cursos) {
            sb.append("- ").append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}


class Reporte {
    public String generar(Estudiante est) {
        return "Reporte del estudiante\n------------------\n" +
                "Nombre: " + est.getNombre() + "\n" +
                "Edad: " + est.getEdad();
    }
}


public class Main {
    public static void main(String[] args) {
        // Profesores
        Profesor prof1 = new Profesor("Juan Pérez", 45);
        Profesor prof2 = new Profesor("María López", 39);

        // Estudiantes
        Estudiante est1 = new Estudiante("Ana Torres", 20);
        Estudiante est2 = new Estudiante("Luis Gómez", 22);
        Estudiante est3 = new Estudiante("Carla Ríos", 19);

        // Cursos (con composición de horarios)
        Curso curso1 = new Curso("Programación", new Horario("Lunes", "10:00 AM"));
        Curso curso2 = new Curso("Matemáticas", new Horario("Martes", "2:00 PM"));

        // Universidad (agregación de cursos)
        Universidad uni = new Universidad("Universidad Nacional");
        uni.agregarCurso(curso1);
        uni.agregarCurso(curso2);

        // Mostrar datos
        System.out.println(prof1);
        System.out.println(prof2);
        System.out.println(est1);
        System.out.println(est2);
        System.out.println(est3);
        System.out.println();
        System.out.println(uni);

        // Generar reporte de un estudiante (dependencia)
        Reporte reporte = new Reporte();
        System.out.println(reporte.generar(est2));
    }
}
