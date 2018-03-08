package extensionReplacement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class ControllerMainWindow {

    private static Stage summaryStage;
    public ChoiceBox choiceBox;

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
    private byte []search;
    private byte [] replacement;

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

    private void loadAsString() throws UnsupportedEncodingException {
        search = bytesBefore.getText().getBytes("UTF-8");
        replacement = bytesAfter.getText().getBytes("UTF-8");

    }
    private void loadASByte(){
        String[] partsBefore = bytesBefore.getText().split(" ");
        String[] partsAfter = bytesAfter.getText().split(" ");
        search = new byte[partsBefore.length];
        replacement = new byte[partsAfter.length];
        for (int i =0; i<partsBefore.length; i++){
            search[i] = new Byte(partsBefore[i]);
        }
        for (int i =0; i<partsAfter.length; i++){
            replacement[i]= new Byte(partsAfter[i]);
        }

    }
    public void submitAction() throws IOException {
        if (selectedDirectory==null || (Objects.equals(choiceBox.getValue().toString(), ""))|| Objects.equals(bytesBefore.getText(), "") || Objects.equals(extensionForSearch.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            System.out.println(choiceBox.getValue().toString());
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

        if (Objects.equals(choiceBox.getValue().toString(), "String")) {
            loadAsString();
        }else if (Objects.equals(choiceBox.getValue().toString(), "Byte")){
            loadASByte();
        }else {
            System.out.println("error");
        }
        for (File o : filter.fileList) {
            byte[] bytes = Files.readAllBytes(new File(o.getAbsolutePath()).toPath());

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            InputStream ris = new ReplacingInputStream(bis, search, replacement);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while (-1 != (b = ris.read())) bos.write(b);
            bos.close();
            ris.close();
            bis.close();
            howManyChanges=0;
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

