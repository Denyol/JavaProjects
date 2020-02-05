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

    private Thread serverThread;
    private Stage stage;
    private MainAppController mainAppController;
    private SignOn signOn;
    private final int port = 25061;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        stage.setTitle("P2P Chat Application");

        this.startChatAppScene();

        ServerThread serverThread = new ServerThread(mainAppController, port);
        serverThread.setDaemon(true);
        serverThread.start();
        ClientThread clientThread = new ClientThread(mainAppController, port);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    private void startChatAppScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("chat_app.fxml")
        );

        Scene scene = new Scene((Pane) loader.load(), 350, 450);

        MainAppController controller = loader.getController();
        this.mainAppController = controller;

        stage.setScene(scene);

        this.stage.show();
    }

    private void startSignOnScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("sign_on.fxml")
        );

        Scene scene = new Scene((Pane) loader.load());

        SignOn controller = loader.getController();
        this.signOn = controller;

        this.stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
