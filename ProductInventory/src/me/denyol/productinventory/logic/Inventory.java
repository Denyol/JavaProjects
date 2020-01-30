package me.denyol.productinventory.logic;

import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {

	private Map<Product, Integer> products = new HashMap<>();

	private TableView<Product> tableView;
	private ProductInventoryController controller;

	public Inventory(TableView<Product> tableView, ProductInventoryController controller) {
		this.tableView = tableView;
		this.controller = controller;
	}

	public void addProduct(Product product, int amount) {
		if (products.containsKey(product)) {
			products.put(product, products.get(product) + amount);
		} else {
			products.put(product, amount);
		}

		this.updateTable();
	}

	public Set<Product> getProducts() {
		return this.products.keySet();
	}

	public boolean removeProduct(Product product, int amount) {
		if (products.containsKey(product)) {
			int currentAmount = products.get(product);
			if (amount > currentAmount)
				return false;

			products.put(product, currentAmount - amount);
			this.updateTable();
			return true;
		} else {
			return false;
		}
	}

	public double getInventoryValue() {
		double value = 0;

		for (Product product : this.products.keySet()) {
			value += this.products.get(product) * product.getPrice();
		}

		return value;
	}

	public int getAmountOfProduct(Product product) {
		Integer amount = this.products.get(product);

		if (amount == null)
			return 0;
		else
			return amount;
	}

	private void updateTable() {
		this.controller.updateProducts();
	}

}
