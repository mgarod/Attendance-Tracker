package GUI;

import DB.MdbInterface;
import Information.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InterruptedIOException;

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
        /*
        primaryStage.setOnCloseRequest(event -> {
            PopUp.display("Error","Please do not close this window");
            event.consume();
        });
        */
        signInBox = new SignInBox();
        signInBox.setPadding(new Insets(50));
        signInBox.setStyle("-fx-border-style: hidden solid hidden hidden");
        signInBox.getSignInButton().setOnAction(event -> handleEmpl());

        signOutBox = new SignOutBox();
        signOutBox.setPadding(new Insets(50));
        signOutBox.getSignOutButton().setOnAction(event -> handleSignOut(signOutBox.getSelectedStudent()));

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

    // Throws NumberFormatException if Integer cannot parse EMPL ID
    private int getEmpl() {
        return Integer.parseInt(signInBox.getTextField().getText());
    }

    // Precondition: emplId is a valid integer which has already been parsed
    // Throws IllegalArgumentException if emplId is not 8 digits
    private boolean validateEmpl(int emplId){
        if(emplId < 9999999 || emplId > 99999999){
            throw new IllegalArgumentException("EMPL ID must be exactly 8 digits long");
        }
        return true;
    }

    private void handleEmpl() {
        int emplId;
        try {
            emplId = getEmpl();
            validateEmpl(emplId);
        } catch (NumberFormatException e) {
            signInBox.getTextField().clear();
            PopUp.display("Error", "EMPL ID field must contain only numbers\n" +
                    "and EMPL ID must be exactly 8 digits long");
            throw new NumberFormatException("EMPL ID must contain only numbers");
        } catch (IllegalArgumentException e) {
            signInBox.getTextField().clear();
            PopUp.display("Error", "EMPL ID field must contain only numbers\n" +
                    "and EMPL ID must be exactly 8 digits long");
            throw new IllegalArgumentException("EMPL ID must be exactly 8 digits long");
        }

        if (mdb.studentExists(emplId)) {
            student = mdb.getStudentByEmplId(emplId);
            signOutBox.addActiveStudent(mdb.getStudentByEmplId(emplId));
            mdb.signIn(emplId);
        } else {
            try {
                new RegistrationWindow();
                student = mdb.getStudentByEmplId(emplId);
                signOutBox.addActiveStudent(mdb.getStudentByEmplId(emplId));
                mdb.signIn(emplId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        signInBox.getTextField().clear();
    }

    private void handleSignOut(Student student) {
        SignOutReviewWindow window = new SignOutReviewWindow(student);
        if(window.successfulSignOut == true)
            signOutBox.signOutStudent(student);
    }
}
