package co.edu.cesde.ga.app;

import co.edu.cesde.ga.model.Person;
import co.edu.cesde.ga.model.Student;
import co.edu.cesde.ga.model.Teacher;

public class Main {

    public static void main(String[] args) {

        Person person = new Person();

        person.setId("student-001");
        person.setUserId("user-001");
        person.setCode("123456");
        person.setDocumentNumber("1234567890");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setStatus("ACTIVE");

        System.out.println("ID: " + person.getId());
        System.out.println("User ID: " + person.getUserId());
        System.out.println("Code: " + person.getCode());
        System.out.println("Document Number: " + person.getDocumentNumber());
        System.out.println("First Name: " + person.getFirstName());
        System.out.println("Last Name: " + person.getLastName());
        System.out.println("Status: " + person.getStatus());

        Person person2 = new Person("student-002", "user-002", "654321", "0987654321", "Jane", "Smith", "INACTIVE");

        System.out.println("ID: " + person2.getId());
        System.out.println("User ID: " + person2.getUserId());
        System.out.println("Code: " + person2.getCode());
        System.out.println("Document Number: " + person2.getDocumentNumber());
        System.out.println("First Name: " + person2.getFirstName());
        System.out.println("Last Name: " + person2.getLastName());
        System.out.println("Status: " + person2.getStatus());

        Teacher teacher = new Teacher();

        teacher.setId("teacher-001");
        teacher.setUserId("user-003");
        teacher.setCode("789012");
        teacher.setDocumentNumber("1122334455");
        teacher.setFirstName("Alice");
        teacher.setLastName("Johnson");
        teacher.setStatus("ACTIVE");

        System.out.println("ID: " + teacher.getId());
        System.out.println("User ID: " + teacher.getUserId());
        System.out.println("Code: " + teacher.getCode());
        System.out.println("Document Number: " + teacher.getDocumentNumber());
        System.out.println("First Name: " + teacher.getFirstName());
        System.out.println("Last Name: " + teacher.getLastName());
        System.out.println("Status: " + teacher.getStatus());

        Teacher teacher2 = new Teacher("teacher-002", "user-004", "210987", "5544332211", "Bob", "Williams", "INACTIVE");

        System.out.println("ID: " + teacher2.getId());
        System.out.println("User ID: " + teacher2.getUserId());
        System.out.println("Code: " + teacher2.getCode());
        System.out.println("Document Number: " + teacher2.getDocumentNumber());
        System.out.println("First Name: " + teacher2.getFirstName());
        System.out.println("Last Name: " + teacher2.getLastName());
        System.out.println("Status: " + teacher2.getStatus());

        Student student = new Student();

        student.setId("student-003");
        student.setUserId("user-005");
        student.setCode("345678");
        student.setDocumentNumber("6677889900");
        student.setFirstName("Charlie");
        student.setLastName("Brown");
        student.setStatus("ACTIVE");
        student.setBirthDate("2000-01-01");

        System.out.println("ID: " + student.getId());
        System.out.println("User ID: " + student.getUserId());
        System.out.println("Code: " + student.getCode());
        System.out.println("Document Number: " + student.getDocumentNumber());
        System.out.println("First Name: " + student.getFirstName());
        System.out.println("Last Name: " + student.getLastName());
        System.out.println("Status: " + student.getStatus());
        System.out.println("Birth Date: " + student.getBirthDate());

        Student student2 = new Student("student-004", "user-006", "876543", "0099887766", "David", "Smith", "INACTIVE", "1999-12-31");

        System.out.println("ID: " + student2.getId());
        System.out.println("User ID: " + student2.getUserId());
        System.out.println("Code: " + student2.getCode());
        System.out.println("Document Number: " + student2.getDocumentNumber());
        System.out.println("First Name: " + student2.getFirstName());
        System.out.println("Last Name: " + student2.getLastName());
        System.out.println("Status: " + student2.getStatus());
        System.out.println("Birth Date: " + student2.getBirthDate());




    }
}
