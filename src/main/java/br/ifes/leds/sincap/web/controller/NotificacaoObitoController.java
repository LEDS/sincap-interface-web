package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.web.utility.Utility;

/**
 *
 * @author marcosdias
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_OBITO)
@SessionScoped
public class NotificacaoObitoController {

    @Autowired
    private AplEndereco aplEndereco;
    private Utility utility = Utility.getInstance();

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovaNotificacao(ModelMap model) {
        utility.preencherEstados(model, aplEndereco);
        return "form-notificacao-obito";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR, method = RequestMethod.GET)
    public String loadAnalisaNotificacaoObito() {
        return "analise-obito";
    }

}
