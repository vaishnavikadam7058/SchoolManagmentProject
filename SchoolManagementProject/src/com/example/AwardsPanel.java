package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class AwardsPanel extends JPanel {
    public AwardsPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Information about Awards & Certifications</h1>" +
                "<p>Awards and certifications recognize achievements and accomplishments in various fields. " +
                "These accolades can be academic, professional, or related to specific skills or competencies.</p>" +
                "<p>Examples of awards and certifications include:</p>" +
                "<ul>" +
                "<li>Academic honors and scholarships</li>" +
                "<li>Professional certifications in industries such as IT, healthcare, and finance</li>" +
                "<li>Recognition for community service or leadership</li>" +
                "<li>Awards for innovation or research</li>" +
                "</ul></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}
