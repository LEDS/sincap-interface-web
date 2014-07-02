package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author marcosdias
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_OBITO)
@SessionScoped
public class NotificacaoObitoController {

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovaNotificacao() {
        return "form-notificacao-obito";
    }
    
    @RequestMapping(value = ContextUrls.APP_ANALISAR, method = RequestMethod.GET)
    public String loadAnalisaNotificacaoObito() {
        return "analise-obito";
    }

}
