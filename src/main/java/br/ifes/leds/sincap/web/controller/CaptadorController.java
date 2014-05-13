/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.web.model.Id;
import br.ifes.leds.sincap.web.model.CaptadorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20131BSI0173
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_CAPTADOR )
@SessionScoped
public class CaptadorController {
    
    @Autowired
    private AplCaptador aplCaptador;

    @Autowired
    private AplBancoOlhos aplBancoOlhos;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {
        preencherListaCaptadores(model);

        return "lista-captador";
    }

    @RequestMapping(ContextUrls.ADICIONAR)
    public String adicionarCaptador(ModelMap model) {
        CaptadorDTO formularioCaptador = new CaptadorDTO();

        preencherListaBancoOlhos(model);
        model.addAttribute("formularioCaptador", formularioCaptador);

        return "form-captador";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarCaptador(@ModelAttribute CaptadorDTO formularioCaptador, ModelMap model) {
        Captador captador;

        captador = mapper.map(formularioCaptador, Captador.class);

        aplCaptador.salvarCaptador(captador);

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_CAPTADOR;
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String editarCaptador(@ModelAttribute Id id, ModelMap model) {
        preencherListaBancoOlhos(model);

        Captador captador = aplCaptador.obterCaptador(id.getId());
        CaptadorDTO formularioCaptador = mapper.map(captador, CaptadorDTO.class);

        model.addAttribute("formularioCaptador", formularioCaptador);

        return "form-captador";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String apagarCaptador(@ModelAttribute Id id, ModelMap model) {
        aplCaptador.delete(id.getId());

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_CAPTADOR;
    }

    private void preencherListaBancoOlhos(ModelMap model) {
        List<BancoOlhos> listaBancoOlhos;
        List<SelectItem> listaBancoOlhosForm = new ArrayList<>();

        listaBancoOlhos = aplBancoOlhos.obter();

        for (BancoOlhos bancoOlhos : listaBancoOlhos) {
            SelectItem setorItem = new SelectItem(bancoOlhos.getId(), bancoOlhos.getNome());
            listaBancoOlhosForm.add(setorItem);
        }

        model.addAttribute("listaBancoOlhos", listaBancoOlhosForm);
    }

    private void preencherListaCaptadores(ModelMap model) {
        List<Captador> listaCaptadores;
        List<CaptadorDTO> listaCaptadoresForm = new ArrayList<>();

        listaCaptadores = aplCaptador.obterTodosCaptadores();

        for (Captador captador : listaCaptadores) {
            listaCaptadoresForm.add(mapper.map(captador, CaptadorDTO.class));
        }

        model.addAttribute("listaCaptadores", listaCaptadoresForm);
    }
 
}
