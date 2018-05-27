package com.store.model;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class Product {

	private long id;
	private String name;
	private double price;
	private HashSet<Part> parts = new LinkedHashSet<>();
	private HashSet<Part> defaultIng = new LinkedHashSet<>();
	private String productPicture;
	private int category = 0;
	private String size = "medium";
	private String dough = "normal";

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public Product(long id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Product(long id, String name, double price, int category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public Product(long id, String name, double price, int category, String size, String dough) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.size = size;
		this.dough = dough;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HashSet<Part> getParts() {
		return parts;
	}

	public HashSet<Part> getDefaultParts() {
		return defaultIng;
	}

	public long getId() {
		return id;
	}

	public void setParts(HashSet<Part> parts) {
		this.parts = parts;
	}

	public void setDefParts(HashSet<Part> parts) {
		this.defaultIng = parts;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public void addPart(Part part) {
		if(!this.parts.contains(part)) {
			if (!this.defaultIng.contains(part)) {
				this.price += part.getPrice();
				price = Math.round(price * 100);
				price = price / 100;
			}
			this.parts.add(part);
		}
	}

	public void removePart(Part part) {
		if (this.parts.contains(part)) {
			this.parts.remove(part);
			if (!this.defaultIng.contains(part)) {
				this.price -= part.getPrice();
				price = Math.round(price * 100);
				price = price/100;
			}

		}
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDough() {
		return dough;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setDough(String dough) {
		this.dough = dough;
	}

}
