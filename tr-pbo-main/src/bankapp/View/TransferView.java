package bankapp.View;

import bankapp.Controller.AccountController;
import bankapp.Controller.TransferController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TransferView extends JFrame {

    private JTextField txtRekeningTujuan, txtNominal;
    private JTextArea txtDeskripsi;
    private JButton btnTransfer, btnKembali;
    private String rekeningSumber;

    public TransferView(int userId, DashboardView dashboardView) {
        setTitle("Transfer - Bank App");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan window di tengah layar
        setResizable(false);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 123, 255));
        headerPanel.setPreferredSize(new Dimension(450, 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("Transfer", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Dapatkan rekening sumber
        AccountController accountController = new AccountController();
        rekeningSumber = accountController.getNomorRekeningByUserId(userId);
        if (rekeningSumber == null) {
            JOptionPane.showMessageDialog(this, "Rekening sumber tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Panel input
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelInput.setBackground(Color.WHITE);

        JLabel lblRekeningSumber = new JLabel( rekeningSumber);
        lblRekeningSumber.setFont(new Font("Arial", Font.BOLD, 14));
        panelInput.add(new JLabel("Rekening Sumber:"));
        panelInput.add(lblRekeningSumber);

        panelInput.add(new JLabel("Rekening Tujuan:"));
        txtRekeningTujuan = new JTextField();
        panelInput.add(txtRekeningTujuan);

        panelInput.add(new JLabel("Nominal (Rp):"));
        txtNominal = new JTextField();
        panelInput.add(txtNominal);

        panelInput.add(new JLabel("Deskripsi:"));
        txtDeskripsi = new JTextArea(3, 20);
        txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(txtDeskripsi);
        panelInput.add(scrollPane);

        add(panelInput, BorderLayout.CENTER);

        // Panel tombol
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButtons.setBackground(Color.WHITE);

        btnTransfer = new JButton("Transfer");
        btnTransfer.setFont(new Font("Arial", Font.BOLD, 14));
        btnTransfer.setBackground(new Color(0, 123, 255));
        btnTransfer.setForeground(Color.WHITE);
        btnTransfer.setPreferredSize(new Dimension(120, 40));

        btnKembali = new JButton("Kembali");
        btnKembali.setFont(new Font("Arial", Font.BOLD, 14));
        btnKembali.setBackground(new Color(220, 53, 69));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setPreferredSize(new Dimension(120, 40));

        panelButtons.add(btnTransfer);
        panelButtons.add(btnKembali);
        add(panelButtons, BorderLayout.SOUTH);

        // Event handler untuk tombol Transfer
        btnTransfer.addActionListener((ActionEvent e) -> {
            String rekeningTujuan = txtRekeningTujuan.getText().trim();
            String nominalStr = txtNominal.getText().trim();
            String deskripsi = txtDeskripsi.getText().trim();

            if (rekeningTujuan.isEmpty() || nominalStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double nominal = Double.parseDouble(nominalStr);
                TransferController transferController = new TransferController();
                String result = transferController.transfer(rekeningSumber, rekeningTujuan, nominal, deskripsi);
                JOptionPane.showMessageDialog(this, result, "Hasil Transfer", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nominal harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Event handler untuk tombol Kembali
        btnKembali.addActionListener(e -> {
            dispose();
            dashboardView.setVisible(true);
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
