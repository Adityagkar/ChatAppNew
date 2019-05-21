package com.upgrad.ChatApp;

public class SocietyData {
    String name;
    String details;
    String url;
    String admin;

    public SocietyData(String name, String details, String url, String admin) {
        this.name = name;
        this.details = details;
        this.url = url;
        this.admin = admin;
    }

    public SocietyData(){

    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
