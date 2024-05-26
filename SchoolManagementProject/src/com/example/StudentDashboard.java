package com.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends JFrame {
    private String username;

    public StudentDashboard(String username) {
        this.username = username;
        setTitle("Student Dashboard - " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sidePanel = new JPanel(new GridLayout(7, 1));
        JButton homeButton = new JButton("Home");
        JButton aboutUsButton = new JButton("About Us");
        JButton contactUsButton = new JButton("Contact Us");
        JButton attendanceButton = new JButton("Attendance Records");
        JButton reviewsButton = new JButton("Reviews");
        JButton queryButton = new JButton("Query");
        JButton rateUsButton = new JButton("Rate Us");

        sidePanel.add(homeButton);
        sidePanel.add(aboutUsButton);
        sidePanel.add(contactUsButton);
        sidePanel.add(attendanceButton);
        sidePanel.add(reviewsButton);
        sidePanel.add(queryButton);
        sidePanel.add(rateUsButton);

        add(sidePanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Student Dashboard"));

        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.add(new JLabel("About Us: We are dedicated to providing quality education to our students."));

        JPanel contactUsPanel = new JPanel();
        contactUsPanel.add(new JLabel("Contact Us: email@example.com, +123456789"));

        JPanel attendancePanel = new JPanel(new BorderLayout());
        JTable attendanceTable = new JTable();
        JButton viewRecordButton = new JButton("View Records");

        attendancePanel.add(new JScrollPane(attendanceTable), BorderLayout.CENTER);
        attendancePanel.add(viewRecordButton, BorderLayout.SOUTH);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(aboutUsPanel, "About Us");
        mainPanel.add(contactUsPanel, "Contact Us");
        mainPanel.add(attendancePanel, "Attendance Records");

        add(mainPanel, BorderLayout.CENTER);

        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        homeButton.addActionListener(e -> cl.show(mainPanel, "Home"));
        aboutUsButton.addActionListener(e -> cl.show(mainPanel, "About Us"));
        contactUsButton.addActionListener(e -> new ContactFormFrame(username).setVisible(true));
        attendanceButton.addActionListener(e -> cl.show(mainPanel, "Attendance Records"));
        reviewsButton.addActionListener(e -> new ReviewsFrame().setVisible(true));
        queryButton.addActionListener(e -> new QueryFrame().setVisible(true));
        rateUsButton.addActionListener(e -> showRateUsDialog());

        viewRecordButton.addActionListener(e -> {
            List<AttendanceRecord> attendanceRecords = fetchAllAttendanceRecords();
            displayAttendanceRecords(attendanceTable, attendanceRecords);
        });

        setVisible(true);
    }

    private List<AttendanceRecord> fetchAllAttendanceRecords() {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/school_db";
        String user = "root";
        String password = "root@123";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, name, standard, attendance_status, date FROM attendance";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String standard = resultSet.getString("standard");
                        String status = resultSet.getString("attendance_status");
                        Date date = resultSet.getDate("date");
                        attendanceRecords.add(new AttendanceRecord(id, name, standard, status, date));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return attendanceRecords;
    }

    private void displayAttendanceRecords(JTable table, List<AttendanceRecord> records) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Standard");
        model.addColumn("Attendance Status");
        model.addColumn("Date");

        for (AttendanceRecord record : records) {
            model.addRow(new Object[]{record.getId(), record.getName(), record.getStandard(), record.getAttendanceStatus(), record.getDate()});
        }

        table.setModel(model);
    }

    private void showRateUsDialog() {
        JFrame frame = new JFrame("Rate Us");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("Please rate us:");
        JPanel starPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton[] stars = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JRadioButton(String.valueOf(i + 1));
            buttonGroup.add(stars[i]);
            starPanel.add(stars[i]);
        }
        panel.add(label);
        panel.add(starPanel);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            int rating = getSelectedRating(stars);
            if (rating > 0)
            {
                JOptionPane.showMessageDialog(frame, "Thank you for rating us " + rating + " stars!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a rating!");
            }
        });
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private int getSelectedRating(JRadioButton[] stars) {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].isSelected()) {
                return i + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard("studentUsername").setVisible(true));
    }
}

class AttendanceRecord1{
    private int id;
    private String name;
    private String standard;
    private String attendanceStatus;
    private Date date;

    public AttendanceRecord1(int id, String name, String standard, String attendanceStatus, Date date) {
        this.id = id;
        this.name = name;
        this.standard = standard;
        this.attendanceStatus = attendanceStatus;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStandard() {
        return standard;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public Date getDate() {
        return date;
    }
}
