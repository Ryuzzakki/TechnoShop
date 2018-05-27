package com.store.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Part;
import com.store.model.Product;

@Component
public class ProductDao {

	@Autowired
	private DBManager dbManager;

	public Product getProduct(long proId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.products where id = ?");
		preparedStatement.setLong(1, proId);
		Product product = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			String productPic = set.getString("pictureUrl");
			int category = set.getInt("category");
			HashSet<Part> ings = getPartsByProductId(id);
			product = new Product(id, productName, productPrice, category);
			product.setParts(ings);
			product.setProductPicture(productPic);
		}

		return product;
	}

	public ArrayList<Product> getAllProducts() throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.products");
		Product product = null;
		ArrayList<Product> products = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			int category = set.getInt("category");
			product = new Product(id, productName, productPrice, category);
			HashSet<Part> parts = getPartsByProductId(id);

			HashSet<Part> defaultParts = new HashSet<>();
			defaultParts.addAll(parts);

			product.setParts(parts);
			product.setDefParts(defaultParts);
			products.add(product);

		}
		return products;

	}

	public HashSet<Part> getPartsByProductId(long productId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(
				"SELECT parts.id, parts.name, parts.price FROM parts JOIN product_has_part ON product_has_part.part_id = parts.id WHERE product_has_part.product_id = ?");
		preparedStatement.setLong(1, productId);
		Part part = null;
		HashSet<Part> parts = new HashSet<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			part = new Part(id, productName, productPrice);
			parts.add(part);

		}

		return parts;
	}

	public ArrayList<Product> getFavoritesByUserId(long userId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT product_id FROM user_has_favorites WHERE user_id = ?");
		preparedStatement.setLong(1, userId);

		ArrayList<Product> favorites = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long productId = set.getLong("product_id");
			Product product = getProduct(productId);
			favorites.add(product);
		}
		return favorites;

	}

	public void addFavoriteProduct(long userId, long productId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("INSERT INTO user_has_favorites (user_id, product_id) VALUES (?,?)");
		
		preparedStatement.setLong(1, userId);
		preparedStatement.setLong(2, productId);
		preparedStatement.executeUpdate();
	}
	
	public void removeFavoriteProduct(long productId, long userId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM user_has_favorites WHERE product_id=? and user_id=?");

		preparedStatement.setLong(1, productId);
		preparedStatement.setLong(2, userId);
		preparedStatement.executeUpdate();
	}

}
