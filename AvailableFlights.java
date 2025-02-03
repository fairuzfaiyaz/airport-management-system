import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;

public class AvailableFlights extends JFrame {
    private String username;
    public AvailableFlights( String username) {
        this.username = username;
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Background Image Setup
        ImageIcon icon = new ImageIcon("availableflight.jpeg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setLayout(new BorderLayout());

        // Create overlay panel with transparency
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);

        // Table styling
        String[] columns = {"Flight No", "From", "To", "Departure", "Arrival", "Price ($)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable flightTable = new JTable(model);
        flightTable.setRowHeight(40);
        flightTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        // Load flight data
        loadFlightData(model);

        // Back button styling
        JButton backButton = new JButton("← Back");
        backButton.setBounds(100, 80, 120, 45);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setFocusPainted(false);
        backButton.setBorder(new CompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        backButton.addActionListener(e -> {
            this.dispose();  // Close the AvailableFlights page
            new PassengerDashboard(username);  // Reopen the PassengerDashboard with the stored username
        });
        // Hover effects for button
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0, 150, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0, 120, 215));
            }
        });

        // Title label
        JLabel titleLabel = new JLabel("Available Flights");
        titleLabel.setBounds(0, 70, 1700, 60);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 120, 215));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.add(titleLabel);
        contentPanel.add(scrollPane);
        contentPanel.add(backButton);

        backgroundImage.add(contentPanel, BorderLayout.CENTER);
        this.setContentPane(backgroundImage);
        this.setVisible(true);
    }

    private void loadFlightData(DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader("flight_schedules.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] flightData = line.split(",");
                if (flightData.length == 6) {
                    model.addRow(flightData);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading flight data: " + e.getMessage(),
                    "Data Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}