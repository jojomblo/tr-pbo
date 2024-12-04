package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MutasiController {

    /**
     * Mengambil daftar mutasi (transaksi) berdasarkan nomor rekening.
     *
     * @param nomorRekening Nomor rekening yang ingin dilihat mutasinya.
     * @return List yang berisi transaksi dalam bentuk string atau pesan kesalahan.
     */
    public List<String> getMutasi(String nomorRekening) {
        List<String> transaksi = new ArrayList<>();

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                transaksi.add("Koneksi ke database gagal.");
                return transaksi;
            }

            // Cek apakah rekening valid
            String cekRekeningSQL = "SELECT id FROM Accounts WHERE nomor_rekening = ?";
            try (PreparedStatement cekRekeningStmt = connection.prepareStatement(cekRekeningSQL)) {
                cekRekeningStmt.setString(1, nomorRekening);

                try (ResultSet resultSet = cekRekeningStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        transaksi.add("Rekening tidak ditemukan.");
                        return transaksi;
                    }

                    int accountId = resultSet.getInt("id");

                    // Ambil daftar transaksi
                    String mutasiSQL = """
                        SELECT jenis_transaksi, nominal, rekening_tujuan, deskripsi, timestamp
                        FROM Transactions
                        WHERE account_id = ?
                        ORDER BY timestamp DESC
                    """;
                    try (PreparedStatement mutasiStmt = connection.prepareStatement(mutasiSQL)) {
                        mutasiStmt.setInt(1, accountId);

                        try (ResultSet mutasiResultSet = mutasiStmt.executeQuery()) {
                            while (mutasiResultSet.next()) {
                                String jenisTransaksi = mutasiResultSet.getString("jenis_transaksi");
                                double nominal = mutasiResultSet.getDouble("nominal");
                                String rekeningTujuan = mutasiResultSet.getString("rekening_tujuan");
                                String deskripsi = mutasiResultSet.getString("deskripsi");
                                String timestamp = mutasiResultSet.getString("timestamp");

                                String transaksiDetail = String.format(
                                    "Jenis: %s | Nominal: %.2f | Tujuan: %s | Deskripsi: %s | Waktu: %s",
                                    jenisTransaksi, nominal, rekeningTujuan == null ? "-" : rekeningTujuan, deskripsi, timestamp
                                );

                                transaksi.add(transaksiDetail);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaksi.add("Terjadi kesalahan saat mengambil mutasi.");
        }

        return transaksi;
    }
}
