import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataModelHandler {

    private Connection conn = null;

    private boolean connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:tagimag.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean disconnect() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public ArrayList<String> getLibraryPaths() {
        ArrayList<String> paths = new ArrayList<String>();

        String sql = "SELECT Path FROM libraries";
        connect();

        try {

            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
           // loop through the result set
            while (rs.next()) {
                paths.add(rs.getString("Path"));
            }
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
        }



        return paths;
    }

    public boolean queryAddLibrary(String libraryPath) {
        String sql = "INSERT INTO libraries(Path) VALUES(?)";
        connect();

        try {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, libraryPath);
            pstmt.executeUpdate();


            disconnect();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
        }


        return false;
    }

    public boolean queryAddFile(FileItem file) {

        String sql = "INSERT INTO files(Path,Filename,Creator) VALUES(?,?,?)";
        connect();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, file.path);
            pstmt.setString(2, file.name);
            pstmt.setString(3, file.creator);
            //pstmt.setString(4, file.group);
            pstmt.executeUpdate();

            disconnect();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
        }

        return false;
    } 

}
