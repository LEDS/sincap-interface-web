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
public class MotivoRecusaForm {
    private String tipoMotivoRecusaId;
    private String id;
    private String tipoMotivoRecusa;
    private String nome;

    public String getTipoMotivoRecusaId() {
        return tipoMotivoRecusaId;
    }

    public void setTipoMotivoRecusaId(String tipoMotivoRecusaId) {
        this.tipoMotivoRecusaId = tipoMotivoRecusaId;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoMotivoRecusa() {
        return tipoMotivoRecusa;
    }

    public void setTipoMotivoRecusa(String tipoMotivoRecusa) {
        this.tipoMotivoRecusa = tipoMotivoRecusa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
