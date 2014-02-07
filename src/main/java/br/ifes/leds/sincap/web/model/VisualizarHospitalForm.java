/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

/**
 *
 * @author 20121BSI0252
 */
public class VisualizarHospitalForm {

    private String nome;
    private Long id;
    
    public VisualizarHospitalForm(String nome, Long id){
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
