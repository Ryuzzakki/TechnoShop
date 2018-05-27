package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.store.model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartDao {
	
	@Autowired
	private DBManager dbManager;


	public Part getIngredient(long ingredientId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.ingredients where id = ?");
		preparedStatement.setLong(1, ingredientId);
		Part part = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String ingredientName = set.getString("name");
			double ingredientPrice = set.getDouble("price");
			part = new Part(id, ingredientName, ingredientPrice);

		}

		return part;

	}

	public ArrayList<Part> getAllIngredients() throws SQLException {
		Connection con =dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.ingredients");
		Part ing = null;
		ArrayList<Part> parts = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String productName = set.getString("name");
			double productPrice = set.getDouble("price");
			ing = new Part(id, productName, productPrice);
			parts.add(ing);

		}
		return parts;

	}

}
