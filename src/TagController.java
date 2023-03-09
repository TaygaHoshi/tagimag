import java.util.ArrayList;
import java.util.Scanner;

public class TagController {

    DataModelHandler db;
    LibraryScanner scn;

    String sample_lib_1 = "./sample_library_folders/sample_library_1";
    String sample_lib_2 = "./sample_library_folders/sample_library_2";


    public TagController() {

        // initialize db handler object
        db = new DataModelHandler();
        db.createNewDatabase();

        // initialize library scanner object
        scn = new LibraryScanner();
        
        // add libraries
        addLibrary(sample_lib_1);
        addLibrary(sample_lib_2);

        // scan libraries
        ArrayList<FileItem> filesFromLibrary = scn.scanLibraries(db.getLibraryPaths());

        // add files to the db
        for (FileItem item : filesFromLibrary) {
            addFile(item);
        }

        //Scanner s = new Scanner(System.in);
        //s.nextLine();
        //removeLibrary(sample_lib_1);
    }
    

    public boolean removeLibrary(String path) {
        
        try {
            db.queryRemoveLibrary(path);
            System.out.println("Removed library: " + path);

            db.queryRemoveMatchingFiles(path);
            System.out.println("Removed files of library: " + path);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to remove a library: " + path);
        }

        return false;
    }

    public boolean addLibrary(String path) {
        try {
            db.queryAddLibrary(path);
            System.out.println("Added library folder: " + path);
            scn.scanLibraries(db.getLibraryPaths());
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add a library folder: " + path);
        }

        return false;
    }

    public boolean  addFile(FileItem file) {

        try {
            db.queryAddFile(file);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add file: " + file.path);
        }

        return false;
    }



}