package com.hit.common.responses;

public class TeamResponseModel {
    private int id;
    private String name;
    private String homeCourtName;
    private String homeCourtAddress;
    private String homeCourtPhone;
    private String logDateTime;

    public TeamResponseModel() {}

    public TeamResponseModel(String name, String homeCourtName, String homeCourtAddress, String homeCourtPhone) {
        this.name = name;
        this.homeCourtName = homeCourtName;
        this.homeCourtAddress = homeCourtAddress;
        this.homeCourtPhone = homeCourtPhone;
    }

    public String getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeCourtName() {
        return homeCourtName;
    }

    public void setHomeCourtName(String homeCourtName) {
        this.homeCourtName = homeCourtName;
    }

    public String getHomeCourtAddress() {
        return homeCourtAddress;
    }

    public void setHomeCourtAddress(String homeCourtAddress) {
        this.homeCourtAddress = homeCourtAddress;
    }

    public String getHomeCourtPhone() {
        return homeCourtPhone;
    }

    public void setHomeCourtPhone(String homeCourtPhone) {
        this.homeCourtPhone = homeCourtPhone;
    }

    @Override
    public String toString() {
        return "TeamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", homeCourtName='" + homeCourtName + '\'' +
                ", homeCourtAddress='" + homeCourtAddress + '\'' +
                ", homeCourtPhone='" + homeCourtPhone + '\'' +
                '}';
    }
}
