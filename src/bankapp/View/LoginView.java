package bankapp.View;

import bankapp.Controller.UserController;
import bankapp.Model.User;
import bankapp.View.RegisterView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() {
        setTitle("Login - Bank App");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan window di tengah layar
        setResizable(false);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin

        // Panel input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        inputPanel.add(lblUsername);
        inputPanel.add(txtUsername);
        inputPanel.add(lblPassword);
        inputPanel.add(txtPassword);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        // Gaya tombol Login
        btnLogin.setBackground(new Color(66, 135, 245));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        // Gaya tombol Register
        btnRegister.setBackground(new Color(34, 167, 120));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        // Tambahkan panel ke main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan main panel ke JFrame
        add(mainPanel);

        // Action Listener untuk tombol Login
        btnLogin.addActionListener(e -> {
            UserController userController = new UserController();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User user = userController.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(LoginView.this, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                new DashboardView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginView.this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listener untuk tombol Register
        btnRegister.addActionListener(e -> {
            new RegisterView().setVisible(true);
            dispose();
        });
    }
    





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
//    public static void main(String args[]) {
//    SwingUtilities.invokeLater(() -> {
//        new LoginView().setVisible(true);
//    });
//}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
