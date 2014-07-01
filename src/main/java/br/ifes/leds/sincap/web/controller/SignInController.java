package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cdp.dto.UsuarioDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.model.Mensagem;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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

        UsuarioDTO usuario = new UsuarioDTO();

        model.addAttribute("usuario", usuario);

        return "signin";
    }

    @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
    public String autenticar(@ModelAttribute UsuarioDTO usuarioDto,
            ModelMap model) {

        try {

            Funcionario usuarioLogado = aplPrincipal.validarLogin(usuarioDto.getUsername(), usuarioDto.getPassword());

            //Guardando os dados do usuario da sessao
            usuarioSessao.setIdHospital(usuarioDto.getHospital());
            usuarioSessao.setIdUsuario(usuarioLogado.getId());
            usuarioSessao.setCpfUsuario(usuarioLogado.getCpf());
            usuarioSessao.setNome(usuarioLogado.getNome());
            model.addAttribute("usuario", usuarioLogado);

            return "redirect:/index";

        } catch (Exception e) {

            model.addAttribute("usuario", usuarioDto);

            return "redirect:/mensagem/erroLogin";
        }

    }
    
    @RequestMapping(value = "/getHospitais", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getHospitais(@RequestBody String cpf) {

        Set<InstituicaoNotificadora> setInstituicoesNotificadoras;
        List<Mensagem> listaMensagem = new ArrayList<Mensagem>();
        Mensagem mensagem;

        try {
            setInstituicoesNotificadoras = aplPrincipal.obterInstituicoesNotificadorasPorCpf(cpf);

            for (InstituicaoNotificadora instituicaoNotificadora : setInstituicoesNotificadoras) {
                mensagem = new Mensagem();
                
                mensagem.setDado(instituicaoNotificadora.getNome());
                mensagem.setId(instituicaoNotificadora.getId().toString());

                listaMensagem.add(mensagem);
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }
        
        return new ResponseEntity<List<Mensagem>>(listaMensagem, HttpStatus.OK);
    }
}
