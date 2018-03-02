package extensionReplacement;

import java.io.File;
import java.util.ArrayList;

class Filter {

    ArrayList<File> fileList = new ArrayList();
    void finder(File directory, String extensionName){
        System.out.println(directory.getAbsolutePath());
        directory.listFiles((dir, filename) -> {
            if (filename.endsWith(extensionName)){
                File file = new File(directory.getAbsolutePath() + "\\" +filename);
                fileList.add(file);
            }
            return filename.endsWith(extensionName);
        });

    }

}