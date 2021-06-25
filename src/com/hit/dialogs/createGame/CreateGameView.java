package com.hit.dialogs.createGame;

import com.hit.dialogs.createGame.models.CreateGameFormResult;
import com.hit.dialogs.createGame.models.MetadataItem;
import com.hit.dialogs.createGame.models.MetadataItemRenderer;
import com.hit.logger.main.java.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;

public class CreateGameView extends JFrame {
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel panel;
    private JLabel dateTimeLabel;
    private JLabel typeTitle;
    private JLabel roundNumberLabel;
    private JTextField roundNumber;
    private JLabel courtLabel;
    private JTextField court;
    private JTextField result;
    private JLabel resultLabel;
    private JLabel opposingTeamLabel;
    private JLabel hostingTeamLabel;
    private JLabel header;
    private JComboBox<Integer> minuteSelector;
    private JComboBox<Integer> hourSelector;
    private JComboBox<Integer> yearSelector;
    private JComboBox<Integer> monthSelector;
    private JComboBox<Integer> daySelector;
    private JComboBox<MetadataItem> gameTypeSelector;
    private JComboBox<MetadataItem> hostingTeamSelector;
    private JComboBox<MetadataItem> opposingTeamSelector;

    public CreateGameView(String title) throws HeadlessException {
        super(title);

        // wait for super to finish (probably some asynchronous tasks going on in the background)
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= 59; i+=5) {
            minuteSelector.addItem(i);
        }
        for (int i = 0; i <= 23; i++) {
            hourSelector.addItem(i);
        }
        for (int i = 2000; i <= 2050; i++) {
            yearSelector.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            monthSelector.addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            daySelector.addItem(i);
        }

        this.yearSelector.setSelectedIndex(LocalDateTime.now().getYear() - 2000);
        this.monthSelector.setSelectedIndex(LocalDateTime.now().getMonth().getValue() - 1);
        this.daySelector.setSelectedIndex(LocalDateTime.now().getDayOfMonth() - 1);
        this.hourSelector.setSelectedIndex(12);
        this.hostingTeamSelector.setRenderer(new MetadataItemRenderer());
        this.opposingTeamSelector.setRenderer(new MetadataItemRenderer());

        this.roundNumber.setText("1");
    }

    public void setContentPane() {
        this.setContentPane(panel);
    }

    public void addTeamSelectorOptions(MetadataItem[] teams) {
        for (var team : teams) {
            this.hostingTeamSelector.addItem(team);
            this.opposingTeamSelector.addItem(team);
        }
        this.opposingTeamSelector.setSelectedIndex(1);
    }

    public CreateGameFormResult getFormResult() {
        var formResult = new CreateGameFormResult();
        formResult.setDay((Integer) this.daySelector.getSelectedItem());
        formResult.setMonth((Integer) this.monthSelector.getSelectedItem());
        formResult.setYear((Integer) this.yearSelector.getSelectedItem());
        formResult.setHour((Integer) this.hourSelector.getSelectedItem());
        formResult.setMinute((Integer) this.minuteSelector.getSelectedItem());
        formResult.setGameType((MetadataItem) this.gameTypeSelector.getSelectedItem());

        formResult.setRoundNumber(this.roundNumber.getText());
        formResult.setCourt(this.court.getText());

        formResult.setHostingTeam((MetadataItem) this.hostingTeamSelector.getSelectedItem());
        formResult.setPariticipatingTeam((MetadataItem) this.opposingTeamSelector.getSelectedItem());

        formResult.setResult(this.result.getText());

        Logger.logDebug("Form sent with data: " + formResult);
        return formResult;
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        this.saveButton.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void addGameTypeSelectorOptions(MetadataItem[] gameTypes) {
        for (var type : gameTypes) {
            this.gameTypeSelector.addItem(type);
        }
    }
}
