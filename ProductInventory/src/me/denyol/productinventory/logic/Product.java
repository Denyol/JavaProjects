package me.denyol.productinventory.logic;

import java.util.Objects;

public class Product {

	private final String itemSKU;
	private String name;
	private final double price;

	public Product(String itemSKU, String name, double price) {
		this.itemSKU = itemSKU;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getItemSKU() {
		return itemSKU;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return price == product.price &&
				Objects.equals(itemSKU, product.itemSKU);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemSKU, price);
	}
}
