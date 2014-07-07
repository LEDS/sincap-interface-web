
package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20102bsi0553
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_CAPTACAO)
@SessionScoped
public class NotificacaoCaptacaoController {
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormCaptacap(ModelMap model) {
        return "form-notificacao-captacao";
    }
    
}
