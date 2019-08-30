package com.tqs.vloja.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Favorito {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String data;
	
	private String alias;
	
	private Integer idUtilizador;
	
	private Integer idProducto;
	
	private Integer idLista;

	public Favorito(Integer id, String data, String alias, Integer idUtilizador, Integer idProducto, Integer idLista) {
		super();
		this.id = id;
		this.data = data;
		this.alias = alias;
		this.idUtilizador = idUtilizador;
		this.idProducto = idProducto;
		this.idLista = idLista;
	}

	public Favorito() {
		super();
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

	public Integer getIdLista() {
		return idLista;
	}

	public void setIdLista(Integer idLista) {
		this.idLista = idLista;
	}

	public Integer getId() {
		return id;
	}
	
	
	
}
