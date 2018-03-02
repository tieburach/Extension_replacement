package extensionReplacement;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

class Filter {

    ArrayList<File> fileList = new ArrayList();
    void finder(File directory, String extensionName){

        directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(extensionName)){
                    File file = new File(filename);
                    fileList.add(file);
                }
                return filename.endsWith(extensionName);
            }
        });

    }

}