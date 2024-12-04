package bankapp.Controller;

import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransferController {

    /**
     * Method untuk melakukan transfer antar rekening.
     *
     * @param rekeningSumber   Nomor rekening sumber
     * @param rekeningTujuan   Nomor rekening tujuan
     * @param nominal          Jumlah yang akan ditransfer
     * @param deskripsi        Deskripsi transaksi (opsional)
     * @return Pesan keberhasilan atau kegagalan
     */
    public String transfer(String rekeningSumber, String rekeningTujuan, double nominal, String deskripsi) {
        // Validasi awal
        if (nominal <= 0) {
            return "Nominal transfer harus lebih dari 0.";
        }

        try (Connection connection = Database.connect()) {
            if (connection == null) {
                return "Koneksi ke database gagal.";
            }

            // Cek apakah rekening sumber valid dan saldo mencukupi
            String cekSaldoSQL = "SELECT id, saldo FROM Accounts WHERE nomor_rekening = ?";
            try (PreparedStatement cekSaldoStmt = connection.prepareStatement(cekSaldoSQL)) {
                cekSaldoStmt.setString(1, rekeningSumber);
                try (ResultSet resultSet = cekSaldoStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        return "Rekening sumber tidak ditemukan.";
                    }

                    double saldoSumber = resultSet.getDouble("saldo");
                    int accountIdSumber = resultSet.getInt("id");

                    if (saldoSumber < nominal) {
                        return "Saldo tidak mencukupi.";
                    }

                    // Cek apakah rekening tujuan valid
                    String cekTujuanSQL = "SELECT id FROM Accounts WHERE nomor_rekening = ?";
                    try (PreparedStatement cekTujuanStmt = connection.prepareStatement(cekTujuanSQL)) {
                        cekTujuanStmt.setString(1, rekeningTujuan);
                        try (ResultSet tujuanResultSet = cekTujuanStmt.executeQuery()) {
                            if (!tujuanResultSet.next()) {
                                return "Rekening tujuan tidak ditemukan.";
                            }

                            int accountIdTujuan = tujuanResultSet.getInt("id");

                            // Lakukan transaksi transfer
                            connection.setAutoCommit(false);

                            // Kurangi saldo rekening sumber
                            String updateSaldoSumberSQL = "UPDATE Accounts SET saldo = saldo - ? WHERE id = ?";
                            try (PreparedStatement updateSaldoSumberStmt = connection.prepareStatement(updateSaldoSumberSQL)) {
                                updateSaldoSumberStmt.setDouble(1, nominal);
                                updateSaldoSumberStmt.setInt(2, accountIdSumber);
                                updateSaldoSumberStmt.executeUpdate();
                            }

                            // Tambah saldo rekening tujuan
                            String updateSaldoTujuanSQL = "UPDATE Accounts SET saldo = saldo + ? WHERE id = ?";
                            try (PreparedStatement updateSaldoTujuanStmt = connection.prepareStatement(updateSaldoTujuanSQL)) {
                                updateSaldoTujuanStmt.setDouble(1, nominal);
                                updateSaldoTujuanStmt.setInt(2, accountIdTujuan);
                                updateSaldoTujuanStmt.executeUpdate();
                            }

                            // Catat transaksi pada rekening sumber
                            String insertTransaksiSumberSQL = """
                                INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                                VALUES (?, 'Transfer', ?, ?, ?)
                            """;
                            try (PreparedStatement insertSumberStmt = connection.prepareStatement(insertTransaksiSumberSQL)) {
                                insertSumberStmt.setInt(1, accountIdSumber);
                                insertSumberStmt.setDouble(2, nominal);
                                insertSumberStmt.setString(3, rekeningTujuan);
                                insertSumberStmt.setString(4, deskripsi);
                                insertSumberStmt.executeUpdate();
                            }

                            // Catat transaksi pada rekening tujuan
                            String insertTransaksiTujuanSQL = """
                                INSERT INTO Transactions (account_id, jenis_transaksi, nominal, rekening_tujuan, deskripsi)
                                VALUES (?, 'Transfer', ?, ?, ?)
                            """;
                            try (PreparedStatement insertTujuanStmt = connection.prepareStatement(insertTransaksiTujuanSQL)) {
                                insertTujuanStmt.setInt(1, accountIdTujuan);
                                insertTujuanStmt.setDouble(2, nominal);
                                insertTujuanStmt.setString(3, rekeningSumber);
                                insertTujuanStmt.setString(4, deskripsi);
                                insertTujuanStmt.executeUpdate();
                            }

                            // Commit transaksi
                            connection.commit();
                            return "Transfer berhasil.";
                        }
                    }
                }
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
                return "Terjadi kesalahan saat melakukan transfer.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan koneksi ke database.";
        }
    }
}
