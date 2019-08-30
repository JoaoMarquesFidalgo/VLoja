package com.tqs.vloja.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String categoria;
	
	private String data;
	
	private String alias;
	
	private Integer idUtilizador;
	
	private Integer idProducto;
	
	private Boolean productoComprado;
	
	public Lista() {
		super();
	}

	public Lista(Integer id, String categoria, String data, String alias, Integer idUtilizador, Integer idProducto,
			boolean productoComprado) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.data = data;
		this.alias = alias;
		this.idUtilizador = idUtilizador;
		this.idProducto = idProducto;
		this.productoComprado = productoComprado;
	}

	public Integer getId() {
		return id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getIdUtilizador() {
		return idUtilizador;
	}

	public void setIdUtilizador(Integer idUtilizador) {
		this.idUtilizador = idUtilizador;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Boolean isProductoComprado() {
		return productoComprado;
	}

	public void setProductoComprado(Boolean productoComprado) {
		this.productoComprado = productoComprado;
	}
	
}
