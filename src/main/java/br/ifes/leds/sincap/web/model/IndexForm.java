package br.ifes.leds.sincap.web.model;

import java.util.Date;
import java.util.List;

public class IndexForm {

    private List<IndexForm> listaNotificacoes;

    private String idNotificacao;
    private String protocolo;
    private Date dataNotificacao;
    private Date dataObito;
    private String paciente;
    private String instituicao;

    public IndexForm() {
        // TODO Auto-generated constructor stub
    }

    public IndexForm(String idNotificacao, String protocolo, Date dataNotificacao, Date dataObito, String paciente,
            String instituicao) {
        this.idNotificacao = idNotificacao;
        this.protocolo = protocolo;
        this.dataNotificacao = dataNotificacao;
        this.dataObito = dataObito;
        this.paciente = paciente;
        this.instituicao = instituicao;
    }
    
    public String getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(String idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public Date getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(Date dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public Date getDataObito() {
        return dataObito;
    }

    public void setDataObito(Date dataObito) {
        this.dataObito = dataObito;
    }

    
    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
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
