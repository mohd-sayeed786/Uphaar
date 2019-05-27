package com.sayeed.bloodbank;

public class VolunteerData {

    String id, name, gender, age, address, mobile, time, joi, exp;

    public VolunteerData()
    {

    }

    public VolunteerData(String id, String name, String gender, String age, String address, String mobile, String time, String joi, String exp) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.mobile = mobile;
        this.time = time;
        this.joi = joi;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTime() {
        return time;
    }

    public String getJoi() {
        return joi;
    }

    public String getExp() {
        return exp;
    }
}
