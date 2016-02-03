package GUI;

import Information.ClassYear;
import Information.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationStudentInfoEntryBox extends VBox {

    private Label emplIdLabel, firstNameLabel, lastNameLabel, classyearLabel;
    private TextField emplIdTextField;
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private ChoiceBox<ClassYear> yearChoiceBox;
    private HBox horizontalEmplId, horizontalFirstName, horizontalLastName, horizontalChoiceBox;

    public RegistrationStudentInfoEntryBox() {
        this(0);
    }

    public RegistrationStudentInfoEntryBox(double spacing) {
        super(spacing);
        initializeFields();
        iniatlizeLayout();
    }

    private void initializeFields() {
        emplIdLabel = makeNewLabel("EMPL ID");
        firstNameLabel = makeNewLabel("First Name");
        lastNameLabel = makeNewLabel("Last Name");
        classyearLabel = makeNewLabel("Class Year");

        emplIdTextField = new TextField();
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        yearChoiceBox = new ChoiceBox<>();
        yearChoiceBox.getItems().addAll(ClassYear.values());
        yearChoiceBox.setValue(ClassYear.FRESHMAN);

        horizontalEmplId = makeNewHorizontalLayout();
        horizontalFirstName = makeNewHorizontalLayout();
        horizontalLastName = makeNewHorizontalLayout();
        horizontalChoiceBox = makeNewHorizontalLayout();
    }

    private Label makeNewLabel(String text) {
        Label label = new Label(text);
        label.setMinWidth(80);
        return label;
    }

    private HBox makeNewHorizontalLayout() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private void iniatlizeLayout() {
        horizontalEmplId.getChildren().addAll(emplIdLabel, emplIdTextField);
        horizontalFirstName.getChildren().addAll(firstNameLabel, firstNameTextField);
        horizontalLastName.getChildren().addAll(lastNameLabel, lastNameTextField);
        horizontalChoiceBox.getChildren().addAll(classyearLabel, yearChoiceBox);

        getChildren().addAll(horizontalEmplId, horizontalFirstName, horizontalLastName, horizontalChoiceBox);

        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
    }

    public Integer getEmplId() {
        Integer emplId = null;

        emplId = Integer.parseInt(emplIdTextField.getText());

        return emplId;
    }

    public String getFirstNameText() {
        return firstNameTextField.getText();
    }

    public String getLastNameText() {
        return lastNameTextField.getText();
    }

    public ClassYear getClassYear() {
        return yearChoiceBox.getValue();
    }

    public Student getStudent() {
        return new Student(getEmplId(), getFirstNameText(), getLastNameText(), getClassYear());
    }
}
