package com.store.controler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.store.WebInitializer;
import com.store.model.Restaurant;
import com.store.model.User;
import com.store.model.UserException;
import com.store.model.dao.RestaurantDao;
import com.store.model.dao.UserDao;
import com.store.util.Encrypter;

@Controller
@MultipartConfig
public class UserControler {

	public static final String AVATAR_URL = "C:/upload/users/";
	//	public static final String AVATAR_URL = "D:/upload/users/";
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Encrypter encrypter;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {

		String email = request.getParameter("email");
		String enteredPass = request.getParameter("pass");
		String pass = encrypter.encrypt(enteredPass);
		
		synchronized (servletContext) {
			if (servletContext.getAttribute("restaurants")==null) {
				ArrayList<Restaurant> restaurants = new ArrayList<>();
				try {
					restaurants = restaurantDao.getAllRestaurants();
				} catch (SQLException e) {
					return "error";
				}
				servletContext.setAttribute("restaurants", restaurants)	;
			}
		}
		try {
			if (userDao.userExists(email, pass)) {
				request.getSession().setAttribute("logged", true);
				request.getSession().setAttribute("user", userDao.getUserByEmail(email));
				return "address";
			} else {
				request.setAttribute("userNotExists", true);
				return "login";
			}
		} catch (SQLException | UserException e) {
			System.out.println(e);
			return "error";
		}
	}

	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public String setAddressForOrder(HttpServletRequest request) {
		String choice = request.getParameter("address");
		request.getSession().setAttribute("address", choice);
		if (choice == null || choice.isEmpty()) {
			return "address";
		}
		if (choice.equals("restaurant")) {
			return "restaurantmap";
		}
		if (choice.equals("home")) {
			return "useraddresses";
		}
		return "address";

	}

	@RequestMapping(value = "/addressprofile", method = RequestMethod.POST)
	public String addAddressToUser(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null) {
			return "login";
		}
		String streetNumber = req.getParameter("streetNumber");
		String street = req.getParameter("street");
		String newStreet = street.replace("\"", " ");
		String city = req.getParameter("city");

		StringBuilder newAddress = new StringBuilder();
		newAddress.append("No:" + streetNumber + ", ");
		newAddress.append(newStreet + ", ");
		newAddress.append(city);

		if (newAddress.length() <= 10) {
			req.setAttribute("invalidAddress", true);
			return "addnewaddress";
		}
		User u = (User) req.getSession().getAttribute("user");
		try {
			String addedAddres = String.valueOf(newAddress);
			userDao.addAddressForUser(u.getId(), addedAddres);
			u.getAddress().add(addedAddres);
			return "profileaddress";
		} catch (SQLException e) {
			return "error";
			
		}
	}

	@RequestMapping(value = "/addressprofile", method = RequestMethod.GET)
	public String removeAddressFromUser(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null) {
			return "login";
		}
		String removeAddress = req.getParameter("address");
		User u = (User) req.getSession().getAttribute("user");
		HashSet<String> addresses = new HashSet<>();
		try {
			addresses.addAll(userDao.getAllAdrress(u.getId()));
		} catch (SQLException e) {
			return "error";
		}
		if (addresses.contains(removeAddress)) {
			u.getAddress().remove(removeAddress);
			try {
				userDao.removeAddressForUser(removeAddress);
			} catch (SQLException e) {
				return "error";
			}
		}

		return "profileaddress";
	}

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public void showAvatarServlet(HttpServletRequest req, HttpServletResponse resp) {
		User u = (User) req.getSession().getAttribute("user");
		String avatar = u.getAvatarUrl();
		if (avatar == null) {
			avatar = "default.jpeg";
		}
		try {
			File myFile = new File(AVATAR_URL + avatar);
			OutputStream out = resp.getOutputStream();
			Path path = myFile.toPath();
			Files.copy(path, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public String uploadAvatar(HttpServletRequest request, @RequestParam("avatar") MultipartFile file) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		User user = (User) request.getSession().getAttribute("user");
		String avatarUrl = user.getEmail() + ".jpg";
		String fileType = (file.getContentType()).split("/")[0];
		try {
			if (file.isEmpty() || !fileType.equals("image")) {
				return "myprofile";
			}
			File f = new File(WebInitializer.LOCATION + File.separator + avatarUrl);
			file.transferTo(f);
			userDao.insertAvatar(user.getEmail(), avatarUrl);
			request.getSession().setAttribute("avatar", avatarUrl);
			user.setAvatarUrl(avatarUrl);
		} catch (IOException| SQLException e) {
			return "error";
		}
		return "myprofile";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "login";
	}

	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public String goToMyProfile() {
		return "myprofile";
	}

	@RequestMapping(value = "/profileaddress", method = RequestMethod.GET)
	public String goToMyAddresses() {
		return "profileaddress";
	}

	@RequestMapping(value = "/addnewaddress", method = RequestMethod.GET)
	public String goToAddNewAddress() {
		return "addnewaddress";
	}

	@RequestMapping(value = "/address/map", method = RequestMethod.GET)
	public String restaurantMap() {
		return "restaurantmap";
	}

}
