public class FileItem {
    
    public String path;
    public String name;
    public String creator;
    public String group;

    public FileItem(String filepath, String filecreator, String filegroup) {

        path = filepath;
        String[] pathArr = path.split("/");
        name = pathArr[pathArr.length - 1];
        creator = filecreator;
        group = filegroup;

    }

    public String toString() { return path + " by " + creator + "@" + group; }
    

}
