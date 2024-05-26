package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class SupportPanel extends JPanel {
    private String username;
    private JTextArea messageArea;

    public SupportPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Enquiry Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        JLabel messageLabel = new JLabel("Message:");
        messageArea = new JTextArea(5, 20);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        formPanel.add(messageLabel);
        formPanel.add(messageScrollPane);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::submitEnquiry);
        add(formPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void submitEnquiry(ActionEvent e) {
        String message = messageArea.getText();

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "root@123");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO enquiries (parent_username, message) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Enquiry submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
               
                messageArea.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit enquiry.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while processing your request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
