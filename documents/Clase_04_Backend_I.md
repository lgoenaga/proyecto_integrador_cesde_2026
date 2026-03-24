# Clase 04 - Fase 2: implementación InMemory y consumo por interfaz

## Objetivo

En esta fase ya no trabajamos solo con el contrato del repositorio.

Ahora vamos a:

1. crear una implementación concreta en memoria
2. ubicarla en `repository.impl`
3. hacer que `Main` dependa de la interfaz `StudentRepository`
4. usar un nombre explícito: `studentRepository`

---

## ¿Por qué no dejar todo en `StudentRepository`?

Porque queremos separar dos cosas distintas:

- el **contrato** (`StudentRepository`)
- la **implementación concreta** (`StudentRepositoryInMemory`)

Esto deja la aplicación preparada para el futuro.

Más adelante podremos tener otra implementación como:

- `StudentRepositoryJpa`

sin necesidad de reescribir todo el programa.

---

## Estructura deseada

```text
repository/
├── StudentRepository.java
└── impl/
    └── StudentRepositoryInMemory.java
```

---

## Paso 1. Mantener `StudentRepository` como interfaz

En esta fase la interfaz se conserva igual.

### Código completo de `StudentRepository.java`

```java
package co.edu.cesde.ga.repository;

import co.edu.cesde.ga.model.Student;

import java.util.List;

public interface StudentRepository {

    Student create(Student student);

    List<Student> findAll();

    Student findById(Long studentId);

    Student findByDocumentNumber(String documentNumber);

    boolean update(Student updatedStudent);

    boolean delete(Long studentId);

    boolean existsByDocumentNumber(String documentNumber);

    int count();
}
```

---

## Paso 2. Crear `StudentRepositoryInMemory`

Aquí movemos la lógica que antes estaba acoplada a una sola clase.

### ¿Qué se logra con esto?

- el `ArrayList` queda aislado en la implementación concreta
- el autoincremental simulado también queda aislado
- más adelante se puede reemplazar esta clase por una implementación JPA
- la clase concreta cumple el contrato usando `implements StudentRepository`

### Código completo de `StudentRepositoryInMemory.java`

```java
package co.edu.cesde.ga.repository.impl;

import co.edu.cesde.ga.model.Student;
import co.edu.cesde.ga.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryInMemory implements StudentRepository {

    private final List<Student> students;
    private Long nextStudentId;

    public StudentRepositoryInMemory() {
        this.students = new ArrayList<>();
        this.nextStudentId = 1L;
    }

    @Override
    public Student create(Student student) {
        if (student == null) {
            return null;
        }

        if (existsByDocumentNumber(student.getDocumentNumber())) {
            return null;
        }

        student.setStudentId(nextStudentId++);
        students.add(student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public Student findById(Long studentId) {
        if (studentId == null) {
            return null;
        }

        for (Student student : students) {
            if (studentId.equals(student.getStudentId())) {
                return student;
            }
        }
        return null;
    }

    @Override
    public Student findByDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.isBlank()) {
            return null;
        }

        for (Student student : students) {
            if (documentNumber.equals(student.getDocumentNumber())) {
                return student;
            }
        }
        return null;
    }

    @Override
    public boolean update(Student updatedStudent) {
        if (updatedStudent == null || updatedStudent.getStudentId() == null) {
            return false;
        }

        for (Student student : students) {
            if (!student.getStudentId().equals(updatedStudent.getStudentId())
                    && student.getDocumentNumber().equals(updatedStudent.getDocumentNumber())) {
                return false;
            }
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(updatedStudent.getStudentId())) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return false;
        }
        return students.remove(student);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return findByDocumentNumber(documentNumber) != null;
    }

    @Override
    public int count() {
        return students.size();
    }
}
```

---

## Paso 3. Ajustar `Main` para usar la interfaz

Aquí hay una decisión importante.

No debemos usar un nombre ambiguo como:

```java
StudentRepository repository = new StudentRepositoryInMemory();
```

Eso después sería confuso cuando existan:
- `teacherRepository`
- `programRepository`
- `userRepository`

Por eso se usa el nombre explícito:

```java
private static final StudentRepository studentRepository = new StudentRepositoryInMemory();
```

