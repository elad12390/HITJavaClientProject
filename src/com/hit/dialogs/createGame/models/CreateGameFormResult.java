package com.hit.dialogs.createGame.models;

public class CreateGameFormResult {
    Integer day;
    Integer month;
    Integer year;
    Integer hour;
    Integer minute;
    MetadataItem gameType;
    MetadataItem hostingTeam;
    MetadataItem pariticipatingTeam;

    String roundNumber;
    String court;
    String result;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public MetadataItem getGameType() {
        return gameType;
    }

    public void setGameType(MetadataItem gameType) {
        this.gameType = gameType;
    }

    public MetadataItem getHostingTeam() {
        return hostingTeam;
    }

    public void setHostingTeam(MetadataItem hostingTeam) {
        this.hostingTeam = hostingTeam;
    }

    public MetadataItem getParticipatingTeam() {
        return pariticipatingTeam;
    }

    public void setPariticipatingTeam(MetadataItem participating) {
        this.pariticipatingTeam = participating;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(String roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CreateGameFormResult{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", hour=" + hour +
                ", minute=" + minute +
                ", gameType=" + gameType +
                ", hostingTeam=" + hostingTeam +
                ", opposingTeam=" + pariticipatingTeam +
                ", roundNumber='" + roundNumber + '\'' +
                ", court='" + court + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
