package com.example;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryFrame extends JFrame {
    private JTextField subjectField;
    private JTextArea queryArea;

    public QueryFrame() {
        setTitle("Query");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Submit Query", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JLabel subjectLabel = new JLabel("Subject:");
        subjectField = new JTextField();
        JLabel queryLabel = new JLabel("Query:");
        queryArea = new JTextArea();
        queryArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(queryArea);

        formPanel.add(subjectLabel);
        formPanel.add(subjectField);
        formPanel.add(queryLabel);
        formPanel.add(scrollPane);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitQuery());

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void submitQuery() {
        String subject = subjectField.getText();
        String queryText = queryArea.getText();

        try {
           
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "root@123");

            
            String sql = "INSERT INTO queries (subject, query) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subject);
            statement.setString(2, queryText);

           
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Query submitted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit query!");
            }


            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(QueryFrame::new);
    }
}
