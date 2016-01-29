package GUI;

import Information.CompSciClass;
import Information.Courses;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class RegistrationClassEntryBox extends VBox {

    private ArrayList<HBox> possibleCourses;
    private ArrayList<CompSciClass> enrolledcourses;
    private HBox title;
    private Label emptyLabel, classesLabel, professorLabel;

    // Placeholder objects
    private HBox hBox;
    private CheckBox checkBox;
    private Label courseNameLabel;
    private TextField professorNameTextField;

    public RegistrationClassEntryBox() {
        this(0);
    }

    public RegistrationClassEntryBox(double spacing) {
        super(spacing);
        initializeFields();
        iniatlizeLayout();
    }

    private void initializeFields() {
        possibleCourses = new ArrayList<>();
        enrolledcourses = new ArrayList<>();

        emptyLabel = new Label();
        emptyLabel.setMinWidth(30);

        classesLabel = new Label("Classes");
        classesLabel.setMinWidth(105);
        classesLabel.setFont(new Font(16));

        professorLabel = new Label("Professor");
        professorLabel.setMinWidth(300);
        professorLabel.setFont(new Font(16));

        title = new HBox(emptyLabel, classesLabel, professorLabel);
        this.getChildren().add(title);

        for (Courses course : Courses.values()) {
            checkBox = new CheckBox();
            checkBox.setMinWidth(25);

            courseNameLabel = new Label(course.toString());
            courseNameLabel.setMinWidth(100);

            professorNameTextField = new TextField();
            professorNameTextField.setMinWidth(300);

            hBox = new HBox(checkBox, courseNameLabel, professorNameTextField);
            possibleCourses.add(hBox);
        }
    }

    private void iniatlizeLayout() {
        for (HBox row : possibleCourses) {
            row.setAlignment(Pos.CENTER);
            this.setMargin(row, new Insets(5, 0, 0, 0));
            this.getChildren().add(row);
        }
        setMargin(title, new Insets(0, 0, 10, 0));
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
    }

    public ArrayList<CompSciClass> getEnrolledCourses() {
        for (HBox row : possibleCourses) {
            if (((CheckBox) row.getChildren().get(0)).isSelected()) {
                String courseName = ((Label) row.getChildren().get(1)).getText();
                String professor = ((TextField) row.getChildren().get(2)).getText();
                enrolledcourses.add(new CompSciClass(Courses.getCourse(courseName), professor));
            }
        }
        return enrolledcourses;
    }
}
