import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GenerateReports extends JFrame {

    private JTextArea reportArea;

    public GenerateReports() {
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("generatereports.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);

        JLabel titleLabel = new JLabel("Generate Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 50, 400, 40);
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);

        // Report area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.PLAIN, 16));  // Changed to normal font
        reportArea.setBackground(Color.WHITE);  // Changed background to white
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBounds(50, 100, 1600, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        this.add(scrollPane);

        // Styled button
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.setBounds(650, 730, 200, 40);
        generateReportButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateReportButton.setBackground(new Color(59, 89, 182));
        generateReportButton.setForeground(Color.WHITE);
        generateReportButton.setFocusPainted(false);
        generateReportButton.addActionListener(e -> generateReport());
        this.add(generateReportButton);

    // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 730, 150, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(220, 53, 69)); // Red color for visibility
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            new AdminDashboard(); // Open Admin Dashboard
            this.dispose(); // Close the current window
        });
        this.add(backButton);



        this.setVisible(true);
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("%-60s\n", "AIRLINE MANAGEMENT SYSTEM REPORT")).append("\n");
        report.append("====================================================================================================\n\n");

        report.append("FLIGHT SCHEDULES\n");
        report.append("----------------\n");
        loadFlightSchedules(report);
        report.append("\n");

        report.append("STAFF ASSIGNMENTS\n");
        report.append("-----------------\n");
        loadStaffAssignments(report);
        report.append("\n");

        report.append("BOOKING HISTORY\n");
        report.append("---------------\n");
        loadBookingHistory(report);
        report.append("\n");

        // Removed User Information section

        reportArea.setText(report.toString());
    }

    private void loadFlightSchedules(StringBuilder report) {
        try (BufferedReader reader = new BufferedReader(new FileReader("flight_schedules.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // Trim extra spaces
                if (line.isEmpty()) continue;
                String[] flightInfo = line.split(",");

                // Check if the line has exactly 6 parts
                if (flightInfo.length == 6) {
                    report.append(String.format("✈ Flight %-5s: %-15s to %-15s\n", flightInfo[0], flightInfo[1], flightInfo[2]));
                    report.append(String.format("   Departure: %-16s | Arrival: %-16s | Seats: %-3s\n",
                            flightInfo[3], flightInfo[4], flightInfo[5]));
                    report.append("   ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n");
                } else {
                    report.append("⚠ Invalid flight data: ").append(line).append("\n");
                }
            }
        } catch (IOException e) {
            report.append("❌ Error loading flight schedules\n");
        }
    }

    private void loadStaffAssignments(StringBuilder report) {
        try (BufferedReader reader = new BufferedReader(new FileReader("staff_assignments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // Trim extra spaces
                if (line.isEmpty()) continue;
                String[] staffInfo = line.split(",");

                // Check if the line has exactly 2 parts
                if (staffInfo.length == 2) {
                    report.append(String.format("➤ Flight %-5s: Assigned to %-20s\n", staffInfo[0], staffInfo[1]));
                } else {
                    report.append("⚠ Invalid staff assignment: ").append(line).append("\n");
                }
            }
        } catch (IOException e) {
            report.append("❌ Error loading staff assignments\n");
        }
    }

    private void loadBookingHistory(StringBuilder report) {
        try (BufferedReader reader = new BufferedReader(new FileReader("booking_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // Trim extra spaces
                if (line.isEmpty()) continue;

                if (line.startsWith("Flight:")) {
                    String[] flight = line.split(",");
                    report.append(String.format("✅ Booking: %-5s (%s to %s)\n",
                            flight[0].split(":")[1].trim(), flight[1].trim(), flight[2].trim()));
                    report.append(String.format("   Departure: %-16s | Arrival: %s\n",
                            flight[3].trim(), flight[4].trim()));
                } else if (line.startsWith("Card Number:")) {
                    report.append(String.format("   Card: %-20s", line.split(":")[1].trim()));
                } else if (line.startsWith("Name:")) {
                    report.append(String.format(" | Name: %-20s\n", line.split(":")[1].trim()));
                } else if (line.startsWith("Expiry Date:")) {
                    report.append(String.format("   Expiry: %s\n", line.split(":")[1].trim()));
                    report.append("   ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――\n");
                }
            }
        } catch (IOException e) {
            report.append("❌ Error loading booking history\n");
        }
    }
}
