/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20121bsi0252
 */
@Controller
@RequestMapping(ContextUrls.INDEX)
@SessionScoped
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        return "index";
    }

}
