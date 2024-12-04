package bankapp.View;

import bankapp.Controller.UserController;
import bankapp.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Login - Bank App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan window di tengah layar
        setResizable(false);

        // Panel utama dengan background putih
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin

        // Panel input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1, 10, 10));
        inputPanel.setOpaque(false);

        // Panel input username
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setOpaque(false);
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setForeground(Color.BLACK);
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setMargin(new Insets(5, 5, 5, 5));
        txtUsername.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        usernamePanel.add(lblUsername, BorderLayout.NORTH);
        usernamePanel.add(txtUsername, BorderLayout.CENTER);

        // Panel input password
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setOpaque(false);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(Color.BLACK);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMargin(new Insets(5, 5, 5, 5));
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        passwordPanel.add(lblPassword, BorderLayout.NORTH);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);

        inputPanel.add(usernamePanel);
        inputPanel.add(passwordPanel);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(66, 135, 245));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect untuk tombol
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(33, 102, 203));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(66, 135, 245));
            }
        });

        buttonPanel.add(btnLogin);

        // Tambahkan panel ke main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan main panel ke JFrame
        add(mainPanel);

        // Event Listener untuk tombol Login
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            // Validasi input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginView.this,
                        "Harap isi semua kolom!",
                        "Validasi Input",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            UserController userController = new UserController();
            User user = userController.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(LoginView.this,
                        "Login berhasil! Selamat datang, " + user.getUsername(),
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                new DashboardView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginView.this,
                        "Username atau Password salah!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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
