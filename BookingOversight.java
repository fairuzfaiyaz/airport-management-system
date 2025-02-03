import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BookingOversight extends JFrame {
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JButton cancelButton, modifyButton;

    public BookingOversight() {
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Background Image
        ImageIcon icon = new ImageIcon("bookoversight.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        backgroundImage.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Booking Oversight");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 50, 400, 40);
        titleLabel.setForeground(Color.WHITE);
        backgroundImage.add(titleLabel);

        // Buttons for managing bookings
        cancelButton = new JButton("Cancel Booking");
        cancelButton.setBounds(500, 250, 200, 40);
        cancelButton.addActionListener(e -> cancelBooking());
        backgroundImage.add(cancelButton);

        modifyButton = new JButton("Modify Booking");
        modifyButton.setBounds(750, 250, 200, 40);
        modifyButton.addActionListener(e -> modifyBooking());
        backgroundImage.add(modifyButton);

        // Table to display bookings
        String[] columns = {"Flight", "Card Number", "Name", "Expiry Date"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(30);
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(100, 320, 1500, 400);
        backgroundImage.add(scrollPane);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 250, 150, 40);
        backButton.addActionListener(e -> {
            new AdminDashboard(); // Open AdminDashboard
            this.dispose(); // Close current window
        });
        backgroundImage.add(backButton);


        // Load all bookings
        loadBookings();

        this.setContentPane(backgroundImage);
        this.setVisible(true);
    }

    private void loadBookings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("booking_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Flight:")) {
                    String flightDetails = line.substring(8);
                    String cardNumber = reader.readLine().substring(13);
                    String name = reader.readLine().substring(6);
                    String expiryDate = reader.readLine().substring(13);

                    // Add to table
                    tableModel.addRow(new Object[]{flightDetails, cardNumber, name, expiryDate});

                    // Skip the separator line
                    reader.readLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading booking history.");
        }
    }

    private void cancelBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");
            return;
        }

        String flightDetails = (String) tableModel.getValueAt(selectedRow, 0);

        // Confirmation for cancellation
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel the booking for " + flightDetails + "?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                File inputFile = new File("booking_history.txt");
                File tempFile = new File("booking_history_temp.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean bookingFound = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Flight:") && !bookingFound) {
                        String flightInfo = line.substring(8);
                        if (flightInfo.equals(flightDetails)) {
                            // Skip the next 3 lines of the booking to remove it
                            reader.readLine(); // Card Number
                            reader.readLine(); // Name
                            reader.readLine(); // Expiry Date
                            writer.newLine(); // Skip separator line
                            bookingFound = true;
                            continue;
                        }
                    }
                    writer.write(line);
                    writer.newLine();
                }

                reader.close();
                writer.close();

                inputFile.delete();
                tempFile.renameTo(inputFile);

                // Remove from table
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error cancelling booking.");
            }
        }
    }

    private void modifyBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to modify.");
            return;
        }

        String flightDetails = (String) tableModel.getValueAt(selectedRow, 0);
        String cardNumber = (String) tableModel.getValueAt(selectedRow, 1);
        String name = (String) tableModel.getValueAt(selectedRow, 2);
        String expiryDate = (String) tableModel.getValueAt(selectedRow, 3);

        // Show a dialog to modify booking details
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Flight Details:"));
        panel.add(new JTextField(flightDetails));
        panel.add(new JLabel("Card Number:"));
        JTextField cardField = new JTextField(cardNumber);
        panel.add(cardField);
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(name);
        panel.add(nameField);
        panel.add(new JLabel("Expiry Date:"));
        JTextField expiryField = new JTextField(expiryDate);
        panel.add(expiryField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Modify Booking", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newCardNumber = cardField.getText().trim();
            String newName = nameField.getText().trim();
            String newExpiryDate = expiryField.getText().trim();

            // Update the table
            tableModel.setValueAt(newCardNumber, selectedRow, 1);
            tableModel.setValueAt(newName, selectedRow, 2);
            tableModel.setValueAt(newExpiryDate, selectedRow, 3);

            // Update the booking history file
            try {
                File inputFile = new File("booking_history.txt");
                File tempFile = new File("booking_history_temp.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean bookingFound = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Flight:") && !bookingFound) {
                        String flightInfo = line.substring(8);
                        if (flightInfo.equals(flightDetails)) {
                            writer.write("Flight: " + flightDetails);
                            writer.newLine();
                            writer.write("Card Number: " + newCardNumber);
                            writer.newLine();
                            writer.write("Name: " + newName);
                            writer.newLine();
                            writer.write("Expiry Date: " + newExpiryDate);
                            writer.newLine();
                            writer.newLine();
                            bookingFound = true;
                            continue;
                        }
                    }
                    writer.write(line);
                    writer.newLine();
                }

                reader.close();
                writer.close();

                inputFile.delete();
                tempFile.renameTo(inputFile);

                JOptionPane.showMessageDialog(this, "Booking modified successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error modifying booking.");
            }
        }
    }

    public static void main(String[] args) {
        new BookingOversight();
    }
}
