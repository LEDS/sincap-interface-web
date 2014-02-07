package br.ifes.leds.sincap.web.model;

import java.util.List;

public class IndexForm {
	
	private List<IndexForm> listaNotificacoes;
	
	private String protocolo;
	private String data;
	private String nome;
	private String instituicao;
	
	
	public IndexForm(String protocolo, String data, String nome,
			String instituicao) {
		
		this.protocolo = protocolo; 
		this.data = data;
		this.nome = nome;
		this.instituicao = instituicao;
		
	}

	public IndexForm() {
		// TODO Auto-generated constructor stub
	}

	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	
	public List<IndexForm> getListaNotificacoes() {
		return listaNotificacoes;
	}

	public void setListaNotificacoes(List<IndexForm> listaNotificacoes) {
		this.listaNotificacoes = listaNotificacoes;
	}
	
	public List<IndexForm> getListaNotificacao() {
 
		return listaNotificacoes;
 
	}

}
