package com.tqs.vloja.classes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;	
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="category")
	private String category;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="image", nullable = true)
	private String image;
	
	/*
	 * Relation one to many with users
	 * various products can have only one original user
	 */
	
	@Column(name = "user_id")
    private Integer userId;
	
	/*
	 * Connect list table with favorite by making a one to many relation,
	 * one list can be in zero or more favorites
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
	private List<Favorite> favorites;
	
	/*
	 * Many to Many relation with List, solution was to create an extra table by making a 
	 * one to many relation to that table 
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductList> productList;

	public Product() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}
}
