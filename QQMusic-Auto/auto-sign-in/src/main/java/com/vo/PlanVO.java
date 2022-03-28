package com.vo;

public class PlanVO {

    private String date;
    private String type;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "PlanVO{" +
                "date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chromeDriverLocation='" + chromeDriverLocation + '\'' +
                '}';
    }

    public String getChromeDriverLocation() {
        return chromeDriverLocation;
    }

    public void setChromeDriverLocation(String chromeDriverLocation) {
        this.chromeDriverLocation = chromeDriverLocation;
    }

    private String chromeDriverLocation;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
