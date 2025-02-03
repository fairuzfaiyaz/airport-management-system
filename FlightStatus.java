import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class FlightStatus extends JFrame {
    private JTable flightStatusTable;
    private DefaultTableModel tableModel;
    private String username;

    public FlightStatus(String username) {
        this.username=username;
        setupFrame();
        displayFlightStatus();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Set background image
        ImageIcon icon = new ImageIcon("flightstatus.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(
                icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        backgroundImage.setLayout(null);
        this.setContentPane(backgroundImage);

        // Back Button
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
        backButton.addActionListener(e -> {
            this.dispose();
            new PassengerDashboard(username);
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

        // Title label
        JLabel titleLabel = new JLabel("Flight Status Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 50, 400, 40);
        titleLabel.setForeground(Color.WHITE);
        backgroundImage.add(titleLabel);

        // Table setup
        String[] columns = {"Flight Code", "Departure", "Arrival", "Departure Time", "Arrival Time", "Seat Count", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightStatusTable = new JTable(tableModel);
        flightStatusTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightStatusTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        flightStatusTable.setRowHeight(40);
        flightStatusTable.setGridColor(new Color(200, 200, 200));
        flightStatusTable.setShowGrid(true);
        flightStatusTable.setAutoCreateRowSorter(true);

        // Table header styling
        JTableHeader header = flightStatusTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));

        // Center-align table cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < flightStatusTable.getColumnCount(); i++) {
            flightStatusTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(flightStatusTable);
        scrollPane.setBounds(50, 120, 1600, 600);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 120, 215), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundImage.add(scrollPane);

        this.setVisible(true);
    }

    private void displayFlightStatus() {
        try (BufferedReader reader = new BufferedReader(new FileReader("booking_history.txt"))) {
            String line;
            Random random = new Random();
            String[] statuses = {"On-time", "Delayed", "Canceled"};

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Flight:")) {
                    // Remove the "Flight:" prefix and split details by comma
                    String flightData = line.replaceFirst("Flight:\\s*", "");
                    String[] flightDetails = flightData.split(",");

                    // Ensure the flight details array has 6 elements: Flight Code, Departure, Arrival, Departure Time, Arrival Time, Seat Count
                    if (flightDetails.length != 6) {
                        continue;
                    }

                    // Get a random flight status
                    String flightStatus = statuses[random.nextInt(statuses.length)];

                    // Prepare row data
                    Object[] rowData = {
                            flightDetails[0].trim(),  // Flight Code
                            flightDetails[1].trim(),  // Departure
                            flightDetails[2].trim(),  // Arrival
                            flightDetails[3].trim(),  // Departure Time
                            flightDetails[4].trim(),  // Arrival Time
                            flightDetails[5].trim(),  // Seat Count
                            flightStatus
                    };
                    tableModel.addRow(rowData);

                    // Skip the next lines until the delimiter line ("---------")
                    // Typically, the next 4 lines are: Card Number, Name, Expiry Date, and the delimiter line.
                    for (int i = 0; i < 4; i++) {
                        reader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading flight status: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
