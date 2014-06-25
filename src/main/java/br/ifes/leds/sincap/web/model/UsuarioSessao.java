package br.ifes.leds.sincap.web.model;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import java.io.Serializable;

/**
 *
 * @author 20102bsi0553
 */
public class UsuarioSessao implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Long idUsuario;
    private Long idHospital;
    private String cpfUsuario;
    private ProcessoNotificacao notificacao;
    private int etapaNotifica;

    private String nome;

    public ProcessoNotificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(ProcessoNotificacao notificacao) {
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
