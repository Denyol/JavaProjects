package me.denyol.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import me.denyol.chatapp.Main;

import java.io.IOException;

public class SignOn {

	@FXML private TextField portField;
	@FXML private TextField userField;

	private Main main;

	@FXML
	private void initialize() {

	}

	public void init(Main main) {
		this.main = main;
	}

	@FXML
	public void handleJoinButtonAction(ActionEvent action) {
		try {
			this.main.setPort(Main.DEFAULT_PORT + Integer.parseInt(portField.getText()));

			if (!this.userField.getText().isEmpty()) {
				this.main.setName(this.userField.getText());
			}

			main.startChatAppScene();
		} catch (NumberFormatException | IOException exception) {
			exception.printStackTrace();
			this.portField.setText("1");
		}
	}

}
