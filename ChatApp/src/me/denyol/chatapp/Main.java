package me.denyol.chatapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.denyol.chatapp.controller.MainAppController;
import me.denyol.chatapp.controller.SignOn;
import me.denyol.chatapp.logic.ClientThread;
import me.denyol.chatapp.logic.ServerThread;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private MainAppController mainAppController;
    private int port;
    private ServerThread serverThread;
    private ClientThread clientThread;
    private String name = "Default";

    public final static int METADATA_BYTE_LENGTH = 16;
    public final static int DEFAULT_PORT = 25570;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        stage.setTitle("P2P Chat Application");

        this.startSignOnScene();
    }

    public void startChatAppScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("chat_app.fxml")
        );

        Scene scene = new Scene((Pane) loader.load(), 350, 450);

        MainAppController controller = loader.getController();
        this.mainAppController = controller;

        serverThread = new ServerThread(mainAppController, this);
        serverThread.start();
        clientThread = new ClientThread(mainAppController, this);
        clientThread.start();

        controller.init(this);

        stage.setScene(scene);

        this.stage.show();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getServerPort() {
        return port;
    }

    public int getClientPort() {
        return port + 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void startSignOnScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("sign_on.fxml")
        );

        this.serverThread = null;
        this.clientThread = null;

        Scene scene = new Scene((Pane) loader.load());

        SignOn controller = loader.getController();
        controller.init(this);

        this.stage.setScene(scene);
        this.stage.show();
    }

    public String getName() {
        return name;
    }

    @Override
    public void stop() throws Exception {
        if (serverThread != null && this.clientThread != null) {
            serverThread.getMessageOutQueue().offer(this.name + " is leaving the room! o/");
            this.serverThread.stopRunning();
            this.clientThread.stopRunning();
        }

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
