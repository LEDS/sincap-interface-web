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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum.*;
import static br.ifes.leds.sincap.web.controller.ContextUrls.INDEX;
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
                        @RequestParam(value = "sucessoEntrevista", defaultValue = "false") boolean sucessoEntrevista,
                        @RequestParam(value = "entrevistaConfirmada", defaultValue = "false") boolean entrevistaConfirmada,
                        @RequestParam(value = "entrevistaRecusada", defaultValue = "false") boolean entrevistaRecusada,
                        @RequestParam(value = "sucessoArquivamento", defaultValue = "false") boolean sucessoArquivamento,
                        @RequestParam(value = "captacaoSucesso", defaultValue = "false") boolean captacaoSucesso,
                        @RequestParam(value = "captacaoConfirmado", defaultValue = "false") boolean captacaoConfirmado,
                        @RequestParam(value = "captacaoRecusado", defaultValue = "false") boolean captacaoRecusado) {

        List<String> autoridades = authoritiesSetToStringList(getContext().getAuthentication().getAuthorities());

        seForNotificador(model, autoridades);
        seForNotificadorOuCaptador(model, autoridades, (UsuarioSessao) session.getAttribute("user"));
        seForCaptador(model, autoridades, (UsuarioSessao) session.getAttribute("user"));
        seForAnalista(model, autoridades);

        model.addAttribute("sucessoObito", sucessoObito);
        model.addAttribute("obitoConfirmado", obitoConfirmado);
        model.addAttribute("obitoRecusado", obitoRecusado);
        model.addAttribute("idEntrevista", idEntrevista);
        model.addAttribute("sucessoEntrevista", sucessoEntrevista);
        model.addAttribute("entrevistaConfirmada", entrevistaConfirmada);
        model.addAttribute("entrevistaRecusada", entrevistaRecusada);
        model.addAttribute("captacaoSucesso", captacaoSucesso);
        model.addAttribute("captacaoConfirmado", captacaoConfirmado);
        model.addAttribute("captacaoRecusado", captacaoRecusado);
        model.addAttribute("sucessoArquivamento", sucessoArquivamento);

        return "index";
    }

    private void seForAnalista(ModelMap model, List<String> autoridades) {
        if (autoridades.contains("ROLE_ANALISTA")) {
            List<ProcessoNotificacao> processosObitoAnalisePendente = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOANALISEOBITO);
            List<ProcessoNotificacao> processosEntrevistaAnalisePendente = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOANALISEENTREVISTA);
            List<ProcessoNotificacao> processosCaptacoesAnalisePendente = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOANALISECAPTACAO);
            List<ProcessoNotificacao> processosAguardandoArquivamento = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOARQUIVAMENTO);

            model.addAttribute("processosObitoAnalisePendente", processosObitoAnalisePendente);
            model.addAttribute("processosEntrevistaAnalisePendente", processosEntrevistaAnalisePendente);
            model.addAttribute("processosCaptacoesAnalisePendente", processosCaptacoesAnalisePendente);
            model.addAttribute("processosAguardandoArquivamento", processosAguardandoArquivamento);
        }
    }

    private void seForCaptador(ModelMap model, List<String> autoridades, UsuarioSessao usuarioSessao) {
        if (autoridades.contains("ROLE_CAPTADOR")) {
            List<ProcessoNotificacao> processosCaptacoesAguardandoCorrecao = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOCORRECAOCAPTACACAO);
            List<ProcessoNotificacaoDTO> processosEntrevistasAguardandoCaptacao = aplProcessoNotificacao.
                    retornarNotificacaoPorEstadoAtualEBancoOlhos(AGUARDANDOCAPTACAO, usuarioSessao.getIdUsuario());

            model.addAttribute("processosCaptacoesAguardandoCorrecao", processosCaptacoesAguardandoCorrecao);
            model.addAttribute("processosEntrevistasAguardandoCaptacao", processosEntrevistasAguardandoCaptacao);
        }
    }

    private void seForNotificadorOuCaptador(ModelMap model, List<String> autoridades, UsuarioSessao usuarioSessao) {
        if (autoridades.contains("ROLE_NOTIFICADOR") || autoridades.contains("ROLE_CAPTADOR")) {
            List<ProcessoNotificacao> processosObitoAguardandoEntrevista = aplProcessoNotificacao
                    .retornarNotificacaoPorEstadoAtualEHospital(AGUARDANDOENTREVISTA, usuarioSessao.getIdHospital());
            List<ProcessoNotificacao> processosEntrevistaAguardandoCorrecao = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOCORRECAOENTREVISTA);

            model.addAttribute("processosObitoAguardandoEntrevista", processosObitoAguardandoEntrevista);
            model.addAttribute("processosEntrevistaAguardandoCorrecao", processosEntrevistaAguardandoCorrecao);
        }
    }

    private void seForNotificador(ModelMap model, List<String> autoridades) {
        if (autoridades.contains("ROLE_NOTIFICADOR")) {
            List<ProcessoNotificacao> processosObitoAguardandoCorrecao = aplProcessoNotificacao
                    .retornarProcessoNotificacaoPorEstadoAtual(AGUARDANDOCORRECAOOBITO);

            model.addAttribute("processosObitoAguardandoCorrecao", processosObitoAguardandoCorrecao);
        }
    }

    private static List<String> authoritiesSetToStringList(Collection<? extends GrantedAuthority> authorities) {
        List<String> auth = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : authorities) {
            auth.add(grantedAuthority.toString());
        }

        return auth;
    }
}
