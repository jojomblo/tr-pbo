package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarikController {

    /**
     * Method untuk melakukan tarik (penarikan) dari rekening.
     *
     * @param nomorRekening Nomor rekening yang akan ditarik
     * @param nominal Jumlah yang akan ditarik
     * @return Pesan keberhasilan atau kegagalan
     */
    public String tarik(String nomorRekening, double nominal) {
        if (nominal <= 0) {
            return "Nominal tarik harus lebih dari 0.";
        }

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                return "Koneksi ke database gagal.";
            }

            String cekSaldoSQL = "SELECT id, saldo FROM Accounts WHERE nomor_rekening = ?";
            try (PreparedStatement cekSaldoStmt = connection.prepareStatement(cekSaldoSQL)) {
                cekSaldoStmt.setString(1, nomorRekening);
                try (ResultSet resultSet = cekSaldoStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        return "Rekening tidak ditemukan.";
                    }

                    double saldoRekening = resultSet.getDouble("saldo");
                    int accountId = resultSet.getInt("id");

                    if (saldoRekening < nominal) {
                        return "Saldo tidak mencukupi untuk penarikan.";
                    }

                    connection.setAutoCommit(false); // Mulai transaksi

                    String updateSaldoSQL = "UPDATE Accounts SET saldo = saldo - ? WHERE id = ?";
                    try (PreparedStatement updateSaldoStmt = connection.prepareStatement(updateSaldoSQL)) {
                        updateSaldoStmt.setDouble(1, nominal);
                        updateSaldoStmt.setInt(2, accountId);
                        updateSaldoStmt.executeUpdate();
                    }

                    String insertTransaksiSQL = """
                        INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                        VALUES (?, 'Tarik', ?, NULL, 'Penarikan Uang')
                    """;
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertTransaksiSQL)) {
                        insertStmt.setInt(1, accountId);
                        insertStmt.setDouble(2, nominal);
                        insertStmt.executeUpdate();
                    }

                    connection.commit(); // Commit transaksi
                    return "Penarikan berhasil.";
                }
            } catch (SQLException e) {
                if (!connection.getAutoCommit()) {
                    connection.rollback(); // Rollback hanya jika transaksi sudah dimulai
                }
                e.printStackTrace();
                return "Terjadi kesalahan saat melakukan tarik.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan koneksi ke database.";
        }
    }
}
