package com.example;

import java.util.Date;

public class AttendanceRecord {
    private int id;
    private String name;
    private String standard;
    private String attendanceStatus;
    private Date date;

    public AttendanceRecord() {}

    public AttendanceRecord(int id, String name, String standard, String attendanceStatus, Date date) {
        this.id = id;
        this.name = name;
        this.standard = standard;
        this.attendanceStatus = attendanceStatus;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStandard() { return standard; }
    public void setStandard(String standard) { this.standard = standard; }
    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

}
