package me.denyol.productinventory.logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import me.denyol.productinventory.Main;

public class AddItemController {

	@FXML private TextField skuField;
	@FXML private TextField amountField;
	@FXML private TextField nameField;
	@FXML private TextField priceField;

	private Main main;

	@FXML
	private void handleCancelButtonAction(ActionEvent event) throws Exception {
		System.out.println("Cancel clicked");
		main.setMainScene();
	}

	@FXML
	private void handleAddButtonAction(ActionEvent event) {

	}

	public void setMain(Main main) {
		this.main = main;
	}

}
