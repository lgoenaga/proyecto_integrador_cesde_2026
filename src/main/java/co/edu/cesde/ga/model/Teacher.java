package co.edu.cesde.ga.model;

public class Teacher extends Person {

    public Teacher() {
        super();
    }

    public Teacher(String id, String userId, String code, String documentNumber, String firstName, String lastName, String status) {
        super(id, userId, code, documentNumber, firstName, lastName, status);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + getId() + '\'' +
                ", userId='" + getUserId() + '\'' +
                ", code='" + getCode() + '\'' +
                ", documentNumber='" + getDocumentNumber() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
