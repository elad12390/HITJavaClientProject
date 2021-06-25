package com.hit.dialogs.gameTable;

import com.hit.common.responses.GameModel;
import com.hit.common.responses.TeamResponseModel;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameTableModel {
    private GameModel[] games;
    private TeamResponseModel[] teams;
    private String[][] gameTableData;
    private String[] gameTableHeaders;
    private DefaultTableModel tableModel;
    private Set<Integer> changedRows;
    private Set<Integer> deletedRows;
    private AtomicBoolean ignoreUpdateTableEvents = new AtomicBoolean(false);

    public GameTableModel() {
        this.games = null;
        this.changedRows = new HashSet<>();
    }

    public TeamResponseModel[] getTeams() {
        return teams;
    }

    public void setTeams(TeamResponseModel[] teams) {
        this.teams = teams;
    }

    public GameModel[] getGames() {
        return games;
    }

    public GameModel getGameById(int id) {
        return Arrays.stream(games).filter(game -> game.getId() == id).findFirst().orElse(null);
    }

    public void setGames(GameModel[] games) {
        this.games = games;
    }

    public void setGameTableData(String[][] gameTableData) {
        this.gameTableData = new String[gameTableData.length][];
        for (int i = 0; i < gameTableData.length; i++) {
            this.gameTableData[i] = gameTableData[i].clone();
        }
    }

    public boolean getIgnoreUpdateTableEvents() {
        return ignoreUpdateTableEvents.get();
    }

    public void setIgnoreUpdateTableEvents(boolean ignoreUpdateTableEvents) {
        this.ignoreUpdateTableEvents.set(ignoreUpdateTableEvents);
    }

    public void addChangedRow(int idx) {
        changedRows.add(idx);
    }

    public void resetChangedRows() {
        changedRows.clear();
    }

    public final Set<Integer> getChangedRows() {
        return changedRows;
    }

    public void addDeletedRow(int idx) {
        deletedRows.add(idx);
    }

    public void resetDeletedRows() {
        deletedRows.clear();
    }

    public final Set<Integer> getDeletedRows() {
        return deletedRows;
    }

    public String[][] getGameTableData() {
        return gameTableData;
    }

    public void setGameTableHeaders(String[] gameTableHeaders) {
        this.gameTableHeaders = gameTableHeaders.clone();
    }

    public String[] getGameTableHeaders() {
        return gameTableHeaders;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel() {

        if (this.gameTableData == null || this.gameTableHeaders == null) return;

        var data = new String[this.gameTableData.length][];
        for (int i = 0; i < this.gameTableData.length; i++) {
            data[i] = this.gameTableData[i].clone();
        }

        var headers = this.gameTableHeaders.clone();

        this.tableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 6 & column != 5 & column != 0 & column != 2;
            }
        };

    }

    public void setTableModel(String[][] data, String[] columnNames) {
        if (data == null || columnNames == null) return;
        this.setGameTableData(data);
        this.setGameTableHeaders(columnNames);
        this.setTableModel();
    }

    public void updateGame(GameModel game) {
        for (int i = 0; i < games.length; i++) {
            if (games[i].getId() == game.getId()) {
                games[i] = new GameModel(game);
            }
        }
    }

    public void removeGame(int id) {
        games = Arrays.stream(games).filter(game -> game.getId() != id).toArray(GameModel[]::new);
    }
}
