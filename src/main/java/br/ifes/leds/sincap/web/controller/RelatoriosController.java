package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.bean.SessionScoped;

/**
 * Created by Home on 18/09/2014.
 */
@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatoriosController {
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;

    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA+"/termodedoacao"+"/{cartaosus}", method = RequestMethod.GET)
    public String emitirTermoDoacao(ModelMap model, @PathVariable String cartaoSus){
        ProcessoNotificacao pn = aplProcessoNotificacao.obterPorNumeroSus(cartaoSus);

        model.addAttribute("processoNotificacao", pn);
        //TODO: Substituir pelo endereco do formulario!
        return "termodedoacao";
    }
}
