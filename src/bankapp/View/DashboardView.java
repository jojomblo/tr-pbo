package bankapp.View;

import bankapp.Controller.AccountController;
import bankapp.Model.Account;
import bankapp.Model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class DashboardView extends JFrame {

    private JButton btnCekSaldo, btnTransfer, btnSetor, btnTarik, btnMutasi, btnLogout;

    public DashboardView(User user) {
        setTitle("Dashboard - Bank App");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan window di tengah layar
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel untuk bagian atas (Header)
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color1 = new Color(70, 130, 180);
                Color color2 = new Color(100, 149, 237);
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topPanel.setPreferredSize(new Dimension(600, 80));
        topPanel.setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Selamat Datang, " + user.getUsername(), JLabel.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        topPanel.add(lblWelcome, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Panel untuk tombol fitur
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tombol dengan gaya modern
        btnCekSaldo = createStyledButton("Cek Saldo");
        btnTransfer = createStyledButton("Transfer");
        btnSetor = createStyledButton("Setor");
        btnTarik = createStyledButton("Tarik");
        btnMutasi = createStyledButton("Mutasi");
        btnLogout = createStyledButton("Logout");

        buttonPanel.add(btnCekSaldo);
        buttonPanel.add(btnTransfer);
        buttonPanel.add(btnSetor);
        buttonPanel.add(btnTarik);
        buttonPanel.add(btnMutasi);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(600, 40));
        footerPanel.setBackground(new Color(240, 240, 240));

        JLabel lblFooter = new JLabel("© 2024 Bank App. All Rights Reserved.", JLabel.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);

        footerPanel.add(lblFooter);
        add(footerPanel, BorderLayout.SOUTH);

        // Event handler untuk tombol Cek Saldo
        btnCekSaldo.addActionListener((ActionEvent e) -> {
            AccountController accountController = new AccountController();
            Account account = accountController.getAccountByUserId(user.getId());

            if (account != null) {
                setVisible(false); // Sembunyikan DashboardView
                new CekSaldoView(account, this).setVisible(true); // Buka CekSaldoView
            } else {
                JOptionPane.showMessageDialog(this,
                        "Akun tidak ditemukan!",
                        "Cek Saldo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Event handler untuk tombol Transfer
        btnTransfer.addActionListener(e -> {
            setVisible(false); // Sembunyikan DashboardView
            new TransferView(user.getId(), this).setVisible(true); // Buka TransferView
        });

        // Event handler untuk tombol Logout
        btnLogout.addActionListener((ActionEvent e) -> {
            new LoginView().setVisible(true); // Kembali ke LoginView
            dispose(); // Menutup DashboardView
        });
    }

    // Metode untuk membuat tombol dengan gaya modern
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
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
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new DashboardView().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
