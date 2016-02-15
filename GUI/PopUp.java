package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp {

    public static void display(String title, String message) {
        Stage mainStage = new Stage();

        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setTitle(title);
        mainStage.setMinWidth(250);

        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> mainStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
