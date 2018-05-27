package com.store.controler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.store.model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.store.model.Order;
import com.store.model.Product;
import com.store.model.dao.PartDao;
import com.store.model.dao.ProductDao;

@Controller
public class PartControler {

	@Autowired
	PartDao partDao;
	@Autowired
	ProductDao productDao;

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String addIngredient(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (request.getSession().getAttribute("order") == null) {
			return "login";
		}
		String ingredientid = request.getParameter("ingredientId");
		String currentId = request.getParameter("productId");
		Order order = (Order) request.getSession().getAttribute("order");
		Long id = Long.valueOf(currentId);
		Part part = null;

		try {
			Product product = order.findProductInMap(id);
			if (request.getSession().getAttribute("modifiedProduct" + currentId) == null) {
				request.getSession().setAttribute("modifiedProduct" + currentId, product);
			}
			if (ingredientid != null) {
				part = partDao.getIngredient(Long.valueOf(ingredientid));
				product = (Product) request.getSession().getAttribute("modifiedProduct" + currentId);
				product.addIngredient(part);
			}
		} catch (SQLException e) {
			return "error";
		}

		request.setAttribute("currentId", currentId);
		return "redirect:modify?productId=" + currentId;
	}

	@RequestMapping(value = "/removeing", method = RequestMethod.POST)
	public String removeIngredient(HttpServletRequest request) {
		if (request.getSession().getAttribute("order") == null) {
			return "login";
		}
		String product = request.getParameter("productId");
		String ingredient = request.getParameter("ingredientId");
		Order order = (Order) request.getSession().getAttribute("order");
		try {
			Product currentProduct = order.findProductInMap(Long.valueOf(product));
			Part currentPart = partDao.getIngredient(Long.valueOf(ingredient));
			currentProduct.removeIngredient(currentPart);
		} catch (NumberFormatException | SQLException e) {
			return "error";
		}
		return "redirect:modify?productId=" + product;
	}

	@RequestMapping(value = "/chooser", method = RequestMethod.POST)
	public String pizzaFormChooser(HttpServletRequest request) {
		if (request.getSession().getAttribute("order") == null) {
			return "login";
		}
		String currentId = request.getParameter("productId");
		String size = request.getParameter("size");
		String dough = request.getParameter("dough");
		try {
			Product product = productDao.getProduct(Long.valueOf(currentId));
			if (request.getSession().getAttribute("modifiedProduct" + currentId) == null) {
				request.getSession().setAttribute("modifiedProduct" + currentId, product);
			}
		} catch (SQLException e) {
			return "error";
		}
		Product product = (Product) request.getSession().getAttribute("modifiedProduct" + currentId);
		product.setSize(size);
		product.setDough(dough);
		return "redirect: cart";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String getToModify() {
		return "modify";
	}

}