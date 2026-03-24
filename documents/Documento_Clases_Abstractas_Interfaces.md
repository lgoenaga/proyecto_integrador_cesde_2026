
# Introducción a Clases Abstractas e Interfaces en Java

Este documento está diseñado para estudiantes que están iniciando en Java y que ya han visto los temas de **modelos**, **encapsulamiento**, **herencia** y **polimorfismo**. Ahora continuaremos con dos conceptos fundamentales: **clases abstractas** e **interfaces**, aplicados a un proyecto real basado en la base de datos del sistema académico CESDE (2026).

---
## 1. ¿Qué es una Clase Abstracta?
Una **clase abstracta** es una clase que sirve como molde o plantilla para otras clases. No puede instanciarse directamente y se utiliza para compartir atributos y comportamientos comunes entre varias clases.

### Características principales:
- No se puede crear un objeto directamente: `new MiClaseAbstracta()` ❌
- Puede tener **métodos abstractos** (sin implementación) y **métodos normales**.
- Puede tener atributos.
- Se usa con **herencia**.

### Ejemplo aplicado al proyecto (Tabla `users`)
Podemos crear una clase abstracta para entidades que tienen datos personales:

```java
public abstract class Person {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String documentNumber;

    public Person(String id, String firstName, String lastName, String documentNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
    }

    // Método abstracto
    public abstract String getRole();
}
```

### Clase hija: `Student` (tabla *students*)
```java
public class Student extends Person {
    private String birthDate;

    public Student(String id, String firstName, String lastName, String documentNumber, String birthDate) {
        super(id, firstName, lastName, documentNumber);
        this.birthDate = birthDate;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }
}
```

### Clase hija: `Teacher` (tabla *teachers*)
```java
public class Teacher extends Person {
    public Teacher(String id, String firstName, String lastName, String documentNumber) {
        super(id, firstName, lastName, documentNumber);
    }

    @Override
    public String getRole() {
        return "TEACHER";
    }
}
```

Esto nos permite aplicar **herencia + polimorfismo**:

```java
Person p = new Student(...);
System.out.println(p.getRole()); // STUDENT
```

---
## 2. ¿Qué es una Interface en Java?
Una **interface** define un conjunto de métodos que una clase debe implementar. Representa un CONTRATO.

### Características:
- Todos los métodos son abstractos (a menos que usen `default`).
- Una clase puede implementar **muchas interfaces**.
- No hay atributos (solo constantes).

### Ejemplo aplicado al proyecto
Podemos definir acciones comunes entre modelos:

```java
public interface Identifiable {
    String getId();
}
```

Implementación en `Student`:
```java
public class Student extends Person implements Identifiable {
    @Override
    public String getId() {
        return this.id;
    }
}
```

Implementación en `Teacher`:
```java
public class Teacher extends Person implements Identifiable {
    @Override
    public String getId() {
        return this.id;
    }
}
```

### Otro ejemplo: Interface para repositorios
```java
public interface Repository<T> {
    T findById(String id);
    List<T> findAll();
    void save(T entity);
    void delete(String id);
}
```

---
## 3. ¿Cuándo usar Clase Abstracta y cuándo Interface?
| Necesidad | Usa Clase Abstracta | Usa Interface |
|----------|----------------------|----------------|
| Compartir atributos | ✔️ | ❌ |
| Implementar lógica compartida | ✔️ | ✔️ (default) |
| Definir reglas obligatorias | ✔️ | ✔️ |
| Herencia múltiple | ❌ | ✔️ |

### Regla general:
- **Clase abstracta**: cuando las clases comparten comportamiento y atributos.
- **Interface**: cuando varias clases diferentes deben compartir un conjunto de acciones.

---
## 4. Aplicación al proyecto: Base de Datos
Basado en la imagen proporcionada, el modelo académico tiene tablas como:
- `students`
- `teachers`
- `users`
- `programs`
- `groups`
- `subjects`
- `enrollments`
- `grades`

### Ejemplo: Clases basadas en tablas
La tabla `students` contiene:
- student_id
- user_id
- code
- document_number
- first_name
- last_name
- birth_date
- status

Esto se convierte en un modelo:

```java
public class Student extends Person {
    private String code;
    private String status;
    private String birthDate;
}
```

---
## 5. Ejemplo completo usando Clase Abstracta + Interface

### Interface
```java
public interface Statusable {
    String getStatus();
    void setStatus(String status);
}
```

### Clase abstracta
```java
public abstract class Person implements Statusable {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String documentNumber;

    public Person(...) { ... }
}
```

### Clase concreta
```java
public class Student extends Person {
    private String birthDate;
    private String status;

    @Override
    public String getStatus() { return status; }
    @Override
    public void setStatus(String status) { this.status = status; }
}
```

---
## 6. Conclusión
Con clases abstractas e interfaces puedes:
- Generalizar comportamientos.
- Reutilizar código.
- Aplicar polimorfismo.
- Crear arquitecturas limpias.
- Modelar tu sistema según la base de datos real.

Esto prepara el camino para temas posteriores: repositorios JDBC, servicios, controladores y APIs.

---
**¿Deseas que convierta este documento a PDF o entregarlo con diagramas incluidos?**
