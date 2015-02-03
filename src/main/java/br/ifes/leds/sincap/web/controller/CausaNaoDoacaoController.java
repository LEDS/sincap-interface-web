/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.CausaNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplCausaNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 *
 * @author aleao
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_CAUSA_NAO_DOACAO)
@SessionScoped
public class CausaNaoDoacaoController {
    @Autowired
    private AplCausaNaoDoacao aplCausaNaoDoacao;
    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private UtilityWeb utilityWeb;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<CausaNaoDoacao> causas = aplCausaNaoDoacao.obter();
        model.addAttribute("listaCausasNaoDoacao", causas);

        return "causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String carregarNovoForm(ModelMap model){
        String titulo = "causa-nao-doacao.cadastro";
        model.addAttribute("titulo",titulo);
        model.addAttribute("listaTiposNaoDoacao", utilityWeb.getTipoNaoDoacaoSelectItem());
        model.addAttribute("status",false);
        return "form-causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarNovoRegistro(ModelMap model, @ModelAttribute CausaNaoDoacao causaNaoDoacao){
        model.addAttribute("status",false);
        aplCausaNaoDoacao.cadastrar(causaNaoDoacao);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_CAUSA_NAO_DOACAO;
    }
    
    @RequestMapping(value = ContextUrls.EDITAR+"/{idCausaNaoDoacao}" ,method = RequestMethod.GET)
    public String preencherCausaNaoDoacao(ModelMap model, @PathVariable Long idCausaNaoDoacao){
        CausaNaoDoacao causa = aplCausaNaoDoacao.obter(idCausaNaoDoacao);
        String titulo = "causa-nao-doacao.editar";
        model.addAttribute("titulo",titulo);
        model.addAttribute("listaTiposNaoDoacao", utilityWeb.getTipoNaoDoacaoSelectItem());
        model.addAttribute("causa", causa);
        model.addAttribute("status",false);
        return "form-causa-nao-doacao";
    }
    
    @RequestMapping(value = ContextUrls.APAGAR +"/{idCausaNaoDoacao}", method = RequestMethod.POST)
    public String apagarNovoRegistro(ModelMap model, @PathVariable Long idCausaNaoDoacao){
        CausaNaoDoacao causa = aplCausaNaoDoacao.obter(idCausaNaoDoacao);

        Integer qtdNotificacoes = aplProcessoNotificacao.quantidadeCausaNaoDoacao(causa.getId());

        if(qtdNotificacoes == 0) {
            aplCausaNaoDoacao.excluir(causa);
            List<CausaNaoDoacao> causas = aplCausaNaoDoacao.obter();
            model.addAttribute("listaCausasNaoDoacao", causas);
        } else {
            List<CausaNaoDoacao> causas = aplCausaNaoDoacao.obter();
            model.addAttribute("listaCausasNaoDoacao", causas);
            model.addAttribute("status",true);
            model.addAttribute("mensagem","A causa não pode ser exlcuída pois existem notificações vinculadas.");
        }
        return "causa-nao-doacao";
    }

}
