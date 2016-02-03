package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignInBox extends VBox {

    private Label signInBoxLabel, emplIdPromptLabel;
    private TextField textField;
    private Button signInButton;
    private HBox horizontalLayout;

    public SignInBox() {
        this(0);
    }

    public SignInBox(double spacing) {
        super(spacing);
        initializeFields();
        initializeLayout();
    }

    private void initializeFields() {
        signInBoxLabel = new Label("Sign In");
        signInBoxLabel.setFont(new Font(20));

        emplIdPromptLabel = new Label("Please enter your EMPL ID");
        textField = new TextField();
        textField.setPromptText("########");

        signInButton = new Button("Sign In");

        horizontalLayout = new HBox();
        horizontalLayout.setAlignment(Pos.BOTTOM_RIGHT);
    }

    private void initializeLayout() {
        horizontalLayout.getChildren().add(signInButton);
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(signInBoxLabel, emplIdPromptLabel, textField, horizontalLayout);
        setVgrow(horizontalLayout, Priority.ALWAYS);
        setMargin(emplIdPromptLabel, new Insets(20, 0, 0, 0));
        setMinSize(250, 250);
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getSignInButton() {
        return signInButton;
    }
}
