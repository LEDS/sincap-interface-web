/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.web.model.IndexForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20101BSI0534
 */
@Controller
@RequestMapping("/listanotificacoes")
@SessionScoped
public class ListaNotificacoes {

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        IndexForm indexForm = new IndexForm();

        preencherForm(model);

        model.addAttribute("indexForm", indexForm);

        return "lista-notificacao";
    }

    private void preencherForm(ModelMap model) {
//        List<Estado> listaEstado = new ArrayList<Estado>();
        List<IndexForm> list = new ArrayList<IndexForm>();

//    	List<SelectItem> listaEstadoItem = new ArrayList<SelectItem>();
//    	listaEstado = aplEndereco.obterEstadosPorNomePais("Brasil");
//    	for(Estado estado : listaEstado){
//    		SelectItem estadoItem = new SelectItem(estado.getId(), estado.getNome());
//    		listaEstadoItem.add(estadoItem);
//    	}
//    	model.addAttribute("listaEstadoItem", listaEstadoItem);
    }
}
