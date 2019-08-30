package com.tqs.vloja.classes;

public class ApiResponse {
	
	private Boolean erro;
	private Integer codigo;
	private String descricao;
	private Object dados;
	public ApiResponse(Boolean erro, Integer codigo, String descricao, Object dados) {
		super();
		this.erro = erro;
		this.codigo = codigo;
		this.descricao = descricao;
		this.dados = dados;
	}
	public ApiResponse(Boolean erro, Integer codigo, String descricao) {
		super();
		this.erro = erro;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	public ApiResponse() {
		super();
	}
	public Boolean getErro() {
		return erro;
	}
	public void setErro(Boolean erro) {
		this.erro = erro;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Object getDados() {
		return dados;
	}
	public void setDados(Object dados) {
		this.dados = dados;
	}
	
}
