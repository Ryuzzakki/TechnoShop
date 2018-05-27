package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Restaurant;

@Component
public class RestaurantDao {
	
	@Autowired
	private DBManager dbManager;

	public Restaurant getRestaurant(long RestorantId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM techno_store.restaurants where id = ?");
		preparedStatement.setLong(1, RestorantId);

		Restaurant restaurant = null;
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String restName = set.getString("name");
			String restLocation = set.getString("location");
			String phone = set.getString("phone_number");
			restaurant = new Restaurant(id, restName, restLocation, phone);
		}
		if (restaurant != null) {
			return restaurant;
		}
		return restaurant;

	}
	public ArrayList<Restaurant> getAllRestaurants() throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM techno_store.restaurants");
		Restaurant restaurant = null;
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long id = set.getLong("id");
			String name = set.getString("name");
			String location = set.getString("location");
			String phoneNumber = set.getString("phone_number");
			restaurant = new Restaurant(id, name, location, phoneNumber);
			restaurants.add(restaurant);

		}
		return restaurants;

	}
}