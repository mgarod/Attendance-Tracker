package GUI;

import Information.CompSciClass;
import Information.Course;
import Information.Professor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private ComboBox<Professor> professorNameComboBox;

    public RegistrationClassEntryBox() {
        this(0);
    }

    public RegistrationClassEntryBox(double spacing) {
        super(spacing);
        initializeFields();
        initializeLayout();
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

        for (Course course : Course.values()) {
            checkBox = new CheckBox();
            checkBox.setMinWidth(25);

            courseNameLabel = new Label(course.toString());
            courseNameLabel.setMinWidth(100);

            professorNameComboBox = new ComboBox<>();
            populateProfessorNameComboBox(professorNameComboBox, course);
            professorNameComboBox.setMinWidth(300);

            hBox = new HBox(checkBox, courseNameLabel, professorNameComboBox);
            possibleCourses.add(hBox);
        }
    }

    private void initializeLayout() {
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
                Professor professor = ((ComboBox<Professor>) row.getChildren().get(2)).getValue();
                enrolledcourses.add(new CompSciClass(Course.getCourse(courseName), professor));
            }
        }
        return enrolledcourses;
    }

    private void populateProfessorNameComboBox(ComboBox<Professor> comboBox, Course course){
        for (Professor professor : course.getProfessors()){
            comboBox.getItems().add(professor);
        }
    }
}
