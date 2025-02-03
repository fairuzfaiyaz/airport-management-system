import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LeaveApproval extends JFrame {
    private JTable requestTable;
    private DefaultTableModel tableModel;
    private JButton approveButton, declineButton;

    public LeaveApproval() {
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Background Image
        ImageIcon icon = new ImageIcon("leaveapproval.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        backgroundImage.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Leave Approval");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 50, 400, 40);
        titleLabel.setForeground(Color.WHITE);
        backgroundImage.add(titleLabel);

        // Buttons for managing requests
        approveButton = new JButton("Approve Request");
        approveButton.setBounds(500, 250, 200, 40);
        approveButton.addActionListener(e -> approveRequest());
        backgroundImage.add(approveButton);

        declineButton = new JButton("Decline Request");
        declineButton.setBounds(750, 250, 200, 40);
        declineButton.addActionListener(e -> declineRequest());
        backgroundImage.add(declineButton);

        // Table to display leave requests
        String[] columns = {"Username", "Request Type", "Flight"};
        tableModel = new DefaultTableModel(columns, 0);
        requestTable = new JTable(tableModel);
        requestTable.setRowHeight(30);
        requestTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(requestTable);
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


        // Load leave requests
        loadRequests();

        this.setContentPane(backgroundImage);
        this.setVisible(true);
    }

    private void loadRequests() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cancellation_requests.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username:")) {
                    String username = line.substring(9).trim();
                    String requestType = reader.readLine().substring(12).trim();
                    String flight = reader.readLine().substring(8).trim();

                    // Add to table
                    tableModel.addRow(new Object[]{username, requestType, flight});

                    // Skip the separator line
                    reader.readLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests.");
        }
    }

    private void approveRequest() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to approve.");
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRow, 0);
        String flight = (String) tableModel.getValueAt(selectedRow, 2);

        // Confirmation for approval
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to approve the request for " + username + " on flight " + flight + "?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                File inputFile = new File("cancellation_requests.txt");
                File tempFile = new File("cancellation_requests_temp.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean requestFound = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Username:") && !requestFound) {
                        String requestUsername = line.substring(9).trim();
                        if (requestUsername.equals(username)) {
                            // Skip this request and do not write it to the temp file (cancels the request)
                            reader.readLine(); // Skip Request Type line
                            reader.readLine(); // Skip Flight line
                            reader.readLine(); // Skip separator line
                            requestFound = true;
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

                JOptionPane.showMessageDialog(this, "Request approved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error approving the request.");
            }
        }
    }

    private void declineRequest() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to decline.");
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRow, 0);
        String flight = (String) tableModel.getValueAt(selectedRow, 2);

        // Confirmation for decline
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to decline the request for " + username + " on flight " + flight + "?", "Confirm Decline", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                File inputFile = new File("cancellation_requests.txt");
                File tempFile = new File("cancellation_requests_temp.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean requestFound = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Username:") && !requestFound) {
                        String requestUsername = line.substring(9).trim();
                        if (requestUsername.equals(username)) {
                            // Skip this request and do not write it to the temp file (declines the request)
                            reader.readLine(); // Skip Request Type line
                            reader.readLine(); // Skip Flight line
                            reader.readLine(); // Skip separator line
                            requestFound = true;
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

                JOptionPane.showMessageDialog(this, "Request declined successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error declining the request.");
            }
        }
    }


}
