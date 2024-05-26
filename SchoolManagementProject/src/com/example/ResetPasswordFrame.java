package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;

    public ResetPasswordFrame() {
        setTitle("Reset Password");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel userLabel = new JLabel("Username:");
        JLabel oldPassLabel = new JLabel("Old Password:");
        JLabel newPassLabel = new JLabel("New Password:");
        usernameField = new JTextField();
        oldPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        JButton resetButton = new JButton("Reset Password");

        resetButton.addActionListener(this::resetPassword);

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(oldPassLabel);
        panel.add(oldPasswordField);
        panel.add(newPassLabel);
        panel.add(newPasswordField);
        panel.add(new JLabel());
        panel.add(resetButton);

        add(panel);
    }

    private void resetPassword(ActionEvent e) {
        String username = usernameField.getText();
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
            checkStmt.setString(1, username);
            checkStmt.setString(2, oldPassword);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or old password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

