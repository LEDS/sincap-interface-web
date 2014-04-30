/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.MotivoRecusaEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoMotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplTipoMotivoRecusa;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.MotivoRecusaForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/motivorecusa")
public class MotivoRecusaController {
    @Autowired
    AplMotivoRecusa aplMotivoRecusa;
    @Autowired
    AplTipoMotivoRecusa aplTipoMotivoRecusa;
    @Autowired
    AplNotificacao aplNotificacao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String LoadForm(ModelMap model)
    {
        CarregarLista(model);
        return "lista-motivosrecusa";
    }
    
    
    public void CarregarLista(ModelMap model)
    {
        List<MotivoRecusa> motivosRecusa = aplMotivoRecusa.obter();
        List<MotivoRecusaForm> motivosRecusaForm = new ArrayList<>();
        
        System.out.println(motivosRecusa.size());
        
        
        for(MotivoRecusa m : motivosRecusa)
        {
            MotivoRecusaForm mf = new MotivoRecusaForm();
            mf.setId(m.getId().toString());
            mf.setNome(m.getNome());
            mf.setTipoMotivoRecusa(m.getTipoMotivoRecusa().getNome());
            mf.setTipoMotivoRecusaId(m.getTipoMotivoRecusa().getId().toString());
            motivosRecusaForm.add(mf);
        }
        
        model.addAttribute("motivosRecusaForm", motivosRecusaForm);
    }
    
    
    
    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
    public String apagarMotivo(@PathVariable long id, ModelMap model) throws Exception{
        //VERIFICAR SE ESTÁ EM USO EM NOTIFICAÇÕES
        
        MotivoRecusa motivoRecusa = aplMotivoRecusa.obter(id);
        if(aplNotificacao.motivoRecusaEmUso(motivoRecusa))
        {
            aplMotivoRecusa.excluir(motivoRecusa);
        }
        else
        {
            throw new MotivoRecusaEmUsoException();
        }
        
        return "redirect:/admin/motivorecusa";
    }
    
    @RequestMapping("/novo")
    public String criarMotivo(ModelMap model)
    {
        carregarForm(model);
        return "motivosrecusa";
    }
    
    @RequestMapping(value = "salvar", method = RequestMethod.POST)
    public String salvarMotivoRecusa(@ModelAttribute MotivoRecusaForm motivoRecusaForm, ModelMap model) throws Exception {

        MotivoRecusa motivoRecusa = null;
        TipoMotivoRecusa tipoMotivoRecusa;

        try {
            //salvando
            if (motivoRecusaForm.getId().trim().isEmpty()) {
                motivoRecusa = new MotivoRecusa();
                tipoMotivoRecusa = aplTipoMotivoRecusa.obter(new Long(motivoRecusaForm.getTipoMotivoRecusaId())); 
                motivoRecusa.setTipoMotivoRecusa(tipoMotivoRecusa);
                System.out.println("SALVANDO!");
            } else {
                //Editando 
                motivoRecusa = aplMotivoRecusa.obter(new Long(motivoRecusaForm.getId()));
                System.out.println("EDITANDO!!!");
            }

            //salvar                
            motivoRecusa.setNome(motivoRecusaForm.getNome());

            aplMotivoRecusa.salvar(motivoRecusa);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/admin/motivorecusa";
    }
    
    
    public void carregarForm(ModelMap model)
    {
        preencherDropdown(model);
        MotivoRecusaForm m = new MotivoRecusaForm();
        model.addAttribute("motivoRecusaForm", m);
    }        
    
    private void preencherDropdown(ModelMap model) {

        List<TipoMotivoRecusa> listaTipoMotivos;
        List<SelectItem> listaTipoMotivosSelectItem = new ArrayList<SelectItem>();

        try {
            listaTipoMotivos = aplTipoMotivoRecusa.obter();
            for (TipoMotivoRecusa tipoMotivo : listaTipoMotivos) {
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
        MotivoRecusa motivoRecusa = aplMotivoRecusa.obter(id);

        //jogando para a tela
        MotivoRecusaForm motivoRecusaForm = new MotivoRecusaForm();

        motivoRecusaForm.setNome(motivoRecusa.getNome());
        motivoRecusaForm.setTipoMotivoRecusaId(motivoRecusa.getTipoMotivoRecusa().getId().toString());
        motivoRecusaForm.setId(motivoRecusa.getId().toString());
        model.addAttribute("motivoCadastrado", motivoCadastrado);
        model.addAttribute("motivoRecusaForm", motivoRecusaForm);

        return "motivosrecusa";
    }
    
    
    
   
    
    
    
   
}
