package com.store.controler;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.Ingredient;
import com.store.model.Product;
import com.store.model.Restaurant;
import com.store.model.User;
import com.store.model.UserException;
import com.store.model.dao.IngredientDao;
import com.store.model.dao.ProductDao;
import com.store.model.dao.RestaurantDao;
import com.store.model.dao.UserDao;
import com.store.util.Encrypter;

@Controller
public class FacebookLoginControler {
	@Autowired
	private RestaurantDao restaurant;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private IngredientDao ingredientDao;
	@Autowired
	UserDao userDao;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	Encrypter encrypter;

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public String loginWithFacebook(HttpServletRequest request) {

		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String firstName = "";
		String lastName = "";

		if (name != null) {
			String[] names = name.split("_");
			firstName = names[0];
			lastName = names[names.length - 1];
		}

		try {
			if (userDao.userExistsByEmail(email)) {
				request.getSession().setAttribute("logged", true);
				request.getSession().setAttribute("user", userDao.getUserByEmail(email));
				synchronized (servletContext) {
					if (servletContext.getAttribute("products") == null
							|| servletContext.getAttribute("restaurants") == null) {
						ArrayList<Product> products = new ArrayList<>();
						ArrayList<Ingredient> ingredients = new ArrayList<>();
						ArrayList<Restaurant> restaurants = new ArrayList<>();
						products = productDao.getAllProducts();
						ingredients = ingredientDao.getAllIngredients();
						restaurants = restaurant.getAllRestaurants();
						servletContext.setAttribute("products", products);
						servletContext.setAttribute("ingredients", ingredients);
						servletContext.setAttribute("restaurants", restaurants);
					}
				}
				return "address";
			} else {
				return "redirect:facereg?firstName=" + firstName + "&lastName=" + lastName + "&email=" + email;
			}
		} catch (SQLException | UserException e) {
			return "error";
		}
	}

	@RequestMapping(value = "/facebookregister", method = RequestMethod.POST)
	public String registerWithFacebook(HttpServletRequest request) {

		String name = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String enteredPass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");
		String pass1 = encrypter.encrypt(enteredPass);

		if (enteredPass.equals(pass2) && enteredPass.length() >= 6) {
			try {
				User u = new User(name, lastName, pass1, phone, email);
				userDao.addUser(u);
				request.setAttribute("registered", true);
				return "redirect:facebook?firstName=" + name + "&lastName=" + lastName + "&user_email=" + email;
			} catch (SQLException e) {
				return "error";
			}
		}
		return "redirect:facereg?firstName=" + name + "&lastName=" + lastName + "&email=" + email;

	}

	@RequestMapping(value = "/facereg", method = RequestMethod.GET)
	public String goToFaceRegisterPage(HttpServletRequest request) {
		return "facereg";
	}

}
