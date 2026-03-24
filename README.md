# Proyecto Integrador CESDE 2026

Proyecto base para construir, por etapas, un sistema académico en Java con Maven.

La idea del repositorio es avanzar de forma incremental:
- primero **modelado orientado a objetos**
- luego **repositorios en memoria**
- después **menús y lógica básica de negocio**
- y más adelante **persistencia con JPA / Hibernate**

---

## Estado actual del proyecto

Hasta este punto se ha trabajado principalmente en:

- definición de modelos del dominio según el diagrama E-R
- refactor de `Person` como clase base abstracta
- separación de identificadores lógicos:
  - `studentId`
  - `teacherId`
- implementación de un `StudentRepository` en memoria con `ArrayList`
- creación de un menú principal en consola
- implementación del submenú CRUD completo para estudiantes

---

## Objetivo del proyecto

Construir un sistema académico que permita gestionar:

- usuarios
- roles
- estudiantes
- profesores
- programas
- materias
- periodos
- grupos
- inscripciones
- calificaciones

Todo esto se hace inicialmente en memoria para entender bien la lógica antes de conectar base de datos.

---

## Estructura del proyecto

```text
proyecto_integrador_cesde_2026/
├── documents/
│   ├── Clase_02_Backend_I.md
│   ├── Clase_03_Backend_I.md
│   └── bd-backend-I-cesde2026.png
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/cesde/ga/
│   │   │       ├── app/
│   │   │       │   └── Main.java
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── middleware/
│   │   │       ├── model/
│   │   │       │   ├── Enrollment.java
│   │   │       │   ├── Grade.java
│   │   │       │   ├── Group.java
│   │   │       │   ├── GroupSubject.java
│   │   │       │   ├── Period.java
│   │   │       │   ├── Person.java
│   │   │       │   ├── Program.java
│   │   │       │   ├── Role.java
│   │   │       │   ├── Student.java
│   │   │       │   ├── Subject.java
│   │   │       │   ├── Teacher.java
│   │   │       │   ├── User.java
│   │   │       │   └── UserRole.java
│   │   │       ├── repository/
│   │   │       │   └── StudentRepository.java
│   │   │       ├── service/
│   │   │       └── utils/
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

---

## Qué se ha realizado por capas

### 1. `model`
Contiene las clases que representan el dominio del negocio.

#### Decisiones importantes
- `Person` no representa una tabla real; se usa como **clase abstracta base**.
- `Student` tiene `studentId` como PK lógica.
- `Teacher` tiene `teacherId` como PK lógica.
- `userId` puede ser `null` porque una persona puede existir antes de tener usuario del sistema.
- `documentType` representa valores como `CC`, `TI`, `PAS`, etc.
- `documentNumber` representa la identificación real.

### 2. `repository`
Aquí se implementan los repositorios en memoria.

Actualmente existe:
- `StudentRepository`

Este repositorio:
- usa `ArrayList`
- simula autoincremental con `nextStudentId`
- valida unicidad por `documentNumber`
- implementa CRUD completo

### 3. `app`
Aquí vive el punto de entrada del sistema.

Actualmente `Main.java` tiene:
- menú principal por entidades
- submenú funcional de estudiantes
- lectura por consola con `Scanner`

---

## Cómo se debe trabajar en este proyecto

### Flujo recomendado
Trabajar siempre por ramas y con cambios pequeños.

### Estrategia sugerida
1. partir de una rama base estable
2. crear una nueva rama para cada clase o bloque funcional
3. hacer cambios pequeños y verificables
4. compilar antes de cada commit
5. usar commits granulares
6. hacer push sin mezclar directamente con `main`

---

## Convención de trabajo sugerida

### Crear rama nueva
```bash
git checkout clase02
git checkout -b clase03
```

### Verificar compilación
```bash
mvn clean compile
```

### Commits granulares
```bash
git add src/main/java/co/edu/cesde/ga/model/Person.java \
        src/main/java/co/edu/cesde/ga/model/Student.java \
        src/main/java/co/edu/cesde/ga/model/Teacher.java
git commit -m "refactor(model): abstract person and logical ids"
```

```bash
git add src/main/java/co/edu/cesde/ga/repository/StudentRepository.java
git commit -m "feat(repository): in-memory student repository"
```

```bash
git add src/main/java/co/edu/cesde/ga/app/Main.java
git commit -m "feat(app): student CRUD menu"
```

### Publicar la rama
```bash
git push -u origin clase03
```

---

## Cómo ejecutar el proyecto

### Compilar
```bash
mvn clean compile
```

### Ejecutar
```bash
java -cp target/classes co.edu.cesde.ga.app.Main
```

> Si primero quieres compilar sin limpiar:

```bash
mvn compile
```

---

## Lineamientos de diseño usados hasta ahora

### 1. Empezar simple
Antes de usar JPA/Hibernate, primero se construye la lógica con:
- clases
- herencia
- listas en memoria
- menús por consola

### 2. Separación de responsabilidades
- `model`: representa entidades del negocio
- `repository`: guarda y consulta datos
- `app`: interacción con el usuario

### 3. Coherencia con el modelo E-R
Se busca que el diseño Java se acerque al modelo relacional, sin forzar que todo sea idéntico.

Por ejemplo:
- `Person` no está en el E-R como tabla
- pero sí es útil como abstracción orientada a objetos

### 4. Preparación para futuras etapas
Este trabajo deja lista la base para luego migrar a:
- JPA
- Hibernate
- repositorios reales conectados a base de datos

---

## Documentación disponible

En la carpeta `documents/` se deja material de apoyo por clase.

### Documentos actuales
- `Clase_02_Backend_I.md`
- `Clase_03_Backend_I.md`
- `bd-backend-I-cesde2026.png`

`Clase_03_Backend_I.md` contiene:
- explicación paso a paso
- decisiones de diseño
- código completo
- orden recomendado de commits
- forma de probar la solución

---

## Próximos pasos recomendados

El orden sugerido para seguir construyendo la aplicación es:

1. `TeacherRepository`
2. submenú de profesores
3. repositorios de programas y materias
4. lógica de grupos e inscripciones
5. validaciones de negocio más completas
6. paso a servicios
7. integración con JPA / Hibernate

---

## Buenas prácticas para estudiantes

- compilar frecuentemente
- hacer un cambio a la vez
- evitar mezclar refactor con nuevas funcionalidades en un mismo commit
- documentar decisiones importantes
- mantener nombres claros en clases y atributos
- alinear el código con el modelo del negocio, no solo con la base de datos

---

## Nota final

Este proyecto está pensado como una construcción progresiva. La meta no es solo “que funcione”, sino que el estudiante entienda:

- por qué se modela así
- por qué se separan capas
- por qué se empieza en memoria antes de JPA
- y cómo preparar una base limpia para las siguientes clases

