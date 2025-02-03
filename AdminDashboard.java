import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("admindashboard.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);

        // Left Panel for Admin Information
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 400, this.getHeight());
        leftPanel.setBackground(new Color(0, 0, 0)); // Semi-transparent black background
        this.add(leftPanel);

        // Admin Panel Title
        JLabel adminTitle = new JLabel("Admin Panel");
        adminTitle.setFont(new Font("Arial", Font.BOLD, 30));
        adminTitle.setForeground(Color.WHITE);
        adminTitle.setBounds(20, 50, 200, 40);
        leftPanel.add(adminTitle);

        // Features Label
        JLabel featuresLabel = new JLabel("<html>Manage flights, assign staff, oversee bookings, approve leaves,<br>and generate reports.</html>");
        featuresLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        featuresLabel.setForeground(Color.WHITE);
        featuresLabel.setBounds(20, 100, 350, 150);
        leftPanel.add(featuresLabel);

        // Buttons Panel on the right
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 20)); // 6 buttons, vertical layout with spacing
        buttonPanel.setBounds(450, 100, 300, 500); // Positioning the buttons
        this.add(buttonPanel);

        // Create buttons for each functionality
        JButton flightManagementButton = new JButton("Flight Management");
        flightManagementButton.setFont(new Font("Arial", Font.PLAIN, 20));
        flightManagementButton.addActionListener(e -> openFlightManagement());
        buttonPanel.add(flightManagementButton);

        JButton assignStaffButton = new JButton("Assign Staff to Flights");
        assignStaffButton.setFont(new Font("Arial", Font.PLAIN, 20));
        assignStaffButton.addActionListener(e -> openAssignStaffToFlights());
        buttonPanel.add(assignStaffButton);

        JButton bookingOversightButton = new JButton("Booking Oversight");
        bookingOversightButton.setFont(new Font("Arial", Font.PLAIN, 20));
        bookingOversightButton.addActionListener(e -> openBookingOversight());
        buttonPanel.add(bookingOversightButton);

        JButton leaveApprovalButton = new JButton("Leave Approval");
        leaveApprovalButton.setFont(new Font("Arial", Font.PLAIN, 20));
        leaveApprovalButton.addActionListener(e -> openLeaveApproval());
        buttonPanel.add(leaveApprovalButton);

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        generateReportsButton.addActionListener(e -> openGenerateReports());
        buttonPanel.add(generateReportsButton);

        // Back Button to go back to login
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        this.setVisible(true);
    }

    private void openFlightManagement() {
        new FlightManagement();
        this.dispose();
    }

    private void openAssignStaffToFlights() {
        new AssignStaffToFlights();
        this.dispose();
    }

    private void openBookingOversight() {
        new BookingOversight();
        this.dispose();
    }

    private void openLeaveApproval() {
        new LeaveApproval();
        this.dispose();
    }

    private void openGenerateReports() {
        new GenerateReports();
        this.dispose();
    }

    private void logout() {
        // Implement logout functionality (return to login screen)
        new LoginPage();  // Assuming a login page exists
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard());
    }
}
