/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.CausaNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplCausaNaoDoacao;
import br.ifes.leds.sincap.web.utility.Utility;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author aleao
 */
@Controller
@RequestMapping(ContextUrls.APP_CAUSA_NAO_DOACAO)
@SessionScoped
public class CausaNaoDoacaoController {
    @Autowired
    private AplCausaNaoDoacao aplCausaNaoDoacao;
    Utility utilityWeb = Utility.INSTANCE;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<CausaNaoDoacao> causas = aplCausaNaoDoacao.obter();
        model.addAttribute("listaCausasNaoDoacao", causas);
        return "causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String carregarNovoForm(ModelMap model){
        model.addAttribute("listaTiposNaoDoacao", utilityWeb.getTipoNaoDoacaoSelectItem());
        return "form-causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarNovoRegistro(ModelMap model, @ModelAttribute CausaNaoDoacao causaNaoDoacao){
        aplCausaNaoDoacao.cadastrar(causaNaoDoacao);
        return "causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.EDITAR+"/{idCausaNaoDoacao}" ,method = RequestMethod.GET)
    public String preencherCausaNaoDoacao(ModelMap model, @PathVariable Long idCausaNaoDoacao){
        CausaNaoDoacao causa = aplCausaNaoDoacao.obter(idCausaNaoDoacao);
        model.addAttribute("causaNaoDoacao", causa);
        return "form-causa-nao-doacao";
    }
    
    

}
