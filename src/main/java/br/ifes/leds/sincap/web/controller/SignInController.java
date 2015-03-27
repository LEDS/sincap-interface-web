package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author 20102bsi0553
 */
@Controller
@RequestMapping("/")
@SessionScoped
public class SignInController {

    @Autowired
    private AplPrincipal aplPrincipal;
    @Autowired
    private Utility utility;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(Principal principal) {
        if (principal != null) {
            return "index";
        }
        return "signin";
    }

    @RequestMapping(value = "autenticar", method = RequestMethod.POST)
    public String autenticar(HttpSession session,
                             ModelMap model,
                             @RequestParam("username") String username,
                             @RequestParam(value = "hospital", defaultValue = "") Long idHospital) {

        Funcionario func = aplPrincipal.validarLogin(username);
        if(func != null){
            UsuarioSessao usuarioSessao = new UsuarioSessao();

            //Guardando os dados do usuario da sessao
            usuarioSessao.setFuncionario(func);
            usuarioSessao.setIdHospital(idHospital);
            usuarioSessao.setIdUsuario(func.getId());
            usuarioSessao.setCpfUsuario(func.getCpf());
            usuarioSessao.setNome(func.getNome());
            session.setAttribute("user", usuarioSessao);

            return "forward:/j_spring_security_check";
        }

        model.addAttribute("loginErro", true);
        return "signin";

    }

    @RequestMapping("403")
    public String accessDeniedHandler() {
        return "403";
    }

    @RequestMapping(value = ContextUrls.LOGOUT, method = RequestMethod.POST)
    public String logout() {
        return "forward:/j_spring_security_logout";
    }

    @RequestMapping(value = ContextUrls.GET_HOSPITAIS, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map>> getHospitais(@RequestParam("cpf") String cpf) {

        Set<InstituicaoNotificadora> setInstituicoesNotificadoras = aplPrincipal.obterInstituicoesNotificadorasPorCpf(cpf);
        List<InstituicaoNotificadora> instituicoes = new ArrayList<>();

        try {
            instituicoes.addAll(setInstituicoesNotificadoras);
        } catch (NullPointerException e) {
            return new ResponseEntity<List<Map>>(new ArrayList<Map>(), OK);
        }

        return new ResponseEntity<>(utility.mapList(instituicoes, Map.class), OK);
    }


}
