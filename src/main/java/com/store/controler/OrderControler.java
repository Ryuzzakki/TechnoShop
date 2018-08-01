package com.store.controler;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.Order;
import com.store.model.Product;
import com.store.model.Shop;
import com.store.model.User;
import com.store.model.UserException;
import com.store.model.dao.OrderDao;
import com.store.model.dao.OrderDetailsDao;
import com.store.model.dao.CustomAddonDao;
import com.store.model.dao.UserDao;

@Controller
public class OrderControler {

	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderDetailsDao orderDetailsDao;
	@Autowired
	UserDao userDao;
	@Autowired
    CustomAddonDao customAddonDao;

	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public String makeOrderInDB(HttpServletRequest req) {
		if (req.getSession().getAttribute("order") == null) {
			return "login";
		}
		Order order = (Order) req.getSession().getAttribute("order");
		User u = (User) req.getSession().getAttribute("user");
		Shop r = (Shop) req.getSession().getAttribute("shop");
		String deliveryAddress = (String) req.getSession().getAttribute("userAddress");
		double price = (double) req.getSession().getAttribute("totalPriceWithDiscount");
		HashMap<Product, Integer> map = order.getProducts();
		try {
			if (map.size() != 0) {
				orderDao.makeFullOrder(deliveryAddress, u, r, map, price);
				req.getSession().setAttribute("user", userDao.getUserByEmail(u.getEmail()));
				req.getSession().setAttribute("order", new Order(u, r));
				req.setAttribute("order", true);
				req.setAttribute("orderPrice", price);
				req.setAttribute("orderAddress", deliveryAddress);
			}
		} catch (SQLException | UserException e) {
			return "error";
		}
		return "main";
	}

	@RequestMapping(value = "/sortOrders", method = RequestMethod.GET)
	public String sortOrders(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		String sort = request.getParameter("sort");
		User u = (User) request.getSession().getAttribute("user");
		if (u == null) {
			return "login";
		}
		TreeSet<Order> set = new TreeSet<>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				if (sort.equals("asc")) {
					return o1.getFullDate().compareTo(o2.getFullDate());
				} else {
					return o2.getFullDate().compareTo(o1.getFullDate());
				}
			}
		});
		set.addAll(u.getOrders());
		u.setOrders(set);
		return "orders";
	}

}
