package br.ifes.leds.sincap.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Usuario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.model.Mensagem;
import br.ifes.leds.sincap.web.model.UsuarioForm;
import br.ifes.leds.sincap.web.model.UsuarioSessao;

@Controller
@RequestMapping("/")
@SessionScoped
public class SignInController {

	@Autowired
	AplPrincipal aplPrincipal;
	
	@Autowired
	UsuarioSessao usuarioSessao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(ModelMap model) {
		
		UsuarioForm usuario = new UsuarioForm();
	
		model.addAttribute("usuario", usuario);
		
		return "signin";
	}
	
	@RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	public String addUsuario(@ModelAttribute UsuarioForm usuarioForm,
			ModelMap model) {
	
		try{
			
			Usuario usuarioLogado = aplPrincipal.validarLogin(usuarioForm.getUsername(), usuarioForm.getPassword());
			
			//Guardando os dados do usuario da sessao
			usuarioSessao.setIdHospital(new Long(usuarioForm.getHospital()));
			usuarioSessao.setIdUsuario(usuarioLogado.getId());
			usuarioSessao.setCpfUsuario(usuarioForm.getUsername());
			
			model.addAttribute("usuario", usuarioLogado);
			
			return "redirect:/index";
			
		}catch (Exception e){
			
			model.addAttribute("usuario", usuarioForm);
			
			return "redirect:/mensagem/erroLogin";
		}
		
	}	

	@RequestMapping(value = "/mensagem/{conteudo}", method = RequestMethod.GET)
	public String tratarErro(@PathVariable String conteudo,
			ModelMap model) {
	
		UsuarioForm usuario = new UsuarioForm();
		
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("mensagem","Login invalido!");
		
		return "signin";
	}
	
	@RequestMapping(value = "/getHospitais", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Mensagem>> getHospitais(@RequestBody String username) {

		Set<Hospital> setHospitais;
		List<Mensagem> listHospitais = new ArrayList<Mensagem>();
		
				
		try {
			setHospitais = aplPrincipal.obterHopitaisPorUsername(username);
			
			for(Hospital hospital : setHospitais){
				Mensagem mensagem = new Mensagem();
				
				mensagem.setDado(hospital.getNome());
				mensagem.setId(hospital.getId().toString());
				
				listHospitais.add(mensagem);
			}			
			
		} catch (Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<Mensagem>>(listHospitais, HttpStatus.INTERNAL_SERVER_ERROR);
		}

			
		return new ResponseEntity<List<Mensagem>>(listHospitais, HttpStatus.OK);

		
	}

}
