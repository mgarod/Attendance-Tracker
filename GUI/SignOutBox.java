package GUI;

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
    private ListView<Integer> listView;
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
        listView = new ListView<Integer>();
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

    private void addActiveStudent(Integer emplId) {
        listView.getItems().add(emplId);
    }

    private void signOutStudent(Integer emplId) {
        listView.getItems().removeAll(emplId);
    }
}
