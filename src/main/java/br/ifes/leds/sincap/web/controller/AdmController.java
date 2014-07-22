/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.faces.bean.SessionScoped;

/**
 * Controla os eventos da página principal do administrador.
 *
 * @author Lucas Possatti
 */
@Controller
@RequestMapping(ContextUrls.ADMIN)
@SessionScoped
public class AdmController {

    /**
     * Exibe a página principal.
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "adm-index";
    }
}
