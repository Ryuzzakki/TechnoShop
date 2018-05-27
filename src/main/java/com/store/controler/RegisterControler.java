package com.store.controler;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.store.model.User;
import com.store.model.UserException;
import com.store.model.dao.UserDao;
import com.store.util.Encrypter;

@Controller
@RequestMapping(value = "/register")
@SessionAttributes("user")
public class RegisterControler {

	@Autowired
	private UserDao userDao;
	private Validator validator;

	@Autowired
	private Encrypter encrypter;

	public RegisterControler() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		User user = new User();
		model.addAttribute("userNew", user);
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("userNew") User user, BindingResult result, SessionStatus status,
			HttpServletRequest request) {
		String confirm = request.getParameter("confirmPassword");
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for (ConstraintViolation<User> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			result.addError(new FieldError("user", propertyPath, message));
		}
		if (result.hasErrors()) {
			return "register";
		}
		if (confirm.equals(user.getPassword())) {
			try {
				if (!userDao.userExistsByEmail(user.getEmail())) {
					user.setPassword(encrypter.encrypt(user.getPassword()));
					userDao.addUser(user);
					request.setAttribute("userRegistered", true);
					return "login";
				} else {
					request.setAttribute("userExists", true);
					return "register";
				}

			} catch (SQLException e) {
				return "error";
			}
		} else {
			return "register";
		}
	}
}
