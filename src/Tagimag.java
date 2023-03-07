import java.io.File;
import java.util.ArrayList;

public class Tagimag {


    public static void main(String[] args) throws Exception {
        // FileItem testfile = new FileItem("/home/taygahoshi/Pictures/index.png", "Unknown", "Unknown");
        // System.out.println("File is called: " + testfile.name);
        
        LibraryScanner scn = new LibraryScanner();

        ArrayList<FileItem> filesFromLibrary = scn.scanLibrary("/home/taygahoshi/Pictures/");
        

        for (FileItem item : filesFromLibrary) {

            System.out.println(item.toString());

        }

        
    }
}
