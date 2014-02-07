package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/usuario")
@SessionScoped
public class UsuarioController {
	@RequestMapping(value="/novo", method = RequestMethod.GET)
	public String loadFormNovo(ModelMap model) {
		
		return "form-usuario";
	}
	
	@RequestMapping(value="/lista", method = RequestMethod.GET)
	public String loadFormBusca(ModelMap model) {
		
		return "lista-usuarios";
	}
}

