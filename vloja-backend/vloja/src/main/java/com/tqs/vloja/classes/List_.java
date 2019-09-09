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

@Entity
@Table(name="list")
public class List_ {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="category")
	private String category;
	
	@Column(name="date")
	private String date;
	
	@Column(name="alias")
	private String alias;
	
	/*
	 * Relation one to many with users
	 * various lists can have only one original user
	 */
	
	@Column(name = "user_id")
    private Integer userId;
	
	/*
	 * Connect list table with favorite by making a one to many relation,
	 * one list can be in zero or more favorites
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "list_id")
	private List<Favorite> favorites;
	
	/*
	 * Many to Many relation with ProductList, solution was to create an extra table by making a 
	 * one to many relation to that table 
	 */
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "list_id")
    private List<ProductList> productList;
	
	public List_() {
		super();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public Integer getId() {
		return id;
	}

	public List<ProductList> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductList> productList) {
		this.productList = productList;
	}
}
