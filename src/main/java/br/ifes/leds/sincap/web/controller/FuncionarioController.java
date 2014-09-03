package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    Utility utility = Utility.INSTANCE;
    @Autowired
    private AplAnalistaCNCDO aplAnalistaCNCDO;
    @Autowired
    private AplNotificador aplNotificador;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplEndereco aplEndereco;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<AnalistaCNCDO> listaAnalista = aplAnalistaCNCDO.obter();
        List<Notificador> listaNotificador = aplNotificador.obterTodosNotificadores();
        List<Captador> listaCaptador = aplCaptador.obter();

        model.addAttribute("analistas", listaAnalista);
        model.addAttribute("notificadores", listaNotificador);
        model.addAttribute("captadores", listaCaptador);

        return "funcionario-index";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_ANALISTA, method = RequestMethod.GET)
    public String cadastrarAnalista(ModelMap model) {
        String titulo = "funcionario.cadastro.analista";
        utility.preencherEstados(model, aplEndereco);
        model.addAttribute("titulo", titulo);
        return "form-cadastro-analista";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_ANALISTA, method = RequestMethod.POST)
    public String salvarAnalista(ModelMap model, @ModelAttribute AnalistaCNCDO analistaCNCDO) {
        aplAnalistaCNCDO.salvar(analistaCNCDO);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_NOTIFICADOR, method = RequestMethod.GET)
    public String cadastrarNotificador(ModelMap model) {
        String titulo = "funcionario.cadastro.notificador";
        model.addAttribute("titulo", titulo);
        return "form-cadastro-notificador";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_NOTIFICADOR, method = RequestMethod.POST)
    public String salvarNotificador(ModelMap model, @ModelAttribute Notificador notificador) {
        aplNotificador.salvarNotificador(notificador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_CAPTADOR, method = RequestMethod.GET)
    public String cadastrarCaptador(ModelMap model) {
        String titulo = "funcionario.cadastro.captador";
        model.addAttribute("titulo", titulo);
        return "form-cadastro-captador";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_CAPTADOR, method = RequestMethod.POST)
    public String salvarCaptador(ModelMap model, @ModelAttribute Captador captador) {
        aplCaptador.salvar(captador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }
}
