package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.AnalistaCNCDO;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplAnalistaCNCDO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplNotificador;
import br.ifes.leds.sincap.web.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by aleao on 25/08/14.
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO)
@SessionScoped
public class FuncionarioController {
    @Autowired
    private AplAnalistaCNCDO aplAnalistaCNCDO;
    @Autowired
    private AplNotificador aplNotificador;

    private AplCaptador aplCaptador;

    Utility utilityWeb = Utility.INSTANCE;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<AnalistaCNCDO> listaAnalista = aplAnalistaCNCDO.obter();
        List<Notificador> listaNotificador = aplNotificador.obterTodosNotificadores();
        List<Captador> listaCaptador = aplCaptador.obter();

        model.addAttribute("listaAnalista", listaAnalista);
        model.addAttribute("listaNotificador", listaNotificador);
        model.addAttribute("listaCaptador", listaCaptador);

        return "funcionario-index";
    }

}
