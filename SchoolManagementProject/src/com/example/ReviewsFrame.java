package com.example;


import javax.swing.*;
import java.awt.*;

public class ReviewsFrame extends JFrame {
    private JTextArea reviewArea;

    public ReviewsFrame() {
        setTitle("Reviews");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Write a Review", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        reviewArea = new JTextArea();
        reviewArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(reviewArea);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitReview());

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void submitReview() {
        String review = reviewArea.getText();
        
        JOptionPane.showMessageDialog(this, "Thank you for your review!");
        dispose();
    }
}

