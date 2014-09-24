/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
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
    private AplHospital aplHospital;
    @Autowired
    private AplBancoOlhos aplBancoOlhos;
    @Autowired
    private UtilityWeb utilityWeb;
    @Autowired
    private Utility utility;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Hospital> listaHospitais = aplHospital.obter();
        model.addAttribute("listaHospitais", listaHospitais);
        return "hospital";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String adicionar(ModelMap model){
        String titulo = "hospital.cadastro";
        List<BancoOlhos> listaBancoOlhos = aplBancoOlhos.obter();
        model.addAttribute("listaBancoOlhos",utility.mapList(listaBancoOlhos, SelectItem.class));

        model.addAttribute("titulo",titulo);
        utilityWeb.preencherEstados(model);
        return "form-hospital";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvar(@ModelAttribute Hospital hospital){
        aplHospital.cadastrar(hospital);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL;
    }

}
