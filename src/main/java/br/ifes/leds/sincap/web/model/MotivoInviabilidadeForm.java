/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

/**
 *
 * @author 20112BSI0083
 */
public class MotivoInviabilidadeForm {

    private String nome;
    private String tipoMotivoInviabilidadeId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MotivoInviabilidadeForm() {

    }

    public MotivoInviabilidadeForm(String nome, String tipoMotivoInviabilidadeId) {
        this.nome = nome;
        this.tipoMotivoInviabilidadeId = tipoMotivoInviabilidadeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoMotivoInviabilidadeId() {
        return tipoMotivoInviabilidadeId;
    }

    public void setTipoMotivoInviabilidadeId(String tipoMotivoInviabilidadeId) {
        this.tipoMotivoInviabilidadeId = tipoMotivoInviabilidadeId;
    }

}
