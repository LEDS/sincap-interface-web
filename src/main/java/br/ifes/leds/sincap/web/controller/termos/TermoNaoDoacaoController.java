package br.ifes.leds.sincap.web.controller.termos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.faces.bean.SessionScoped;

import static br.ifes.leds.sincap.web.controller.ContextUrls.*;

@Controller
@RequestMapping(RELATORIOS + APP_NOTIFICACAO_ENTREVISTA + RLT_TERMO_NAO_DOACAO)
@SessionScoped
public class TermoNaoDoacaoController extends TermoTemplate {

    @Override
    public String viewBusca() {
        return "termo-nao-doacao";
    }

    @Override
    public String viewTermo() {
        return "form-termo-nao-doacao";
    }
}
