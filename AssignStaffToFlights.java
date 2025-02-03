import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AssignStaffToFlights extends JFrame {
    private JComboBox<String> flightComboBox;
    private JTextField staffNameField;
    private JButton assignButton, updateButton, deleteButton;
    private JTable staffTable;
    private DefaultTableModel tableModel;

    public AssignStaffToFlights() {
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Background
        ImageIcon icon = new ImageIcon("assignstaff.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Assign Staff to Flights");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setBounds(600, 50, 500, 50);
        titleLabel.setForeground(new Color(0, 120, 215));
        backgroundImage.add(titleLabel);

        // Flight selection
        JLabel flightLabel = new JLabel("Select Flight:");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        flightLabel.setBounds(500, 150, 150, 30);
        backgroundImage.add(flightLabel);

        flightComboBox = new JComboBox<>();
        flightComboBox.setBounds(700, 150, 200, 30);
        populateFlights();
        backgroundImage.add(flightComboBox);

        // Staff Name
        JLabel staffLabel = new JLabel("Staff Name:");
        staffLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        staffLabel.setBounds(500, 200, 150, 30);
        backgroundImage.add(staffLabel);

        staffNameField = new JTextField();
        staffNameField.setBounds(700, 200, 200, 30);
        backgroundImage.add(staffNameField);

        // Buttons
        assignButton = new JButton("Assign Staff");
        assignButton.setBounds(500, 250, 150, 40);
        assignButton.addActionListener(e -> assignStaff());
        backgroundImage.add(assignButton);

        updateButton = new JButton("Update Assignment");
        updateButton.setBounds(670, 250, 200, 40);
        updateButton.addActionListener(e -> updateAssignment());
        backgroundImage.add(updateButton);

        deleteButton = new JButton("Delete Assignment");
        deleteButton.setBounds(900, 250, 200, 40);
        deleteButton.addActionListener(e -> deleteAssignment());
        backgroundImage.add(deleteButton);

        // Table
        String[] columns = {"Flight No", "Staff Name"};
        tableModel = new DefaultTableModel(columns, 0);
        staffTable = new JTable(tableModel);
        staffTable.setRowHeight(30);
        staffTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBounds(400, 320, 900, 400);
        backgroundImage.add(scrollPane);

        loadAssignments();

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, 120, 40);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                new EmptyBorder(5, 15, 5, 15)
        ));

// Add action listener to go back to the Admin Dashboard or previous window
        backButton.addActionListener(e -> {
            new AdminDashboard(); // Open the Admin Dashboard
            this.dispose(); // Close the current AssignStaffToFlights window
        });

// Add the Back button to the background image or main container
        backgroundImage.add(backButton);


        this.setContentPane(backgroundImage);
        this.setVisible(true);
    }

    private void populateFlights() {
        try (BufferedReader reader = new BufferedReader(new FileReader("flight_schedules.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length > 0) {
                    flightComboBox.addItem(details[0]); // Adding just the flight number
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading flight list.");
        }
    }

    private void loadAssignments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("staff_assignments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 2) {
                    tableModel.addRow(new Object[]{details[0], details[1]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading staff assignments.");
        }
    }

    private void assignStaff() {
        String flightNo = (String) flightComboBox.getSelectedItem();
        String staffName = staffNameField.getText().trim();

        if (flightNo == null || staffName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a flight and enter a staff name.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staff_assignments.txt", true))) {
            writer.write(flightNo + "," + staffName);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Staff assigned successfully!");
            tableModel.addRow(new Object[]{flightNo, staffName});
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error assigning staff.");
        }
    }

    private void updateAssignment() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an assignment to update.");
            return;
        }

        String flightNo = (String) flightComboBox.getSelectedItem();
        String staffName = staffNameField.getText().trim();

        if (staffName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Staff name cannot be empty.");
            return;
        }

        try {
            File inputFile = new File("staff_assignments.txt");
            File tempFile = new File("staff_assignments_temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(flightNo)) {
                    line = flightNo + "," + staffName;
                }
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);
            tableModel.setValueAt(staffName, selectedRow, 1);
            JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating assignment.");
        }
    }

    private void deleteAssignment() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an assignment to delete.");
            return;
        }

        String flightNo = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            File inputFile = new File("staff_assignments.txt");
            File tempFile = new File("staff_assignments_temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(flightNo)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Assignment deleted successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting assignment.");
        }
    }


}
