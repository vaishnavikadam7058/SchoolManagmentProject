package com.example;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ReviewsPanel extends JPanel {
    public ReviewsPanel() {
        setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("<html><h1>Information about Reviews & Ratings</h1>" +
                "<p>Reviews and ratings provide feedback and evaluations of products, services, or experiences. " +
                "In the context of education, reviews and ratings can help students and parents make informed decisions " +
                "about schools, colleges, and other educational institutions.</p>" +
                "<p>Common sources of reviews and ratings in education include:</p>" +
                "<ul>" +
                "<li>Online review platforms</li>" +
                "<li>Parent and student testimonials</li>" +
                "<li>Educational rankings and surveys</li>" +
                "</ul></html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.CENTER);
    }
}


