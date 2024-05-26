package com.example;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactFormFrame extends JFrame {
    private JTextField nameField;
    private JTextField contactNumberField;
    private JTextField emailField;
    private JTextArea messageArea;
    private String username;

    public ContactFormFrame(String username) {
        this.username = username;

        setTitle("Contact Us");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel infoLabel = new JLabel("<html><p style='text-align: center;'>"
                + "Thank you for reaching out to the Schooling App! We are dedicated to providing the best educational experience for our students, parents, and staff. "
                + "Whether you have a question, feedback, or need assistance, we are here to help. "
                + "Please fill out the form below with your contact details and message, and our support team will get back to you as soon as possible.</p>"
                + "<p style='text-align: center;'>"
                + "You can also contact us directly at:</p>"
                + "<p style='text-align: center;'>"
                + "Email: support@schoolingapp.com<br>"
                + "Phone: +123-456-7890</p>"
                + "<p style='text-align: center;'>"
                + "We value your feedback and look forward to assisting you.</p></html>");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(infoLabel);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        contactNumberField = new JTextField();
        emailField = new JTextField();
        messageArea = new JTextArea(5, 20);
        JButton submitButton = new JButton("Submit");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Contact Number:"));
        formPanel.add(contactNumberField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Message:"));
        formPanel.add(new JScrollPane(messageArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);

        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        submitButton.addActionListener(e -> submitQuery());
    }

    private void submitQuery() {
        String name = nameField.getText();
        String contactNumber = contactNumberField.getText();
        String email = emailField.getText();
        String message = messageArea.getText();

        if (name.trim().isEmpty() || contactNumber.trim().isEmpty() || email.trim().isEmpty() || message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields before submitting.");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO contact_queries (username, name, contact_number, email, message) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, contactNumber);
            stmt.setString(4, email);
            stmt.setString(5, message);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Contact form submitted successfully! We will get back to you soon.");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting query. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactFormFrame("adminUsername").setVisible(true));
    }
}
