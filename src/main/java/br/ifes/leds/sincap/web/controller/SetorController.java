/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorEmUsoException;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorExistenteException;
import java.util.ArrayList;
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
import br.ifes.leds.sincap.web.model.SetorForm;
import br.ifes.leds.sincap.web.model.VisualizarSetorForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author 20121BSI0252
 */
@Controller
@RequestMapping("/admin/setor/")
@SessionScoped
public class SetorController {

    @Autowired
    AplSetor aplSetor;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "visualizar-setor-form";
    }

    @RequestMapping(value = "/apagar", method = RequestMethod.POST)
    public String apagarSetor(@RequestBody String id, ModelMap model) {

        id = id.split("=")[1];//table1%3A0%3Aid=15

        try {
            aplSetor.excluir(Long.parseLong(id));
            
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "true");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        } catch (SetorEmUsoException e) {
            e.printStackTrace();
            
            model.addAttribute("displayError", "true");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        }

        return loadForm(model);
        //return "redirect:/admin/setor";

    }

    private void preecherLista(ModelMap model) {

        List<Setor> listaSetor = aplSetor.obter();
        List<VisualizarSetorForm> listaSetoresForm = new ArrayList<VisualizarSetorForm>();

        for (Setor setor : listaSetor) {
            VisualizarSetorForm setorForm = new VisualizarSetorForm(
                    setor.getNome(), setor.getId());
            listaSetoresForm.add(setorForm);

        }

        model.addAttribute("listaSetoresForm", listaSetoresForm);

    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String novoSetor(ModelMap model) {

        SetorForm setorForm = new SetorForm();

        model.addAttribute("setorForm", setorForm);

        return "setor-form";
    }

    @RequestMapping(value = "/novo/atualizar/{id}", method = RequestMethod.GET)
    public String atualizarSetor(@PathVariable long id, ModelMap model)
            throws Exception {

        //pegando do banco
        Setor setor = aplSetor.buscarSetor(id);

        //jogando para a tela
        SetorForm setorForm = new SetorForm();

        setorForm.setNome(setor.getNome());
        setorForm.setId(setor.getId());
        model.addAttribute("setorForm", setorForm);

        return "setor-form";
    }

    // Salvar e Editar
    @RequestMapping(value = "/novo/salvar", method = RequestMethod.POST)
    public String salvarSetor(
            @ModelAttribute SetorForm setorForm,
            ModelMap model) throws Exception {

        Setor setor = null;

        try {
            // salvando
            if (setorForm.getId() == null) {
                setor = new Setor();
            } else {
                // Editando
                setor = aplSetor.buscarSetor(setorForm.getId());
            }

            // salvar
            setor.setNome(setorForm.getNome());

            aplSetor.adicionar(setor);
            
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "block");
            model.addAttribute("displayNovoError", "none");
        } catch (SetorExistenteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "block");
        }
        
        return loadForm(model);
        //return "redirect:/admin/setor/";
    }

}
