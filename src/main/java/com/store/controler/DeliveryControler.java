package com.store.controler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.Restaurant;
import com.store.model.dao.RestaurantDao;

@Controller
public class DeliveryControler {

	@Autowired
	RestaurantDao restaurantDao;

	@RequestMapping(value = "/homeDelivery", method = RequestMethod.POST)
	public String pickAddressForHomeDelivery(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		try {
			String homeAddress = (String) request.getParameter("homeAddress");
			Restaurant defaultRest = restaurantDao.getRestaurant(1);
			request.getSession().setAttribute("restaurant", defaultRest);
			request.getSession().setAttribute("userAddress", homeAddress);
		} catch (SQLException e) {
			return "error";
		}
		return "redirect: main";
	}

	@RequestMapping(value = "/pickFromRestaurant", method = RequestMethod.POST)
	public String getProductFromRestaurant(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		String restaurantId =  (String) request.getParameter("restaurantId");
		Long id = Long.valueOf(restaurantId);
		try {
			Restaurant restaurant = restaurantDao.getRestaurant(id);
			request.getSession().setAttribute("restaurant", restaurant);
			request.getSession().setAttribute("userAddress", restaurant.getLocation());
		} catch (SQLException e) {
			return "error";
		}
		return "redirect: main";
	}

}
