import java.io.File;
import java.util.ArrayList;

public class LibraryScanner {
    public ArrayList<FileItem> scanLibrary(String libraryPath) {
        ArrayList<FileItem> foundFiles = new ArrayList<FileItem>();

        File libraryFolder = new File(libraryPath);
        File[] libraryItemList = libraryFolder.listFiles();


        for (File file : libraryItemList) {
            if (file.isFile()) {
                // System.out.println(file.getName() + " is a file.");
                foundFiles.add(new FileItem(file.getAbsolutePath(), "Unknown", "Unknown"));
            }
            else if (file.isDirectory()) {
                // System.out.println(file.getName() + " is a directory. Recursively searching.");
                foundFiles.addAll(scanLibrary(file.getAbsolutePath())); 
            }
        }


        return foundFiles;
    }
}
