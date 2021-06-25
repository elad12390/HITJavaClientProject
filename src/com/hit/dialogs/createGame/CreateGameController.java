package com.hit.dialogs.createGame;

import com.hit.common.responses.GameTypeResponse;
import com.hit.common.responses.TeamResponseModel;
import com.hit.common.services.socket.SocketService;
import com.hit.common.services.socket.enums.ESocketMethod;
import com.hit.common.services.socket.responses.SocketResponse;
import com.hit.dialogs.createGame.exceptions.RequiredFieldException;
import com.hit.dialogs.createGame.models.MetadataItem;
import com.hit.logger.main.java.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CreateGameController {
    private final CreateGameView view;
    private final CreateGameModel model;
    private final SocketService service;

    public CreateGameController(CreateGameView createGameView, CreateGameModel createGameModel, SocketService service) {
        this.view = createGameView;
        this.model = createGameModel;
        this.service = service;

        setViewOptions(view);


        this.getAllGameTypes();


        // run asynchronous task synchronously because we need to wait for metadata before passing on.
        try {
            this.getAllTeamsRequest();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        this.view.addSaveButtonListener(e -> {
            var formResult = this.view.getFormResult();
            try {
                Integer.parseInt(formResult.getRoundNumber());
                if (formResult.getCourt().equals("")) {
                    throw new RequiredFieldException("אולם");
                }
            } catch (NumberFormatException formatException) {
                alert("מחזור חייב להיות מספר");
                return;
            } catch (RequiredFieldException requiredFieldException) {
                alert("השדה "+ "\"" + requiredFieldException.getMessage() + "\"" + " הוא שדה חובה");
                return;
            }
            this.model.saveFormData(formResult);

            createGameRequest();
        });

        this.view.addCancelButtonListener(e -> {
            this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
        });


    }

    private void createGameRequest() {
        this.service.send(ESocketMethod.Create, "/api/Game/", null, this.model.getGameToSave(), (SocketResponse res) -> {
            Logger.logDebug(res.toString());

            this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
        });
    }

    public void setViewOptions(CreateGameView view) {
        view.setContentPane();
        view.setSize(800,400);
        view.setMinimumSize(new Dimension(800, 600));
    }

    private void alert(String err) {
        JOptionPane.showMessageDialog(this.view, err);
    }

    private void getAllTeamsRequest() throws ExecutionException, InterruptedException {
        this.service.send(ESocketMethod.Get, "/api/Team/", (SocketResponse teams) -> {
            var teamsModel = teams.getData(TeamResponseModel[].class);
            var teamsMetadata = Arrays.stream(teamsModel).map(team -> new MetadataItem(team.getId(), team.getName())).toArray(MetadataItem[]::new);
            this.model.setTeams(teamsMetadata);
            this.view.addTeamSelectorOptions(this.model.getTeams());
        });
    }

    private void getAllGameTypes() {
        this.model.setGameTypes(new MetadataItem[] {
                new MetadataItem(GameTypeResponse.None.getValue(), GameTypeResponse.None.toString()),
                new MetadataItem(GameTypeResponse.League.getValue(), GameTypeResponse.League.toString()),
                new MetadataItem(GameTypeResponse.Trophy.getValue(), GameTypeResponse.Trophy.toString()),
                new MetadataItem(GameTypeResponse.Playoff.getValue(), GameTypeResponse.Playoff.toString())
        });

        this.view.addGameTypeSelectorOptions(this.model.getGameTypes());
    }
}
