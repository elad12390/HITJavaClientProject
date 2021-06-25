package com.hit.dialogs.playerHome;

import com.hit.common.responses.GameModel;
import com.hit.common.responses.PlayerResponseModel;
import com.hit.common.responses.TeamResponseModel;
import com.hit.common.services.socket.SocketService;
import com.hit.common.services.socket.enums.ESocketMethod;
import com.hit.common.services.socket.requests.ParamRequestMap;
import com.hit.common.services.socket.responses.SocketResponse;
import com.hit.dialogs.gameTable.GameTableController;
import com.hit.dialogs.gameTable.GameTableModel;
import com.hit.dialogs.gameTable.GameTableView;
import com.hit.logger.main.java.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerHomeController {
    private final PlayerHomeModel model;
    private final PlayerHomeView view;
    private final SocketService service;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public PlayerHomeController(PlayerHomeView view, PlayerHomeModel model, SocketService service) {
        this.model = model;
        this.view = view;
        this.service = service;

        setViewOptions(view);
        startClockThread();
        getNextGameRequest();
        getPlayerRequest();
        addListeners();
    }

    // ****************** View Options *********************** //

    private void setViewOptions(PlayerHomeView view) {
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setResizable(false);
        view.setContentPane();
        view.setSize(600,400);
    }

    // ****************** Listeners *********************** //

    private void addListeners() {
        addGameListButtonListener(view, service);
    }

    private void addGameListButtonListener(PlayerHomeView view, SocketService service) {
        this.view.addGameListButtonListener(actionEvent -> {
            openGameTableDialog(view, service);
        });
    }

    // ****************** Server Requests *********************** //

    private void getNextGameRequest() {
        this.service.send(ESocketMethod.Get, "/api/Game/Next/", (SocketResponse game) -> {
            this.model.setNextGame(game.getData(GameModel.class));
            if (this.model.getNextGame() != null) {
                this.view.setNextGameDateTime(this.model.getNextGame().getDateTime());
                this.view.setNextGameCourtName(this.model.getNextGame().getCourt());

                // after getting the next game, get the opposing team
                getOpposingTeamRequest();

            } else {
                Logger.logInformation("There was no next game to present in the screen :(");
                this.view.setNextGameDateTime("אין משחק הבא");
                this.view.setNextGameCourtName("");
            }
        });
    }

    private void getPlayerRequest() {
        ParamRequestMap params = new ParamRequestMap();
        params.put("id", 1);
        this.service.send(ESocketMethod.Get, "/api/Team/Player/", params, (SocketResponse player) -> {
            this.model.setPlayer(player.getData(PlayerResponseModel.class));
            if (this.model.getPlayer() != null) {
                this.view.setPlayerName(this.model.getPlayerName());
                // after got player get his team
                getTeamRequest();

            } else {
                Logger.logInformation("Could not find player");
                this.view.setNextGameDateTime("אין משחק הבא");

            }
        });
    }

    private void getTeamRequest() {
        ParamRequestMap params = new ParamRequestMap();
        params.put("id", this.model.getPlayer().getTeamId());
        this.service.send(ESocketMethod.Get, "/api/Team/", params, (SocketResponse team) -> {
            this.model.setTeam(team.getData(TeamResponseModel.class));
            if (this.model.getTeam() != null) {
                this.view.setTeamName(this.model.getTeamName());
                this.view.setHomeCourtAddress(this.model.getHomeCourtAddress());
                this.view.setHomeCourtName(this.model.getHomeCourtName());
                this.view.setHomeCourtPhoneNumber(this.model.getHomeCourtPhoneNumber());
            } else {
                Logger.logInformation("Could not find player");
                this.view.setTeamName("השחקן אינו נמצא בקבוצה");
            }
        });
    }

    private void getOpposingTeamRequest() {
        ParamRequestMap params = new ParamRequestMap();
        var teamId = this.model.getNextGame().getHostingTeamId() == this.model.getTeam().getId() ?
                this.model.getNextGame().getParticipatingTeamId() : this.model.getNextGame().getHostingTeamId();
        params.put("id", teamId);
        this.service.send(ESocketMethod.Get, "/api/Team/", params, (SocketResponse team) -> {
            this.model.setNextGameOpposingTeam(team.getData(TeamResponseModel.class));
            if (this.model.getTeam() != null) {
                this.view.setNextGameOpposingTeamName(this.model.getNextGameOpposingTeamName());
            } else {
                Logger.logInformation("Could not find player");
                this.view.setTeamName("השחקן אינו נמצא בקבוצה");
            }
        });
    }

    // ****************** Open Dialogs *********************** //

    private void openGameTableDialog(PlayerHomeView view, SocketService service) {
        GameTableView gameTableView = new GameTableView("טבלת משחקים");
        GameTableModel gameTableModel = new GameTableModel();
        GameTableController gameTableController = new GameTableController(gameTableView, gameTableModel, service);
        view.disableGameListButton();
        gameTableView.setVisible(true);
        addGameTableViewCloseListener(view, gameTableView);
    }

    private void addGameTableViewCloseListener(PlayerHomeView view, GameTableView gameTableView) {
        gameTableView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                getNextGameRequest();
                view.enableGameListButton();
            }
        });
    }

    // ****************** Misc ******************************* //

    private void startClockThread() {
        executor.submit(() -> {
            while(true) {
                this.view.setDateTimeNow(this.model.getDateTimeNow());
                Thread.sleep(499);
            }
        });
    }
}
