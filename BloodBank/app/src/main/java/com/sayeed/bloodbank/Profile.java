package com.sayeed.bloodbank;

public class Profile {

    private String classname;
    private String Date;
    private String time;
    private String Location;
    private boolean status;

    public Profile() {
    }

    public Profile(String classname, String date, String time, String location, boolean status) {
        this.classname = classname;
        Date = date;
        this.time = time;
        Location = location;
        this.status = status;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
