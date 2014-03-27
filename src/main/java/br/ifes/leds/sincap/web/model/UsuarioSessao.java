package br.ifes.leds.sincap.web.model;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import java.io.Serializable;

public class UsuarioSessao implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long idUsuario;
    private Long idHospital;
    private String cpfUsuario;
    private Notificacao notificacao;
    private int etapaNotifica;

    private String nome;

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public int getEtapaNotifica() {
        return etapaNotifica;
    }

    public void setEtapaNotifica(int etapaNotifica) {
        this.etapaNotifica = etapaNotifica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(Long idHospital) {
        this.idHospital = idHospital;
    }

}
