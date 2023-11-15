import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

// Menambahkan enum untuk jenis kendaraan
enum JenisKendaraan {
    MOTOR("Motor"),
    MOBIL("Mobil");

    private final String value;

    JenisKendaraan(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

public class AplikasiParkir extends JFrame {
    private JTextField nomorKendaraanField;
    private JTextField jenisKendaraanField;
    private JTextField TanggalMasukField;
    private JTextField jamMasukField;
    private JTextField JamKeluartextField;
    private JTextField DurasiField;
    private JTextField TotalField;
    private ArrayList<String[]> dataParkirList = new ArrayList<String[]>();
    private DefaultTableModel tableModel;
    private JTable dataTable;
    private JComboBox<JenisKendaraan> jenisKendaraanComboBox;

    public AplikasiParkir() {
        // Inisialisasi frame
        setTitle("PARKIR TUNJUNGAN PLAZA"); // Menambahkan judul di sini
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel dengan latar belakang biru muda
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.setBackground(new Color(173, 216, 230)); // Warna biru muda

        // Membuat font "Elephant" untuk judul
        Font titleFont = new Font("Elephant", Font.BOLD, 20);

        // Membuat judul "PARKIR TUNJUNGAN PLAZA"
        JLabel titleLabel = new JLabel("PARKIR TUNJUNGAN PLAZA");
        titleLabel.setFont(titleFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Menempati 3 kolom
        panel.add(titleLabel, gbc);

        // Membuat font "Times New Roman" untuk komponen teks
        Font timesNewRomanFont = new Font("Times New Roman", Font.PLAIN, 14);

        // Membuat komponen-komponen
        JLabel nomorKendaraanLabel = new JLabel("Nomor Kendaraan:");
        nomorKendaraanLabel.setFont(timesNewRomanFont);
        nomorKendaraanField = new JTextField(10);

        JLabel jenisKendaraanLabel = new JLabel("Jenis Kendaraan:");
        jenisKendaraanLabel.setFont(timesNewRomanFont);
        jenisKendaraanField = new JTextField(10);

        // Membuat JComboBox untuk jenis kendaraan
        jenisKendaraanComboBox = new JComboBox<>(JenisKendaraan.values());
        jenisKendaraanComboBox.setFont(timesNewRomanFont);

        JLabel TanggalMasukLabel = new JLabel("Tanggal Masuk:");
        TanggalMasukLabel.setFont(timesNewRomanFont);
        TanggalMasukField = new JTextField(10);

        JLabel jamMasukLabel = new JLabel("Jam Masuk:");
        jamMasukLabel.setFont(timesNewRomanFont);
        jamMasukField = new JTextField(10);

        JLabel JamKeluarLabel = new JLabel("Jam Keluar:");
        JamKeluarLabel.setFont(timesNewRomanFont);
        JamKeluartextField = new JTextField(10);

        JLabel DurasiLabel = new JLabel("Durasi:");
        DurasiLabel.setFont(timesNewRomanFont);
        DurasiField = new JTextField(10);

        JLabel TotalLabel = new JLabel("Total Ongkos Parkir:");
        TotalLabel.setFont(timesNewRomanFont);
        TotalField = new JTextField(10);

        JButton setTanggalMasukButton = new JButton("Set Tanggal Masuk");
        setTanggalMasukButton.setFont(timesNewRomanFont);
        JButton jamMasukButton = new JButton("Set Jam Masuk");
        jamMasukButton.setFont(timesNewRomanFont);
        JButton jamKeluarButton = new JButton("Set Jam Keluar");
        jamKeluarButton.setFont(timesNewRomanFont);
        JButton hitungOngkosButton = new JButton("Hitung Ongkos Parkir");
        hitungOngkosButton.setFont(timesNewRomanFont);
        JButton simpanButton = new JButton("Simpan");
        simpanButton.setFont(timesNewRomanFont);
        JButton updateDataButton = new JButton("Update");
        updateDataButton.setFont(timesNewRomanFont);
        JButton tampilkanDataButton = new JButton("Tampilkan Data");
        tampilkanDataButton.setFont(timesNewRomanFont);
        JButton tambahDataButton = new JButton("Tambah Data");
        tambahDataButton.setFont(timesNewRomanFont);
        JButton hapusButton = new JButton("Hapus");
        hapusButton.setFont(timesNewRomanFont);

        // Membuat tabel
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nomor Kendaraan");
        tableModel.addColumn("Jenis Kendaraan");
        tableModel.addColumn("Tanggal Masuk");
        tableModel.addColumn("Jam Masuk");
        tableModel.addColumn("Jam Keluar");
        tableModel.addColumn("Durasi");
        tableModel.addColumn("Total Ongkos Parkir");
        dataTable = new JTable(tableModel);

        // Menambahkan ActionListener ke tombol "Set Tanggal Masuk"
        setTanggalMasukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTanggalMasuk();
            }
        });

        // Menambahkan ActionListener ke tombol
        jamMasukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date currentDateTime = new Date();
                String jamMasuk = timeFormat.format(currentDateTime);

                jamMasukField.setText(jamMasuk);
            }
        });

        jamKeluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date currentDateTime = new Date();
                String jamKeluar = timeFormat.format(currentDateTime);

                JamKeluartextField.setText(jamKeluar);
                hitungOngkosParkir();
            }
        });

        hitungOngkosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungOngkosParkir();
            }
        });

        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanDataParkir();
            }
        });

        updateDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDataParkir();
            }
        });

        tampilkanDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanData();
            }
        });

        tambahDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahDataParkir();
            }
        });

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the nomorKendaraan (vehicle number) to identify the record to delete
                String nomorKendaraanToDelete = nomorKendaraanField.getText();

                // Check if nomorKendaraanToDelete is not empty
                if (!nomorKendaraanToDelete.isEmpty()) {
                    try {
                        // Establish a database connection to MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/parkir"; // Change the database name to "parkir"
                        String username = "root"; // Change the username to "root"
                        String password = ""; // Set the password for your MySQL server
                        Connection connection = DriverManager.getConnection(url, username, password);

                        // Write a SQL query to delete the record with the specified nomorKendaraan
                        String deleteSQL = "DELETE FROM parkiran WHERE Nomor_Kendaraan = ?";

                        // Create a PreparedStatement and set the parameter
                        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                        preparedStatement.setString(1, nomorKendaraanToDelete);

                        // Execute the delete query
                        int rowsDeleted = preparedStatement.executeUpdate();

                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(AplikasiParkir.this, "Data berhasil dihapus dari database.");
                        } else {
                            JOptionPane.showMessageDialog(AplikasiParkir.this, "Data dengan nomor kendaraan " + nomorKendaraanToDelete + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Close the PreparedStatement and the database connection
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AplikasiParkir.this, "Terjadi kesalahan dalam menghapus data dari database.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AplikasiParkir.this, "Driver JDBC MySQL tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AplikasiParkir.this, "Masukkan nomor kendaraan yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Menambahkan komponen-komponen ke panel dengan GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 1; // Mulai dari baris ke-1
        gbc.gridwidth = 1; // Kembalikan ke 1 kolom
        panel.add(nomorKendaraanLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomorKendaraanField, gbc);

        // Menambahkan komponen-komponen ke panel dengan GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(jenisKendaraanLabel, gbc);
        gbc.gridx = 1;
        panel.add(jenisKendaraanComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(TanggalMasukLabel, gbc);
        gbc.gridx = 1;
        panel.add(TanggalMasukField, gbc);
        gbc.gridx = 2;
        panel.add(setTanggalMasukButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(jamMasukLabel, gbc);
        gbc.gridx = 1;
        panel.add(jamMasukField, gbc);
        gbc.gridx = 2;
        panel.add(jamMasukButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(JamKeluarLabel, gbc);
        gbc.gridx = 1;
        panel.add(JamKeluartextField, gbc);
        gbc.gridx = 2;
        panel.add(jamKeluarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(DurasiLabel, gbc);
        gbc.gridx = 1;
        panel.add(DurasiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(TotalLabel, gbc);
        gbc.gridx = 1;
        panel.add(TotalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(simpanButton, gbc);
        gbc.gridx = 1;
        panel.add(simpanButton, gbc);
        gbc.gridx = 2;
        panel.add(updateDataButton, gbc);
        gbc.gridx = 0;
        panel.add(tampilkanDataButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridx = 1;
        panel.add(tambahDataButton, gbc);
        gbc.gridx = 2;
        panel.add(hapusButton, gbc);

        // Menambahkan panel ke frame
        add(panel);

        // Menampilkan frame
        setVisible(true);
    }

    // Metode untuk mengatur Tanggal Masuk secara otomatis
    private void setTanggalMasuk() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        TanggalMasukField.setText(dateFormat.format(currentDate));
    }

    private void hitungOngkosParkir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date jamMasuk = dateFormat.parse(jamMasukField.getText());
            Date jamKeluar = dateFormat.parse(JamKeluartextField.getText());

            long durasi = (jamKeluar.getTime() - jamMasuk.getTime()) / (60 * 1000); // dalam menit
            DurasiField.setText(String.valueOf(durasi) + " menit");

            int hargaPerJam = 5000;
            int ongkosParkir = (int) Math.ceil((double) durasi / 60) * hargaPerJam;

            TotalField.setText("Rp " + ongkosParkir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam menghitung ongkos parkir.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanDataParkir() {
        String nomorKendaraan = nomorKendaraanField.getText();
        String jenisKendaraan = ((JenisKendaraan) jenisKendaraanComboBox.getSelectedItem()).getValue();
        String tanggalMasukString = TanggalMasukField.getText();
        String jamMasuk = jamMasukField.getText();
        String jamKeluar = JamKeluartextField.getText();

        // Extract the numeric part of "DurasiField" and parse it as an integer
        String durasiText = DurasiField.getText();
        int durasiInt = 0; // Default value if parsing fails
        try {
            // Split "durasiText" to extract the numeric part (remove " menit")
            String numericPart = durasiText.split(" ")[0];
            durasiInt = Integer.parseInt(numericPart);
        } catch (NumberFormatException e) {
            // Handle the NumberFormatException, or log it as needed
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam mengkonversi durasi.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        String totalOngkosParkir = TotalField.getText();

        try {
            // Set up the connection to the MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/parkir"; // Change the database name to "parkir"
            String username = "root"; // Change the username to "root"
            String password = ""; // Set the password for your MySQL server
            Connection connection = DriverManager.getConnection(url, username, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(tanggalMasukString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date jamMasukDate = timeFormat.parse(jamMasuk);
            java.sql.Time jamMasukTime = new java.sql.Time(jamMasukDate.getTime());

            SimpleDateFormat timeFormatT = new SimpleDateFormat("HH:mm:ss");
            Date jamKeluarDate = timeFormatT.parse(jamKeluar);
            java.sql.Time jamKeluarTime = new java.sql.Time(jamKeluarDate.getTime());

            double ongkosParkir = 0.0; // Default value if parsing fails
            try {
                ongkosParkir = Double.parseDouble(totalOngkosParkir.replace("Rp ", "").replace(",", ""));
            } catch (NumberFormatException e) {
                // Handle the NumberFormatException, or log it as needed
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam mengkonversi total ongkos parkir.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Prepare the SQL statement
            String sql = "INSERT INTO parkiran (Nomor_Kendaraan, Jenis_Kendaraan, Tanggal_Masuk, Jam_Masuk, Jam_Keluar, Durasi, Total_Ongkos_Parkir) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomorKendaraan);
            preparedStatement.setString(2, jenisKendaraan);
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setTime(4, jamMasukTime);
            preparedStatement.setTime(5, jamKeluarTime);
            preparedStatement.setInt(6, durasiInt);
            preparedStatement.setDouble(7, ongkosParkir);

            // Execute the SQL statement
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke database.");
            }

            // Close the connection and prepared statement
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam menyimpan data ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver JDBC MySQL tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the ParseException or log it as needed
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam mengkonversi tanggal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateDataParkir() {
        // Get the nomorKendaraan (vehicle number) to identify the record to update
        String nomorKendaraanToUpdate = nomorKendaraanField.getText();

        // Check if nomorKendaraanToUpdate is not empty
        if (!nomorKendaraanToUpdate.isEmpty()) {
            try {
                // Establish a database connection to MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/parkir"; // Change the database name to "parkir"
                String username = "root"; // Change the username to "root"
                String password = ""; // Set the password for your MySQL server
                Connection connection = DriverManager.getConnection(url, username, password);

                // Check if the record with the specified nomorKendaraan exists
                if (isRecordExists(nomorKendaraanToUpdate, connection)) {
                    // Perform the update operation
                    String updateSQL = "UPDATE parkiran SET Nomor_Kendaraan = ? WHERE Nomor_Kendaraan = ?";

                    // Set the updated values
                    String newNomorKendaraan = nomorKendaraanField.getText();

                    // Create a PreparedStatement and set the parameters
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                    preparedStatement.setString(1, newNomorKendaraan);
                    preparedStatement.setString(2, nomorKendaraanToUpdate);

                    // Execute the update query
                    int rowsUpdated = preparedStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(AplikasiParkir.this, "Data berhasil diupdate di database.");
                    } else {
                        JOptionPane.showMessageDialog(AplikasiParkir.this, "Gagal mengupdate data.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close the PreparedStatement and the database connection
                    preparedStatement.close();
                    connection.close();
                } else {
                    JOptionPane.showMessageDialog(AplikasiParkir.this, "Data dengan nomor kendaraan " + nomorKendaraanToUpdate + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(AplikasiParkir.this, "Terjadi kesalahan dalam mengupdate data di database.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(AplikasiParkir.this, "Driver JDBC MySQL tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(AplikasiParkir.this, "Masukkan nomor kendaraan yang ingin diupdate.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to check if a record with the specified nomorKendaraan exists
    private boolean isRecordExists(String nomorKendaraan, Connection connection) throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM parkiran WHERE Nomor_Kendaraan = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkSQL);
        checkStatement.setString(1, nomorKendaraan);
        ResultSet resultSet = checkStatement.executeQuery();

        resultSet.next();
        int count = resultSet.getInt(1);

        // Close the PreparedStatement
        checkStatement.close();

        return count > 0;
    }

    private void tampilkanData() {
        try {
            // Establish a database connection to MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/parkir"; // Change the database name to "parkir"
            String username = "root"; // Change the username to "root"
            String password = ""; // Set the password for your MySQL server
            Connection connection = DriverManager.getConnection(url, username, password);

            // Write a SQL query to retrieve all data from the database
            String selectSQL = "SELECT * FROM parkiran";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = selectStatement.executeQuery();

            // Create a new frame to display the data
            JFrame frame = new JFrame("Data Parkir");
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);

            // Create a new table to display the data
            DefaultTableModel dataTableModel = new DefaultTableModel();
            dataTableModel.addColumn("Nomor Kendaraan");
            dataTableModel.addColumn("Jenis Kendaraan");
            dataTableModel.addColumn("Tanggal Masuk");
            dataTableModel.addColumn("Jam Masuk");
            dataTableModel.addColumn("Jam Keluar");
            dataTableModel.addColumn("Durasi");
            dataTableModel.addColumn("Total Ongkos Parkir");
            JTable dataTable = new JTable(dataTableModel);

            // Populate the table with data from the ResultSet
            while (resultSet.next()) {
                String nomorKendaraan = resultSet.getString("Nomor_Kendaraan");
                String jenisKendaraan = resultSet.getString("Jenis_Kendaraan");
                String tanggalMasuk = resultSet.getString("Tanggal_Masuk");
                String jamMasuk = resultSet.getString("Jam_Masuk");
                String jamKeluar = resultSet.getString("Jam_Keluar");
                String durasi = resultSet.getString("Durasi");
                String totalOngkosParkir = resultSet.getString("Total_Ongkos_Parkir");

                dataTableModel.addRow(new Object[]{nomorKendaraan, jenisKendaraan, tanggalMasuk, jamMasuk, jamKeluar, durasi, totalOngkosParkir});
            }

            // Add the table to the frame
            JScrollPane scrollPane = new JScrollPane(dataTable);
            frame.add(scrollPane);

            // Set the frame to be visible
            frame.setVisible(true);

            // Close the ResultSet, PreparedStatement, and the database connection
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AplikasiParkir.this, "Terjadi kesalahan dalam menampilkan data dari database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tambahDataParkir() {
        nomorKendaraanField.setText("");
        jenisKendaraanField.setText("");
        TanggalMasukField.setText("");
        jamMasukField.setText("");
        JamKeluartextField.setText("");
        DurasiField.setText("");
        TotalField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplikasiParkir();
            }
        });
    }
}