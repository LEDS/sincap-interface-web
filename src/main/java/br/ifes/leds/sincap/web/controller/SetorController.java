/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorEmUsoException;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorExistenteException;
import java.util.List;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.model.Id;
import br.ifes.leds.sincap.web.model.SetorDTO;
import org.dozer.Mapper;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author 20121BSI0252
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_SETOR)
@SessionScoped
public class SetorController {

    @Autowired
    AplSetor aplSetor;

    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "visualizar-setor-form";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String apagarSetor(@ModelAttribute Id id, ModelMap model) {

        try {
            aplSetor.excluir(id.getId());

            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "true");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        } catch (SetorEmUsoException e) {
            model.addAttribute("displayError", "true");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        }

        return loadForm(model);
    }

    private void preecherLista(ModelMap model) {

        List<Setor> listaSetor = aplSetor.obter();

        model.addAttribute("listaSetoresForm", listaSetor);
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String novoSetor(ModelMap model) {

        SetorDTO setorForm = new SetorDTO();

        model.addAttribute("setorForm", setorForm);

        return "setor-form";
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String atualizarSetor(@ModelAttribute Id id, ModelMap model)
            throws Exception {

        Setor setor = aplSetor.buscarSetor(id.getId());

        SetorDTO setorForm = mapper.map(setor, SetorDTO.class);

        model.addAttribute("setorForm", setorForm);

        return "setor-form";
    }

    // Salvar e Editar
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarSetor(
            @ModelAttribute SetorDTO setorForm,
            ModelMap model) throws Exception {

        Setor setor = mapper.map(setorForm, Setor.class);

        try {
            aplSetor.adicionar(setor);

            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "block");
            model.addAttribute("displayNovoError", "none");
        } catch (SetorExistenteException e) {
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "block");

            return novoSetor(model);
        }

        return loadForm(model);
    }

}
