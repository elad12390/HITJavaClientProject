package com.hit.driver;

import com.google.gson.Gson;
import com.hit.common.services.socket.SocketService;
import com.hit.dialogs.playerHome.PlayerHomeController;
import com.hit.dialogs.playerHome.PlayerHomeModel;
import com.hit.dialogs.playerHome.PlayerHomeView;
import com.hit.logger.main.java.Logger;
import com.hit.logger.main.java.enums.ELoggingLevel;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MVCDriver {
    public static final Gson gson = new Gson();
    public static final int port = 9110;
    public static InetAddress serverAddress;

    public static void main(String[] args) {
        try {
            setup();
        } catch (UnknownHostException e) {
            System.out.println("Err with getting host ip: " + e);
            return;
        }
        run();
    }

    private static void setup() throws UnknownHostException {
        // set this pc current ip address for sending requests
        serverAddress = InetAddress.getLocalHost();
        Logger.setLoggingLevel(ELoggingLevel.DEBUG);
    }

    private static void setupDialogs(SocketService service) {
        PlayerHomeView playerHomeView = new PlayerHomeView("מערכת לניהול משחקים");
        PlayerHomeModel playerHomeModel = new PlayerHomeModel();
        PlayerHomeController playerHomeController = new PlayerHomeController(playerHomeView, playerHomeModel, service);

        playerHomeView.setVisible(true);
    }

    private static void run() {
        SocketService service = new SocketService();
        MVCDriver.setupDialogs(service);
    }
}
