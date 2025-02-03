import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener, FocusListener, KeyListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1700, 840);
        this.setLayout(null);

        // Background image
        ImageIcon icon = new ImageIcon("Untitled.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        JLabel backgroundImage = new JLabel(scaledIcon);
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(backgroundImage);

        // Login panel with lower transparency
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(170, 200, 400, 350);
        loginPanel.setBackground(new Color(50, 50, 50, 150));
        loginPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome Back!");
        titleLabel.setBounds(100, 20, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 80, 80, 30);
        usernameLabel.setForeground(Color.WHITE);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 80, 220, 30);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        usernameField.addFocusListener(this);
        usernameField.addKeyListener(this);
        loginPanel.add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 130, 80, 30);
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 130, 220, 30);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passwordField.addFocusListener(this);
        passwordField.addKeyListener(this);
        loginPanel.add(passwordField);

        // Login button
        loginButton = createStyledButton("Login");
        loginButton.setBounds(120, 200, 100, 40);
        loginButton.addActionListener(this);
        loginButton.addKeyListener(this);
        loginPanel.add(loginButton);

        // Sign-up button
        signUpButton = createStyledButton("Sign Up");
        signUpButton.setBounds(240, 200, 100, 40);
        signUpButton.addActionListener(this);
        signUpButton.addKeyListener(this);
        loginPanel.add(signUpButton);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setBounds(30, 260, 360, 30);
        messageLabel.setForeground(Color.RED);
        loginPanel.add(messageLabel);

        this.add(loginPanel);

        // Right side content panel (fixed height)
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(600, 150, 600, 450); // Increased height
        rightPanel.setLayout(null);
        rightPanel.setOpaque(false);

        // Airport Management System text
        JLabel systemTitle = new JLabel("Airport Management System");
        systemTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        systemTitle.setForeground(Color.WHITE);
        systemTitle.setBounds(50, 30, 400, 40);
        rightPanel.add(systemTitle);

        // Welcome greeting
        JLabel welcomeLabel = new JLabel("Welcome to the most efficient Airport management system.");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 100, 500, 30);
        rightPanel.add(welcomeLabel);

        // Key Features Title
        JLabel keyFeaturesTitle = new JLabel("Key Features:");
        keyFeaturesTitle.setFont(new Font("Arial", Font.BOLD, 16));
        keyFeaturesTitle.setForeground(Color.WHITE);
        keyFeaturesTitle.setBounds(50, 150, 200, 30);
        rightPanel.add(keyFeaturesTitle);

        // Key features list
        JLabel feature1 = new JLabel("- Manage Flights");
        feature1.setFont(new Font("Arial", Font.PLAIN, 14));
        feature1.setForeground(Color.WHITE);
        feature1.setBounds(50, 180, 200, 25);
        rightPanel.add(feature1);

        JLabel feature2 = new JLabel("- Track Passenger Details");
        feature2.setFont(new Font("Arial", Font.PLAIN, 14));
        feature2.setForeground(Color.WHITE);
        feature2.setBounds(50, 210, 200, 25);
        rightPanel.add(feature2);

        JLabel feature3 = new JLabel("- Schedule Flight Operations");
        feature3.setFont(new Font("Arial", Font.PLAIN, 14));
        feature3.setForeground(Color.WHITE);
        feature3.setBounds(50, 240, 200, 25);
        rightPanel.add(feature3);

        JLabel feature4 = new JLabel("- Real-time Data Insights");
        feature4.setFont(new Font("Arial", Font.PLAIN, 14));
        feature4.setForeground(Color.WHITE);
        feature4.setBounds(50, 270, 200, 25);
        rightPanel.add(feature4);

        // Additional instructions
        JLabel instructionsLabel = new JLabel("Enter your username and password to get started.");
        instructionsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setBounds(50, 300, 500, 30);
        rightPanel.add(instructionsLabel);

        // Contact Info (adjusted positions)
        JLabel contactTitle = new JLabel("Contact Us:");
        contactTitle.setFont(new Font("Arial", Font.BOLD, 16));
        contactTitle.setForeground(Color.WHITE);
        contactTitle.setBounds(50, 340, 200, 30);
        rightPanel.add(contactTitle);

        JLabel contactInfo = new JLabel("Email: 23-54707-3@student.aiub.edu");
        contactInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        contactInfo.setForeground(Color.WHITE);
        contactInfo.setBounds(50, 370, 300, 25);
        rightPanel.add(contactInfo);

        JLabel phoneInfo = new JLabel("Phone: +880 153 4277 02");
        phoneInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneInfo.setForeground(Color.WHITE);
        phoneInfo.setBounds(50, 400, 300, 25);
        rightPanel.add(phoneInfo);

        this.add(rightPanel);

        // Footer text
        JLabel footerLabel = new JLabel(" MADE BY FAIRUZ FAIYAZ | NOURIN KHAN ZERIN");
        footerLabel.setBounds(this.getWidth() - 420, this.getHeight() -100, 350, 30);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        footerLabel.setOpaque(false);
        this.add(footerLabel);

        // Exit button
        JButton exitButton = createStyledButton("Exit");
        exitButton.setBounds(120, 250, 220, 40); // You can adjust the position as needed
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application
        loginPanel.add(exitButton);


        this.setVisible(true);
    }

    // Rest of the code remains unchanged (actionPerformed, createStyledButton, listeners etc.)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (AuthManager.authenticate(username, password)) {
                messageLabel.setText("Login Successful!");
                messageLabel.setForeground(Color.GREEN);
                this.dispose();
                if (username.equals("admin")) {
                    new AdminDashboard();
                } else {
                    new PassengerDashboard(username);
                }
            } else {
                messageLabel.setText("Invalid username or password!");
                messageLabel.setForeground(Color.RED);
            }
        } else if (e.getSource() == signUpButton) {
            this.dispose();
            new SignUpPage();
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(120, 14, 43));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Focus Listener methods
    @Override
    public void focusGained(FocusEvent e) {
        JComponent source = (JComponent) e.getSource();
        source.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    public void focusLost(FocusEvent e) {
        JComponent source = (JComponent) e.getSource();
        source.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}