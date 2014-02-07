package br.ifes.leds.sincap.web.model;

/**
*
* @author 20121BSI0252
*/ 
public class VisualizarSetorForm {
		
	private String nome;
	private Long id;
	
	public VisualizarSetorForm(String nome, Long id){
		this.nome = nome;
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
