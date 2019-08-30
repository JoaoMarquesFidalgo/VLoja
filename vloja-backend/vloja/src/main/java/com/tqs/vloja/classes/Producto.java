package com.tqs.vloja.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nome;
	
	private String categoria;
	
	private Double preco;
	
	private String marca;
	
	private String imagem;
	
	private String alias;
	
	private Integer idUtilizador;

	public Producto(Integer id, String nome, String categoria, Double preco, String marca, String imagem, String alias,
			Integer idUtilizador) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.preco = preco;
		this.marca = marca;
		this.imagem = imagem;
		this.alias = alias;
		this.idUtilizador = idUtilizador;
	}

	public Producto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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
	
}
