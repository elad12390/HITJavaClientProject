package com.hit.dialogs.playerHome;

import com.hit.common.responses.GameModel;
import com.hit.common.responses.PlayerResponseModel;
import com.hit.common.responses.TeamResponseModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerHomeModel {
    private PlayerResponseModel player;
    private TeamResponseModel team;
    private GameModel nextGame;
    private TeamResponseModel nextGameOpposingTeam;

    public PlayerResponseModel getPlayer() {
        return player;
    }

    public TeamResponseModel getTeam() {
        return team;
    }

    public GameModel getNextGame() {
        return nextGame;
    }

    public TeamResponseModel getNextGameOpposingTeam() {
        return nextGameOpposingTeam;
    }

    public void setPlayer(PlayerResponseModel player) {
        this.player = player;
    }

    public void setTeam(TeamResponseModel team) {
        this.team = team;
    }

    public void setNextGame(GameModel nextGame) {
        this.nextGame = nextGame;
    }

    public void setNextGameOpposingTeam(TeamResponseModel nextGameOpposingTeam) {
        this.nextGameOpposingTeam = nextGameOpposingTeam;
    }

    public String getPlayerName() {
        return this.player != null ? this.player.getFirstName() + " " + this.player.getLastName() : "";
    }

    public String getDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy"));
    }

    public String getTeamName() {
        return this.team != null ? this.team.getName() : "";
    }

    public String getHomeCourtAddress() {
        return this.team != null ? this.team.getHomeCourtAddress() : "";
    }

    public String getHomeCourtPhoneNumber() {
        return this.team != null ? this.team.getHomeCourtPhone() : "";
    }

    public String getHomeCourtName() {
        return this.team != null ? this.team.getHomeCourtName() : "";
    }

    public String getNextGameDateTime() {
        return this.nextGame != null ? this.nextGame.getDateTime() : "";
    }

    public String getNextGameCourtName() {
        return this.nextGame != null ? this.nextGame.getCourt() : "";
    }

    public String getNextGameOpposingTeamName() {
        return this.nextGameOpposingTeam != null ? this.nextGameOpposingTeam.getName() : "";
    }
}
