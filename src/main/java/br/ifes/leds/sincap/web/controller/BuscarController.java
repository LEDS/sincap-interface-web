package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by marcosdias on 26/09/14.
 */
@Controller
@RequestMapping(ContextUrls.APP_BUSCAR)
@SessionScoped
public class BuscarController {
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;

    @RequestMapping(value = ContextUrls.BUSCAR_TODOS, method = RequestMethod.GET)
    public String carregaTodos(ModelMap model) {
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterTodasNotificacoes();
        model.addAttribute("listProcessoNotificacao", pn);

        return "buscar-notificacao";
    }

    @RequestMapping(value = ContextUrls.BUSCAR_TODOS, method = RequestMethod.POST)
    public String buscarTodos(ModelMap model, @RequestParam(value = "buscar", defaultValue = "") String search) {
        List<ProcessoNotificacao> pn = null;

        if (search.trim().isEmpty()) {
            pn = aplProcessoNotificacao.obterTodasNotificacoes();
        } else {
            pn = aplProcessoNotificacao.obterTodasNotificacoes(search);
        }

        model.addAttribute("listProcessoNotificacao", pn);
        model.addAttribute("search", search);
        return "buscar-notificacao";
    }
}
