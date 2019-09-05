package com.tqs.vloja.classes;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="email", unique=true, nullable = false)
	private String email;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="name", nullable = true)
	private String name;
	
	@Column(name="language", nullable = true)
	private String language;
	
	@Column(name="Image", nullable = true)
	private String Image;
	
	/*
	 * Connect product table with user by making a one to many relation,
	 * one user can have multiple products
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Product> products;

	/*
	 * Connect list table with user by making a one to many relation,
	 * one user can have multiple lists
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<List_> lists;
    
    /*
	 * Connect list table with user by making a one to many relation,
	 * one user can have multiple favorites
	 */
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	private List<Favorite> favorites;
    
	public User() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List_> getLists() {
		return lists;
	}

	public void setLists(List<List_> lists) {
		this.lists = lists;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public Integer getUserId() {
		return id;
	}	
}
