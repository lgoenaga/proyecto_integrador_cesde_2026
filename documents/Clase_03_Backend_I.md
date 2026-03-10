# Clase 03 - Fase 1: modelos base e interfaz de repositorio

## Objetivo

En esta fase se busca preparar el proyecto para crecer de forma ordenada antes de llegar a `service`, `controller` y luego a JPA + Hibernate.

La meta de esta fase es:

1. convertir `Person` en una clase abstracta
2. corregir los IDs de `Student` y `Teacher`
3. renombrar `code` a `documentType`
4. dejar `userId` como nullable
5. crear la interfaz `StudentRepository`

---

## ¿Por qué se hace esto primero?

Porque antes de implementar repositorios reales o servicios, necesitamos definir bien el modelo del dominio y el contrato del repositorio.

Esto permite que luego podamos tener varias implementaciones del repositorio, por ejemplo:

- una en memoria
- una con JPA/Hibernate

sin cambiar toda la aplicación.

---

## Paso 1. Convertir `Person` en clase abstracta

`Person` no existe como tabla en el modelo E-R, pero sí es útil como clase base en Java.

Se usa para compartir los campos comunes entre `Student` y `Teacher`.

### Código completo de `Person.java`

```java
package co.edu.cesde.ga.model;

public abstract class Person {

    private Long userId;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String status;

    protected Person() {
    }

    protected Person(Long userId, String documentType, String documentNumber, String firstName, String lastName, String status) {
        this.userId = userId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", documentType='" + documentType + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
```

---

## Paso 2. Ajustar `Student`

Ahora `Student` debe tener su propio identificador lógico: `studentId`.

### Código completo de `Student.java`

```java
package co.edu.cesde.ga.model;

public class Student extends Person {

    private Long studentId;
    private String birthDate;

    public Student() {
        super();
    }

    public Student(Long userId, String documentType, String documentNumber, String firstName, String lastName, String status, String birthDate) {
        super(userId, documentType, documentNumber, firstName, lastName, status);
        this.birthDate = birthDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", userId=" + getUserId() +
                ", documentType='" + getDocumentType() + '\'' +
                ", documentNumber='" + getDocumentNumber() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
```

---

## Paso 3. Ajustar `Teacher`

Ahora `Teacher` debe tener su propio identificador lógico: `teacherId`.

### Código completo de `Teacher.java`

```java
package co.edu.cesde.ga.model;

public class Teacher extends Person {

    private Long teacherId;

    public Teacher() {
        super();
    }

    public Teacher(Long teacherId, Long userId, String documentType, String documentNumber, String firstName, String lastName, String status) {
        super(userId, documentType, documentNumber, firstName, lastName, status);
        this.teacherId = teacherId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", userId=" + getUserId() +
                ", documentType='" + getDocumentType() + '\'' +
                ", documentNumber='" + getDocumentNumber() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
```

---

## Paso 4. Crear la interfaz `StudentRepository`

En esta fase no creamos aún la implementación en memoria. Solo definimos el contrato.

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

## Paso 5. Ajustar `Main` para compilar en esta fase

Como `Person` ahora es abstracta, ya no se puede instanciar directamente.

En esta fase dejamos un `Main` mínimo solo para probar que el nuevo modelo funciona.

### Código completo de `Main.java`

```java
package co.edu.cesde.ga.app;

import co.edu.cesde.ga.model.Student;
import co.edu.cesde.ga.model.Teacher;

public class Main {

    public static void main(String[] args) {
        Teacher teacher = new Teacher(1L, null, "CC", "1122334455", "Alice", "Johnson", "ACTIVE");

        Student student = new Student(null, "TI", "6677889900", "Charlie", "Brown", "ACTIVE", "2000-01-01");
        student.setStudentId(1L);

        System.out.println("=== DEMO FASE 1 ===");
        System.out.println(teacher);
        System.out.println(student);
    }
}
```

---

## Commits sugeridos

```bash
git checkout clase02
git checkout -b clase_03
```

```bash
git add src/main/java/co/edu/cesde/ga/model/Person.java \
        src/main/java/co/edu/cesde/ga/model/Student.java \
        src/main/java/co/edu/cesde/ga/model/Teacher.java \
        src/main/java/co/edu/cesde/ga/app/Main.java
git commit -m "refactor(model): abstract person and logical ids"
```

```bash
git add src/main/java/co/edu/cesde/ga/repository/StudentRepository.java
git commit -m "feat(repository): add StudentRepository interface"
```

```bash
git add documents/Clase_03_Backend_I.md
git commit -m "docs: add class 03 phase 1 guide"
```

---

## Verificación

```bash
mvn clean compile
java -cp target/classes co.edu.cesde.ga.app.Main
```

---

## Resultado de la fase

Al terminar esta fase el proyecto queda preparado para la siguiente:

- ya existe una abstracción `StudentRepository`
- el modelo está alineado con el E-R
- `Main` compila con la nueva estructura
- en la fase siguiente se podrá crear `StudentRepositoryInMemory` en `repository.impl`
