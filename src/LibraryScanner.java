import java.io.File;
import java.util.ArrayList;

public class LibraryScanner {

    public ArrayList<FileItem> scanLibraries(ArrayList<String> libraryPaths) {
        ArrayList<FileItem> foundFiles = new ArrayList<FileItem>();

        for (String libfolder : libraryPaths) {

            foundFiles.addAll(scanLibrary(libfolder));
            
        }

        return foundFiles;

    }

    private ArrayList<FileItem> scanLibrary(String libraryPath) {
        // scans one library folder recursively

        ArrayList<FileItem> foundFiles = new ArrayList<FileItem>();

        File libraryFolder = new File(libraryPath);
        File[] libraryItemList = libraryFolder.listFiles();


        for (File file : libraryItemList) {
            if (file.isFile()) {
                // add file to the file list
                foundFiles.add(new FileItem(file.getAbsolutePath(), "Unknown", "Unknown"));
            }
            else if (file.isDirectory()) {
                // recursively scan subfolders
                foundFiles.addAll(scanLibrary(file.getAbsolutePath())); 
            }
        }


        return foundFiles;
    }
}
