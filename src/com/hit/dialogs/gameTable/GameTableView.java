package com.hit.dialogs.gameTable;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GameTableView extends JFrame {
    private JTable gamesTable;
    private JPanel panel;
    private JLabel header;
    private JButton cancelButton;
    private JButton saveButton;
    private JButton backButton;
    private JButton addGameButton;

    public GameTableView(String title) throws HeadlessException {
        super(title);
    }

    public void addTableFocusLostEventListener(FocusAdapter focusAdapter) {
        this.gamesTable.addFocusListener(focusAdapter);
    }

    public void addTableModelListener(TableModelListener modelListener) {
        this.gamesTable.getModel().addTableModelListener(modelListener);
    }

    public void addDeleteKeyActionListener(Action actionListener) {
        gamesTable.getActionMap().put("deleteRow", actionListener);
    }

    public void addSaveButtonActionListener(ActionListener action) {
        this.saveButton.addActionListener(action);
    }

    public void addBackButtonActionListener(ActionListener action) {
        this.backButton.addActionListener(action);
    }

    public void addCancelButtonActionListener(ActionListener action) {
        this.cancelButton.addActionListener(action);
    }

    public void addAddGameButtonActionListener(ActionListener action) {
        this.addGameButton.addActionListener(action);
    }

    public void enableEditingButtons() {
        this.enableSaveButton();
        this.enableCancelButton();
    }

    public void disableEditingButtons() {
        this.disableSaveButton();
        this.disableCancelButton();
    }

    public void disableAddGameButton() {
        this.addGameButton.setEnabled(false);
    }

    public void enableAddGameButton() {
        this.addGameButton.setEnabled(true);
    }

    public void setContentPane() {
        this.setContentPane(panel);
    }

    private void enableCancelButton() {
        cancelButton.setEnabled(true);
    }

    private void disableCancelButton() {
        cancelButton.setEnabled(false);
    }

    private void enableSaveButton() {
        saveButton.setEnabled(true);
    }

    private void disableSaveButton() {
        saveButton.setEnabled(false);
    }

    public JTable getGamesTable() {
        return gamesTable;
    }

    public void setTableModelData(DefaultTableModel model) {
        gamesTable.setModel(model);

        // remove the id column
        if (gamesTable.getColumnModel().getColumnCount() > 7) {
            gamesTable.removeColumn(gamesTable.getColumnModel().getColumn(0));
        }
    }
}
