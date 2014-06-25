package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20102bsi0553
 */
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

        //UsuarioForm usuario = new UsuarioForm();

        //model.addAttribute("usuario", usuario);

        return "signin";
    }
}
