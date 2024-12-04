package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TarikController {

    /**
     * Method untuk melakukan tarik (penarikan) dari rekening.
     *
     * @param nomorRekening Nomor rekening yang akan ditarik
     * @param nominal Jumlah yang akan ditarik
     * @param deskripsi Deskripsi transaksi (opsional)
     * @return Pesan keberhasilan atau kegagalan
     */
    public String tarik(String nomorRekening, double nominal, String deskripsi) {
        // Validasi nominal
        if (nominal <= 0) {
            return "Nominal tarik harus lebih dari 0.";
        }

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                return "Koneksi ke database gagal.";
            }

            // Cek apakah rekening sumber valid dan saldo mencukupi
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

                    // Lakukan transaksi tarik
                    connection.setAutoCommit(false);

                    // Kurangi saldo rekening
                    String updateSaldoSQL = "UPDATE Accounts SET saldo = saldo - ? WHERE id = ?";
                    try (PreparedStatement updateSaldoStmt = connection.prepareStatement(updateSaldoSQL)) {
                        updateSaldoStmt.setDouble(1, nominal);
                        updateSaldoStmt.setInt(2, accountId);
                        updateSaldoStmt.executeUpdate();
                    }

                    // Catat transaksi tarik pada tabel Transactions
                    String insertTransaksiSQL = """
                        INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                        VALUES (?, 'Tarik', ?, NULL, ?)
                    """;
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertTransaksiSQL)) {
                        insertStmt.setInt(1, accountId);
                        insertStmt.setDouble(2, nominal);
                        insertStmt.setString(3, deskripsi);
                        insertStmt.executeUpdate();
                    }

                    // Commit transaksi
                    connection.commit();
                    return "Penarikan berhasil.";
                }
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
                return "Terjadi kesalahan saat melakukan tarik.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan koneksi ke database.";
        }
    }
}
