import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FlightManagement extends JFrame {
    // Six text fields corresponding to the six columns.
    private JTextField flightCodeField, fromField, toField, departureField, arrivalTimeField, priceField;
    private JTable flightTable;
    private DefaultTableModel model;
    private JButton addButton, updateButton, deleteButton;
    private final String FILE_NAME = "flight_schedules.txt";

    public FlightManagement() {
        setupFrame();
    }

    private void setupFrame() {
        // Frame settings
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Background image
        ImageIcon icon = new ImageIcon("flightmanagement.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(
                icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setLayout(new BorderLayout());

        // Transparent content panel over the background
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);

        // Title label
        JLabel titleLabel = new JLabel("Flight Management");
        titleLabel.setBounds(0, 30, 1700, 60);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 120, 215));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel);

        // Table setup with six columns matching flight data
        String[] columns = {"Flight Code", "From", "To", "Departure", "Arrival", "Price ($)"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightTable = new JTable(model);
        flightTable.setRowHeight(40);
        flightTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightTable.setAutoCreateRowSorter(true);

        // Header styling
        JTableHeader header = flightTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));

        // Center-align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < flightTable.getColumnCount(); i++) {
            flightTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scroll pane with a compound border
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(100, 120, 1500, 400);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 120, 215), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        contentPanel.add(scrollPane);

        // Load flight data from file
        loadFlightData();

        // Create and style input labels and fields for 6 columns.
        // We'll use a horizontal layout; each field gets a fixed width and gap.
        int labelY = 550, fieldY = 590, labelWidth = 100, fieldWidth = 150, gapX = 50;
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Column 0: Flight Code
        JLabel flightCodeLabel = new JLabel("Flight Code:");
        flightCodeLabel.setFont(fieldFont);
        flightCodeLabel.setBounds(100, labelY, labelWidth, 30);
        contentPanel.add(flightCodeLabel);

        flightCodeField = new JTextField();
        flightCodeField.setFont(fieldFont);
        flightCodeField.setBounds(100, fieldY, fieldWidth, 30);
        contentPanel.add(flightCodeField);

        // Column 1: From
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(fieldFont);
        fromLabel.setBounds(100 + (fieldWidth + gapX), labelY, labelWidth, 30);
        contentPanel.add(fromLabel);

        fromField = new JTextField();
        fromField.setFont(fieldFont);
        fromField.setBounds(100 + (fieldWidth + gapX), fieldY, fieldWidth, 30);
        contentPanel.add(fromField);

        // Column 2: To
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(fieldFont);
        toLabel.setBounds(100 + 2 * (fieldWidth + gapX), labelY, labelWidth, 30);
        contentPanel.add(toLabel);

        toField = new JTextField();
        toField.setFont(fieldFont);
        toField.setBounds(100 + 2 * (fieldWidth + gapX), fieldY, fieldWidth, 30);
        contentPanel.add(toField);

        // Column 3: Departure
        JLabel departureLabel = new JLabel("Departure:");
        departureLabel.setFont(fieldFont);
        departureLabel.setBounds(100 + 3 * (fieldWidth + gapX), labelY, labelWidth, 30);
        contentPanel.add(departureLabel);

        departureField = new JTextField();
        departureField.setFont(fieldFont);
        departureField.setBounds(100 + 3 * (fieldWidth + gapX), fieldY, fieldWidth, 30);
        contentPanel.add(departureField);

        // Column 4: Arrival
        JLabel arrivalLabel = new JLabel("Arrival:");
        arrivalLabel.setFont(fieldFont);
        arrivalLabel.setBounds(100 + 4 * (fieldWidth + gapX), labelY, labelWidth, 30);
        contentPanel.add(arrivalLabel);

        arrivalTimeField = new JTextField();
        arrivalTimeField.setFont(fieldFont);
        arrivalTimeField.setBounds(100 + 4 * (fieldWidth + gapX), fieldY, fieldWidth, 30);
        contentPanel.add(arrivalTimeField);

        // Column 5: Price ($)
        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setFont(fieldFont);
        priceLabel.setBounds(100 + 5 * (fieldWidth + gapX), labelY, labelWidth, 30);
        contentPanel.add(priceLabel);

        priceField = new JTextField();
        priceField.setFont(fieldFont);
        priceField.setBounds(100 + 5 * (fieldWidth + gapX), fieldY, fieldWidth, 30);
        contentPanel.add(priceField);

        // Create and style the buttons; place them below the text fields.
        int buttonY = 640;
        addButton = createStyledButton("Add", 400, buttonY);
        addButton.addActionListener(e -> addFlight());
        contentPanel.add(addButton);

        updateButton = createStyledButton("Update", 600, buttonY);
        updateButton.addActionListener(e -> updateFlight());
        contentPanel.add(updateButton);

        deleteButton = createStyledButton("Delete", 800, buttonY);
        deleteButton.addActionListener(e -> deleteFlight());
        contentPanel.add(deleteButton);

        // Create the Back button using the existing createStyledButton function
        JButton backButton = createStyledButton("Back", 50, 50);

