/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.web.model.VisualizarHospitalForm;

/**
 *
 * @author 20121BSI0252
 */
@Controller
@RequestMapping("/admin/hospital")
@SessionScoped
public class VisualizarHospitalController {

    @Autowired
    AplHospital aplHospital;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "lista-hospital";
    }

//    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    public String getMotivoInviabilidade(@PathVariable long id, ModelMap model)
//            throws Exception {
//        //pegando do banco
//        MotivoInviabilidade motivoInviabilidade = aplMotivoInviabilidade.buscar(id);
//        boolean motivoCadastrado = true;
//        //jogando para a tela
//        MotivoInviabilidadeForm motivoInviabilidadeForm = new MotivoInviabilidadeForm(); 
//        motivoInviabilidadeForm.setNome(motivoInviabilidade.getNome());
//        motivoInviabilidadeForm.setTipoMotivoInviabilidadeId(motivoInviabilidade.getTipoMotivo().getId());
//        
//        model.addAttribute("motivoInviabilidadeForm", motivoInviabilidadeForm);
//        model.addAttribute("motivoCadastrado", motivoCadastrado);
//        
//        return "form-motivosinviabilidade/{motivoInviabilidadeForm.getId()}";
//    }
    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
    public String apagarMotivo(@PathVariable long id, ModelMap model) {

//		aplHospital.remover(id);
        return "redirect:/admin/hospital";
    }

    private void preecherLista(ModelMap model) {

        List<Hospital> hospitais = new ArrayList<Hospital>();
        List<VisualizarHospitalForm> listaHospitaisForm = new ArrayList<VisualizarHospitalForm>();

        hospitais = aplHospital.obter();

        for (Hospital hospital : hospitais) {
            VisualizarHospitalForm hospitalForm = new VisualizarHospitalForm(
                    hospital.getNome(), hospital.getId());
            listaHospitaisForm.add(hospitalForm);
            
        }

        model.addAttribute("listaHospitaisForm", listaHospitaisForm);

    }

}
