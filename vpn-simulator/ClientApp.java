import java.awt.*;
import javax.swing.*;

public class ClientApp extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField messageField;
    private JButton loginButton, signupButton, connectButton;
    private DatabaseManager dbManager;

    public ClientApp() {
        dbManager = new DatabaseManager();

        setTitle("Java VPN Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        // Title
        JLabel titleLabel = new JLabel("Java VPN Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        // Message Field
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        messageField = new JTextField();
        messagePanel.add(new JLabel("Message to send:"), BorderLayout.NORTH);
        messagePanel.add(messageField, BorderLayout.CENTER);

        // Auth Buttons
        JPanel authPanel = new JPanel();
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        signupButton.setEnabled(true);
        loginButton.setEnabled(true);
        authPanel.add(loginButton);
        authPanel.add(signupButton);

        // Connect Button
        JPanel vpnPanel = new JPanel();
        connectButton = new JButton("Connect to VPN");
        connectButton.setEnabled(true);
        vpnPanel.add(connectButton);

        // Layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.AFTER_LINE_ENDS);
        add(authPanel, BorderLayout.SOUTH);
        add(vpnPanel, BorderLayout.PAGE_END);

        // Actions
        signupButton.addActionListener(e -> handleSignup());
        loginButton.addActionListener(e -> handleLogin());
        connectButton.addActionListener(e -> handleVPNConnect());

        setVisible(true);
    }

    private void handleSignup() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        if (dbManager.registerUser(user, pass)) {
            JOptionPane.showMessageDialog(this, "Signup Successful! You can now log in.");
        } else {
            JOptionPane.showMessageDialog(this, "Signup Failed. Username may already exist.");
        }
    }

    private void handleLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        if (dbManager.validateUser(user, pass)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            connectButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void handleVPNConnect() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message to send.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Connecting to VPN...");
        VPNClient client = new VPNClient();
        client.start(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientApp::new);
    }
}
