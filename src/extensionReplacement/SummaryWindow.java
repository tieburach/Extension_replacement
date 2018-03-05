package extensionReplacement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SummaryWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ViewSummaryWindow.fxml"));
        primaryStage.setTitle("Podsumowanie");
        Scene scene = new Scene(root, 400, 300);
        String css = Main.class.getResource("css\\style.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
