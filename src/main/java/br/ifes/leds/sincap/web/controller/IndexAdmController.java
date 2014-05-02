package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.IndexForm;

@Controller
@RequestMapping("/adm")
@SessionScoped
public class IndexAdmController {

    @Autowired
    AplNotificacao aplNotificacao;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        IndexForm indexForm = new IndexForm();

        model.addAttribute("indexForm", indexForm);

        return "index-adm";
    }
}
