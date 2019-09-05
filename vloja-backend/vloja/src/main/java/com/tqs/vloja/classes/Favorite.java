package com.tqs.vloja.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="favorite")
public class Favorite {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private String date;
	
	private String alias;
	
	/*
	 * Relation one to many with users
	 * various favorites can have only one original user
	 */

	@Column(name = "user_id")
    private Integer userId;
	
	/*
	 * Relation many to two with products
	 * various favorites can have only one or zero product
	 */
	/*
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
	*/
	@Column(name = "product_id")
    private Integer productId;
	
	/*
	 * Relation one to many with list
	 * various favorites can have only one or zero list
	 */
	/*
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
	*/
	@Column(name = "list_id")
	private Integer listId;
	
	public Favorite() {
		super();
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

	public Integer getUser() {
		return userId;
	}

	public void setUser(Integer userId) {
		this.userId = userId;
	}

	public Integer getProduct() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getList() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getId() {
		return id;
	}
}
