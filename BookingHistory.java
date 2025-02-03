import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;

public class BookingHistory extends JFrame {

    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private String username;


    public BookingHistory(String username) {
        this.username = username;
        setupFrame();
        loadBookingHistory();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Set background image
        ImageIcon icon = new ImageIcon("bookinghistory.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        backgroundImage.setLayout(null);
        this.setContentPane(backgroundImage);

        // Table setup with columns
        String[] columns = {"Flight No", "From", "To", "Departure", "Arrival", "Price ($)",
                "Card Number", "Name", "Expiry Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bookingTable.setRowHeight(40);
        bookingTable.setGridColor(new Color(200, 200, 200));
        bookingTable.setShowGrid(true);
        bookingTable.setAutoCreateRowSorter(true);

        // Table header styling
        JTableHeader header = bookingTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));

        // Center-align table cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < bookingTable.getColumnCount(); i++) {
            bookingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(100, 150, 1500, 600);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 120, 215), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundImage.add(scrollPane);

        // Back Button (opens PassengerDashboard if needed, or simply closes)
        JButton backButton = new JButton("← Back");
        backButton.setBounds(50, 50, 120, 45);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        // Modify the action to open PassengerDashboard with username if required.
        backButton.addActionListener(e -> {
            // Uncomment the next line if PassengerDashboard accepts a username.
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

        this.setVisible(true);
    }

    private void loadBookingHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader("booking_history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Expecting the record to start with "Flight:"
                if (line.startsWith("Flight:")) {
                    // Remove the prefix and split the flight details
                    String flightData = line.replaceFirst("Flight:\\s*", "");
                    String[] flightParts = flightData.split(",");
                    // Ensure there are exactly 6 parts for flight details
                    if (flightParts.length != 6) {
                        continue;
                    }

                    // Read the next three lines for payment details
                    String cardLine = br.readLine();  // e.g., "Card Number: 123"
                    String nameLine = br.readLine();  // e.g., "Name: 123"
                    String expiryLine = br.readLine();  // e.g., "Expiry Date: 123"

                    // Extract values after the colon
                    String cardNumber = cardLine != null && cardLine.contains(":") ? cardLine.split(":", 2)[1].trim() : "";
                    String name = nameLine != null && nameLine.contains(":") ? nameLine.split(":", 2)[1].trim() : "";
                    String expiry = expiryLine != null && expiryLine.contains(":") ? expiryLine.split(":", 2)[1].trim() : "";

                    // Add the row to the table model
                    Object[] rowData = {
                            flightParts[0].trim(),  // Flight No
                            flightParts[1].trim(),  // From
                            flightParts[2].trim(),  // To
                            flightParts[3].trim(),  // Departure
                            flightParts[4].trim(),  // Arrival
                            flightParts[5].trim(),  // Price ($)
                            cardNumber,
                            name,
                            expiry
                    };
                    tableModel.addRow(rowData);

                    // Skip the delimiter line ("---------")
                    br.readLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading booking history: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
