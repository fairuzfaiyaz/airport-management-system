import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SignUpPage extends JFrame implements ActionListener {
    private JTextField usernameField, nameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JRadioButton maleButton, femaleButton, otherButton;
    private JCheckBox termsCheckBox;
    private JComboBox<String> countryDropdown;
    private JTextArea addressArea;
    private JButton signUpButton, backButton;

    public SignUpPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1700, 840);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("signuppage.jpeg");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        JLabel backgroundImage = new JLabel(scaledIcon);
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(100, 100, 700, 600);
        formPanel.setBackground(new Color(50, 50, 50, 180));
        formPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setBounds(250, 20, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        formPanel.add(titleLabel);

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 150, 30);
        nameLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(200, 80, 400, 30);
        formPanel.add(nameField);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 130, 150, 30);
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(200, 130, 400, 30);
        formPanel.add(usernameField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 180, 150, 30);
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(200, 180, 400, 30);
        formPanel.add(passwordField);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 230, 150, 30);
        emailLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(200, 230, 400, 30);
        formPanel.add(emailField);

        // Phone field
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 280, 150, 30);
        phoneLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(200, 280, 400, 30);
        formPanel.add(phoneField);

        // Gender radio buttons
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 330, 150, 30);
        genderLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(genderLabel);

        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        maleButton.setBounds(200, 330, 80, 30);
        femaleButton.setBounds(290, 330, 80, 30);
        otherButton.setBounds(380, 330, 80, 30);
        maleButton.setOpaque(false);
        femaleButton.setOpaque(false);
        otherButton.setOpaque(false);
        maleButton.setForeground(Color.LIGHT_GRAY);
        femaleButton.setForeground(Color.LIGHT_GRAY);
        otherButton.setForeground(Color.LIGHT_GRAY);
        formPanel.add(maleButton);
        formPanel.add(femaleButton);
        formPanel.add(otherButton);

        // Country dropdown
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(50, 380, 150, 30);
        countryLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(countryLabel);

        String[] countries = Locale.getISOCountries();
        String[] countryNames = new String[countries.length + 1];
        countryNames[0] = "Select Country";
        for (int i = 0; i < countries.length; i++) {
            Locale locale = new Locale("", countries[i]);
            countryNames[i + 1] = locale.getDisplayCountry();
        }
        countryDropdown = new JComboBox<>(countryNames);
        countryDropdown.setBounds(200, 380, 400, 30);
        formPanel.add(countryDropdown);

        // Address field
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 430, 150, 30);
        addressLabel.setForeground(Color.LIGHT_GRAY);
        formPanel.add(addressLabel);
        addressArea = new JTextArea();
        addressArea.setBounds(200, 430, 400, 60);
        formPanel.add(addressArea);

        // Terms checkbox
        termsCheckBox = new JCheckBox("I agree to the terms and conditions.");
        termsCheckBox.setBounds(200, 510, 400, 30);
        termsCheckBox.setForeground(Color.LIGHT_GRAY);
        termsCheckBox.setOpaque(false);
        formPanel.add(termsCheckBox);

        // Sign-up button
        signUpButton = createStyledButton("Sign Up");
        signUpButton.setBounds(200, 550, 150, 40);
        signUpButton.addActionListener(this);
        signUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(new Color(70, 130, 180));
            }
        });
        formPanel.add(signUpButton);

        // Back button
        backButton = createStyledButton("Back");
        backButton.setBounds(370, 550, 150, 40);
        backButton.addActionListener(this);
        formPanel.add(backButton);

        this.add(formPanel);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(900, 100, 600, 600);
        infoPanel.setBackground(new Color(50, 50, 50, 150));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to Sign Up!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.YELLOW);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionsLabel = new JLabel("<html><br>Follow these steps:<br><br>" +
                "1. Fill in your details<br>2. Choose credentials<br>" +
                "3. Select gender/address<br>4. Agree to terms<br>" +
                "5. Click 'Sign Up'</html>");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 20));

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(welcomeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(instructionsLabel);
        infoPanel.add(Box.createVerticalGlue());

        this.add(infoPanel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            StringBuilder missingFields = new StringBuilder();
            if (nameField.getText().isEmpty()) missingFields.append("Name\n");
            if (usernameField.getText().isEmpty()) missingFields.append("Username\n");
            if (new String(passwordField.getPassword()).isEmpty()) missingFields.append("Password\n");
            if (emailField.getText().isEmpty()) missingFields.append("Email\n");
            if (phoneField.getText().isEmpty()) missingFields.append("Phone Number\n");
            if (!maleButton.isSelected() && !femaleButton.isSelected() && !otherButton.isSelected()) missingFields.append("Gender\n");
            if (countryDropdown.getSelectedIndex() == 0) missingFields.append("Country\n");
            if (addressArea.getText().isEmpty()) missingFields.append("Address\n");

            if (missingFields.length() > 0) {
                JOptionPane.showMessageDialog(this, "Missing fields:\n" + missingFields, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!termsCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Agree to terms!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (!AuthManager.register(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Username exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (FileWriter writer = new FileWriter("user_information.txt", true)) {
                    String gender = maleButton.isSelected() ? "Male" :
                            femaleButton.isSelected() ? "Female" : "Other";
                    writer.write(String.join(",",
                            nameField.getText(),
                            usernameField.getText(),
                            emailField.getText(),
                            phoneField.getText(),
                            gender,
                            countryDropdown.getSelectedItem().toString(),
                            addressArea.getText().replace("\n", " ")
                    ) + "\n");
                }

                JOptionPane.showMessageDialog(this, "Signup Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new LoginPage();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            this.dispose();
            new LoginPage();
        }
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
}