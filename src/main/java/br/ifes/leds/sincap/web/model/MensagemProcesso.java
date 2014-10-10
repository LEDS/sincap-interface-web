package br.ifes.leds.sincap.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by marcosdias on 03/10/14.
 */
@Getter
@Setter
public class MensagemProcesso {
    private Long id;
    private String tempo;
    private String estado;
    private String codigo;
    private String urlRelativa;
}
