package com.example;



import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField secretKeyField; 
    private JButton loginButton;
    private JButton registerButton;
    private JButton resetPasswordButton;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();

        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2)); 
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        secretKeyField = new JPasswordField(); 
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        resetPasswordButton = new JButton("Reset Password");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Secret Key (Admin Only):"));
        panel.add(secretKeyField);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        panel.add(new JLabel(""));
        panel.add(registerButton);
        panel.add(new JLabel(""));
        panel.add(resetPasswordButton); 

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String secretKey = new String(secretKeyField.getPassword());

            if ("admin".equals(username) && "adminSecretKey".equals(secretKey)) {
                new AdminDashboard(username).setVisible(true);
                dispose();
            } else if (userDAO.authenticate(username, password)) {
                String role = userDAO.getRoleByUsername(username);
                if (role != null) {
                    switch (role) {
                        case "Staff":
                            new StaffDashboard(username).setVisible(true);
                            break;
                        case "Student":
                            new StudentDashboard(username).setVisible(true);
                            break;
                        case "Parent":
                            new SchoolingApp(username).setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Unknown role!");
                    }
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed!");
            }
        });

        registerButton.addActionListener(e -> new RegisterFrame().setVisible(true));

        resetPasswordButton.addActionListener(e -> new ResetPasswordFrame().setVisible(true)); // Adding action listener for reset password
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

