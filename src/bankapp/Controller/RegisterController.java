package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {
    /**
     * Method untuk mendaftarkan akun baru, membuat rekening, dan mencatat setoran awal.
     *
     * @param username       Username pengguna
     * @param password       Password pengguna
     * @param nama           Nama lengkap pengguna
     * @param nomorRekening  Nomor rekening yang akan dibuat
     * @param saldoAwal      Saldo awal untuk rekening
     * @return Pesan keberhasilan atau kegagalan
     */
    public String register(String username, String password, String nama, String nomorRekening, double saldoAwal) {
        if (username.isEmpty() || password.isEmpty() || nama.isEmpty() || nomorRekening.isEmpty()) {
            return "Semua field harus diisi.";
        }
        if (saldoAwal < 0) {
            return "Saldo awal tidak boleh negatif.";
        }

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                return "Koneksi ke database gagal.";
            }

            connection.setAutoCommit(false);

            // Masukkan data ke tabel Users
            String insertUserSQL = """
                INSERT INTO Users (username, password, nama) VALUES (?, ?, ?)
            """;
            try (PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, username);
                userStmt.setString(2, password);
                userStmt.setString(3, nama);
                userStmt.executeUpdate();

                // Ambil ID pengguna yang baru saja dibuat
                try (var generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Masukkan data ke tabel Accounts
                        String insertAccountSQL = """
                            INSERT INTO Accounts (user_id, nomor_rekening, saldo) VALUES (?, ?, ?)
                        """;
                        try (PreparedStatement accountStmt = connection.prepareStatement(insertAccountSQL)) {
                            accountStmt.setInt(1, userId);
                            accountStmt.setString(2, nomorRekening);
                            accountStmt.setDouble(3, saldoAwal);
                            accountStmt.executeUpdate();
                        }

                        // Catat transaksi setoran awal ke tabel Transactions
                        String insertTransactionSQL = """
                            INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                            VALUES ((SELECT id FROM Accounts WHERE nomor_rekening = ?), 'Setor', ?, ?, 'Setoran awal')
                        """;
                        try (PreparedStatement transactionStmt = connection.prepareStatement(insertTransactionSQL)) {
                            transactionStmt.setString(1, nomorRekening);
                            transactionStmt.setDouble(2, saldoAwal);
                            transactionStmt.setString(3, nomorRekening);
                            transactionStmt.executeUpdate();
                        }
                    } else {
                        connection.rollback();
                        return "Gagal membuat akun baru.";
                    }
                }
            }

            connection.commit();
            return "Pendaftaran berhasil.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan saat melakukan pendaftaran.";
        }
    }
}
