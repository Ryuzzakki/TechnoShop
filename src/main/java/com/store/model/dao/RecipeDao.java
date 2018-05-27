package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import com.store.model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Product;

@Component
public class RecipeDao {
	
	@Autowired
	private DBManager dbManager;
	@Autowired
	private PartDao partDao;

	public HashSet<Part> getAllPartsFromRecipe(long order_id, long product_id) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM techno_store.recipe where product_id = ? and order_id = ? ");
		preparedStatement.setLong(1, product_id);
		preparedStatement.setLong(2, order_id);
		HashSet<Part> parts = new HashSet<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long partID = set.getLong("part_id");
			Part part = partDao.getPart(partID);
			parts.add(part);
		}
		return parts;

	}

	public void addPartToRecipe(long order_id, long part_id, Product p) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("INSERT INTO techno_store.recipe (order_id, product_id, part_id) VALUES (?,(SELECT id FROM techno_store.products WHERE name = ?), ?)");
		preparedStatement.setLong(1, order_id);
		preparedStatement.setString(2, p.getName());
		preparedStatement.setLong(3, part_id);
		preparedStatement.executeUpdate();
	}



}
