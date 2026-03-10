package co.edu.cesde.ga.app;

import co.edu.cesde.ga.model.Student;
import co.edu.cesde.ga.repository.StudentRepository;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StudentRepository STUDENT_REPOSITORY = new StudentRepository();

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        int option;

        do {
            System.out.println("\n===== SISTEMA ACADEMICO CESDE 2026 =====");
            System.out.println("1. Gestion de estudiantes");
            System.out.println("2. Gestion de profesores");
            System.out.println("3. Gestion de usuarios");
            System.out.println("4. Gestion de roles");
            System.out.println("5. Gestion de programas");
            System.out.println("6. Gestion de materias");
            System.out.println("7. Gestion de periodos");
            System.out.println("8. Gestion de grupos");
            System.out.println("9. Gestion de inscripciones");
            System.out.println("10. Gestion de calificaciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            option = readInt();

            switch (option) {
                case 1 -> showStudentMenu();
                case 2, 3, 4, 5, 6, 7, 8, 9, 10 -> System.out.println("Modulo pendiente por implementar.");
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (option != 0);
    }

    private static void showStudentMenu() {
        int option;

        do {
            System.out.println("\n===== SUBMENU ESTUDIANTES =====");
            System.out.println("1. Crear estudiante");
            System.out.println("2. Listar estudiantes");
            System.out.println("3. Buscar estudiante por ID");
            System.out.println("4. Buscar estudiante por numero de documento");
            System.out.println("5. Actualizar estudiante");
            System.out.println("6. Eliminar estudiante");
            System.out.println("7. Total de estudiantes");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            option = readInt();

            switch (option) {
                case 1 -> createStudent();
                case 2 -> listStudents();
                case 3 -> findStudentById();
                case 4 -> findStudentByDocumentNumber();
                case 5 -> updateStudent();
                case 6 -> deleteStudent();
                case 7 -> System.out.println("Total estudiantes: " + STUDENT_REPOSITORY.count());
                case 0 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (option != 0);
    }

    private static void createStudent() {
        System.out.println("\n--- Crear estudiante ---");

        String documentType = readRequiredString("Tipo de documento (CC/TI/PAS): ");
        String documentNumber = readRequiredString("Numero de documento: ");

        if (STUDENT_REPOSITORY.existsByDocumentNumber(documentNumber)) {
            System.out.println("Ya existe un estudiante con ese numero de documento.");
            return;
        }

        String firstName = readRequiredString("Nombres: ");
        String lastName = readRequiredString("Apellidos: ");
        String birthDate = readRequiredString("Fecha de nacimiento (YYYY-MM-DD): ");
        String status = readRequiredString("Estado: ");
        Long userId = readOptionalLong("User ID (opcional, Enter para omitir): ");

        Student student = new Student(userId, documentType, documentNumber, firstName, lastName, status, birthDate);
        Student created = STUDENT_REPOSITORY.create(student);

        if (created == null) {
            System.out.println("No fue posible crear el estudiante.");
            return;
        }

        System.out.println("Estudiante creado correctamente: ");
        System.out.println(created);
    }

    private static void listStudents() {
        System.out.println("\n--- Lista de estudiantes ---");
        List<Student> students = STUDENT_REPOSITORY.findAll();

        if (students.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void findStudentById() {
        System.out.println("\n--- Buscar estudiante por ID ---");
        Long studentId = readLong("Student ID: ");
        if (studentId == null) {
            System.out.println("ID invalido.");
            return;
        }

        Student student = STUDENT_REPOSITORY.findById(studentId);
        if (student == null) {
            System.out.println("No se encontro estudiante con ese ID.");
            return;
        }

        System.out.println(student);
    }

    private static void findStudentByDocumentNumber() {
        System.out.println("\n--- Buscar estudiante por documento ---");
        String documentNumber = readRequiredString("Numero de documento: ");
        Student student = STUDENT_REPOSITORY.findByDocumentNumber(documentNumber);

        if (student == null) {
            System.out.println("No se encontro estudiante con ese documento.");
            return;
        }

        System.out.println(student);
    }

    private static void updateStudent() {
        System.out.println("\n--- Actualizar estudiante ---");
        Long studentId = readLong("Student ID del estudiante a actualizar: ");
        if (studentId == null) {
            System.out.println("ID invalido.");
            return;
        }

        Student student = STUDENT_REPOSITORY.findById(studentId);
        if (student == null) {
            System.out.println("No existe un estudiante con ese ID.");
            return;
        }

        System.out.println("Presione Enter para conservar el valor actual.");

        String documentType = readOptionalString("Tipo de documento actual [" + student.getDocumentType() + "]: ");
        if (!documentType.isBlank()) {
            student.setDocumentType(documentType);
        }

        String documentNumber = readOptionalString("Numero de documento actual [" + student.getDocumentNumber() + "]: ");
        if (!documentNumber.isBlank() && !documentNumber.equals(student.getDocumentNumber())
                && STUDENT_REPOSITORY.existsByDocumentNumber(documentNumber)) {
            System.out.println("Ya existe otro estudiante con ese numero de documento.");
            return;
        }
        if (!documentNumber.isBlank()) {
            student.setDocumentNumber(documentNumber);
        }

        String firstName = readOptionalString("Nombres actuales [" + student.getFirstName() + "]: ");
        if (!firstName.isBlank()) {
            student.setFirstName(firstName);
        }

        String lastName = readOptionalString("Apellidos actuales [" + student.getLastName() + "]: ");
        if (!lastName.isBlank()) {
            student.setLastName(lastName);
        }

        String birthDate = readOptionalString("Fecha de nacimiento actual [" + student.getBirthDate() + "]: ");
        if (!birthDate.isBlank()) {
            student.setBirthDate(birthDate);
        }

        String status = readOptionalString("Estado actual [" + student.getStatus() + "]: ");
        if (!status.isBlank()) {
            student.setStatus(status);
        }

        Long userId = readOptionalLongWithCurrent("User ID actual [" + (student.getUserId() == null ? "null" : student.getUserId()) + "] (Enter conserva, 0 limpia): ");
        if (userId != Long.MIN_VALUE) {
            student.setUserId(userId == 0L ? null : userId);
        }

        if (STUDENT_REPOSITORY.update(student)) {
            System.out.println("Estudiante actualizado correctamente.");
            System.out.println(student);
        } else {
            System.out.println("No fue posible actualizar el estudiante.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- Eliminar estudiante ---");
        Long studentId = readLong("Student ID del estudiante a eliminar: ");
        if (studentId == null) {
            System.out.println("ID invalido.");
            return;
        }

        if (STUDENT_REPOSITORY.delete(studentId)) {
            System.out.println("Estudiante eliminado correctamente.");
        } else {
            System.out.println("No existe un estudiante con ese ID.");
        }
    }

    private static int readInt() {
        try {
            return Integer.parseInt(SCANNER.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private static Long readLong(String message) {
        System.out.print(message);
        String value = SCANNER.nextLine().trim();
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Long readOptionalLong(String message) {
        System.out.print(message);
        String value = SCANNER.nextLine().trim();
        if (value.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Long readOptionalLongWithCurrent(String message) {
        System.out.print(message);
        String value = SCANNER.nextLine().trim();
        if (value.isBlank()) {
            return Long.MIN_VALUE;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            return Long.MIN_VALUE;
        }
    }

    private static String readRequiredString(String message) {
        String value;
        do {
            System.out.print(message);
            value = SCANNER.nextLine().trim();
        } while (value.isBlank());
        return value;
    }

    private static String readOptionalString(String message) {
        System.out.print(message);
        return SCANNER.nextLine().trim();
    }
}
