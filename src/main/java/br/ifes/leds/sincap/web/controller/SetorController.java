/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.dto.SetorDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.utility.Utility;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author 20112bsi0083
 */
@Controller
@RequestMapping(ContextUrls.APP_SETOR)
@SessionScoped
public class SetorController {
     @Autowired
    private AplSetor aplSetor;
    Utility utilityWeb = Utility.INSTANCE;
    
    @RequestMapping(method = RequestMethod.GET)
    public String carregarLista(ModelMap model){
        List<SetorDTO> setores = aplSetor.obterDTO();
        model.addAttribute("listaSetores", setores);
        return "setor";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String carregaForm(ModelMap model){
        SetorDTO novoSetor = new SetorDTO();
        model.addAttribute("setor", novoSetor);
        return "form-setor";
    }
    
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarSetor(ModelMap model, 
            @ModelAttribute SetorDTO setor){
        try {
            aplSetor.adicionar(setor);
        } catch (Exception e) {
            
        }
        
        return "redirect:"+ContextUrls.APP_SETOR;
    }
    @RequestMapping(method = RequestMethod.POST)
    public String excluirSetor(ModelMap model, 
            @RequestParam("id") Long setorId){
        try{
            aplSetor.excluir(setorId);    
        }catch(SetorEmUsoException exception){
            //TODO
        }
        return "redirect:"+ContextUrls.APP_SETOR;
    }
}
