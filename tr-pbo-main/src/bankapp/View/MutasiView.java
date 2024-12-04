package bankapp.view;

import bankapp.Controller.MutasiController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MutasiView extends JFrame {

    private JTextField txtNomorRekening;
    private JButton btnLihatMutasi;
    private JTextArea txtAreaMutasi;
    private JLabel lblStatus;

    private MutasiController mutasiController;

    public MutasiView() {
        mutasiController = new MutasiController();
        initComponents();
    }

    private void initComponents() {
        // Komponen
        JLabel lblNomorRekening = new JLabel("Nomor Rekening:");
        txtNomorRekening = new JTextField(20);

        btnLihatMutasi = new JButton("Lihat Mutasi");
        txtAreaMutasi = new JTextArea(15, 30);
        txtAreaMutasi.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtAreaMutasi);

        lblStatus = new JLabel("Status: ");

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lblNomorRekening);
        panel.add(txtNomorRekening);
        panel.add(btnLihatMutasi);
        panel.add(scrollPane);
        panel.add(lblStatus);

        this.add(panel);

        // Action Listener
        btnLihatMutasi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLihatMutasi();
            }
        });

        // Properti Frame
        this.setTitle("Lihat Mutasi");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
    }

    private void handleLihatMutasi() {
        String nomorRekening = txtNomorRekening.getText().trim();
        if (nomorRekening.isEmpty()) {
            lblStatus.setText("Status: Nomor rekening harus diisi.");
            return;
        }

        List<String> mutasi = mutasiController.getMutasi(nomorRekening);

        txtAreaMutasi.setText(""); // Reset area mutasi
        for (String transaksi : mutasi) {
            txtAreaMutasi.append(transaksi + "\n");
        }

        lblStatus.setText("Status: Mutasi berhasil dimuat.");
    }

    
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