// Add action listener to go back to the AdminDashboard
        backButton.addActionListener(e -> {
            new AdminDashboard(); // Replace with the actual class name for your Admin Dashboard
            this.dispose(); // Close the current FlightManagement window
        });

// Add the Back button to the content panel
        contentPanel.add(backButton);


        // Add selection listener to load row data into fields when a row is selected
        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && flightTable.getSelectedRow() != -1) {
                int selectedRow = flightTable.getSelectedRow();
                flightCodeField.setText(model.getValueAt(selectedRow, 0).toString());
                fromField.setText(model.getValueAt(selectedRow, 1).toString());
                toField.setText(model.getValueAt(selectedRow, 2).toString());
                departureField.setText(model.getValueAt(selectedRow, 3).toString());
                arrivalTimeField.setText(model.getValueAt(selectedRow, 4).toString());
                priceField.setText(model.getValueAt(selectedRow, 5).toString());
            }
        });

        // Add content panel to the background image and set it as the frame content
        backgroundImage.add(contentPanel, BorderLayout.CENTER);
        this.setContentPane(backgroundImage);
        this.setVisible(true);
    }

    // Helper method to create a styled button with hover effects
    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 45);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 150, 255));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
        return button;
    }

    // Load flight data from file into the table
    private void loadFlightData() {
        model.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma and add the row only if exactly 6 columns are present.
                String[] flightData = line.split(",");
                if (flightData.length == 6) {
                    model.addRow(flightData);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading flights.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add flight: append new flight to file and refresh table
    private void addFlight() {
        String flightDetails = String.join(",",
                flightCodeField.getText().trim(),
                fromField.getText().trim(),
                toField.getText().trim(),
                departureField.getText().trim(),
                arrivalTimeField.getText().trim(),
                priceField.getText().trim()
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(flightDetails);
            writer.newLine();
            loadFlightData();
            clearFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error adding flight.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update the selected flight in the table model and persist changes to file.
    private void updateFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a flight to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setValueAt(flightCodeField.getText().trim(), selectedRow, 0);
        model.setValueAt(fromField.getText().trim(), selectedRow, 1);
        model.setValueAt(toField.getText().trim(), selectedRow, 2);
        model.setValueAt(departureField.getText().trim(), selectedRow, 3);
        model.setValueAt(arrivalTimeField.getText().trim(), selectedRow, 4);
        model.setValueAt(priceField.getText().trim(), selectedRow, 5);
        saveFlightData();
        clearFields();
    }

    // Delete the selected flight from the table and update file
    private void deleteFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            saveFlightData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a flight to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Write the current table model back to the file
    private void saveFlightData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    line.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving flight data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear all text fields and clear the table selection.
    private void clearFields() {
        flightCodeField.setText("");
        fromField.setText("");
        toField.setText("");
        departureField.setText("");
        arrivalTimeField.setText("");
        priceField.setText("");
        flightTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlightManagement());
    }
}
