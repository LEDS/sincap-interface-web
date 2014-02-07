package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/relatorios")
@SessionScoped
public class RelatorioController {
	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(ModelMap model) {
		
		return "relatorios";
	}
}
