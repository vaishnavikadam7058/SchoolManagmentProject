package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ContactPanel extends JPanel {
    public ContactPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Contact Us</h1>" +
                "<p>If you have any questions or inquiries, please feel free to contact us:</p>" +
                "<p>Email: info@schoolingapp.com</p>" +
                "<p>Phone: 123-456-7890</p>" +
                "<p>We look forward to hearing from you!</p></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}