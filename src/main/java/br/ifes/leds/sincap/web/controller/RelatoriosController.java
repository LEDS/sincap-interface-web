/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.view.InstituicaoNotificadoraDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.web.model.BuscarPorDataForm;
import br.ifes.leds.sincap.web.model.DataForm;

import java.util.List;
import javax.faces.bean.SessionScoped;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author leds-6752
 */
@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatoriosController {

    @Autowired
    AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        BuscarPorDataForm buscarPorDataForm = new BuscarPorDataForm();
        model.addAttribute("buscarPorDataForm", buscarPorDataForm);

        return "form-relatorios";
    }

    @RequestMapping(ContextUrls.RLT_DOACAO_HOSPITAL)
    public String relatorioDoacaoXHospital(@ModelAttribute BuscarPorDataForm buscarData, ModelMap model) {
        DataForm data = mapper.map(buscarData, DataForm.class);
        List<InstituicaoNotificadoraDTO> instituicao = aplInstituicaoNotificadora.doacoesPorInstituicao(data.getDataIni(), data.getDataFim());
        model.addAttribute("doacoesPorHospital", instituicao);
        return "lista-doacaoporhospital";
    }
    
    @RequestMapping(ContextUrls.RLT_N_DOACAO_HOSPITAL)
    public String relatorioNaoDoacaoXHospital(@ModelAttribute BuscarPorDataForm buscarData, ModelMap model) {
        DataForm data = mapper.map(buscarData, DataForm.class);
        List<InstituicaoNotificadoraDTO> instituicao = aplInstituicaoNotificadora.naoDoacoesPorInstituicao(data.getDataIni(), data.getDataFim());
        model.addAttribute("naoDoacoesPorHospital", instituicao);
        return "lista-naodoacaoporhospital";
    }
}
