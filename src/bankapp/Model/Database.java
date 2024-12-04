package bankapp.Model;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "BankApp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            // Cek atau buat database dan tabel saat koneksi pertama
            createDatabaseAndTables();
            return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createDatabaseAndTables() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Buat Database jika belum ada
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement.executeUpdate(createDatabaseSQL);

            // Gunakan database yang baru dibuat
            String useDatabaseSQL = "USE " + DB_NAME;
            statement.executeUpdate(useDatabaseSQL);

            // Buat tabel Users
            String createUsersTableSQL = """
                CREATE TABLE IF NOT EXISTS Users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    nama VARCHAR(100) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                )
            """;
            statement.executeUpdate(createUsersTableSQL);

            // Buat tabel Accounts
            String createAccountsTableSQL = """
                CREATE TABLE IF NOT EXISTS Accounts (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL UNIQUE, 
                    nomor_rekening VARCHAR(20) NOT NULL UNIQUE,
                    saldo DECIMAL(15, 2) DEFAULT 0.00,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
                );
            """;
            statement.executeUpdate(createAccountsTableSQL);

            // Buat tabel Transactions
            String createTransactionsTableSQL = """
                CREATE TABLE IF NOT EXISTS Transactions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    account_id INT NOT NULL,
                    jenis_transaksi ENUM('Setor', 'Tarik', 'Transfer') NOT NULL,
                    nominal DECIMAL(15, 2) NOT NULL,
                    rekening_tujuan VARCHAR(20) DEFAULT NULL,
                    waktu_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    deskripsi TEXT,
                    FOREIGN KEY (account_id) REFERENCES Accounts(id) ON DELETE CASCADE
                )
            """;
            statement.executeUpdate(createTransactionsTableSQL);

            // Tambahkan data default jika tabel kosong
            insertDefaultData(connection);

            System.out.println("Database, tables, and default data are ready!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertDefaultData(Connection connection) {
        try {
            // Tambahkan data default ke tabel Users jika kosong
            String checkUsersSQL = "SELECT COUNT(*) FROM Users";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(checkUsersSQL)) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    String insertUserSQL = """
                        INSERT INTO Users (username, password, nama)
                        VALUES ('admin', 'admin123', 'Administrator')
                    """;
                    statement.executeUpdate(insertUserSQL);
                    System.out.println("Default user added to Users table.");
                }
            }
             
            String checkAccountsSQL = "SELECT COUNT(*) FROM Accounts";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(checkAccountsSQL)) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    String insertAccountSQL = """
                        INSERT INTO Accounts (user_id, nomor_rekening, saldo)
                        VALUES (1, '1234567890', 100000.00)
                    """;
                    statement.executeUpdate(insertAccountSQL);
                    System.out.println("Default account added to Accounts table.");
                }
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
