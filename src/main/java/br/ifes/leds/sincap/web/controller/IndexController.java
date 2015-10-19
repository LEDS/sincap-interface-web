/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.util.List;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum.*;
import static br.ifes.leds.sincap.web.controller.ContextUrls.INDEX;
import static br.ifes.leds.sincap.web.utility.UtilityWeb.authoritiesSetToStringList;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controla os eventos da página principal.
 *
 * @author Lucas Possatti
 */
@Controller
@RequestMapping(INDEX)
@SessionScoped
public class IndexController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;

    /**
     * Exibe a página principal.
     */
    @DefaultTimeZone
    @RequestMapping(method = GET)
    public String index(ModelMap model, HttpSession session,
                        @RequestParam(value = "sucessoObito", defaultValue = "false") boolean sucessoObito,
                        @RequestParam(value = "obitoConfirmado", defaultValue = "false") boolean obitoConfirmado,
                        @RequestParam(value = "obitoRecusado", defaultValue = "false") boolean obitoRecusado,
                        @RequestParam(value = "idEntrevista", required = false) Long idEntrevista,
                        @RequestParam(value = "entrevistaConfirmada", defaultValue = "false") boolean entrevistaConfirmada,
                        @RequestParam(value = "entrevistaRecusada", defaultValue = "false") boolean entrevistaRecusada,
                        @RequestParam(value = "realizada", defaultValue = "false") boolean entrevistaRealizada,
                        @RequestParam(value = "sucessoArquivamento", defaultValue = "false") boolean sucessoArquivamento,
                        @RequestParam(value = "captacaoSucesso", defaultValue = "false") boolean captacaoSucesso,
                        @RequestParam(value = "captacaoConfirmado", defaultValue = "false") boolean captacaoConfirmado,
                        @RequestParam(value = "captacaoRecusado", defaultValue = "false") boolean captacaoRecusado,
                        @RequestParam(value = "doacaoNaoAutorizada", defaultValue = "false") boolean doacaoNaoAutorizada){


        model.addAttribute("sucessoObito", sucessoObito);
        model.addAttribute("obitoConfirmado", obitoConfirmado);
        model.addAttribute("obitoRecusado", obitoRecusado);
        model.addAttribute("idEntrevista", idEntrevista);
        model.addAttribute("sucessoEntrevista", idEntrevista != null? true:false);
        model.addAttribute("entrevistaRealizada", entrevistaRealizada);
        model.addAttribute("entrevistaConfirmada", entrevistaConfirmada);
        model.addAttribute("entrevistaRecusada", entrevistaRecusada);
        model.addAttribute("captacaoSucesso", captacaoSucesso);
        model.addAttribute("captacaoConfirmado", captacaoConfirmado);
        model.addAttribute("captacaoRecusado", captacaoRecusado);
        model.addAttribute("sucessoArquivamento", sucessoArquivamento);
        model.addAttribute("doacaoNaoAutorizada", doacaoNaoAutorizada);

        return "index";
    }

}
