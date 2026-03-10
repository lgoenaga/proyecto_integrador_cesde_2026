package co.edu.cesde.ga.repository;

import co.edu.cesde.ga.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final List<Student> students;
    private Long nextStudentId;

    public StudentRepository() {
        this.students = new ArrayList<>();
        this.nextStudentId = 1L;
    }

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

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

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

    public boolean delete(Long studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return false;
        }
        return students.remove(student);
    }

    public boolean existsByDocumentNumber(String documentNumber) {
        return findByDocumentNumber(documentNumber) != null;
    }

    public int count() {
        return students.size();
    }
}

