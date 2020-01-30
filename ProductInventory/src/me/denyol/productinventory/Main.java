package me.denyol.productinventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.denyol.productinventory.logic.AddItemController;
import me.denyol.productinventory.logic.Product;
import me.denyol.productinventory.logic.ProductInventoryController;

public class Main extends Application {

	/*
	Product Inventory Project – Create an application which manages an inventory of products.
		Create a product class which has a price, id, and quantity on hand.
		Then create an inventory class which keeps track of various products and can sum up the inventory value.
	 */

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		this.setMainScene();

		stage.setTitle("Product Inventory");
		stage.show();
	}

	public void setAddItemScene() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("add_item.fxml"));
		BorderPane pane = (BorderPane) loader.load();

		AddItemController controller = loader.getController();

		controller.setMain(this);

		stage.setScene(new Scene(pane));
	}

	public void setMainScene() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("product_inventory.fxml"));
		Parent root = (Parent) loader.load();

		ProductInventoryController controller = loader.getController();
		controller.setMain(this);

		stage.setScene(new Scene(root));

		controller.getProductInventory().addProduct(new Product("99", "Text1", 10), 1);
		controller.getProductInventory().addProduct(new Product("99", "Text", 10), 1);
	}
}
