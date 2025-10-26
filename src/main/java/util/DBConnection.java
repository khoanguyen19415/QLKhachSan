package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=QLKhachSan;encrypt=false";
    static String user = "sa";
    static String pass = "sa"; // sửa lại cho đúng mật khẩu SQL Server của bạn

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("ket noi CSDL thanh cong!");
        } catch (Exception ex) {
            System.out.println("loi ket noi CSDL: " + ex.toString());
        }
        return conn;
    }

    public static void main(String[] args) {
        System.out.println(DBConnection.getConnection());
    }
}
