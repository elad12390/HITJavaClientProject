package com.hit.dialogs.gameTable;

import com.hit.common.responses.GameModel;
import com.hit.common.responses.TeamResponseModel;
import com.hit.common.services.socket.SocketService;
import com.hit.common.services.socket.enums.ESocketMethod;
import com.hit.common.services.socket.requests.ParamRequestMap;
import com.hit.common.services.socket.responses.SocketResponse;
import com.hit.dialogs.ErrorDialog;
import com.hit.dialogs.createGame.CreateGameController;
import com.hit.dialogs.createGame.CreateGameModel;
import com.hit.dialogs.createGame.CreateGameView;
import com.hit.logger.main.java.Logger;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class GameTableController {
    private final GameTableModel model;
    private final GameTableView view;
    private final SocketService service;

    public GameTableController(GameTableView view, GameTableModel model, SocketService service) {
        this.model = model;
        this.view = view;
        this.service = service;



        this.setViewOptions(view);
        // in case the model was reopened
        this.model.resetChangedRows();

        // adding the listeners the listeners asynchronously because we need the table and data to start
        getAllTeamsRequest();
        addButtonListeners();
    }

    // ****************** View Options *********************** //

    private void setViewOptions(GameTableView view) {
        view.setContentPane();
        view.setSize(900,600);
        view.setMinimumSize(new Dimension(900, 180));
        view.getGamesTable().getInputMap().put(KeyStroke.getKeyStroke("DELETE"),
                "deleteRow");

        view.getGamesTable().getTableHeader().setReorderingAllowed(false);

        view.disableEditingButtons();
    }

    // ****************** Listeners *********************** //

    private void addTableListeners() {
        addTableEditUpdateListener();
        addDeleteKeyListener();
    }

    private void addButtonListeners() {
        addSaveButtonListener();
        addCancelButtonListener();

        addAddGameButtonListener();

        addBackButtonListener();
    }

    private void addTableEditUpdateListener() {
        this.model.getTableModel().addTableModelListener((TableModelEvent e) -> {
            if (this.model.getIgnoreUpdateTableEvents()) return;
            Logger.logDebug("Table was edited");
            // column 0 is the id
            if (this.view.getGamesTable().isEditing()) {
                this.view.getGamesTable().getCellEditor().cancelCellEditing();
            }

            final int row = e.getFirstRow();
            if (row < 0) return;
            switch (e.getType()) {
                case TableModelEvent.UPDATE:
                    model.addChangedRow(row);
                    Logger.logDebug("Client edited row " + row);
                    view.enableEditingButtons();
                    break;
            }
        });
    }

    private void addBackButtonListener() {
        this.view.addBackButtonActionListener(e -> {
            Logger.logDebug("Pressed back button");
            this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
        });
    }

    private void addSaveButtonListener() {
        this.view.addSaveButtonActionListener(e -> {
            var table = this.model.getTableModel();
            Logger.logDebug("Pressed save button");

//            this.view.disableEditingButtons();
            for (var rowIdx: this.model.getChangedRows()) {
                GameModel game = getEntireRowGameValue(this.view.getGamesTable().getModel(), rowIdx);
                if (game != null) {
                    updateGameRequest(game);
                    Logger.logDebug("Sent update req");
                }
            }

            this.model.resetChangedRows();
        });
    }

    private void addDeleteKeyListener() {
        this.view.addDeleteKeyActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // column 0 is the id
                if (view.getGamesTable().isEditing()) {
                    view.getGamesTable().getCellEditor().cancelCellEditing();
                }
                final int row = view.getGamesTable().getSelectedRow();

                Logger.logDebug("Client pressed \"Delete\" button at row " + row);
                int id =  Integer.parseInt((String)model.getTableModel().getValueAt(row, 0));
                Logger.logDebug("Requested delete for " + id);
                deleteGameRequest(id);
            }
        });
    }

    private void addAddGameButtonListener() {
        this.view.addAddGameButtonActionListener(e -> {
            Logger.logDebug("Add game button pressed");
            openCreateGameDialog();
        });
    }

    private void addCancelButtonListener() {
        this.view.addCancelButtonActionListener(e -> {
            this.resetTable();
            this.model.resetChangedRows();
            Logger.logDebug("Pressed cancel button");
        });
    }


    // ****************** Control table model and view *********************** //

    private void resetTable() {
        this.model.setTableModel();
        this.view.setTableModelData(this.model.getTableModel());
        this.view.disableEditingButtons();
        this.addTableListeners();
    }

    private void saveGamesToTableModel() {
        this.model.setIgnoreUpdateTableEvents(true);

        var savedGames = this.model.getGames();
        var data = new String[savedGames.length][];
        var headers = new String[]{"ID", "תאריך ושעה", "סוג", "מחזור", "אולם", "קבוצה מארחת", "קבוצה נגדית", "תוצאה"};
        mapGamesDataToModel(savedGames, data);
        this.model.setTableModel(data,headers);
        this.view.setTableModelData(this.model.getTableModel());

        this.addTableListeners();

        this.model.setIgnoreUpdateTableEvents(false);
    }

    private void updateDataInTableModel() throws ParseException {
        this.model.setIgnoreUpdateTableEvents(true);

        var savedGames = this.model.getGames();
        var data = new String[savedGames.length][];
        mapGamesDataToModel(savedGames, data);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.model.getTableModel().setValueAt(data[i][j], i, j);
            }
        }

        this.model.setIgnoreUpdateTableEvents(false);
    }

    private void removeLastRowFromTableModel() throws ParseException {
        this.model.setIgnoreUpdateTableEvents(true);

        this.model.getTableModel().removeRow(this.model.getTableModel().getRowCount() - 1);

        this.model.setIgnoreUpdateTableEvents(false);
    }

    // ****************** Server Requests *********************** //

    private void getAllTeamsRequest() {
        this.service.send(ESocketMethod.Get, "/api/Team/", (SocketResponse teams) -> {
            this.model.setTeams(teams.getData(TeamResponseModel[].class));
            getAllGamesRequest();
        });
    }

    private void getAllGamesRequest() {
        this.service.send(ESocketMethod.Get, "/api/Game/", (SocketResponse games) -> {
            this.model.setGames(games.getData(GameModel[].class));
            Thread.sleep(0);
            if (this.model.getGames() != null) {
                saveGamesToTableModel();
            }
        });
    }

    private void updateGameRequest(GameModel game) {
        var query = new ParamRequestMap();
        query.put("id", game.getId());
        this.service.send(ESocketMethod.Update, "/api/Game/", query, game, (SocketResponse res) -> {
            if (res.isSuccessful()) {
                Logger.logDebug(res.toString());
                this.model.updateGame(game);
                this.updateDataInTableModel();
                this.view.setTableModelData(this.model.getTableModel());
                this.view.disableEditingButtons();
            }
        });
    }

    private void deleteGameRequest(int id) {
        var query = new ParamRequestMap();
        query.put("id", id);
        this.service.send(ESocketMethod.Delete, "/api/Game/", query, null, (SocketResponse res) -> {
            if (res.isSuccessful()) {
                this.model.removeGame(id);
                Logger.logDebug(Arrays.toString(this.model.getGames()));
                this.updateDataInTableModel();
                this.removeLastRowFromTableModel();
                this.view.setTableModelData(this.model.getTableModel());
                this.view.disableEditingButtons();
            }
        });
    }

    // ****************** Mapping *********************** //

    /**
     * Map games to the table model
     * @param savedGames the games recieved from the server
     * @param data a matrix of the table to store the data
     * @apiNote this function maps the response of the models to the table model (stringified) using the other metadata
     */
    private void mapGamesDataToModel(GameModel[] savedGames, String[][] data) {
        for (int i = 0; i < savedGames.length; i++) {
            var game = savedGames[i];
            try {

                String[] currGameData = new String[8];

                currGameData[0] = String.valueOf(game.getId());
                currGameData[1] = game.getDateTimeLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                currGameData[2] = game.getType().toString();
                currGameData[3] = String.valueOf(game.getRoundNumber());
                currGameData[4] = game.getCourt();

                Arrays.stream(this.model.getTeams())
                        .filter(team -> team.getId() == game.getHostingTeamId())
                        .findFirst().ifPresent(opposingTeam -> currGameData[5] = opposingTeam.getName());

                Arrays.stream(this.model.getTeams())
                        .filter(team -> team.getId() == game.getParticipatingTeamId())
                        .findFirst().ifPresent(participatingTeam -> currGameData[6] = participatingTeam.getName());

                currGameData[7] = game.getResult();

                data[i] = currGameData;
            } catch (ParseException e) {
                Logger.logError("Could not parse date in game(" + game.getId() + ") Error was: " + e.toString());
            }
        }
    }

    /**
     * Map a changed row back to the game model (for updating the item)
     * @param tableModel
     * @param selectedRow
     * @return
     */
    private GameModel getEntireRowGameValue(TableModel tableModel, int selectedRow) {
        if (selectedRow < 0) return null;

        var id = (String)tableModel.getValueAt(selectedRow, 0);
        GameModel game = this.model.getGameById(Integer.parseInt(id));

        var dateTime = (String)tableModel.getValueAt(selectedRow, 1);
        var roundNumber = (String)tableModel.getValueAt(selectedRow, 3);
        var court = (String)tableModel.getValueAt(selectedRow, 4);
        var result = (String)tableModel.getValueAt(selectedRow, 7);

        try {
            game.setDateTime(dateTime);
            game.setRoundNumber(Integer.parseInt(roundNumber));
            game.setCourt(court);
            game.setResult(result);
        } catch (NumberFormatException formatException) {
            this.resetTable();

            // present error dialog
            var errorDialog = new ErrorDialog();
            errorDialog.setVisible(true);
        }

        return game;
    }

    // ****************** Open Dialogs *********************** //

    private void openCreateGameDialog() {
        CreateGameView createGameView = new CreateGameView("יצירת משחק");
        CreateGameModel createGameModel = new CreateGameModel();
        CreateGameController gameTableController = new CreateGameController(createGameView, createGameModel, service);
        this.view.disableAddGameButton();
        createGameView.setVisible(true);
        addCreateGameViewCloseListener(createGameView);
    }

    private void addCreateGameViewCloseListener(CreateGameView createGameView) {
        createGameView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                view.enableAddGameButton();
                getAllGamesRequest();
            }
        });
    }


}
