package GUI;

import Information.CompSciClass;
import Information.Student;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegistrationWindow extends Application {

    private Button registerButton;
    private HBox buttonHBox;
    private Scene scene;
    private VBox vBox, studentInfo, classEntry;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("New Student Registration");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);

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
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void registerNewStudent() {
        try {
            Student student = ((RegistrationStudentInfoEntryBox) studentInfo).getStudent();
            ArrayList<CompSciClass> classes = ((RegistrationClassEntryBox) classEntry).getEnrolledCourses();
            student.setCurrentClasses(classes);
            String text = student.getFirstName() + student.getLastName() + "\nYou are enrolled in:\n";
            for (CompSciClass c : student.getCurrentClasses()) {
                text += c + "\n";
            }
            PopUp.display("Confirmation", text);
        } catch (Exception e) {
            e.printStackTrace();
            PopUp.display("Error", "Something went wrong, try again");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
