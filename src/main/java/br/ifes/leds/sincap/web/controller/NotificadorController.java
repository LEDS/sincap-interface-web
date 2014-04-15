/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplNotificador;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    private final String urlNotificadorController = "/admin/notificador";

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {
        preencherListaNotificadores(model);

        return "lista-notificador";
    }

    private void preencherListaNotificadores(ModelMap model) {
        List<Notificador> listaNotificadores;

        listaNotificadores = aplNotificador.obterTodosNotificadores();

        model.addAttribute("listaNotificadores", listaNotificadores);
    }

}
