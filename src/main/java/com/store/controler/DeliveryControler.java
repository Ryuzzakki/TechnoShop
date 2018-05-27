package com.store.controler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.store.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.model.dao.ShopDao;

@Controller
public class DeliveryControler {

	@Autowired
	ShopDao shopDao;

	@RequestMapping(value = "/homeDelivery", method = RequestMethod.POST)
	public String pickAddressForHomeDelivery(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		try {
			String homeAddress = (String) request.getParameter("homeAddress");
			Shop defaultRest = shopDao.getShop(1);
			request.getSession().setAttribute("shop", defaultRest);
			request.getSession().setAttribute("userAddress", homeAddress);
		} catch (SQLException e) {
			return "error";
		}
		return "redirect: main";
	}

	@RequestMapping(value = "/pickFromShop", method = RequestMethod.POST)
	public String getProductFromShop(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		}
		String shopId =  (String) request.getParameter("shopId");
		Long id = Long.valueOf(shopId);
		try {
			Shop shop = shopDao.getShop(id);
			request.getSession().setAttribute("shop", shop);
			request.getSession().setAttribute("userAddress", shop.getLocation());
		} catch (SQLException e) {
			return "error";
		}
		return "redirect: main";
	}

}
