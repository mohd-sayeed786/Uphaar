package com.sayeed.bloodbank;


public class Ngodata {

    String id, name, gender, age, address, quali, pref, clas, exp;

    public Ngodata()
    {

    }

    public Ngodata(String id, String name, String gender, String age, String address, String quali, String pref, String clas, String exp) {

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.quali = quali;
        this.pref = pref;
        this.clas = clas;
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

    public String getQuali() {
        return quali;
    }

    public String getPref() {
        return pref;
    }

    public String getClas() {
        return clas;
    }

    public String getExp() {
        return exp;
    }
}
