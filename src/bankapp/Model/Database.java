
package bankapp.Model;

import java.sql.Connection;
import java.sql.DriverManager;
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/BankApp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}