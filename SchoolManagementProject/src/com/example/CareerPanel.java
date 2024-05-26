package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class CareerPanel extends JPanel {
    public CareerPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Career Opportunities</h1>" +
                "<p>Explore career opportunities with Schooling App:</p>" +
                "<ul>" +
                "<li>Teaching positions</li>" +
                "<li>Administrative roles</li>" +
                "<li>Software development</li>" +
                "<li>Marketing and outreach</li>" +
                "<li>Customer support</li>" +
                "</ul>" +
                "<p>For current job openings, visit our careers page.</p></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}
