package extensionReplacement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class FileWriter {
    private String path;
    private byte[] bytes;

    FileWriter(String path, byte[] bytes) throws FileNotFoundException {
        this.bytes=bytes;
        this.path = path;
    }

    void write() throws IOException {
        FileOutputStream stream = new FileOutputStream(path);
        stream.write(bytes);
        stream.close();
    }


}
