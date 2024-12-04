package bankapp.Controller;

import bankapp.Model.Account;
import bankapp.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountController {

    public Account getAccountByUserId(int userId) {
        Account account = null;
        String query = "SELECT * FROM Accounts WHERE user_id = ?";
        try (Connection connection = Database.connect(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getInt("user_id"));
                account.setNomorRekening(rs.getString("nomor_rekening"));
                account.setSaldo(rs.getBigDecimal("saldo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public String getNomorRekeningByUserId(int userId) {
        String nomorRekening = null;
        String query = "SELECT nomor_rekening FROM Accounts WHERE user_id = ?";
        try (Connection connection = Database.connect(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomorRekening = rs.getString("nomor_rekening");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nomorRekening;
    }

}
