import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataModelHandler {

    private Connection conn = null;
    private String dbname;

    public DataModelHandler() {
        // build database
        dbname = "tagimag.db";
        createNewDatabase();
    }

    private boolean createNewTables() {
        // creates tables
        
        connect();
        try {
            String files_sql = "CREATE TABLE IF NOT EXISTS files (\n"
                                        + " id  INTEGER NOT NULL UNIQUE,\n"
                                        + " path    TEXT NOT NULL UNIQUE,\n"
                                        + " filename TEXT NOT NULL DEFAULT 'File Not Found',\n"
                                        + " creator TEXT NOT NULL DEFAULT 'Unknown Artist',\n"
                                        + " creatorgroup   TEXT NOT NULL DEFAULT 'Unknown Group',\n"
                                        + "PRIMARY KEY(id AUTOINCREMENT)\n"
                                        + ");";
            String libraries_sql = "CREATE TABLE IF NOT EXISTS libraries (\n"
                                        + " id  INTEGER NOT NULL UNIQUE,\n"
                                        + " path    TEXT NOT NULL UNIQUE,\n"
                                        + "PRIMARY KEY(id AUTOINCREMENT)\n"
                                        + ");";

            Statement files_stmt = conn.createStatement();
            files_stmt.execute(files_sql);
            System.out.println("Created table: files");

            Statement libraries_stmt = conn.createStatement();
            libraries_stmt.execute(libraries_sql);
            System.out.println("Created table: libraries");

            disconnect();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
        }

        return false;
    }

    private boolean createNewDatabase() {
        // creates a new database
        String url = "jdbc:sqlite:" + dbname;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                System.out.println("Creating tables...");
                createNewTables();
            }

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean connect() {
        // connects to database

        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbname;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean disconnect() {
        // disconnects from database
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<String> getLibraryPaths() {
        // gets library paths from database

        ArrayList<String> paths = new ArrayList<String>();

        String sql = "SELECT path FROM libraries";
        connect();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                paths.add(rs.getString("path"));
            }
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
        }

        return paths;
    }

    public boolean queryAddLibrary(String libraryPath) {
        // add a new library to database

        String sql = "INSERT INTO libraries(path) VALUES(?)";
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
        // add a new file to the database (only happens on scanLibraries)

        String sql = "INSERT INTO files(path,filename,creator,creatorgroup) VALUES(?,?,?,?)";
        connect();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, file.path);
            pstmt.setString(2, file.name);
            pstmt.setString(3, file.creator);
            pstmt.setString(4, file.group);
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
