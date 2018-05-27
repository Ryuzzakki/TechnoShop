package com.store.controler;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.store.model.Ingredient;
import com.store.model.Order;
import com.store.model.Product;
import com.store.model.Restaurant;
import com.store.model.User;
import com.store.model.dao.ProductDao;

@Controller
public class ProductControler {

	@Autowired
	ProductDao productDao;

	public static final String PICTURE_URL = "C:/upload/products/";
	//public static final String PICTURE_URL = "D:/upload/products/";

	@RequestMapping(value = "/productPic", method = RequestMethod.GET)
	public void productPictures(HttpServletRequest req, HttpServletResponse resp) {
		int currentProduct = Integer.parseInt(req.getParameter("currentProductId"));
		String pictureUrl = null;

		try {
			pictureUrl = productDao.getProduct(Long.valueOf(currentProduct)).getProductPicture();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (pictureUrl == null || pictureUrl.isEmpty()) {
			pictureUrl = "defaultPizza.png";
		}
		try {
			File myFile = new File(PICTURE_URL + pictureUrl);
			OutputStream out = resp.getOutputStream();
			Path path = myFile.toPath();
			Files.copy(path, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String addProductToCart(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		User currentUser = (User) request.getSession().getAttribute("user");
		Restaurant currentRestaurant = (Restaurant) request.getSession().getAttribute("restaurant");
		if (request.getSession().getAttribute("order") == null) {
			if (currentRestaurant == null) {
				return "address";
			}
			Order order = new Order(currentUser, currentRestaurant);
			request.getSession().setAttribute("order", order);
		}
		String id = request.getParameter("productId");
		try {
			Order order = (Order) request.getSession().getAttribute("order");
			Product p = productDao.getProduct(Long.parseLong(id));

			HashSet<Ingredient> defaultIngredients = new HashSet<>();
			defaultIngredients.addAll(p.getIngredients());
			p.setDefIngredients(defaultIngredients);

			order.addToProducts(p);
		} catch (NumberFormatException | SQLException e) {
			return "error";
		}
		request.setAttribute("added", true);
		return "redirect: cart";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String increaseProduct(HttpServletRequest request) {
		if (request.getSession().getAttribute("order") == null) {
			return "login";
		}
		String id = request.getParameter("productId");
		try {
			Order order = (Order) request.getSession().getAttribute("order");
			Product p = order.findProductInMap(Long.valueOf(id));
			int value = order.getProducts().get(p);
			order.getProducts().put(p, value + 1);
		} catch (NumberFormatException e) {
			return "error";
		}
		request.setAttribute("added", true);
		return "redirect: cart";
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String showProductInCart(HttpServletRequest req, HttpServletResponse resp) {
		if (req.getSession().getAttribute("order") == null) {
			return "redirect: pizzas";
		}
		Order order = (Order) req.getSession().getAttribute("order");
		if (req.getSession().getAttribute("user") == null) {
			return "main";
		}
		if (order.getProducts().size() == 0) {
			return "redirect: pizzas";
		}
		HashMap<Product, Integer> map = order.getProducts();
		double totalPrice = order.calculateTotalPrice();
		double totalPriceWithDiscount = order.discount(totalPrice);
		req.getSession().setAttribute("totalPrice", totalPrice);
		req.getSession().setAttribute("totalPriceWithDiscount", totalPriceWithDiscount);
		req.getSession().setAttribute("productsInCart", map);
		return "mycart";

	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removeProductFromCart(HttpServletRequest request) {
		if (request.getSession().getAttribute("order") == null) {
			return "login";
		}
		String productId = request.getParameter("productId");
		int valueToBeRemoved = 1;

		Order order = (Order) request.getSession().getAttribute("order");
		order.removeProductFromOrder(Long.valueOf(productId), valueToBeRemoved);
		double totalPrice = order.calculateTotalPrice();
		double totalPriceWithDiscount = order.discount(totalPrice);
		request.getSession().setAttribute("totalPriceWithDiscount", totalPriceWithDiscount);
		request.getSession().setAttribute("totalPrice", totalPrice);
		return "mycart";
	}

	@RequestMapping(value = "/makeFav", method = RequestMethod.POST)
	public String addToFavorites(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "main";
		}
		String proId = request.getParameter("productId");
		Long productId = Long.valueOf(proId);
		User u = (User) request.getSession().getAttribute("user");

		try {
			Product favoriteProduct = productDao.getProduct(productId);
			for (Product p : u.getFavorites()) {
				if (p.getId() == favoriteProduct.getId()) {
					request.setAttribute("hasFavorite", true);
					return "categories";
				}
			}
			productDao.addFavoriteProduct(u.getId(), productId);
			u.getFavorites().add(favoriteProduct);
		} catch (NumberFormatException | SQLException e) {
			return "error";
		}
		request.setAttribute("favorite", true);
		return "categories";
	}

	@RequestMapping(value = "/removeFav", method = RequestMethod.POST)
	public String removeFavoriteProduct(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "main";
		}

		User user = (User) request.getSession().getAttribute("user");
		String id = request.getParameter("productId");
		Long productId = Long.valueOf(id);
		try {
			productDao.removeFavoriteProduct(productId, user.getId());
		} catch (SQLException e) {
			return "error";
		}
		for (int i = 0; i < user.getFavorites().size(); i++) {
			if (user.getFavorites().get(i).getId() == productId) {
				user.getFavorites().remove(i);
			}
		}
		return "favorites";
	}
}
