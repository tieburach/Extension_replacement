package extensionReplacement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerSummaryWindow {
    @FXML
    public Label howManyFiles;
    public Label howManyChanges;
    public Button finishButton;
    public Label howManyFilesNumber;
    public Label howManyChangesNumber;
    public Label description;
    public Label directory;

    public void finishSummary(ActionEvent actionEvent) {
    }
    @FXML
    void initialize(){
        directory.setText("Przeszukiwany folder: " + ControllerMainWindow.getDirectorySelected());
        howManyFiles.setText("Ile plików z rozszerzeniem .)"+ControllerMainWindow.getExtension());
        howManyFilesNumber.setText(" " + ControllerMainWindow.getHowManyFiles());
        howManyChangesNumber.setText(" " + ControllerMainWindow.getHowManyChanges());
        description.setText("Po inicjazacji");
        howManyChanges.setText("Po inicjalizacji");
        howManyFiles.setText("Po inicjalizacji");
    }
}
