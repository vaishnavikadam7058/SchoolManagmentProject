package com.example;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class StaffDashboard extends JFrame {
    private AttendanceDAO attendanceDAO;
    private String username;

    public StaffDashboard(String username) {
        this.username = username;
        attendanceDAO = new AttendanceDAO();

        setTitle("Staff Dashboard - " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sidePanel = new JPanel(new GridLayout(6, 1));
        JButton homeButton = new JButton("Home");
        JButton aboutUsButton = new JButton("About Us");
        JButton contactUsButton = new JButton("Contact Us");
        JButton attendanceButton = new JButton("Attendance Records");
        JButton reviewsButton = new JButton("Reviews");
        JButton queryButton = new JButton("Query");

        sidePanel.add(homeButton);
        sidePanel.add(aboutUsButton);
        sidePanel.add(contactUsButton);
        sidePanel.add(attendanceButton);
        sidePanel.add(reviewsButton);
        sidePanel.add(queryButton);

        add(sidePanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Staff Dashboard"));

        JPanel aboutUsPanel = new JPanel();
        JTextArea aboutUsText = new JTextArea("About Us:\n\nSchooling App is dedicated to providing quality education to students of all ages. We strive to create an engaging and interactive learning environment that fosters creativity and critical thinking. Our team of experienced educators is committed to helping students reach their full potential.");

        aboutUsText.setLineWrap(true);
        aboutUsText.setWrapStyleWord(true);
        aboutUsText.setEditable(false);
        aboutUsText.setPreferredSize(new Dimension(300, 150));
        aboutUsPanel.add(aboutUsText);

        JPanel contactUsPanel = new JPanel();
        contactUsPanel.add(new JLabel("Contact Us: email@example.com, +123456789"));

        JPanel attendancePanel = new JPanel(new BorderLayout());
        JTable attendanceTable = new JTable();
        JButton addRecordButton = new JButton("Add Record");
        JButton updateRecordButton = new JButton("Update Record");
        JButton deleteRecordButton = new JButton("Delete Record");

        attendancePanel.add(new JScrollPane(attendanceTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addRecordButton);
        buttonPanel.add(updateRecordButton);
        buttonPanel.add(deleteRecordButton);
        attendancePanel.add(buttonPanel, BorderLayout.SOUTH);

        updateAttendanceTable(attendanceTable);

        addRecordButton.addActionListener(e -> addAttendanceRecord(attendanceTable));
        updateRecordButton.addActionListener(e -> updateAttendanceRecord(attendanceTable));
        deleteRecordButton.addActionListener(e -> deleteAttendanceRecord(attendanceTable));

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
        queryButton.setForeground(Color.BLUE);
        queryButton.setFont(new Font("Arial", Font.BOLD, 14));
        queryButton.addActionListener(e -> showStudentQueries());

        setVisible(true);
    }

    private void updateAttendanceTable(JTable table) {
        List<AttendanceRecord> records = attendanceDAO.getAllAttendanceRecords();
        String[] columnNames = {"ID", "Name", "Standard", "Status", "Date"};
        Object[][] data = new Object[records.size()][5];
        for (int i = 0; i < records.size(); i++) {
            AttendanceRecord record = records.get(i);
            data[i][0] = record.getId();
            data[i][1] = record.getName();
            data[i][2] = record.getStandard();
            data[i][3] = record.getAttendanceStatus();
            data[i][4] = record.getDate();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void addAttendanceRecord(JTable table) {
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        String standard = JOptionPane.showInputDialog(this, "Enter Standard:");
        String attendanceStatus = JOptionPane.showInputDialog(this, "Enter Attendance Status:");
        String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");

        AttendanceRecord record = new AttendanceRecord();
        record.setName(name);
        record.setStandard(standard);
        record.setAttendanceStatus(attendanceStatus);
        record.setDate(java.sql.Date.valueOf(date));

        attendanceDAO.addAttendanceRecord(record);
        updateAttendanceTable(table);
    }

    private void updateAttendanceRecord(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = JOptionPane.showInputDialog(this, "Enter Name:", table.getValueAt(selectedRow, 1));
        String standard = JOptionPane.showInputDialog(this, "Enter Standard:", table.getValueAt(selectedRow, 2));
        String attendanceStatus = JOptionPane.showInputDialog(this, "Enter Attendance Status:", table.getValueAt(selectedRow, 3));
        String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):", table.getValueAt(selectedRow, 4));

        AttendanceRecord record = new AttendanceRecord();
        record.setId(id);
        record.setName(name);
        record.setStandard(standard);
        record.setAttendanceStatus(attendanceStatus);
        record.setDate(java.sql.Date.valueOf(date));

        attendanceDAO.updateAttendanceRecord(record);
        updateAttendanceTable(table);
    }

    private void deleteAttendanceRecord(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        attendanceDAO.deleteAttendanceRecord(id);
        updateAttendanceTable(table);
    }

    private void showStudentQueries() {
        JFrame queryFrame = new JFrame("Student Queries");
        queryFrame.setSize(400, 300);
        queryFrame.setLocationRelativeTo(this);

        // Fetch queries from the database
        List<String> queries = attendanceDAO.getAllStudentQueries();

        // Create a table model with the fetched queries
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Query");

        for (String query : queries) {
            model.addRow(new Object[]{query});
        }

        // Create and configure the table
        JTable queryTable = new JTable(model);
        queryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(queryTable);

        // Add the table to the frame
        queryFrame.add(scrollPane);
        queryFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StaffDashboard("staffUsername").setVisible(true));
    }
}
