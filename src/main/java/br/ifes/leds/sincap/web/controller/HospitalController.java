/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 *
 * @author aleao
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL)
@SessionScoped
public class HospitalController {
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private UtilityWeb utilityWeb;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<InstituicaoNotificadora> listaHospitais = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listaHospitais", listaHospitais);
        return "hospital";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String adicionar(ModelMap model){
        String titulo = "hospital.cadastro";
        model.addAttribute("titulo",titulo);
        utilityWeb.preencherEstados(model);
        return "form-hospital";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvar(@ModelAttribute InstituicaoNotificadora hospital){
        aplInstituicaoNotificadora.salvar(hospital);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL;
    }
//
//    @RequestMapping(value = ContextUrls.EDITAR+"/{idCausaNaoDoacao}" ,method = RequestMethod.GET)
//    public String preencherCausaNaoDoacao(ModelMap model, @PathVariable Long idCausaNaoDoacao){
//        CausaNaoDoacao causa = aplCausaNaoDoacao.obter(idCausaNaoDoacao);
//        String titulo = "causa-nao-doacao.editar";
//        model.addAttribute("titulo",titulo);
//        model.addAttribute("listaTiposNaoDoacao", utilityWeb.getTipoNaoDoacaoSelectItem());
//        model.addAttribute("causa", causa);
//        return "form-causa-nao-doacao";
//    }
//
//    @RequestMapping(value = ContextUrls.APAGAR +"/{idCausaNaoDoacao}", method = RequestMethod.POST)
//    public String apagarNovoRegistro(ModelMap model, @PathVariable Long idCausaNaoDoacao){
//        CausaNaoDoacao causa = aplCausaNaoDoacao.obter(idCausaNaoDoacao);
//        aplCausaNaoDoacao.excluir(causa);
//        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_CAUSA_NAO_DOACAO;
//    }

}
