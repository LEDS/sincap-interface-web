/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.SetorExistenteException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.model.SetorForm;

/**
 * 
 * @author 20121BSI0252
 */
@Controller
@RequestMapping("/admin/setor/novo")
@SessionScoped
public class SetorController {

	@Autowired
	AplSetor aplSetor;

	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(ModelMap model) {

		SetorForm setorForm = new SetorForm();

		model.addAttribute("setorForm", setorForm);

		return "setor-form";
	}

	 @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
	 public String atualizarSetor(@PathVariable long id, ModelMap model)
	 throws Exception {
		
		 //pegando do banco
		 Setor setor = aplSetor.buscarSetor(id);
		
		 //jogando para a tela
		 SetorForm setorForm = new SetorForm();
		
		 setorForm.setNome(setor.getNome());
		 setorForm.setId(setor.getId());
		 model.addAttribute("setorForm", setorForm);
		
		 return "setor-form";
	 }

	// Salvar e Editar
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvarSetor(
			@ModelAttribute SetorForm setorForm,
			ModelMap model) throws Exception {

		Setor setor = null;

		try {
			// salvando
			if (setorForm.getId() == null) {
				setor = new Setor();
			} else {
				// Editando
				setor = aplSetor.buscarSetor(setorForm.getId());
			}

			// salvar
			setor.setNome(setorForm.getNome());

			aplSetor.adicionar(setor);
		} catch (SetorExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/admin/setor/";
	}
}
