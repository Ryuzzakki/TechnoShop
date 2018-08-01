package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.store.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopDao {
	
	@Autowired
	private DBManager dbManager;

	public Shop getShop(long ShopId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM techno_store.shops where id = ?");
		preparedStatement.setLong(1, ShopId);

		Shop shop = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String restName = set.getString("name");
			String restLocation = set.getString("location");
			String phone = set.getString("phone_number");
			shop = new Shop(id, restName, restLocation, phone);
		}
		if (shop != null) {
			return shop;
		}
		return shop;

	}
	public ArrayList<Shop> getAllShops() throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM techno_store.shops");
		Shop shop = null;
		ArrayList<Shop> shops = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String name = set.getString("name");
			String location = set.getString("location");
			String phoneNumber = set.getString("phone_number");
			shop = new Shop(id, name, location, phoneNumber);
			shops.add(shop);

		}
		return shops;

	}
}
