package bankapp.View;

import bankapp.Controller.TarikController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TarikView extends JFrame {

    private JTextField nomorRekeningField;
    private JTextField nominalField;
    private JTextField deskripsiField;
    private JButton tarikButton;
    private JLabel resultLabel;

    public TarikView() {
        setTitle("Penarikan Uang");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label dan input nomor rekening
        JLabel nomorRekeningLabel = new JLabel("Nomor Rekening:");
        nomorRekeningLabel.setBounds(20, 20, 150, 25);
        add(nomorRekeningLabel);

        nomorRekeningField = new JTextField();
        nomorRekeningField.setBounds(180, 20, 180, 25);
        add(nomorRekeningField);

        // Label dan input nominal
        JLabel nominalLabel = new JLabel("Nominal:");
        nominalLabel.setBounds(20, 60, 150, 25);
        add(nominalLabel);

        nominalField = new JTextField();
        nominalField.setBounds(180, 60, 180, 25);
        add(nominalField);

        // Label dan input deskripsi
        JLabel deskripsiLabel = new JLabel("Deskripsi:");
        deskripsiLabel.setBounds(20, 100, 150, 25);
        add(deskripsiLabel);

        deskripsiField = new JTextField();
        deskripsiField.setBounds(180, 100, 180, 25);
        add(deskripsiField);

        // Tombol tarik
        tarikButton = new JButton("Tarik");
        tarikButton.setBounds(140, 150, 100, 30);
        add(tarikButton);

        // Label hasil
        resultLabel = new JLabel("");
        resultLabel.setBounds(20, 200, 340, 25);
        add(resultLabel);

        // Event handler untuk tombol tarik
        tarikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTarikAction();
            }
        });

        setVisible(true);
    }

    private void handleTarikAction() {
        String nomorRekening = nomorRekeningField.getText();
        String nominalStr = nominalField.getText();
        String deskripsi = deskripsiField.getText();

        try {
            double nominal = Double.parseDouble(nominalStr);

            // Panggil controller
            TarikController controller = new TarikController();
            String result = controller.tarik(nomorRekening, nominal, deskripsi);

            // Tampilkan hasil
            resultLabel.setText(result);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Nominal harus berupa angka.");
        }
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
