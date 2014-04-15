/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.MotivoInviabilidadeEmUsoException;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.MotivoInviabilidadeExistenteException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplTipoMotivoInviabilidade;
import br.ifes.leds.sincap.web.model.ListaMotivoInviabilidadeForm;
import br.ifes.leds.sincap.web.model.MotivoInviabilidadeForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/motivoInviabilidade")
@SessionScoped
public class ListaMotivoInviabilidadeController {

    @Autowired
    AplMotivoInviabilidade aplMotivoInviabilidade;
    @Autowired
    AplTipoMotivoInviabilidade aplTipoMotivoInviabilidade;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "form-lista-motivosinviabilidade";
    }

    @RequestMapping(value = "/apagar/", method = RequestMethod.POST)
    public String apagarMotivo(@RequestBody String id, ModelMap model) {

        id = id.split("=")[1];//table1%3A0%3Aid=2
        
        MotivoInviabilidade motivoInviabilidade = aplMotivoInviabilidade.buscar(Long.parseLong(id));

        try {
            aplMotivoInviabilidade.excluir(motivoInviabilidade);
           
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "block");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        } catch (MotivoInviabilidadeEmUsoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            model.addAttribute("displayError", "block");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");

        }
        return loadForm(model);
        //return "redirect:/admin/motivoInviabilidade";
    }

    private void preecherLista(ModelMap model) {

        List<MotivoInviabilidade> listaMotivos = aplMotivoInviabilidade.obter();
        List<ListaMotivoInviabilidadeForm> listaMotivosForm = new ArrayList<ListaMotivoInviabilidadeForm>();

        for (MotivoInviabilidade motivo : listaMotivos) {
            try {
                ListaMotivoInviabilidadeForm motivoInviabilidadeForm = new ListaMotivoInviabilidadeForm(
                        motivo.getNome(), aplTipoMotivoInviabilidade.obter(
                                motivo.getTipoMotivo().getId()).getNome(),
                        motivo.getId());
                listaMotivosForm.add(motivoInviabilidadeForm);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        model.addAttribute("listaMotivosForm", listaMotivosForm);

    }
    
    @RequestMapping(value ="/novo", method = RequestMethod.GET)
    public String novoMotivo(ModelMap model) {

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

    @RequestMapping(value = "/novo/atualizar/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/novo/salvar", method = RequestMethod.POST)
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
            
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "block");
            model.addAttribute("displayNovoError", "none");
        } catch (MotivoInviabilidadeExistenteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "block");
            
            return novoMotivo(model); //Redireciona para a pagina atual
        }

        return loadForm(model);
        //return "redirect:/admin/motivoInviabilidade";
    }

}
