package com.example;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private UserDAO userDAO;

    public RegisterFrame() {
        userDAO = new UserDAO();

        setTitle("Register");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Staff", "Student", "Parent"});
        registerButton = new JButton("Register");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);

            if (userDAO.register(user)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
        });
    }
}
