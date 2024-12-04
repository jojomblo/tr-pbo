package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MutasiController {

    /**
     * Method untuk mendapatkan daftar mutasi berdasarkan nomor rekening.
     *
     * @param nomorRekening Nomor rekening untuk mencari mutasi
     * @return Daftar mutasi atau pesan error jika terjadi kesalahan
     */
    public List<String> getMutasi(String nomorRekening) {
        List<String> mutasiList = new ArrayList<>();

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                mutasiList.add("Koneksi ke database gagal.");
                return mutasiList;
            }

            // Ambil ID rekening berdasarkan nomor rekening
            String cekRekeningSQL = "SELECT id FROM Accounts WHERE nomor_rekening = ?";
            try (PreparedStatement cekRekeningStmt = connection.prepareStatement(cekRekeningSQL)) {
                cekRekeningStmt.setString(1, nomorRekening);
                try (ResultSet resultSet = cekRekeningStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        mutasiList.add("Rekening tidak ditemukan.");
                        return mutasiList;
                    }

                    int accountId = resultSet.getInt("id");

                    // Ambil data transaksi untuk rekening tersebut
                    String mutasiSQL = """
                        SELECT id, jenis_transaksi, nominal, 
                               rekening_tujuan, waktu_transaksi, deskripsi
                        FROM Transactions
                        WHERE account_id = ?
                        ORDER BY waktu_transaksi DESC
                    """;
                    try (PreparedStatement mutasiStmt = connection.prepareStatement(mutasiSQL)) {
                        mutasiStmt.setInt(1, accountId);
                        try (ResultSet mutasiResultSet = mutasiStmt.executeQuery()) {
                            while (mutasiResultSet.next()) {
                                int id = mutasiResultSet.getInt("id");
                                String jenisTransaksi = mutasiResultSet.getString("jenis_transaksi");
                                double nominal = mutasiResultSet.getDouble("nominal");
                                String rekeningTujuan = mutasiResultSet.getString("rekening_tujuan");
                                String waktuTransaksi = mutasiResultSet.getString("waktu_transaksi");
                                String deskripsi = mutasiResultSet.getString("deskripsi");

                                // Format data transaksi sebagai string
                                String mutasi = String.format(
                                    "ID: %d | Jenis: %s | Nominal: %.2f | Rekening Tujuan: %s | " +
                                    "Waktu: %s | Deskripsi: %s",
                                    id, jenisTransaksi, nominal,
                                    (rekeningTujuan == null ? "N/A" : rekeningTujuan),
                                    waktuTransaksi, deskripsi
                                );
                                mutasiList.add(mutasi);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mutasiList.add("Terjadi kesalahan saat mengambil data mutasi.");
        }

        // Jika tidak ada mutasi, tambahkan pesan bahwa tidak ada data
        if (mutasiList.isEmpty()) {
            mutasiList.add("Tidak ada mutasi untuk rekening ini.");
        }

        return mutasiList;
    }
}
