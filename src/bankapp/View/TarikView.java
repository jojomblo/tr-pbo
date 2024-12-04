package bankapp.View;

import bankapp.Controller.AccountController;
import bankapp.Controller.TarikController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

public class TarikView extends JFrame {

    private JTextField txtNominal;
    private JLabel lblNomorRekening;
    private JButton btnTarik, btnKembali;
    private String nomorRekening;

    public TarikView(int userId, DashboardView dashboardView) {
        setTitle("Tarik Tunai - Bank App");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Retrieve account number based on user ID
        AccountController accountController = new AccountController();
        nomorRekening = accountController.getNomorRekeningByUserId(userId);

        if (nomorRekening == null) {
            JOptionPane.showMessageDialog(this, "Nomor rekening tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Input panel
        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInput.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelInput.setBackground(Color.WHITE);

        JLabel lblRekening = new JLabel("Nomor Rekening:");
        lblRekening.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelInput.add(lblRekening);

        lblNomorRekening = new JLabel(nomorRekening);
        lblNomorRekening.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNomorRekening.setForeground(Color.BLUE);
        panelInput.add(lblNomorRekening);

        JLabel lblNominal = new JLabel("Nominal (Rp):");
        lblNominal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelInput.add(lblNominal);

        txtNominal = new JTextField();
        txtNominal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelInput.add(txtNominal);

        add(panelInput, BorderLayout.CENTER);

        // Button panel
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(Color.WHITE);

        btnTarik = new JButton("Tarik");
        btnTarik.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTarik.setBackground(new Color(0, 123, 255));
        btnTarik.setForeground(Color.WHITE);
        btnTarik.setPreferredSize(new Dimension(120, 40));

        btnKembali = new JButton("Kembali");
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKembali.setBackground(new Color(220, 53, 69));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setPreferredSize(new Dimension(120, 40));

        panelButtons.add(btnTarik);
        panelButtons.add(btnKembali);

        add(panelButtons, BorderLayout.SOUTH);

        // Tarik button event handler
        btnTarik.addActionListener((ActionEvent e) -> {
            String nominalStr = txtNominal.getText().trim();

            if (nominalStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Field nominal harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double nominal = Double.parseDouble(nominalStr);

                if (nominal <= 0) {
                    JOptionPane.showMessageDialog(this, "Nominal harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TarikController tarikController = new TarikController();
                String result = tarikController.tarik(nomorRekening, nominal);

                JOptionPane.showMessageDialog(this, result, "Hasil Penarikan", JOptionPane.INFORMATION_MESSAGE);

                if (result.equals("Penarikan berhasil.")) {
                    txtNominal.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nominal harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Kembali button event handler
        btnKembali.addActionListener(e -> {
            dispose();
            dashboardView.setVisible(true);
        });

        // Set window location and visibility
        setLocationRelativeTo(null);
        setVisible(true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
