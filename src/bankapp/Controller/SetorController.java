package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SetorController {

    /**
     * Method untuk melakukan setor (penyimpanan) ke rekening.
     *
     * @param nomorRekening  Nomor rekening yang akan disetor
     * @param nominal        Jumlah yang akan disetor
     * @return Pesan keberhasilan atau kegagalan
     */
    public String setor(String nomorRekening, double nominal) {
        // Validasi nominal
        if (nominal <= 0) {
            return "Nominal setor harus lebih dari 0.";
        }

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                return "Koneksi ke database gagal.";
            }

            // Cek apakah rekening sumber valid
            String cekRekeningSQL = "SELECT id FROM Accounts WHERE nomor_rekening = ?";
            try (PreparedStatement cekRekeningStmt = connection.prepareStatement(cekRekeningSQL)) {
                cekRekeningStmt.setString(1, nomorRekening);
                try (ResultSet resultSet = cekRekeningStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        return "Rekening tidak ditemukan.";
                    }

                    int accountId = resultSet.getInt("id");

                    // Lakukan transaksi setor
                    connection.setAutoCommit(false);

                    // Tambah saldo rekening
                    String updateSaldoSQL = "UPDATE Accounts SET saldo = saldo + ? WHERE id = ?";
                    try (PreparedStatement updateSaldoStmt = connection.prepareStatement(updateSaldoSQL)) {
                        updateSaldoStmt.setDouble(1, nominal);
                        updateSaldoStmt.setInt(2, accountId);
                        updateSaldoStmt.executeUpdate();
                    }

                    // Catat transaksi setor pada tabel Transactions dengan deskripsi hardcoded
                    String insertTransaksiSQL = """
                        INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                        VALUES (?, 'Setor', ?, NULL, 'Setoran Uang')
                    """;
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertTransaksiSQL)) {
                        insertStmt.setInt(1, accountId);
                        insertStmt.setDouble(2, nominal);
                        insertStmt.executeUpdate();
                    }

                    // Commit transaksi
                    connection.commit();
                    return "Setoran berhasil.";
                }
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
                return "Terjadi kesalahan saat melakukan setor.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan koneksi ke database.";
        }
    }
}
