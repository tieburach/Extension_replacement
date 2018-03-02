package extensionReplacement;

import java.io.*;
import java.nio.file.Files;

public class Replacer {
    private File file;
    private int counter;
    private byte[] bytesArray = new byte[(int) (file != null ? file.length() : 0)];
    private String string;

    Replacer(File file) {
        this.file = file;
    }

    void convertToByte() throws IOException {
        System.out.println(file.getAbsolutePath());
        System.out.println(file.canRead());
        FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray);
        fis.close();
        System.out.println("Moj bytesarray");
        System.out.println(bytesArray);

    }

    void saveFromByte() {
        File fnew = file;
        try {
            FileWriter f2 = new FileWriter(fnew, false);
            f2.write(string);
            f2.close();
    //        System.out.println("Jestem w savefrombyte");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void replaceBytes(String first, String last) {
        string = new String(bytesArray);
        if (string.contains(first)){
            counter++;
            System.out.println(counter);
            System.out.println("Tu jestem w replace bytes");
        }
        System.out.println("moja postac string w replace dla pliku +" + file + " to " + string);
        string = string.replace(first,last);
        bytesArray = string.getBytes();
        System.out.println("teraz bytesarray w replace"+bytesArray);
     //   System.out.println("Jestem w replace na zewnatrz");
    }

}
