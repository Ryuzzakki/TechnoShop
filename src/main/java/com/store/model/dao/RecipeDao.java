package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Ingredient;
import com.store.model.Product;

@Component
public class RecipeDao {
	
	@Autowired
	private DBManager dbManager;
	@Autowired
	private IngredientDao ingredientDao;

	public HashSet<Ingredient> getAllIngredientsFromRecipe(long order_id, long product_id) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.recipe where product_id = ? and order_id = ? ");
		preparedStatement.setLong(1, product_id);
		preparedStatement.setLong(2, order_id);
		HashSet<Ingredient> ingredients = new HashSet<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long ingredientID = set.getLong("ingredient_id");
			Ingredient ingredient = ingredientDao.getIngredient(ingredientID);
			ingredients.add(ingredient);
		}
		return ingredients;

	}

	public void addIngredientToRecipe(long order_id, long ingredient_id, Product p) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("INSERT INTO pizza_store.recipe (order_id, product_id, ingredient_id) VALUES (?,(SELECT id FROM pizza_store.products WHERE name = ?), ?)");
		preparedStatement.setLong(1, order_id);
		preparedStatement.setString(2, p.getName());
		preparedStatement.setLong(3, ingredient_id);
		preparedStatement.executeUpdate();
	}



}
