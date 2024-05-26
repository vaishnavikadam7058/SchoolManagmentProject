package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentDashboard {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SchoolingApp("parentUsername").setVisible(true));
    }
}

class SchoolingApp extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public SchoolingApp(String username) {
        this.username = username;
        setTitle("Parent Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sidebar = new JPanel(new GridLayout(10, 1));
        JButton homeButton = new JButton("Home");
        JButton schoolsButton = new JButton("Schools");
        JButton collegesButton = new JButton("Colleges & Universities");
        JButton awardsButton = new JButton("Awards & Certifications");
        JButton reviewsButton = new JButton("Reviews & Ratings");
        JButton contactButton = new JButton("Contact Us");
        JButton careerButton = new JButton("Career");
        JButton supportButton = new JButton("Support");

        sidebar.add(homeButton);
        sidebar.add(schoolsButton);
        sidebar.add(collegesButton);
        sidebar.add(awardsButton);
        sidebar.add(reviewsButton);
        sidebar.add(contactButton);
        sidebar.add(careerButton);
        sidebar.add(supportButton);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new HomePanel(username), "Home");
        mainPanel.add(new SchoolsPanel(), "Schools");
        mainPanel.add(new CollegesPanel(), "Colleges & Universities");
        mainPanel.add(new AwardsPanel(), "Awards & Certifications");
        mainPanel.add(new ReviewsPanel(), "Reviews & Ratings");
        mainPanel.add(new ContactPanel(), "Contact Us");
        mainPanel.add(new CareerPanel(), "Career");
        mainPanel.add(new SupportPanel(username), "Support");

        homeButton.addActionListener(e -> showCard("Home"));
        schoolsButton.addActionListener(e -> showCard("Schools"));
        collegesButton.addActionListener(e -> showCard("Colleges & Universities"));
        awardsButton.addActionListener(e -> showCard("Awards & Certifications"));
        reviewsButton.addActionListener(e -> showCard("Reviews & Ratings"));
        contactButton.addActionListener(e -> showCard("Contact Us"));
        careerButton.addActionListener(e -> showCard("Career"));
        supportButton.addActionListener(e -> showCard("Support"));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private void showCard(String card) {
        cardLayout.show(mainPanel, card);
    }
}



class HomePanel extends JPanel {
    private String username;
    private JComboBox<String> filterComboBox;
    private JComboBox<String> sortComboBox;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> ratingSortComboBox;
    private DefaultTableModel tableModel;
    private DefaultTableModel ratingTableModel;

    public HomePanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        
        JLabel titleLabel = new JLabel("Schooling Application", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

       
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel filterLabel = new JLabel("Filter by:");
        filterComboBox = new JComboBox<>(new String[]{"All", "Schools", "Colleges", "Universities"});
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        add(filterPanel, BorderLayout.NORTH);

      
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel sortLabel = new JLabel("Sort by:");
        sortComboBox = new JComboBox<>(new String[]{"Rating (High to Low)", "Rating (Low to High)"});
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        add(sortPanel, BorderLayout.NORTH);

        
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[]{"All", "Schools", "Colleges", "Universities"});
        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        add(typePanel, BorderLayout.NORTH);

        
        JPanel ratingSortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel ratingSortLabel = new JLabel("Sort by Rating:");
        ratingSortComboBox = new JComboBox<>(new String[]{"Rating (High to Low)", "Rating (Low to High)"});
        ratingSortPanel.add(ratingSortLabel);
        ratingSortPanel.add(ratingSortComboBox);
        add(ratingSortPanel, BorderLayout.NORTH);

        
        String[] columnNames = {"Type", "Name", "Rating"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        
        String[] ratingColumnNames = {"Type", "Name", "Rating"};
        ratingTableModel = new DefaultTableModel(ratingColumnNames, 0);
        JTable ratingTable = new JTable(ratingTableModel);
        JScrollPane ratingScrollPane = new JScrollPane(ratingTable);
        add(ratingScrollPane, BorderLayout.SOUTH);

        
        updateTable(null);

        
        filterComboBox.addActionListener(this::updateTable);
        sortComboBox.addActionListener(this::updateTable);
        typeComboBox.addActionListener(this::updateTable);
        ratingSortComboBox.addActionListener(this::updateRatingTable);
    }

    private void updateTable(ActionEvent e) {
        String filter = filterComboBox.getSelectedItem().toString();
        String sortBy = sortComboBox.getSelectedItem().toString();
        String type = typeComboBox.getSelectedItem().toString();
        loadData(filter, sortBy, type);
    }

    private void updateRatingTable(ActionEvent e) {
        String filter = filterComboBox.getSelectedItem().toString();
        String sortBy = ratingSortComboBox.getSelectedItem().toString();
        String type = typeComboBox.getSelectedItem().toString();
        loadRatingData(filter, sortBy, type);
    }

    private void loadData(String filter, String sortBy, String type) {
        List<String[]> data = getFilteredData(filter, sortBy, type);
        tableModel.setRowCount(0);
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void loadRatingData(String filter, String sortBy, String type) {
        List<String[]> data = getFilteredData(filter, sortBy, type);
        ratingTableModel.setRowCount(0);
        for (String[] row : data) {
            ratingTableModel.addRow(row);
        }
    }

    private List<String[]> getFilteredData(String filter, String sortBy, String type) {
        List<String[]> data = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "root@123");
             Statement stmt = conn.createStatement()) {
            String query = "";
            if (filter.equals("All")) {
                if (type.equals("All")) {
                    if (sortBy.equals("Rating (High to Low)")) {
                        query = "SELECT 'Schools' AS type, name, rating FROM schools " +
                                "UNION " +
                                "SELECT 'Colleges' AS type, name, rating FROM colleges " +
                                "UNION " +
                                "SELECT 'Universities' AS type, name, rating FROM universities " +
                                "ORDER BY rating DESC";
                    } else {
                        query = "SELECT 'Schools' AS type, name, rating FROM schools " +
                                "UNION " +
                                "SELECT 'Colleges' AS type, name, rating FROM colleges " +
                                "UNION " +
                                "SELECT 'Universities' AS type, name, rating FROM universities " +
                                "ORDER BY rating ASC";
                    }
                } else { 
                    query = "SELECT '" + type + "' AS type, name, rating FROM " + type.toLowerCase();
                    if (sortBy.equals("Rating (High to Low)")) {
                        query += " ORDER BY rating DESC";
                    } else {
                        query += " ORDER BY rating ASC";
                    }
                }
            } else { 
                query = "SELECT '" + filter + "' AS type, name, rating FROM " + filter.toLowerCase();
                if (sortBy.equals("Rating (High to Low)")) {
                    query += " ORDER BY rating DESC";
                } else {
                    query += " ORDER BY rating ASC";
                }
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.add(new String[]{rs.getString("type"), rs.getString("name"), String.valueOf(rs.getFloat("rating"))});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
