package extensionReplacement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class ControllerMainWindow {

    private static Stage summaryStage;

    static Stage getSummaryStage(){
        return summaryStage;
    }

    @FXML
    public Label howToUse;
    public Label title;
    public TextField directorySelected;
    public TextField bytesAfter;
    public Button browseButton;
    public Button submitButton;
    public TextField extensionForSearch;
    public TextField bytesBefore;
    private File selectedDirectory;
    private static int howManyFiles = 0;
    private static int howManyChanges = 0;
    private static String extension = "";
    private static String directory = "";

    static String getExtension() {
        return extension;
    }

    static String getDirectory() {
        return directory;
    }


    private void clearTextFields(){
        directorySelected.setText("");
        bytesBefore.setText("");
        bytesAfter.setText("");
        extensionForSearch.setText("");

    }

    static int getHowManyFiles() {
        return howManyFiles;
    }

    static int getHowManyChanges() {
        return howManyChanges;
    }

    public void browseDirectoryAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder:");
        selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());
        directorySelected.setText(" " + selectedDirectory);
    }

    public void submitAction() throws IOException {
        if (selectedDirectory==null || Objects.equals(bytesBefore.getText(), "") || Objects.equals(extensionForSearch.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Nie wypełniłeś wszystkich pól");
            alert.setContentText("Proszę, wypełnij wszystkie pola jeżeli chcesz kontynuować");
            alert.showAndWait();
        }
        else {
        Filter filter = new Filter();
        filter.finder(selectedDirectory, extensionForSearch.getText());
        howManyFiles = filter.fileList.size();
        extension = extensionForSearch.getText();
        directory = selectedDirectory.toString();

        for (File o : filter.fileList) {
            byte[] bytes = Files.readAllBytes(new File(o.getAbsolutePath()).toPath());
            byte[] search = bytesBefore.getText().getBytes("UTF-8");
            byte[] replacement = bytesAfter.getText().getBytes("UTF-8");
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            InputStream ris = new ReplacingInputStream(bis, search, replacement);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while (-1 != (b = ris.read())) bos.write(b);
            bos.close();
            ris.close();
            bis.close();
            howManyChanges = ReplacingInputStream.getHowMany();
            FileWriter fileWriter = new FileWriter(o.getAbsolutePath(), bos.toByteArray());
            fileWriter.write();
        }
        clearTextFields();
        summaryStage = new Stage();
        SummaryWindow summaryWindow = new SummaryWindow();
        try {
            summaryWindow.start(summaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
}

