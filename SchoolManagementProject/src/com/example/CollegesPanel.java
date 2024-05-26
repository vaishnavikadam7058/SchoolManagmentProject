package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class CollegesPanel extends JPanel {
    public CollegesPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Information about Colleges & Universities</h1>" +
                "<p>Colleges and universities provide higher education and offer a wide range of academic programs, " +
                "including undergraduate and graduate degrees. These institutions focus on advanced learning, research, " +
                "and professional development.</p>" +
                "<p>Key features of colleges and universities include:</p>" +
                "<ul>" +
                "<li>Diverse academic programs and majors</li>" +
                "<li>Advanced research facilities</li>" +
                "<li>Faculty with expertise in various fields</li>" +
                "<li>Campus life and extracurricular activities</li>" +
                "</ul></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}
