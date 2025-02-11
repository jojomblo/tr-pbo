package bankapp.View;

import bankapp.Controller.RegisterController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField txtUsername, txtNama, txtNomorRekening, txtSaldoAwal;
    private JPasswordField txtPassword;
    private JButton btnSubmit, btnBack;

    public RegisterView() {
        setTitle("Register - Bank App");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        JLabel lblNama = new JLabel("Nama Lengkap:");
        JLabel lblNomorRekening = new JLabel("Nomor Rekening:");
        JLabel lblSaldoAwal = new JLabel("Saldo Awal:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtNama = new JTextField();
        txtNomorRekening = new JTextField();
        txtSaldoAwal = new JTextField();

        btnSubmit = new JButton("Submit");
        btnBack = new JButton("Back");

        btnSubmit.setBackground(new Color(66, 135, 245));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));

        btnBack.setBackground(new Color(192, 57, 43));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));

        mainPanel.add(lblUsername);
        mainPanel.add(txtUsername);
        mainPanel.add(lblPassword);
        mainPanel.add(txtPassword);
        mainPanel.add(lblNama);
        mainPanel.add(txtNama);
        mainPanel.add(lblNomorRekening);
        mainPanel.add(txtNomorRekening);
        mainPanel.add(lblSaldoAwal);
        mainPanel.add(txtSaldoAwal);
        mainPanel.add(btnSubmit);
        mainPanel.add(btnBack);

        add(mainPanel);

        // Action Listener untuk tombol Submit
        btnSubmit.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String nama = txtNama.getText();
            String nomorRekening = txtNomorRekening.getText();
            double saldoAwal;

            try {
                saldoAwal = Double.parseDouble(txtSaldoAwal.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Saldo awal harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            RegisterController registerController = new RegisterController();
            String message = registerController.register(username, password, nama, nomorRekening, saldoAwal);
            JOptionPane.showMessageDialog(this, message, "Informasi", JOptionPane.INFORMATION_MESSAGE);

            if (message.equals("Pendaftaran berhasil.")) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        // Action Listener untuk tombol Back
        btnBack.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
