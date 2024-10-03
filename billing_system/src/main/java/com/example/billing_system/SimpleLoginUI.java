package com.example.billing_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleLoginUI extends JFrame {

    // UI components
    private JLabel userLabel, passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    // Hardcoded credentials for authentication
    private final String USERNAME = "admin";
    private final String PASSWORD = "password123";

    public SimpleLoginUI() {
        // Frame settings
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Create a panel to hold components
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Create UI components
        userLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        userField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        messageLabel = new JLabel();

        // Add components to panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(messageLabel);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        // Add panel to frame
        add(panel);
    }

    // Method to authenticate the user
    private void authenticate() {
        String username = userField.getText();
        String password = new String(passwordField.getPassword());

        // Simple authentication check
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            messageLabel.setText("Login successful!");
            messageLabel.setForeground(Color.GREEN);

            // You can add code to redirect to another screen or open a new window
            openMainApp();
        } else {
            messageLabel.setText("Invalid credentials");
            messageLabel.setForeground(Color.RED);
        }
    }

    // Method to open the main application after successful login
    private void openMainApp() {
        JOptionPane.showMessageDialog(this, "Welcome to the main application!");
        // Open a new window or redirect here
    }

    // Main method to run the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleLoginUI loginUI = new SimpleLoginUI();
                loginUI.setVisible(true);
            }
        });
    }
}
