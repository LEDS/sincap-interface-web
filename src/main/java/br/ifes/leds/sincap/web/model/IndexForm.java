package br.ifes.leds.sincap.web.model;

import java.util.List;

public class IndexForm {

    private List<IndexForm> listaNotificacoes;

    private String protocolo;
    private String dataNotificacao;
    private String dataObito;
    private String paciente;

    public IndexForm() {
        // TODO Auto-generated constructor stub
    }

    public IndexForm(String protocolo, String dataNotificacao, String dataObito, String paciente,
            String instituicao) {

        this.protocolo = protocolo;
        this.dataNotificacao = dataNotificacao;
        this.dataObito = dataObito;
        this.paciente = paciente;
        this.instituicao = instituicao;
    }

    public String getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(String dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public String getDataObito() {
        return dataObito;
    }

    public void setDataObito(String dataObito) {
        this.dataObito = dataObito;
    }
    private String instituicao;

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
