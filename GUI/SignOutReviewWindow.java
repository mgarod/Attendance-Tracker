package GUI;

import Information.SignOutData;
import Information.Student;
import Information.Topics135;
import Information.Tutor;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignOutReviewWindow extends Stage {

    private Student student;
    private SignOutData sod;
    private VBox mainVBox;
    private RegistrationStudentInfoEntryBox studentInfo;
    private ChoiceBox<Tutor> theTutor;
    private ChoiceBox<Integer> levelOfLearning;
    private Label theTutorLabel, levelOfLearningLabel;
    private HBox theTutorLayout, levelOfLearningLayout;
    private VBox vertical135Layout;
    private CheckBox is135;
    private ListView<Topics135> topicsListView;
    private Scene scene;
    private HBox buttonHBox;
    private Button signOutButton;

    public boolean successfulSignOut = false;

    SignOutReviewWindow(Student student) {
        this.student = student;
        display();
    }

    private void display() {
        initModality(Modality.APPLICATION_MODAL);

        setTitle("Student Review");
        setMinWidth(250);
        setMinHeight(300);

        signOutButton = new Button("SignOut");
        signOutButton.setOnAction(event -> signOutInDB());
        buttonHBox = new HBox(signOutButton);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);

        studentInfo = new RegistrationStudentInfoEntryBox();
        // Disabling all fields
        setValue(studentInfo.getEmplIdTextField(), String.valueOf(student.getEmplId()));
        setValue(studentInfo.getFirstNameTextField(), student.getFirstName());
        setValue(studentInfo.getLastNameTextField(), student.getLastName());
        studentInfo.getYearChoiceBox().setValue(student.getYear());
        studentInfo.getYearChoiceBox().setDisable(true);

        theTutor = new ChoiceBox<>();
        theTutor.getItems().addAll(Tutor.values());
        theTutorLabel = new Label("Tutor ");
        theTutorLayout = new HBox();
        theTutorLayout.getChildren().addAll(theTutorLabel, theTutor);

        is135 = new CheckBox("Discussed 135 Topics? ");
        is135.setOnAction(event -> handleListView());

        // Hidden until is135 is checked
        levelOfLearning = new ChoiceBox<>();
        levelOfLearning.getItems().addAll(1, 2, 3, 4);
        levelOfLearningLabel = new Label("Level Of Learning ");
        levelOfLearningLayout = new HBox();
        levelOfLearningLayout.getChildren().addAll(levelOfLearningLabel, levelOfLearning);
        levelOfLearningLayout.setVisible(false);
        levelOfLearningLayout.setManaged(false);

        // Hidden until is135 is checked
        topicsListView = new ListView<>();
        topicsListView.getItems().addAll(Topics135.values());
        topicsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        topicsListView.setVisible(false);
        topicsListView.setManaged(false);

        vertical135Layout = new VBox(10);
        vertical135Layout.getChildren().addAll(is135, levelOfLearningLayout, topicsListView);

        mainVBox = new VBox(20);
        mainVBox.getChildren().addAll(studentInfo, theTutorLayout, vertical135Layout, buttonHBox);
        mainVBox.setMargin(vertical135Layout, new Insets(0, 20, 0, 20));
        mainVBox.setMargin(buttonHBox, new Insets(20));
        mainVBox.setAlignment(Pos.TOP_CENTER);

        scene = new Scene(mainVBox);
        setScene(scene);
        showAndWait();
    }

    private void setValue(TextField entry, String value) {
        entry.setText(value);
        entry.setDisable(true);
    }

    private void handleListView() {
        if (is135.isSelected()) {
            topicsListView.setVisible(true);
            topicsListView.setManaged(true);
            levelOfLearningLayout.setVisible(true);
            levelOfLearningLayout.setManaged(true);
            setHeight(600);
        } else {
            topicsListView.setVisible(false);
            topicsListView.setManaged(false);
            levelOfLearningLayout.setVisible(false);
            levelOfLearningLayout.setManaged(false);
            setHeight(400);
        }
    }

    private void signOutInDB() {
        checkAllFieldsHandled();

        ObservableList<Topics135> selectedTopics = topicsListView.getSelectionModel().getSelectedItems();
        ArrayList<Topics135> arrayOfTopics = new ArrayList<>();
        if (selectedTopics != null) {
            for (Topics135 t : selectedTopics) {
                arrayOfTopics.add(t);
            }
        }

        if (arrayOfTopics.isEmpty()) {
            arrayOfTopics = null;
        }

        // If not in 135, then Level of Learning will be null. Do not insert level of learning in to DB.
        int levelOfLearningValue = -1;
        if (levelOfLearning.getValue() != null){
            levelOfLearningValue = levelOfLearning.getValue();
        }

        sod = new SignOutData(student.getEmplId(), arrayOfTopics, levelOfLearningValue, theTutor.getValue());

        Main.getMdb().signOut(sod);
        successfulSignOut = true;
        close();
    }

    private void checkAllFieldsHandled() {
        if (theTutor.getValue() == null) {
            PopUp.display("Error", "Please select a tutor");
            throw new IllegalArgumentException("Tutor was not selected");
        }
        else if (is135.isSelected()) {
            if (levelOfLearning.getValue() == null) {
                PopUp.display("Error", "Please select a level of learning for the student");
                throw new IllegalArgumentException("Level of Learning was not selected");
            }

            if (topicsListView.getSelectionModel().getSelectedItems().size() == 0) {
                PopUp.display("Error", "Select at least one topic of discussion\n\n" +
                        "If you are unsure, please select \'Other\'");
                throw new IllegalArgumentException("No class topic was selected");
            }
        }
    }
}
