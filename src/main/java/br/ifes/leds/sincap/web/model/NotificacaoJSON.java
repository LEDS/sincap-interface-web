package br.ifes.leds.sincap.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by breno on 15/12/14.
 */
@Getter
@Setter
public class NotificacaoJSON {

    private List<NotificacaoDTO> data = new ArrayList<NotificacaoDTO>();
}
