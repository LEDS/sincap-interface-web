package br.ifes.leds.sincap.web.model;


public class ListaMotivoInviabilidadeForm {
		
	private String nomeMotivoInviabilidade;
	private String nomeTipoMotivoInviabilidade;
	private Long id;

	 public ListaMotivoInviabilidadeForm(){
	    	
	}
	    
	public ListaMotivoInviabilidadeForm(String nomeMotivo, String nomeTipo, Long id) {
		this.nomeMotivoInviabilidade = nomeMotivo;
		this.nomeTipoMotivoInviabilidade = nomeTipo;
		this.id = id;
	}
	
	public String getNomeMotivoInviabilidade() {
		return nomeMotivoInviabilidade;
	}
	public void setNomeMotivoInviabilidade(String nomeMotivoInviabilidade) {
		this.nomeMotivoInviabilidade = nomeMotivoInviabilidade;
	}
	public String getNomeTipoMotivoInviabilidade() {
		return nomeTipoMotivoInviabilidade;
	}
	public void setNomeTipoMotivoInviabilidade(String nomeTipoMotivoInviabilidade) {
		this.nomeTipoMotivoInviabilidade = nomeTipoMotivoInviabilidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
