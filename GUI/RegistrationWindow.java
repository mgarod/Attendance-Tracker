package GUI;

import Information.CompSciClass;
import Information.Student;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegistrationWindow extends Stage {

    private Button registerButton;
    private HBox buttonHBox;
    private Scene scene;
    private VBox vBox, studentInfo, classEntry;
    private Student student;

    RegistrationWindow() {
        display();
    }

    public void display() {
        initModality(Modality.APPLICATION_MODAL);

        setTitle("New Student Registration");
        setMinWidth(500);
        setMinHeight(400);

        registerButton = new Button("Register");
        registerButton.setOnAction(event -> registerNewStudent());
        buttonHBox = new HBox(registerButton);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        vBox = new VBox();
        studentInfo = new RegistrationStudentInfoEntryBox();
        classEntry = new RegistrationClassEntryBox();
        vBox.getChildren().addAll(studentInfo, classEntry, buttonHBox);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        setScene(scene);
        showAndWait();
    }

    private void registerNewStudent() {
        try {
            student = ((RegistrationStudentInfoEntryBox) studentInfo).getStudent();
            ArrayList<CompSciClass> classes = ((RegistrationClassEntryBox) classEntry).getEnrolledCourses();
            student.setCurrentClasses(classes);
            Main.getMdb().registerStudent(student);
            close();
        } catch (Exception e) {
            e.printStackTrace();
            PopUp.display("Error", "Something went wrong, try again");
        }
    }
}
