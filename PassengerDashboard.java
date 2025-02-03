import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PassengerDashboard extends JFrame implements ActionListener {
    private JTextField nameField, usernameField, emailField, phoneField, genderField, countryField, dobField;
    private JTextArea addressArea;
    private JButton editButton, saveButton, logoutButton;
    private JButton availableFlightsButton, bookFlightButton, bookingHistoryButton, flightStatusButton, cancelFlightButton;
    private String currentUsername;

    public PassengerDashboard(String username) {
        currentUsername = username;
        setupFrame();
        createUserInfoPanel();
        createNavigationButtons();
        loadUserData();
        this.setVisible(true);
    }

    private void setupFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1700, 840);  // Keeping the original window size
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("passangerdashboard.jpeg");  // Background image
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        JLabel backgroundImage = new JLabel(scaledIcon);
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);
    }

    private void createUserInfoPanel() {
        JPanel userPanel = new JPanel();
        userPanel.setBounds(50, 50, 600, 700);  // Panel size remains unchanged
        userPanel.setBackground(new Color(30, 30, 40, 190));  // Dark red with transparency
        userPanel.setLayout(null);

        // User information fields
        nameField = createInfoField("Name:", 50, userPanel);
        usernameField = createInfoField("Username:", 100, userPanel);
        emailField = createInfoField("Email:", 150, userPanel);
        phoneField = createInfoField("Phone:", 200, userPanel);
        genderField = createInfoField("Gender:", 250, userPanel);
        countryField = createInfoField("Country:", 300, userPanel);

        // Date of Birth field
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(20, 350, 100, 30);
        dobLabel.setForeground(Color.WHITE);  // White text for visibility
        userPanel.add(dobLabel);
        dobField = new JTextField();
        dobField.setBounds(120, 350, 450, 30);
        dobField.setEditable(false);
        userPanel.add(dobField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 400, 100, 30);
        addressLabel.setForeground(Color.WHITE);  // White text for visibility
        userPanel.add(addressLabel);
        addressArea = new JTextArea();
        addressArea.setBounds(120, 400, 450, 100);
        addressArea.setEditable(false);
        userPanel.add(addressArea);

        // Buttons
        editButton = createStyledButton("Edit Profile");
        saveButton = createStyledButton("Save Changes");
        logoutButton = createStyledButton("Logout");

        editButton.setBounds(50, 550, 150, 40);
        saveButton.setBounds(220, 550, 150, 40);
        logoutButton.setBounds(390, 550, 150, 40);

        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        logoutButton.addActionListener(this);

        userPanel.add(editButton);
        userPanel.add(saveButton);
        userPanel.add(logoutButton);

        this.add(userPanel);
    }

    private void createNavigationButtons() {
        int xPosition = 700; // Right side of the panel
        int yPosition = 100;

        // Feature description
        JLabel featureLabel = new JLabel("<html><div style='text-align: center;'>Explore Our Features:<br>Book Flights, Check Status, and More!</div></html>");
        featureLabel.setBounds(xPosition, yPosition - 60, 300, 50);
        featureLabel.setFont(new Font("Arial", Font.BOLD, 16));
        featureLabel.setForeground(Color.WHITE);
        this.add(featureLabel);

        availableFlightsButton = createStyledButton("Available Flights");
        availableFlightsButton.setBounds(xPosition, yPosition, 250, 50);  // Bigger button
        availableFlightsButton.addActionListener(this);
        this.add(availableFlightsButton);

        bookFlightButton = createStyledButton("Book Flight");
        bookFlightButton.setBounds(xPosition, yPosition + 70, 250, 50);  // Bigger button
        bookFlightButton.addActionListener(this);
        this.add(bookFlightButton);

        bookingHistoryButton = createStyledButton("Booking History");
        bookingHistoryButton.setBounds(xPosition, yPosition + 140, 250, 50);  // Bigger button
        bookingHistoryButton.addActionListener(this);
        this.add(bookingHistoryButton);

        flightStatusButton = createStyledButton("Flight Status");
        flightStatusButton.setBounds(xPosition, yPosition + 210, 250, 50);  // Bigger button
        flightStatusButton.addActionListener(this);
        this.add(flightStatusButton);

        cancelFlightButton = createStyledButton("Cancel Flight");
        cancelFlightButton.setBounds(xPosition, yPosition + 280, 250, 50);  // Bigger button
        cancelFlightButton.addActionListener(this);
        this.add(cancelFlightButton);
    }

    private JTextField createInfoField(String label, int y, JPanel panel) {
        JLabel jlabel = new JLabel(label);
        jlabel.setBounds(20, y, 100, 30);
        jlabel.setForeground(Color.WHITE);  // White text for visibility
        panel.add(jlabel);

        JTextField field = new JTextField();
        field.setBounds(120, y, 450, 30);
        field.setEditable(false);
        panel.add(field);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadUserData() {
        try (BufferedReader br = new BufferedReader(new FileReader("user_information.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(currentUsername)) {
                    nameField.setText(userData[0]);
                    usernameField.setText(userData[1]);
                    emailField.setText(userData[2]);
                    phoneField.setText(userData[3]);
                    genderField.setText(userData[4]);
                    countryField.setText(userData[5]);
                    dobField.setText(userData.length > 6 ? userData[6] : "");
                    addressArea.setText(userData.length > 7 ? userData[7] : "");
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading user data!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            toggleEditing(true);
        } else if (e.getSource() == saveButton) {
            saveUserData();
            toggleEditing(false);
        } else if (e.getSource() == logoutButton) {
            this.dispose();
            new LoginPage();  // Assuming LoginPage is a valid class
        } else if (e.getSource() == availableFlightsButton) {
            this.dispose();
            new AvailableFlights(currentUsername);
        } else if (e.getSource() == bookFlightButton) {
            this.dispose();
            new BookFlight(currentUsername);
        } else if (e.getSource() == bookingHistoryButton) {
            this.dispose();
            new BookingHistory(currentUsername);
        } else if (e.getSource() == flightStatusButton) {
            this.dispose();
            new FlightStatus(currentUsername);
        } else if (e.getSource() == cancelFlightButton) {
            this.dispose();
            new CancelFlight(currentUsername);
        }
    }

    private void toggleEditing(boolean enable) {
        nameField.setEditable(enable);
        emailField.setEditable(enable);
        phoneField.setEditable(enable);
        countryField.setEditable(enable);
        dobField.setEditable(enable);
        addressArea.setEditable(enable);
        editButton.setVisible(!enable);
        saveButton.setVisible(enable);
    }

    private void saveUserData() {
        try {
            File userFile = new File("user_information.txt");
            File tempFile = new File("temp_user.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (userData[1].equals(currentUsername)) {
                        line = String.join(",",
                                nameField.getText(),
                                currentUsername,
                                emailField.getText(),
                                phoneField.getText(),
                                genderField.getText(),
                                countryField.getText(),
                                dobField.getText(),
                                addressArea.getText().replace("\n", " ")
                        );
                    }
                    bw.write(line + "\n");
                }
            }
            userFile.delete();
            tempFile.renameTo(userFile);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            loadUserData();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving changes!");
        }
    }

    public static void main(String[] args) {
        new PassengerDashboard("testUser");  // Replace with actual username
    }
}