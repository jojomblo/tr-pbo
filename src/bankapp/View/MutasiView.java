package bankapp.View;

import bankapp.Controller.MutasiController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MutasiView extends JFrame {

    private JTable tableMutasi;
    private JButton btnKembali;
    private String nomorRekening;

    public MutasiView(String nomorRekening, DashboardView dashboardView) {
        this.nomorRekening = nomorRekening;

        // Set tampilan modern
        setModernUI();

        setTitle("Riwayat Mutasi - Bank App");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Atur Layout Frame
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Tambahkan komponen
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(dashboardView), BorderLayout.SOUTH);

        // Load data mutasi
        loadMutasiData();
    }

    private void setModernUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Button.background", new Color(30, 144, 255));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TableHeader.font", new Font("Arial", Font.BOLD, 14));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(700, 60));
        headerPanel.setBackground(new Color(30, 144, 255));

        JLabel lblHeader = new JLabel("Riwayat Mutasi", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);

        headerPanel.add(lblHeader, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableMutasi = new JTable();
        tableMutasi.setRowHeight(25);
        tableMutasi.setFont(new Font("Arial", Font.PLAIN, 14));
        tableMutasi.setShowGrid(false);

        JTableHeader tableHeader = tableMutasi.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(new Color(230, 230, 230));
        tableHeader.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(tableMutasi);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        panelTable.add(scrollPane, BorderLayout.CENTER);
        return panelTable;
    }

    private JPanel createButtonPanel(DashboardView dashboardView) {
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        btnKembali = new JButton("Kembali");
        btnKembali.setPreferredSize(new Dimension(100, 40));

        btnKembali.addActionListener(e -> {
            dispose();
            dashboardView.setVisible(true);
        });

        panelButtons.add(btnKembali);
        return panelButtons;
    }

    private void loadMutasiData() {
        MutasiController mutasiController = new MutasiController();
        List<String> mutasiList = mutasiController.getMutasi(nomorRekening);

        if (mutasiList.isEmpty() || mutasiList.get(0).startsWith("Tidak ada")) {
            JOptionPane.showMessageDialog(this, "Tidak ada data mutasi.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Kolom tanpa ID
        String[] columnNames = {"Jenis Transaksi", "Nominal (Rp)", "Rekening Tujuan", "Waktu", "Deskripsi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (String mutasi : mutasiList) {
            String[] parts = mutasi.split("\\|");
            if (parts.length != columnNames.length + 1) { // +1 karena ID dihilangkan
                JOptionPane.showMessageDialog(this, "Data mutasi tidak valid.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            Object[] rowData = new Object[columnNames.length];
            try {
                for (int i = 1; i < parts.length; i++) { // Mulai dari indeks 1 untuk mengabaikan ID
                    rowData[i - 1] = parts[i].split(":")[1].trim();
                }
                tableModel.addRow(rowData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format data tidak sesuai.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }

        tableMutasi.setModel(tableModel);
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
