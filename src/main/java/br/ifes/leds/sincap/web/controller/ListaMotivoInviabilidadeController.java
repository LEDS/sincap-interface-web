/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.MotivoInviabilidadeEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplTipoMotivoInviabilidade;
import br.ifes.leds.sincap.web.model.ListaMotivoInviabilidadeForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        
    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
    public String apagarMotivo(@PathVariable long id, ModelMap model){
        
    	MotivoInviabilidade motivoInviabilidade = aplMotivoInviabilidade.buscar(id);

//    	try { -------------------------------------
//			//aplMotivoInviabilidade.excluir(motivoInviabilidade);
//        } catch (MotivoInviabilidadeEmUsoException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//        }
        
        return "redirect:/admin/motivoInviabilidade";
    }
    
    private void preecherLista(ModelMap model){
                        
        
        List<MotivoInviabilidade> listaMotivos = aplMotivoInviabilidade.obter();
        List<ListaMotivoInviabilidadeForm> listaMotivosForm = new ArrayList<ListaMotivoInviabilidadeForm>(); 
        
        for(MotivoInviabilidade motivo : listaMotivos){
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

}

