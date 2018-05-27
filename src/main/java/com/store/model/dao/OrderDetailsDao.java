package com.store.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.model.Order;
import com.store.model.Product;

@Component
public class OrderDetailsDao {

	@Autowired
	private DBManager dbManager;
	@Autowired
	private ProductDao productDao;

	public void addProductToOrderDetails(Product product, Order order, int quantity) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(
				"INSERT INTO pizza_store.order_details (order_id, product_id, quantity,size,dough) "
						+ "VALUES(?, (SELECT id FROM pizza_store.products WHERE name = ?),?,?,? );",
				Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setLong(1, order.getId());
		preparedStatement.setString(2, product.getName());
		preparedStatement.setLong(3, quantity);
		preparedStatement.setString(4, product.getSize());
		preparedStatement.setString(5, product.getDough());
		preparedStatement.executeUpdate();

	}



	public HashMap<Product, Integer> getAllProductsFromOrder(long order_id) throws SQLException {
		Connection con = dbManager.getConnection();
		PreparedStatement preparedStatement = con
				.prepareStatement("SELECT * FROM pizza_store.order_details where order_id = ?");
		preparedStatement.setLong(1, order_id);
		HashMap<Product, Integer> products = new HashMap<>();
		ResultSet set = preparedStatement.executeQuery();
		while (set.next()) {
			long product_id = set.getLong("product_id");
			int productQuantity = set.getInt("quantity");
			String size = set.getString("size");
			String dough = set.getString("dough");
			Product currentProduct = productDao.getProduct(product_id);
			Product product = new Product(currentProduct.getId(), currentProduct.getName(), currentProduct.getPrice(),
					currentProduct.getCategory(), size, dough);
			if (products.containsKey(product)) {
				int quant = products.get(product);
				products.put(product, quant + productQuantity);
			} else {
				products.put(product, productQuantity);

			}
		}
		return products;
	}

}
