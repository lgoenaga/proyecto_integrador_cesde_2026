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
