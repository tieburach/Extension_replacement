package extensionReplacement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

public class ControllerMainWindow {

    @FXML
    private static TextField directorySelected;
    public TextField bytesAfter;
    public Button browseButton;
    public Button submitButton;
    public static TextField extensionForSearch;
    public TextField bytesBefore;
    private File selectedDirectory;
    private static int howManyFiles = 0;
    private static int howManyChanges = 0;


    public static String getDirectorySelected() {
        return directorySelected.toString();
    }

    public static String getExtension(){
        return extensionForSearch.toString();
    }

    public static int getHowManyFiles() {
        return howManyFiles;
    }

    public static int getHowManyChanges() {
        return howManyChanges;
    }

    public void browseDirectoryAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder:");
        selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());
        directorySelected.setText(" Twoj folder to " + selectedDirectory);
    }

    public void submitAction() throws IOException {

        Filter filter = new Filter();
        filter.finder(selectedDirectory, extensionForSearch.getText());
        howManyFiles = filter.fileList.size();

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
            FileWriter fileWriter = new FileWriter(o.getAbsolutePath(), bos.toByteArray());
            fileWriter.write();
        }

        Stage summaryStage = new Stage();
        SummaryWindow summaryWindow = new SummaryWindow();
        try {
            summaryWindow.start(summaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

