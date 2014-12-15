package br.ifes.leds.sincap.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by breno on 15/12/14.
 */
@Getter
@Setter
public class NotificacaoDTO {

    public String protocolo;
    public String dataNotificacao;
    public String dataObito;
    public String paciente;
    public String hospital;
    public String notificador;
    public Long idProcesso;
}
