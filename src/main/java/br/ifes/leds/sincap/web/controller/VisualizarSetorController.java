/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorEmUsoException;
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
import br.ifes.leds.sincap.web.model.VisualizarSetorForm;

/**
 *
 * @author 20121BSI0252
 */
@Controller
@RequestMapping("/admin/setor")
@SessionScoped
public class VisualizarSetorController {

    @Autowired
    AplSetor aplSetor;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "visualizar-setor-form";
    }

    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
    public String apagarMotivo(@PathVariable long id, ModelMap model) {
        
        try{
            aplSetor.excluir(id);
        }catch(SetorEmUsoException e){
            e.printStackTrace();
        }
        

        return "redirect:/admin/setor";
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

}
