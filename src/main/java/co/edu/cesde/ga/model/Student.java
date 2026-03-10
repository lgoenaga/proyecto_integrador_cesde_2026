package co.edu.cesde.ga.model;

public class Student extends Person{

    private String birthDate;

    public Student() {
        super();
    }

    public Student(String id, String userId, String code, String documentNumber, String firstName, String lastName, String status, String birthDate) {
        super(id, userId, code, documentNumber, firstName, lastName, status);
        this.birthDate = birthDate;
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
                "id='" + getId() + '\'' +
                ", userId='" + getUserId() + '\'' +
                ", code='" + getCode() + '\'' +
                ", documentNumber='" + getDocumentNumber() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