### Código completo de `Main.java`

```java
package co.edu.cesde.ga.app;

import co.edu.cesde.ga.model.Student;
import co.edu.cesde.ga.repository.StudentRepository;
import co.edu.cesde.ga.repository.impl.StudentRepositoryInMemory;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepository studentRepository = new StudentRepositoryInMemory();

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
                case 7 -> System.out.println("Total estudiantes: " + studentRepository.count());
                case 0 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (option != 0);
    }

    private static void createStudent() {
        System.out.println("\n--- Crear estudiante ---");

        String documentType = readRequiredString("Tipo de documento (CC/TI/PAS): ");
        String documentNumber = readRequiredString("Numero de documento: ");

        if (studentRepository.existsByDocumentNumber(documentNumber)) {
            System.out.println("Ya existe un estudiante con ese numero de documento.");
            return;
        }

        String firstName = readRequiredString("Nombres: ");
        String lastName = readRequiredString("Apellidos: ");
        String birthDate = readRequiredString("Fecha de nacimiento (YYYY-MM-DD): ");
        String status = readRequiredString("Estado: ");
        Long userId = readOptionalLong("User ID (opcional, Enter para omitir): ");

        Student student = new Student(userId, documentType, documentNumber, firstName, lastName, status, birthDate);
        Student created = studentRepository.create(student);

        if (created == null) {
            System.out.println("No fue posible crear el estudiante.");
            return;
        }

        System.out.println("Estudiante creado correctamente:");
        System.out.println(created);
    }

    private static void listStudents() {
        System.out.println("\n--- Lista de estudiantes ---");
        List<Student> students = studentRepository.findAll();

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

        Student student = studentRepository.findById(studentId);
        if (student == null) {
            System.out.println("No se encontro estudiante con ese ID.");
            return;
        }

        System.out.println(student);
    }

    private static void findStudentByDocumentNumber() {
        System.out.println("\n--- Buscar estudiante por documento ---");
        String documentNumber = readRequiredString("Numero de documento: ");

        Student student = studentRepository.findByDocumentNumber(documentNumber);
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

        Student student = studentRepository.findById(studentId);
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
                && studentRepository.existsByDocumentNumber(documentNumber)) {
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

        if (studentRepository.update(student)) {
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

        if (studentRepository.delete(studentId)) {
            System.out.println("Estudiante eliminado correctamente.");
        } else {
            System.out.println("No existe un estudiante con ese ID.");
        }
    }

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private static Long readLong(String message) {
        System.out.print(message);
        String value = scanner.nextLine().trim();
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Long readOptionalLong(String message) {
        System.out.print(message);
        String value = scanner.nextLine().trim();
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
        String value = scanner.nextLine().trim();
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
            value = scanner.nextLine().trim();
        } while (value.isBlank());
        return value;
    }

    private static String readOptionalString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
```

---

## Commits sugeridos

```bash
git checkout clase_03
git checkout -b clase_04
```

```bash
git add src/main/java/co/edu/cesde/ga/repository/impl/StudentRepositoryInMemory.java
git commit -m "feat(repository): add in-memory student repository implementation"
```

```bash
git add src/main/java/co/edu/cesde/ga/app/Main.java
git commit -m "refactor(app): depend on StudentRepository abstraction"
```

```bash
git add documents/Clase_04_Backend_I.md
git commit -m "docs: add class 04 phase 2 guide"
```

---

## Verificación

```bash
mvn clean compile
java -cp target/classes co.edu.cesde.ga.app.Main
```

---

## Resultado de la fase

Al terminar esta fase el proyecto queda mucho mejor preparado para seguir creciendo:

- `Main` ya no depende de una implementación concreta en el mismo paquete
- existe una implementación en memoria separada
- la interfaz se mantiene estable
- más adelante se podrá crear `StudentRepositoryJpa` con menos cambios

### Nota adicional sobre el modelo

Como ajuste de consistencia del dominio, `userId` se maneja como `Long` en:

- `Person`
- `User`
- `UserRole`

Esto ayuda a que el estudiante vea la misma idea de identificador en las clases relacionadas y evita confusiones cuando más adelante se pasen estas clases a entidades JPA/Hibernate.

