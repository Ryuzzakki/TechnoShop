package com.store.controler;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.Part;
import com.store.model.Product;
import com.store.model.Shop;
import com.store.model.User;
import com.store.model.UserException;
import com.store.model.dao.PartDao;
import com.store.model.dao.ProductDao;
import com.store.model.dao.ShopDao;
import com.store.model.dao.UserDao;
import com.store.util.Encrypter;

@Controller
public class FacebookLoginControler {
	@Autowired
	private ShopDao shop;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private PartDao partDao;
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
							|| servletContext.getAttribute("shops") == null) {
						ArrayList<Product> products = new ArrayList<>();
						ArrayList<Part> parts = new ArrayList<>();
						ArrayList<Shop> shops = new ArrayList<>();
						products = productDao.getAllProducts();
						parts = partDao.getAllParts();
						shops = shop.getAllShops();
						servletContext.setAttribute("products", products);
						servletContext.setAttribute("parts", parts);
						servletContext.setAttribute("shops", shops);
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
