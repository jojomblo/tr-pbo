package bankapp.view;

import bankapp.Controller.SetorController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetorView extends JFrame {

    private JTextField txtNomorRekening;
    private JTextField txtNominal;
    private JTextArea txtDeskripsi;
    private JButton btnSetor;
    private JLabel lblStatus;

    private SetorController setorController;

    public SetorView() {
        setorController = new SetorController();
        initComponents();
    }

    private void initComponents() {
        // Komponen GUI
        JLabel lblNomorRekening = new JLabel("Nomor Rekening:");
        txtNomorRekening = new JTextField(20);

        JLabel lblNominal = new JLabel("Nominal:");
        txtNominal = new JTextField(20);

        JLabel lblDeskripsi = new JLabel("Deskripsi:");
        txtDeskripsi = new JTextArea(5, 20);
        JScrollPane scrollPaneDeskripsi = new JScrollPane(txtDeskripsi);

        btnSetor = new JButton("Setor");
        lblStatus = new JLabel("Status: ");

        // Layout Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(lblNomorRekening);
        panel.add(txtNomorRekening);

        panel.add(lblNominal);
        panel.add(txtNominal);

        panel.add(lblDeskripsi);
        panel.add(scrollPaneDeskripsi);

        panel.add(btnSetor);
        panel.add(lblStatus);

        // Tambahkan panel ke frame
        this.add(panel);

        // Event Listener untuk tombol setor
        btnSetor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetor();
            }
        });

        // Properti Frame
        this.setTitle("Form Setoran Dana");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
    }

    private void handleSetor() {
        String nomorRekening = txtNomorRekening.getText().trim();
        String nominalStr = txtNominal.getText().trim();
        String deskripsi = txtDeskripsi.getText().trim();

        // Validasi input
        if (nomorRekening.isEmpty() || nominalStr.isEmpty()) {
            lblStatus.setText("Status: Semua field harus diisi.");
            return;
        }

        try {
            double nominal = Double.parseDouble(nominalStr);

            // Panggil metode setor pada controller
            String result = setorController.setor(nomorRekening, nominal, deskripsi);
            lblStatus.setText("Status: " + result);
        } catch (NumberFormatException e) {
            lblStatus.setText("Status: Nominal harus berupa angka.");
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

