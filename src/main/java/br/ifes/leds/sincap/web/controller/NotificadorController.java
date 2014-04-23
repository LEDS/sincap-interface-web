/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplNotificador;
import br.ifes.leds.sincap.web.model.NotificadorForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Phillipe Lopes
 */
@Controller
@RequestMapping("/admin/notificador")
@SessionScoped
public class NotificadorController {

    @Autowired
    private AplNotificador aplNotificador;

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;

    private final String urlNotificadorController = "/admin/notificador";
    private final String urlAdicionarNotificador = "/novo";
    private final String urlEditarNotificador = "/editar";
    private final String urlApagarNotificador = "/apagar";
    private final String urlSalvarNotificador = "/salvar";

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {
        preencherListaNotificadores(model);

        return "lista-notificador";
    }

    @RequestMapping(urlAdicionarNotificador)
    public String adicionarNotificador(ModelMap model) {
        NotificadorForm formularioNotificador = new NotificadorForm();

        preencherListaInstituicoes(model);
        model.addAttribute("formularioNotificador", formularioNotificador);

        return "form-notificador";
    }

    @RequestMapping(value = urlSalvarNotificador, method = RequestMethod.POST)
    public String salvarNotificador(@ModelAttribute NotificadorForm formularioNotificador, ModelMap model) {
        Notificador notificador;

        notificador = formularioNotificador.converterParaNotificador(aplInstituicaoNotificadora);

        aplNotificador.salvarNotificador(notificador);

        return "redirect:" + urlNotificadorController;
    }

    @RequestMapping(value = urlEditarNotificador, method = RequestMethod.POST)
    public String editarNotificador(@RequestBody String json, ModelMap model) {
        Long id;
        id = pegarParametroUnicoJSON(json);
        
        preencherListaInstituicoes(model);
        
        Notificador notificador = aplNotificador.obterNotificador(id);
        NotificadorForm formularioNotificador = new NotificadorForm(notificador);
        
        model.addAttribute("formularioNotificador", formularioNotificador);
        
        return "form-notificador";
    }
    
    @RequestMapping(value = urlApagarNotificador, method = RequestMethod.POST)
    public String apagarNotificador(@RequestBody String json, ModelMap model) {
        Long idNotificador;
        idNotificador = pegarParametroUnicoJSON(json);
        
        aplNotificador.delete(idNotificador);
        
        return "redirect:" + urlNotificadorController;
    }

    private void preencherListaInstituicoes(ModelMap model) {
        List<InstituicaoNotificadora> instituicoesNotificadoras;
        List<SelectItem> listaInstituicoesForm = new ArrayList<>();

        instituicoesNotificadoras = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        for (InstituicaoNotificadora instituicao : instituicoesNotificadoras) {
            SelectItem setorItem = new SelectItem(instituicao.getId(), instituicao.getNome());
            listaInstituicoesForm.add(setorItem);
        }

        model.addAttribute("listaInstituicoes", listaInstituicoesForm);
    }

    public Long pegarParametroUnicoJSON(String json) {
        String parametro = json.split("=")[1];

        return Long.parseLong(parametro);
    }

    private void preencherListaNotificadores(ModelMap model) {
        List<Notificador> listaNotificadores;

        listaNotificadores = aplNotificador.obterTodosNotificadores();

        model.addAttribute("listaNotificadores", listaNotificadores);
    }

}
