package com.hit.dialogs.createGame;

import com.hit.common.responses.GameModel;
import com.hit.dialogs.createGame.models.CreateGameFormResult;
import com.hit.dialogs.createGame.models.MetadataItem;

public class CreateGameModel {
    private String roundNumber;
    private String field;
    private String result;
    private MetadataItem[] teams;
    private MetadataItem[] gameTypes;
    private GameModel gameToSave;

    public CreateGameModel() {
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(final String roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getResult() {
        return result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public void setTeams(MetadataItem[] data) {
        this.teams = data;
    }

    public void setGameTypes(MetadataItem[] metadataItems) {
        this.gameTypes = metadataItems;
    }

    public MetadataItem[] getTeams() {
        return teams;
    }

    public MetadataItem[] getGameTypes() {
        return gameTypes;
    }

    public GameModel getGameToSave() {
        return gameToSave;
    }

    public void saveFormData(CreateGameFormResult result) {
        this.gameToSave = new GameModel();
        // "yyyy-MM-dd HH:mm:ss"
        final var dateTime = String.format("%04d-%02d-%02d %02d:%02d:00", result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute());
        this.gameToSave.setDateTime(dateTime);
        this.gameToSave.setType(result.getGameType().getId());
        this.gameToSave.setHostingTeamId(result.getHostingTeam().getId());
        this.gameToSave.setParticipatingTeamId(result.getParticipatingTeam().getId());
        this.gameToSave.setRoundNumber(Integer.parseInt(result.getRoundNumber()));
        this.gameToSave.setCourt(result.getCourt());
        this.gameToSave.setResult(result.getResult());
    }
}