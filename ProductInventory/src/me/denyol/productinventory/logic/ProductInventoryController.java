package me.denyol.productinventory.logic;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import me.denyol.productinventory.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInventoryController implements Initializable {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> skuCol;
	@FXML private TableColumn<Product, String> nameCol;
	@FXML private TableColumn<Product, Double> priceCol;
	@FXML private TableColumn<Product, Integer> amountCol;

	@FXML private TextArea totalValue;

	private Main main;

	private Inventory productInventory;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		productInventory = new Inventory(this.table, this);

		this.updateProducts();

		skuCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> productStringCellDataFeatures) {
				return new ReadOnlyObjectWrapper<>(productStringCellDataFeatures.getValue().getItemSKU());
			}
		});

		nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> productStringCellDataFeatures) {
				return new ReadOnlyObjectWrapper<>(productStringCellDataFeatures.getValue().getName());
			}
		});

		priceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Double>, ObservableValue<Double>>() {
			@Override
			public ObservableValue<Double> call(TableColumn.CellDataFeatures<Product, Double> productDoubleCellDataFeatures) {
				return new ReadOnlyObjectWrapper<>(productDoubleCellDataFeatures.getValue().getPrice());
			}
		});

		amountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Integer>, ObservableValue<Integer>>() {

			private Inventory inventory;

			private Callback<TableColumn.CellDataFeatures<Product, Integer>, ObservableValue<Integer>> init(Inventory inventory) {
				this.inventory = inventory;
				return this;
			}

			@Override
			public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Product, Integer> productIntegerCellDataFeatures) {
				Product product = productIntegerCellDataFeatures.getValue();

				return new ReadOnlyObjectWrapper<>(inventory.getAmountOfProduct(product));
			}
		}.init(productInventory));

	}

	private void updateTotalValue() {
		this.totalValue.setText(this.productInventory.getInventoryValue() + "");
	}

	void updateProducts() {
		this.table.setItems(FXCollections.observableArrayList(this.productInventory.getProducts()));
		updateTotalValue();
	}

	public Inventory getProductInventory() {
		return productInventory;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	private void handleAddButtonClicked(ActionEvent event) throws Exception{
		main.setAddItemScene();
	}
}
