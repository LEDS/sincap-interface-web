/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.MotivoInviabilidadeExistenteException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplTipoMotivoInviabilidade;
import br.ifes.leds.sincap.web.model.MotivoInviabilidadeForm;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/motivoInviabilidade/novo")
@SessionScoped
public class MotivoInviabilidadeController {

    @Autowired
    AplMotivoInviabilidade aplMotivoInviabilidade;
    @Autowired
    AplTipoMotivoInviabilidade aplTipoMotivoInviabilidade;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        MotivoInviabilidadeForm motivoInviabilidadeForm = new MotivoInviabilidadeForm();

        preencherDropdown(model);
        model.addAttribute("motivoInviabilidadeForm", motivoInviabilidadeForm);

        return "form-motivosinviabilidade";
    }

    private void preencherDropdown(ModelMap model) {

        List<TipoMotivoInviabilidade> listaTipoMotivos;
        List<SelectItem> listaTipoMotivosSelectItem = new ArrayList<SelectItem>();

        try {
            listaTipoMotivos = aplTipoMotivoInviabilidade.obter();
            for (TipoMotivoInviabilidade tipoMotivo : listaTipoMotivos) {
                listaTipoMotivosSelectItem.add(new SelectItem(tipoMotivo.getId(), tipoMotivo.getNome()));
            }
            model.addAttribute("listaTipoMotivosSelectItem", listaTipoMotivosSelectItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
    public String atualizarMotivoInviabilidade(@PathVariable long id, ModelMap model)
            throws Exception {
        boolean motivoCadastrado = true;

        preencherDropdown(model);
        //pegando do banco
        MotivoInviabilidade motivoInviabilidade = aplMotivoInviabilidade.buscar(id);

        //jogando para a tela
        MotivoInviabilidadeForm motivoInviabilidadeForm = new MotivoInviabilidadeForm();

        motivoInviabilidadeForm.setNome(motivoInviabilidade.getNome());
        motivoInviabilidadeForm.setTipoMotivoInviabilidadeId(motivoInviabilidade.getTipoMotivo().getId().toString());
        motivoInviabilidadeForm.setId(motivoInviabilidade.getId().toString());
        model.addAttribute("motivoCadastrado", motivoCadastrado);
        model.addAttribute("motivoInviabilidadeForm", motivoInviabilidadeForm);

        return "form-motivosinviabilidade";
    }

    //Salvar e Editar
    @RequestMapping(value = "salvar", method = RequestMethod.POST)
    public String salvarMotivoInviabilidade(@ModelAttribute MotivoInviabilidadeForm motivoInviabilidadeForm, ModelMap model) throws Exception {

        MotivoInviabilidade motivoInviabilidade = null;
        TipoMotivoInviabilidade tipoMotivoInviabilidade;

        try {
            //salvando
            if (motivoInviabilidadeForm.getId().trim().isEmpty()) {
                motivoInviabilidade = new MotivoInviabilidade();
                tipoMotivoInviabilidade = aplTipoMotivoInviabilidade.obter(new Long(motivoInviabilidadeForm.getTipoMotivoInviabilidadeId()));
                motivoInviabilidade.setTipoMotivo(tipoMotivoInviabilidade);
            } else {
                //Editando 
                motivoInviabilidade = aplMotivoInviabilidade.buscar(new Long(motivoInviabilidadeForm.getId()));
            }

            //salvar                
            motivoInviabilidade.setNome(motivoInviabilidadeForm.getNome());

            aplMotivoInviabilidade.adicionar(motivoInviabilidade);
        } catch (MotivoInviabilidadeExistenteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/admin/motivoInviabilidade";
    }
}
