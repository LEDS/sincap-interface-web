/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;

import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoUsuario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplAnalistaCNCDO;
import br.ifes.leds.sincap.web.model.AnalistaCNCDOForm;
import br.ifes.leds.sincap.web.model.BuscarAnalistaForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/analistacncdo")
public class AnalistaCNCDOController {

    //Continuar
    public String LoadForm() {
        return "form-analistacncdoprincipal";
    }

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public String retornaAnalista(ModelMap model, BuscarAnalistaForm buscarAnalistaForm) {
        AplAnalistaCNCDO aplAnalistaCNCDO = new AplAnalistaCNCDO();
        Funcionario analistaCNCDO = new Funcionario();
        AnalistaCNCDOForm analistaCNCDOForm;
        try {
            analistaCNCDO = aplAnalistaCNCDO.obter(buscarAnalistaForm.getCpf());
        } catch (Exception e) {
        }
        analistaCNCDOForm = preencherForm(analistaCNCDO);

        model.addAttribute("analistaCNCDOForm", analistaCNCDOForm);

        return "form-analistacncdo";

    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String loadFormNovo(ModelMap model) {

        AnalistaCNCDOForm analistaCNCDOForm = new AnalistaCNCDOForm();

        model.addAttribute("analistaCNCDOForm", analistaCNCDOForm);

        return "form-analistacncdo";
    }

    @RequestMapping(value = "/novo/add", method = RequestMethod.POST)
    public String addAnalistaCNCDO(@ModelAttribute AnalistaCNCDOForm analistaCNCDOForm, ModelMap model) {
        AplAnalistaCNCDO aplAnalistaCNCDO = new AplAnalistaCNCDO();

        Funcionario analistaCNCDO = preencherDados(analistaCNCDOForm);

        if (analistaCNCDO.getId() == null) {
            aplAnalistaCNCDO.salvar(analistaCNCDO);
        }

        model.addAttribute("analistaCNCDOForm", analistaCNCDOForm);

        return "form-analistacncdo";
    }

    public Funcionario preencherDados(AnalistaCNCDOForm analistaCNCDOForm) {
        Funcionario analistaCNCDO = new Funcionario();
        analistaCNCDO.setActive(analistaCNCDOForm.isActive());
        analistaCNCDO.setCpf(analistaCNCDOForm.getCpf());
        analistaCNCDO.setDocumentoSocial(analistaCNCDOForm.getDocumentoSocial());
        analistaCNCDO.setEmail(analistaCNCDOForm.getEmail());
        analistaCNCDO.setNome(analistaCNCDOForm.getNome());
        analistaCNCDO.setTipoUsuario(TipoUsuario.ANALISTA);
        return analistaCNCDO;
    }

    public AnalistaCNCDOForm preencherForm(Funcionario analistaCNCDO) {
        AnalistaCNCDOForm analistaCNCDOForm = new AnalistaCNCDOForm();
        analistaCNCDOForm.setActive(analistaCNCDO.isActive());
        analistaCNCDOForm.setCpf(analistaCNCDO.getCpf());
        analistaCNCDOForm.setDocumentoSocial(analistaCNCDO.getDocumentoSocial());
        analistaCNCDOForm.setEmail(analistaCNCDO.getEmail());
        analistaCNCDOForm.setNome(analistaCNCDO.getNome());

        //analistaCNCDOForm.setSenha(analistaCNCDO.getSenha());
        //analistaCNCDOForm.setTelefones(analistaCNCDO.get);
        return analistaCNCDOForm;
    }

}
