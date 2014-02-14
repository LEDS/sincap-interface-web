/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

import java.util.Date;

/**
 *
 * @author Phillipe Lopes
 */
public class NotificacaoForm {

    public String instituicao;
    public String id;
    public String uf;
    public String protocolo;
    public String nomePaciente;
    public String dataNotificacao;
    public String horaNotificacao;
    public String causaMortis1;
    public String causaMortis2;
    public String causaMortis3;
    public String causaMortis4;

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(String dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public String getHoraNotificacao() {
        return horaNotificacao;
    }

    public void setHoraNotificacao(String horaNotificacao) {
        this.horaNotificacao = horaNotificacao;
    }

    public String getCausaMortis1() {
        return causaMortis1;
    }

    public void setCausaMortis1(String causaMortis1) {
        this.causaMortis1 = causaMortis1;
    }

    public String getCausaMortis2() {
        return causaMortis2;
    }

    public void setCausaMortis2(String causaMortis2) {
        this.causaMortis2 = causaMortis2;
    }

    public String getCausaMortis3() {
        return causaMortis3;
    }

    public void setCausaMortis3(String causaMortis3) {
        this.causaMortis3 = causaMortis3;
    }

    public String getCausaMortis4() {
        return causaMortis4;
    }

    public void setCausaMortis4(String causaMortis4) {
        this.causaMortis4 = causaMortis4;
    }

}
