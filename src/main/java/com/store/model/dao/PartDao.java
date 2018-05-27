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


	public Part getPart(long partId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.parts where id = ?");
		preparedStatement.setLong(1, partId);
		Part part = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String partName = set.getString("name");
			double partPrice = set.getDouble("price");
			part = new Part(id, partName, partPrice);

		}

		return part;

	}

	public ArrayList<Part> getAllParts() throws SQLException {
		Connection con =dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.parts");
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
