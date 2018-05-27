package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Ingredient;
import com.store.model.Order;
import com.store.model.Product;
import com.store.model.Restaurant;
import com.store.model.User;
import com.store.model.UserException;

@Component
public class OrderDao {

	@Autowired
	private DBManager dbManager;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDetailsDao orderDetailsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RestaurantDao restaurantDao;
	@Autowired
	private RecipeDao recipeDao;

	// get user and restaurant from session
	public long createOrder(User u, Restaurant r, String address) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(
				"insert into orders (user_id,restaurant_id, total_price, order_date, delivery_address) values(?,?,?,now(),?)",
				Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setLong(1, u.getId());
		preparedStatement.setLong(2, r.getId());
		preparedStatement.setDouble(3, 0);
		preparedStatement.setString(4, address);
		preparedStatement.executeUpdate();

		ResultSet rs = preparedStatement.getGeneratedKeys();
		rs.next();
		Long id = rs.getLong(1);
		u.setId(id);

		return id;

	}

	public void makeFullOrder(String deliveryAddress, User u, Restaurant r, HashMap<Product, Integer> map, double price)
			throws SQLException, UserException {
		dbManager.getConnection().setAutoCommit(false);
		try {
			long orderId = createOrder(u, r, deliveryAddress);
			Order newOrderInDB = getOrderById(orderId);
			for (Product p : map.keySet()) {
				orderDetailsDao.addProductToOrderDetails(p, newOrderInDB, map.get(p));
				for (Ingredient ing : p.getIngredients()) {
					recipeDao.addIngredientToRecipe(newOrderInDB.getId(), ing.getId(), p);
				}
			}
			updateOrderPrice(price, newOrderInDB.getId());
			dbManager.getConnection().commit();
		} catch (SQLException e) {
			dbManager.getConnection().rollback();
		} finally {
			dbManager.getConnection().setAutoCommit(true);
		}
	}

	public void calculatePrice(long id) throws SQLException {
		double price = 0;
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("select product_id, quantity from order_details where order_id = ?");
		preparedStatement.setLong(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			long productId = resultSet.getLong("product_id");
			int productQuantity = resultSet.getInt("quantity");
			Product product = productDao.getProduct(productId);
			price += product.getPrice() * productQuantity;
		}
		updateOrderPrice(price, id);

	}

	public void updateOrderPrice(double price, long orderId) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("UPDATE pizza_store.orders SET total_price =? WHERE id=?");
		preparedStatement.setDouble(1, price);
		preparedStatement.setDouble(2, orderId);
		preparedStatement.executeUpdate();

	}

	public HashSet<Order> getAllOrders(long user_id) throws SQLException, UserException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.orders where user_id = ?");
		preparedStatement.setLong(1, user_id);

		HashSet<Order> orders = new HashSet<>();
		ResultSet set = preparedStatement.executeQuery();

		while (set.next()) {
			long orderId = set.getLong("id");
			long userId = user_id;
			long restorantId = set.getLong("restaurant_id");
			double totalPrice = set.getDouble("total_price");
			LocalDateTime dateTime = set.getTimestamp("order_date").toLocalDateTime();
			Order order = new Order(userDao.getUser(userId), restaurantDao.getRestaurant(restorantId), totalPrice,
					dateTime, orderDetailsDao.getAllProductsFromOrder(orderId));
			orders.add(order);

		}
		return orders;

	}

	public Order getOrderById(long id) throws SQLException, UserException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM pizza_store.orders where id  =?");
		preparedStatement.setLong(1, id);

		ResultSet set = preparedStatement.executeQuery();
		Order order = null;
		while (set.next()) {
			long orderId = set.getLong("id");
			long userId = set.getLong("user_id");
			long restorantId = set.getLong("restaurant_id");
			double totalPrice = set.getDouble("total_price");
			LocalDateTime dateTime = set.getTimestamp("order_date").toLocalDateTime();
			order = new Order(orderId, userDao.getUser(userId), restaurantDao.getRestaurant(restorantId), totalPrice,
					dateTime);
		}

		return order;

	}

}
