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
import br.ifes.leds.sincap.web.model.Id;
import br.ifes.leds.sincap.web.model.NotificadorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Phillipe Lopes
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_NOTIFICADOR)
@SessionScoped
public class NotificadorController {

    @Autowired
    private AplNotificador aplNotificador;

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {
        preencherListaNotificadores(model);

        return "lista-notificador";
    }

    @RequestMapping(ContextUrls.ADICIONAR)
    public String adicionarNotificador(ModelMap model) {
        NotificadorDTO formularioNotificador = new NotificadorDTO();

        preencherListaInstituicoes(model);
        model.addAttribute("formularioNotificador", formularioNotificador);

        return "form-notificador";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarNotificador(@ModelAttribute NotificadorDTO formularioNotificador, ModelMap model) {
        Notificador notificador;

        notificador = mapper.map(formularioNotificador, Notificador.class);

        aplNotificador.salvarNotificador(notificador);

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_NOTIFICADOR;
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String editarNotificador(@ModelAttribute Id id, ModelMap model) {
        preencherListaInstituicoes(model);

        Notificador notificador = aplNotificador.obterNotificador(id.getId());
        NotificadorDTO formularioNotificador = mapper.map(notificador, NotificadorDTO.class);

        model.addAttribute("formularioNotificador", formularioNotificador);

        return "form-notificador";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String apagarNotificador(@ModelAttribute Id id, ModelMap model) {
        aplNotificador.delete(id.getId());

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_NOTIFICADOR;
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

    private void preencherListaNotificadores(ModelMap model) {
        List<Notificador> listaNotificadores;
        List<NotificadorDTO> listaNotificadoresForm = new ArrayList<>();

        listaNotificadores = aplNotificador.obterTodosNotificadores();

        for (Notificador notificador : listaNotificadores) {
            listaNotificadoresForm.add(mapper.map(notificador, NotificadorDTO.class));
        }

        model.addAttribute("listaNotificadores", listaNotificadoresForm);
    }

}
