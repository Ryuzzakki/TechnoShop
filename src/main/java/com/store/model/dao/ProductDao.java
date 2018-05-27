package com.store.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Ingredient;
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
			HashSet<Ingredient> ings = getIngredientsByProductId(id);
			product = new Product(id, productName, productPrice, category);
			product.setIngredients(ings);
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
			HashSet<Ingredient> ingredients = getIngredientsByProductId(id);

			HashSet<Ingredient> defaultIngredients = new HashSet<>();
			defaultIngredients.addAll(ingredients);

			product.setIngredients(ingredients);
			product.setDefIngredients(defaultIngredients);
			products.add(product);

		}
		return products;

	}

	public HashSet<Ingredient> getIngredientsByProductId(long productId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(
				"SELECT ingredients.id, ingredients.name, ingredients.price FROM ingredients JOIN product_has_ingredient ON product_has_ingredient.ingredient_id = ingredients.id WHERE product_has_ingredient.product_id = ?");
		preparedStatement.setLong(1, productId);
		Ingredient ingredient = null;
		HashSet<Ingredient> ingredients = new HashSet<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			ingredient = new Ingredient(id, productName, productPrice);
			ingredients.add(ingredient);

		}

		return ingredients;
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
