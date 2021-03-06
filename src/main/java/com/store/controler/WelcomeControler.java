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
import com.store.model.dao.PartDao;
import com.store.model.dao.ProductDao;


@Controller
public class WelcomeControler {
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private PartDao partDao;

	
	@RequestMapping(value = "*", method = RequestMethod.GET)
	public String welcome() {
		return "welcome";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goBack() {
		return "login";

	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String goToMain() {
		
		synchronized (servletContext) {
			if (servletContext.getAttribute("products") == null) {
				ArrayList<Product> products = new ArrayList<>();
				ArrayList<Part> parts = new ArrayList<>();
				
				try {
					products = productDao.getAllProducts();
					parts = partDao.getAllParts();
					for (Product p : products) {
						System.out.println(p);
						
					}
				} catch (SQLException e) {
					System.out.println(e);
					return "error";
				}
				servletContext.setAttribute("products", products);
				servletContext.setAttribute("parts", parts);
			}
		}
		
		return "redirect:computers";

	}
	

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String goToOrders() {
		return "orders";

	}
	
	@RequestMapping(value = "/favorites", method = RequestMethod.GET)
	public String goToFavorites() {
		return "favorites";

	}
	
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String goToChooseAddress() {
		return "address";

	}


	@RequestMapping(value = "/computers", method = RequestMethod.GET)
	public String goToMenuWithComputers(HttpServletRequest req) {
		if (servletContext.getAttribute("products") == null) {
			return "redirect:main";
		}
		req.setAttribute("category", 1);
		return "categories";

	}
	@RequestMapping(value = "/phones", method = RequestMethod.GET)
	public String goToMenuWithPhones(HttpServletRequest req) {
		if (servletContext.getAttribute("products") == null) {
			return "redirect:main";
		}
		req.setAttribute("category", 0);
		return "categories";

	}
	@RequestMapping(value = "/others", method = RequestMethod.GET)
	public String goToMenuWithOthers(HttpServletRequest req) {
		if (servletContext.getAttribute("products") == null) {
			return "redirect:main";
		}
		req.setAttribute("category", 2);
		return "categories";

	}
		
}
