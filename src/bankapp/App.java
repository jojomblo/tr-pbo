package bankapp;

import bankapp.Model.Database;
import bankapp.View.LoginView;

public class App {
    public static void main(String[] args) {
        // Coba koneksi ke database
        if (Database.connect() != null) {
            System.out.println("Koneksi database berhasil!");
        } else {
            System.err.println("Koneksi database gagal! Periksa konfigurasi.");
            return; // Hentikan aplikasi jika koneksi gagal
        }

        // Tampilkan login view
        new LoginView().setVisible(true);
    }
}
