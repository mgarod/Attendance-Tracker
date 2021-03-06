package GUI;

import Information.CompSciClass;
import Information.Student;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.lang.Exception;

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

            if(student.getFirstName().length() == 0 || student.getLastName().length() == 0){
                System.out.println("Empty names");
                throw new IllegalArgumentException();
            }

            ArrayList<CompSciClass> classes = ((RegistrationClassEntryBox) classEntry).getEnrolledCourses();
            student.setCurrentClasses(classes);
            Main.getMdb().registerStudent(student);
            close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            PopUp.display("Error", "Neither first nor last name field may be empty");
        }
        catch (Exception e) {
            e.printStackTrace();
            PopUp.display("Error", "Something went wrong, try again");
        }
    }
}
