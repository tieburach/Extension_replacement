package extensionReplacement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private TextField directorySelected;
    public TextField bytesAfter;
    public Button browseButton;
    public Button submitButton;
    public TextField extensionForSearch;
    public TextField bytesBefore;

    public File getSelectedDirectory() {
        return selectedDirectory;
    }

    public void setSelectedDirectory(File selectedDirectory) {
        this.selectedDirectory = selectedDirectory;
    }

    private File selectedDirectory;

    public void browseDirectoryAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder:");
        selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());
        directorySelected.setText(" Twoj folder to " + selectedDirectory);
    }

    public void submitAction(ActionEvent actionEvent) throws IOException {
        Filter filter = new Filter();
        filter.finder(selectedDirectory, extensionForSearch.getText());

        for (Object o: filter.fileList) {
            Replacer replacer = new Replacer((File)o);
            replacer.convertToByte();
            replacer.replaceBytes(bytesBefore.getText(),bytesAfter.getText());
            replacer.saveFromByte();
        }
        System.out.println(""+ filter.fileList);
    }
}
