package GUI;

import DB.MdbInterface;
import Information.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene scene;
    private HBox horizontalLayout;
    private SignInBox signInBox;
    private SignOutBox signOutBox;
    private static MdbInterface mdb;
    Student student;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hunter CS Tutoring Center");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        signInBox = new SignInBox();
        signInBox.setPadding(new Insets(50));
        signInBox.setStyle("-fx-border-style: hidden solid hidden hidden");
        signInBox.getSignInButton().setOnAction(event -> handleEmpl());

        signOutBox = new SignOutBox();
        signOutBox.setPadding(new Insets(50));

        horizontalLayout = new HBox();
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.getChildren().addAll(signInBox, signOutBox);
        HBox.setMargin(signInBox, new Insets(20, 0, 20, 0));
        HBox.setMargin(signOutBox, new Insets(20, 0, 20, 0));

        scene = new Scene(horizontalLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        mdb = new MdbInterface();
        launch(args);
    }

    public static MdbInterface getMdb() {
        return mdb;
    }

    private int getEmpl() {
        return Integer.parseInt(signInBox.getTextField().getText());
    }

    private void handleEmpl() {
        int emplId = getEmpl();
        if (mdb.studentExists(emplId)) {
            student = mdb.getStudentByEmplId(emplId);
            signOutBox.addActiveStudent(student.getFirstName() + " " + student.getLastName());
        } else {
            try {
                new RegistrationWindow();
                student = mdb.getStudentByEmplId(emplId);
                signOutBox.addActiveStudent(student.getFirstName() + " " + student.getLastName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
