package com.store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

	private long id;
	@Size(min = 2, max = 20, message = "Invalid first name!")
	@Pattern(regexp = "[a-zA-Z]+", message = "Use letters only!")
	private String firstName;
	@Size(min = 2, max = 20, message = "Invalid last name!")
	@Pattern(regexp = "[a-zA-Z]+", message = "Use letters only!")
	private String lastName;
	@Size(min = 6, max = 50, message = "Invalid password! Use 6 or more symbols!")
	private String password;
	@Pattern(regexp = "[0-9]+", message = "Invalid phone number!")
	private String phoneNumber;
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Invalid 	e-mail!")
	private String email;
	private String avatarUrl;
	private HashSet<String> address = new LinkedHashSet<>();
	private Set<Order> orders;
	private ArrayList<Product> favorites = new ArrayList<>();

	public User() {
	};

	public User(String firstname, String lastname, String password, String phonenumber, String email) {
		setFirstName(firstname);
		setLastName(lastname);
		setPassword(password);
		setPhoneNumber(phonenumber);
		setEmail(email);
	}

	public User(long id, String firstname, String lastname, String password, String phonenumber, String email) {
		this(firstname, lastname, password, phonenumber, email);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public HashSet<String> getAddress() {
		return address;
	}

	public Set<Order> getOrders() {
		return Collections.unmodifiableSet(orders);
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public void setAddresses(HashSet<String> addresses) {
		this.address = addresses;
	}

	public void setId(long id) {
		this.id = id;
	}
	public ArrayList<Product> getFavorites() {
		return favorites;
	}
	public void setFavorites(ArrayList<Product> favorites) {
		this.favorites = favorites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phonenumber) {
		this.phoneNumber = phonenumber;
	}

}
