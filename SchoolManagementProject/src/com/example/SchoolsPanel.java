package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class SchoolsPanel extends JPanel {
    public SchoolsPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Information about Schools</h1>" +
                "<p>Schools provide foundational education to children, focusing on basic subjects " +
                "such as math, science, language arts, and social studies. Schools play a crucial role " +
                "in shaping a child's academic and social development.</p>" +
                "<p>Some common features of schools include:</p>" +
                "<ul>" +
                "<li>Qualified teachers</li>" +
                "<li>Curriculum based on educational standards</li>" +
                "<li>Extracurricular activities</li>" +
                "<li>Parent involvement programs</li>" +
                "</ul></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}