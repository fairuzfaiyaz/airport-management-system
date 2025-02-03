import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BookFlight extends JFrame {
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private JButton bookButton, backButton;
    private String[] selectedFlight;
    private String username;

    public BookFlight(String username) {
        this.username = username;
        setupFrame();
        loadFlightDetails();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Set background image
        ImageIcon icon = new ImageIcon("bookflight.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        backgroundImage.setLayout(null);
        this.setContentPane(backgroundImage);

        // Table setup (using the same styling as AvailableFlights)
        String[] columns = {"Flight No", "From", "To", "Departure", "Arrival", "Price ($)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightTable = new JTable(tableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        flightTable.setRowHeight(40);
        flightTable.setGridColor(new Color(200, 200, 200));
        flightTable.setShowGrid(true);
        flightTable.setAutoCreateRowSorter(true);

        // Table header styling
        JTableHeader header = flightTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));

        // Center-align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < flightTable.getColumnCount(); i++) {
            flightTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scroll pane styling
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(100, 150, 1500, 550);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 120, 215), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundImage.add(scrollPane);

        // Book Flight button styling
        bookButton = new JButton("Book Flight");
        bookButton.setBounds(100, 720, 150, 45);
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bookButton.setBackground(new Color(0, 120, 215));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        bookButton.addActionListener(e -> openPaymentPage());
        // Hover effect for book button
        bookButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookButton.setBackground(new Color(0, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookButton.setBackground(new Color(0, 120, 215));
            }
        });
        backgroundImage.add(bookButton);

        // Back button styling
        backButton = new JButton("← Back");
        backButton.setBounds(100, 80, 120, 45);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        backButton.addActionListener(e -> {
            new PassengerDashboard(username);
            this.dispose();
        });
        // Hover effects for back button
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0, 120, 215));
            }
        });
        backgroundImage.add(backButton);

        // Flight selection listener
        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = flightTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedFlight = new String[tableModel.getColumnCount()];
                    for (int i = 0; i < selectedFlight.length; i++) {
                        selectedFlight[i] = tableModel.getValueAt(selectedRow, i).toString();
                    }
                }
            }
        });

        this.setVisible(true);
    }

    private void loadFlightDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader("flight_schedules.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming the file data is comma-separated and has exactly 6 fields.
                String[] data = line.split(",");
                if (data.length == 6) {
                    tableModel.addRow(data);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading flight details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPaymentPage() {
        if (selectedFlight != null) {
            new PaymentPage(String.join(", ", selectedFlight));
        } else {
            JOptionPane.showMessageDialog(this, "Please select a flight first.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
