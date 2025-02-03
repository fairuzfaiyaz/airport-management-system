import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PaymentPage extends JFrame {
    private JTextField cardNumberField, nameField, expiryDateField, cvvField;
    private String selectedFlight;

    public PaymentPage(String selectedFlight) {
        this.selectedFlight = selectedFlight;
        setupFrame();
    }

    private void setupFrame() {
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Payment Gateway for: " + selectedFlight);
        titleLabel.setBounds(50, 20, 500, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(titleLabel);

        // Card Number Label and Field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(50, 60, 100, 30);
        this.add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(160, 60, 200, 30);
        this.add(cardNumberField);

        // Name on Card Label and Field
        JLabel nameLabel = new JLabel("Name on Card:");
        nameLabel.setBounds(50, 100, 120, 30);
        this.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 100, 200, 30);
        this.add(nameField);

        // Expiry Date Label and Field
        JLabel expiryLabel = new JLabel("Expiry Date (MM/YY):");
        expiryLabel.setBounds(50, 140, 150, 30);
        this.add(expiryLabel);

        expiryDateField = new JTextField();
        expiryDateField.setBounds(160, 140, 200, 30);
        this.add(expiryDateField);

        // CVV Label and Field
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(50, 180, 100, 30);
        this.add(cvvLabel);

        cvvField = new JTextField();
        cvvField.setBounds(160, 180, 200, 30);
        this.add(cvvField);

        // Submit Button
        JButton submitButton = new JButton("Submit Payment");
        submitButton.setBounds(160, 230, 150, 40);
        submitButton.addActionListener(e -> submitPayment());
        this.add(submitButton);

        this.setVisible(true);
    }

    private void submitPayment() {
        // Save the payment details and flight booking in a file
        String cardNumber = cardNumberField.getText();
        String name = nameField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || name.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all payment details.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            saveBookingHistory(cardNumber, name, expiryDate);
            JOptionPane.showMessageDialog(this, "Payment Successful! Booking confirmed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close payment page
        }
    }

    private void saveBookingHistory(String cardNumber, String name, String expiryDate) {
        // Save booking details to a history file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("booking_history.txt", true))) {
            bw.write("Flight: " + selectedFlight + "\n");
            bw.write("Card Number: " + cardNumber + "\n");
            bw.write("Name: " + name + "\n");
            bw.write("Expiry Date: " + expiryDate + "\n");
            bw.write("---------\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving booking history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
