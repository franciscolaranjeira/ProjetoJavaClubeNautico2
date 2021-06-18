package db;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {

    private static Connection conn = null;
    final static String DB_URL = "jdbc:mysql://localhost:3306/clube_nautico";
    final static String USER = "root";
    final static String PASS = "";

    public static Connection getConnection() {
        Driver mysqlDriver = null;
        if (conn == null) {
            try {

                mysqlDriver = new com.mysql.cj.jdbc.Driver();//fazer o registo do driver

                DriverManager.registerDriver(mysqlDriver);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException ex) {
                throw new DbException(ex.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new DbException(ex.getMessage());
            }
        }
    }

    public static void closeStatement(PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
