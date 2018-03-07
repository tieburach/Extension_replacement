package extensionReplacement;

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
    public Label extension;

    private int howManyF = ControllerMainWindow.getHowManyFiles();
    private int howManyC = ControllerMainWindow.getHowManyChanges();

    public void finishSummary() {
        ControllerMainWindow.getSummaryStage().close();
    }
    @FXML
    void initialize(){
        directory.setText("Folder: " +ControllerMainWindow.getDirectory());
        extension.setText("Zadane rozszerzenie: ."+ControllerMainWindow.getExtension());
        howManyFilesNumber.setText(" " + howManyF);
        howManyChangesNumber.setText(" " + howManyC);
        if (howManyF==0){
            description.setText("Nie znaleziono żadnych plików o podanym rozszerzeniu.");
        }
        else if (howManyC==0){
            description.setText("W plikach nie znaleziono wystąpienia ciągu bajtów do zamiany.");
        }
        else {
            description.setText("Wykonano zamianę w plikach.");
        }
        howManyChanges.setText("Ile plików zostało znalezionych:");
        howManyFiles.setText("Ile zostało wykonanych podmian");
    }
}
