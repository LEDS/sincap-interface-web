/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.IndexForm;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AplNotificacao aplNotificacao;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        IndexForm indexForm = new IndexForm();

        preencherForm(model);

        return "lista-notificacao";
    }

    private void preencherForm(ModelMap model) {
        List<Notificacao> notificacoes;
        List<IndexForm> listaNotificacoesForm = new ArrayList<>();

        notificacoes = aplNotificacao.obter();

        for (Notificacao notificacao : notificacoes) {
            IndexForm indexForm = new IndexForm(notificacao.getCodigo(),
                    notificacao.getDataAbertura().getTime().toString(),
                    notificacao.getObito().getDataObito().getTime().toString(),
                    notificacao.getObito().getPaciente().getNome(),
                    notificacao.getSetor().getHospital().toString());

            listaNotificacoesForm.add(indexForm);
        }

        model.addAttribute("listaNotificacoesForm", listaNotificacoesForm);
    }
}
