import java.util.ArrayList;

public class TagController {

    public TagController() {

        // initialize db object
        DataModelHandler db = new DataModelHandler();
        db.queryAddLibrary("./sample_library_folders/sample_library_1");
        db.queryAddLibrary("./sample_library_folders/sample_library_2");

        // initialize scanner object
        LibraryScanner scn = new LibraryScanner();
        
        // get library paths from db
        ArrayList<String> sampleLibs = db.getLibraryPaths();
        ArrayList<FileItem> filesFromLibrary = scn.scanLibraries(sampleLibs);

        // add files to the db
        for (FileItem item : filesFromLibrary) {
            db.queryAddFile(item);
        }

    }

}