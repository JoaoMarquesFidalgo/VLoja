package com.tqs.vloja.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_list")
public class ProductList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "product_id")
    private Integer productId;

	@Column(name = "list_id")
    private Integer listId;
    
    @Column(name = "was_bought")
    private Boolean wasBought;

	public ProductList() {
		super();
	}

	public Integer getProduct() {
		return productId;
	}

	public void setProduct(Integer productId) {
		this.productId = productId;
	}

	public Integer getList() {
		return listId;
	}

	public void setList(Integer listId) {
		this.listId = listId;
	}

	public Boolean getWasBought() {
		return wasBought;
	}

	public void setWasBought(Boolean wasBought) {
		this.wasBought = wasBought;
	}

	public Integer getProductListId() {
		return id;
	}
}
