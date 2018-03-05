package extensionReplacement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    private static Stage primaryStage;

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewMainWindow.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 600, 600);
        String css = Main.class.getResource("css\\style.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
