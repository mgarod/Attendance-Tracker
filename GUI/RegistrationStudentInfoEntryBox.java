package GUI;

import Information.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationStudentInfoEntryBox extends VBox {

    private Label emplIdLabel, firstNameLabel, lastNameLabel;
    private TextField emplIdTextField;
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private HBox horizontalEmplId, horizontalFirstName, horizontalLastName;

    public RegistrationStudentInfoEntryBox() {
        this(0);
    }

    public RegistrationStudentInfoEntryBox(double spacing) {
        super(spacing);
        initializeFields();
        iniatlizeLayout();
    }

    private void initializeFields() {
        emplIdLabel = new Label("EMPL ID");
        emplIdLabel.setMinWidth(80);

        firstNameLabel = new Label("First Name");
        firstNameLabel.setMinWidth(80);

        lastNameLabel = new Label("Last Name");
        lastNameLabel.setMinWidth(80);

        emplIdTextField = new TextField();
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();

        horizontalEmplId = new HBox();
        horizontalEmplId.setAlignment(Pos.CENTER);

        horizontalFirstName = new HBox();
        horizontalFirstName.setAlignment(Pos.CENTER);

        horizontalLastName = new HBox();
        horizontalLastName.setAlignment(Pos.CENTER);

    }

    private void iniatlizeLayout() {
        horizontalEmplId.getChildren().addAll(emplIdLabel, emplIdTextField);
        horizontalFirstName.getChildren().addAll(firstNameLabel, firstNameTextField);
        horizontalLastName.getChildren().addAll(lastNameLabel, lastNameTextField);

        getChildren().addAll(horizontalEmplId, horizontalFirstName, horizontalLastName);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
    }

    public Integer getEmplIdTextField() {
        Integer emplId = null;

        emplId = Integer.parseInt(emplIdTextField.getText());

        return emplId;
    }

    public String getFirstNameTextField() {
        return firstNameTextField.getText();
    }

    public String getLastNameTextField() {
        return lastNameTextField.getText();
    }

    public Student getStudent() {
        return new Student(getEmplIdTextField(), getFirstNameTextField(), getLastNameTextField());
    }

}
