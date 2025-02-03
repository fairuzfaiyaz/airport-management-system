import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CancelFlight extends JFrame {
    private JComboBox<String> flightComboBox;
    private JTextField usernameField;
    private String username;

    public CancelFlight(String username) {
        this.username = username;
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(1700, 840);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("cancelflight.jpg");
        JLabel backgroundImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, 100, 40);
        backButton.addActionListener(e -> {
            this.dispose();
            new PassengerDashboard(username);
        });
        this.add(backButton);

        JLabel titleLabel = new JLabel("Cancel or Reschedule Flight");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(600, 50, 400, 40);
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);

        JLabel usernameLabel = new JLabel("Enter your Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setBounds(500, 150, 400, 30);
        usernameLabel.setForeground(Color.WHITE);
        this.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(500, 200, 700, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(usernameField);

        JLabel flightLabel = new JLabel("Select your Flight to Cancel or Reschedule:");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        flightLabel.setBounds(500, 250, 400, 30);
        flightLabel.setForeground(Color.WHITE);
        this.add(flightLabel);

        flightComboBox = new JComboBox<>();
        flightComboBox.setBounds(500, 300, 700, 40);
        flightComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(flightComboBox);

        // Populate the ComboBox with flight options from the file
        loadFlightOptions();

        JButton cancelButton = new JButton("Cancel Flight");
        cancelButton.setBounds(500, 400, 200, 40);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelButton.addActionListener(e -> requestCancellationOrReschedule("Cancel"));
        this.add(cancelButton);

        JButton rescheduleButton = new JButton("Reschedule Flight");
        rescheduleButton.setBounds(750, 400, 200, 40);
        rescheduleButton.setFont(new Font("Arial", Font.PLAIN, 18));
        rescheduleButton.addActionListener(e -> requestCancellationOrReschedule("Reschedule"));
        this.add(rescheduleButton);

        this.setVisible(true);
    }

    private void loadFlightOptions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("booking_history.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Flight:")) {
                    String[] flightDetails = line.split(",");
                    String flightCode = flightDetails[0].split(":")[1].trim();
                    String departure = flightDetails[1].trim();
                    String arrival = flightDetails[2].trim();
                    flightComboBox.addItem(flightCode + " (" + departure + " -> " + arrival + ")");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestCancellationOrReschedule(String actionType) {
        String selectedFlight = (String) flightComboBox.getSelectedItem();
        String username = usernameField.getText().trim();

        if (selectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight first.");
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Do you want to " + actionType + " the flight: " + selectedFlight + "?",
                "Confirm Request", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            saveRequestToFile(username, selectedFlight, actionType);
        }
    }

    private void saveRequestToFile(String username, String selectedFlight, String actionType) {
        try {
            // Write the cancellation/reschedule request to the admin file
            FileWriter requestWriter = new FileWriter("cancellation_requests.txt", true);
            requestWriter.write("Username: " + username + "\n" +
                    "Request Type: " + actionType + "\n" +
                    "Flight: " + selectedFlight + "\n" +
                    "--------------------------------------------------\n\n");
            requestWriter.close();

            JOptionPane.showMessageDialog(this, "Your request for flight " + actionType.toLowerCase() + " has been submitted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
