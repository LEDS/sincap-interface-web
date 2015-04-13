package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
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
import java.util.List;
import java.util.Map;

import static br.ifes.leds.sincap.web.utility.UtilityWeb.authoritiesSetToStringList;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Created by marcosdias on 26/09/14.
 */
@Controller
@RequestMapping(ContextUrls.APP_BUSCAR)
@SessionScoped
public class ComentariosController {

    @RequestMapping(value="/salvarComentario", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Map>> getNotificarInteressados(@RequestParam("idProcesso") Long idProcesso,
                                                              @RequestParam("descricao") String descricao,
                                                              HttpSession session) {
        List<String> autoridades = authoritiesSetToStringList(getContext().getAuthentication().getAuthorities());
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        return new ResponseEntity<>(OK);
    }
}
