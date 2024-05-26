package com.example;

import java.awt.CardLayout;

import javax.swing.*;

public class UserDashboard extends JFrame {
    public UserDashboard(String username) {
        setTitle("User Dashboard - " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the User Dashboard"));

        mainPanel.add(homePanel, "Home");

        add(mainPanel);

        setVisible(true);
    }
}
