package com.store.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Order {
	//
	private static long count = 20;
	//
	private long id;
	private User user;
	private Restaurant restaurant;
	private double totalPrice;
	private LocalDateTime orderDate;
	private HashMap<Product, Integer> products = new LinkedHashMap<>();

	public Order(User user, Restaurant restaurant, double totalPrice, LocalDateTime orderDate,
			HashMap<Product, Integer> products) {
		this.user = user;
		this.restaurant = restaurant;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.products = products;
	}

	public Order(Restaurant restaurant, double totalPrice, LocalDateTime orderDate,
			HashMap<Product, Integer> products) {
		this.restaurant = restaurant;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.products = products;
	}

	public Order(long id, User user, Restaurant restaurant, double totalPrice, LocalDateTime orderDate) {
		this.id = id;
		this.user = user;
		this.restaurant = restaurant;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;

	}

	/**
	 * for creating empty order
	 * 
	 */
	public Order(User user, Restaurant restaurant) {
		this.user = user;
		this.restaurant = restaurant;
		this.totalPrice = calculateTotalPrice();

	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public LocalDate getOrderDate() {
		LocalDate date = orderDate.toLocalDate();
		return date;
	}

	public LocalDateTime getFullDate() {
		return orderDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addToProducts(Product p) {
		if (products.containsKey(p)) {
			int quant = products.get(p);
			products.put(p, quant + 1);
			// set random id
		} else {
			this.products.put(p, 1);
		}
		p.setId(count++);
		this.totalPrice += p.getPrice();
	}

	public Product findProductInMap(long id) {
		System.out.println(products);
		for (Product p : products.keySet()) {
			if (p.getId() == id) {
				return p;
			}

		}
		return null;
	}

	public void removeProductFromOrder(long id, int quanitity) {
		for (Product p : products.keySet()) {
			if (p.getId() == id) {
				if (products.get(p) == quanitity) {
					products.remove(p);
					this.totalPrice -= p.getPrice() * quanitity;
					return;
				} else {
					products.put(p, products.get(p) - quanitity);
					this.totalPrice -= p.getPrice() * quanitity;
					return;
				}

			}
		}
	}

	public double calculateTotalPrice() {
		double price = 0;
		for (Entry<Product, Integer> product : products.entrySet()) {
			price += product.getKey().getPrice() * product.getValue();
		}

		price = Math.round(price * 100);
		price = price / 100;

		return price;
	}

	public double discount(double price) {
		if (price > 10 && price <= 20) {
			price -= price * 0.1;
		}
		if (price > 20 && price <= 30) {
			price -= price * 0.2;
		}
		if (price > 30) {
			price -= price * 0.3;
		}
		price = Math.round(price * 100);
		price = price / 100;
		return price;
	}

}
