package br.ifes.leds.sincap.web.service;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.model.Mensagem;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 20102bsi0553 on 15/09/14.
 */
@Controller
@RequestMapping(ContextUrls.REST)
@SessionScoped
public class SignInServiceController {

    @Autowired
    private Mapper mapper;
    @Autowired
    AplPrincipal aplPrincipal;

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getTokenCSRF(HttpServletRequest request) {
        return new ModelAndView("csrf");
    }

    @RequestMapping(value = ContextUrls.AUTENTICAR, method = RequestMethod.POST)
    public String autenticar(HttpSession session,
                             @RequestParam("username") String username,
                             @RequestParam("hospital") Long idHospital) {

        Funcionario func = aplPrincipal.validarLogin(username);
        UsuarioSessao usuarioSessao = new UsuarioSessao();

        //Guardando os dados do usuario da sessao
        usuarioSessao.setIdHospital(idHospital);
        usuarioSessao.setIdUsuario(func.getId());
        usuarioSessao.setCpfUsuario(func.getCpf());
        usuarioSessao.setNome(func.getNome());
        session.setAttribute("user", usuarioSessao);

        return "forward:/j_spring_security_check";
    }

    @RequestMapping(value = ContextUrls.LOGOUT, method = RequestMethod.GET)
    public String logout() {
        return "redirect:/j_spring_security_logout";
    }

    @RequestMapping(value = ContextUrls.GET_HOSPITAIS, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getHospitais(@RequestParam("cpf") String cpf) {

        Set<InstituicaoNotificadora> setInstituicoesNotificadoras;
        List<Mensagem> listaMensagem = new ArrayList<>();
        Mensagem mensagem;

        try {
            setInstituicoesNotificadoras = aplPrincipal.obterInstituicoesNotificadorasPorCpf(cpf);

            for (InstituicaoNotificadora instituicaoNotificadora : setInstituicoesNotificadoras) {
                mensagem = new Mensagem();

                mensagem.setDado(instituicaoNotificadora.getNome());
                mensagem.setId(instituicaoNotificadora.getId().toString());

                listaMensagem.add(mensagem);
            }

        } catch (Exception ignored) {
        }

        return new ResponseEntity<>(listaMensagem, HttpStatus.OK);
    }
}
