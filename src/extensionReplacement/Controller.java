package extensionReplacement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.nio.file.Files;

public class Controller {

    @FXML
    private TextField directorySelected;
    public TextField bytesAfter;
    public Button browseButton;
    public Button submitButton;
    public TextField extensionForSearch;
    public TextField bytesBefore;

    private File selectedDirectory;

    public void browseDirectoryAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder:");
        selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());
        directorySelected.setText(" Twoj folder to " + selectedDirectory);
    }

    public void submitAction() throws IOException {

        Filter filter = new Filter();
        filter.finder(selectedDirectory, extensionForSearch.getText());


        for (File o : filter.fileList) {

            byte[] bytes = Files.readAllBytes(new File(o.getAbsolutePath()).toPath());
            ByteArrayInputStream bis =
                    new ByteArrayInputStream(bytes);

            byte[] search = bytesBefore.getText().getBytes("UTF-8");

            byte[] replacement = bytesAfter.getText().getBytes("UTF-8");


            InputStream ris = new ReplacingInputStream(bis, search, replacement);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int b;
            while (-1 != (b = ris.read()))
                bos.write(b);
            bos.close();
            ris.close();
            bis.close();
            FileWriter fileWriter = new FileWriter(o.getAbsolutePath(), bos.toByteArray());
            fileWriter.write();
        }
    }}
