/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.ViolacaoDeRIException;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author marcosdias
 */
@Controller
@RequestMapping(ContextUrls.APP_ENTREVISTA)
@SessionScoped
public class NotificacaoEntrevistaController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;

    @RequestMapping(method = RequestMethod.GET)
    public String loadFormEntrevista() {
        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR, method = RequestMethod.GET)
    public String loadAnalisaNotificacaoEntrevista() {
        return "analise-entrevista";
    }

    @RequestMapping(value = ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarEntrevista(@ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO,
            ModelMap model) {

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String cadastrarEntrevista(@ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO,
            ModelMap model) {
        try {
            aplProcessoNotificacao.salvarEntrevista(processoNotificacaoDTO);
        } catch (ViolacaoDeRIException e) {

        }

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarEntrevista(@ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO,
            ModelMap model) {

        return "form-entrevista";
    }
}
