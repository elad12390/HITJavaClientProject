package com.hit.dialogs.playerHome;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PlayerHomeView extends JFrame {
    private JPanel panel;
    private JLabel playerName;
    private JLabel dateTimeNow;
    private JLabel teamName;
    private JLabel houseCourtName;
    private JLabel houseCourtTitle;
    private JLabel houseCourtAddress;
    private JLabel houseCourtPhoneNumber;
    private JLabel nextGameDateTime;
    private JLabel nextGameTitle;
    private JLabel nextGameCourt;
    private JLabel nextGameOpposingTeam;
    private JButton gameListButton;

    public PlayerHomeView(String title) {
        super(title);
    }

    public void addGameListButtonListener(ActionListener al) {
        gameListButton.addActionListener(al);
    }

    public String getPlayerName() {
        return playerName.getText();
    }

    public void setPlayerName(String text) {
        this.playerName.setText(text);
    }

    public String getDateTimeNow() {
        return dateTimeNow.getText();
    }

    public void setDateTimeNow(String text) {
        this.dateTimeNow.setText(text);
    }

    public String getTeamName() {
        return teamName.getText();
    }

    public void setTeamName(String text) {
        this.teamName.setText(text);
    }

    public String getHomeCourtAddress() {
        return this.houseCourtAddress.getText();
    }

    public void setHomeCourtAddress(String text) {
        this.houseCourtAddress.setText("כתובת: " + text);
    }

    public String getHomeCourtPhoneNumber() {
        return houseCourtPhoneNumber.getText();
    }

    public void setHomeCourtPhoneNumber(String text) {
        this.houseCourtPhoneNumber.setText("טלפון: " + text);
    }

    public String getHomeCourtName() {
        return houseCourtName.getText();
    }

    public void setHomeCourtName(String text) {
        this.houseCourtName.setText("שם אולם: " + text);
    }

    public String getNextGameDateTime() {
        return nextGameDateTime.getText();
    }

    public void setNextGameDateTime(String text) {
        this.nextGameDateTime.setText("תאריך: " + text);
    }

    public String getNextGameCourtName() {
        return nextGameCourt.getText();
    }

    public void setNextGameCourtName(String text) {
        this.nextGameCourt.setText("אולם: " + text);
    }

    public String getNextGameOpposingTeamName() {
        return nextGameOpposingTeam.getText();
    }

    public void setNextGameOpposingTeamName(String text) {
        this.nextGameOpposingTeam.setText("נגד: " + text);
    }

    public void disableGameListButton() {
        this.gameListButton.setEnabled(false);
    }

    public void enableGameListButton() {
        this.gameListButton.setEnabled(true);
    }

    public void setData(PlayerHomeView data) {
    }

    public void getData(PlayerHomeView data) {
    }

    public boolean isModified(PlayerHomeView data) {
        return false;
    }

    public void setContentPane() {
        this.setContentPane(panel);
    }
}
