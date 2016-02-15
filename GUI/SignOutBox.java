package GUI;

import Information.Student;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignOutBox extends VBox {

    private Label signOutBoxLabel;
    private ListView<Student> listView;
    private Button signOutButton;
    private HBox horizontalLayout;

    public SignOutBox() {
        this(0);
    }

    public SignOutBox(double spacing) {
        super(spacing);
        initializeFields();
        initializeLayout();
    }

    private void initializeFields() {
        signOutBoxLabel = new Label("Sign Out");
        signOutBoxLabel.setFont(new Font(20));
        listView = new ListView<>();
        listView.setMaxSize(200, 150);
        signOutButton = new Button("Sign Out");
        horizontalLayout = new HBox();
        horizontalLayout.setAlignment(Pos.BOTTOM_RIGHT);
    }

    private void initializeLayout() {
        horizontalLayout.getChildren().add(signOutButton);
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(signOutBoxLabel, listView, horizontalLayout);
        setMargin(listView, new Insets(20, 0, 20, 0));
        setVgrow(horizontalLayout, Priority.ALWAYS);
        setMinSize(250, 250);
    }

    public Button getSignOutButton() {
        return signOutButton;
    }

    public Student getSelectedStudent() {
        Student student = listView.getSelectionModel().getSelectedItem();
        if (student == null) {
            PopUp.display("Error", "No student was selected");
            throw new NullPointerException("No Student was selected");
        }
        return student;
    }

    public void addActiveStudent(Student student) {
        listView.getItems().add(student);
    }

    public void signOutStudent(Student student) {
        listView.getItems().remove(student);
    }

    public boolean containsActiveStudentByEmplId(int emplId) {
        ObservableList<Student> list = listView.getItems();
        for (Student s : list)
            if (s.getEmplId() == emplId)
                return true;

        return false;
    }
}
