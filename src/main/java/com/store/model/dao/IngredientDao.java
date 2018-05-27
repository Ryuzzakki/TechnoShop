package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Ingredient;

@Component
public class IngredientDao {
	
	@Autowired
	private DBManager dbManager;


	public Ingredient getIngredient(long ingredientId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.ingredients where id = ?");
		preparedStatement.setLong(1, ingredientId);
		Ingredient ingredient = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String ingredientName = set.getString("name");
			double ingredientPrice = set.getDouble("price");
			ingredient = new Ingredient(id, ingredientName, ingredientPrice);

		}

		return ingredient;

	}

	public ArrayList<Ingredient> getAllIngredients() throws SQLException {
		Connection con =dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.ingredients");
		Ingredient ing = null;
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			ing = new Ingredient(id, productName, productPrice);
			ingredients.add(ing);

		}
		return ingredients;

	}

}
