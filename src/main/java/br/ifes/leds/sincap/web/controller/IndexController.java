/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Controla os eventos da página principal.
 *
 * @author Lucas Possatti
 */
@Controller
@RequestMapping(ContextUrls.INDEX)
@SessionScoped
public class IndexController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;

    /**
     * Exibe a página principal.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model,
                        @RequestParam(value = "sucessoObito", defaultValue = "false") boolean sucessoObito,
                        @RequestParam(value = "obitoConfirmado", defaultValue = "false") boolean obitoConfirmado,
                        @RequestParam(value = "obitoRecusado", defaultValue = "false") boolean obitoRecusado,
                        @RequestParam(value = "sucessoArquivamento", defaultValue = "false") boolean sucessoArquivamento) {
        // Puxa os três tipos de notificações corretamente da apl.
        List<ProcessoNotificacao> processosObitoAnalisePendente = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOANALISEOBITO);
        List<ProcessoNotificacao> processosObitoAguardandoCorrecao = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOCORRECAOOBITO);
        List<ProcessoNotificacao> processosEntrevistaAguardandoCorrecao = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOCORRECAOENTREVISTA);
        List<ProcessoNotificacao> processosEntrevistaAnalisePendente = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOANALISEENTREVISTA);
        List<ProcessoNotificacao> processosCaptacoesAnalisePendente = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOANALISECAPTACAO);
        List<ProcessoNotificacao> processosCaptacoesAguardandoCorrecao = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOCORRECAOCAPTACACAO);
        List<ProcessoNotificacao> processosAguardandoArquivamento = aplProcessoNotificacao
                .retornarProcessoNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOARQUIVAMENTO);

        // Adiciona as três listas de notificações à página.
        model.addAttribute("processosObitoAnalisePendente",
                processosObitoAnalisePendente);
        model.addAttribute("processosObitoAguardandoCorrecao",
                processosObitoAguardandoCorrecao);
        model.addAttribute("processosEntrevistaAguardandoCorrecao",
                processosEntrevistaAguardandoCorrecao);
        model.addAttribute("processosEntrevistaAnalisePendente",
                processosEntrevistaAnalisePendente);
        model.addAttribute("processosCaptacoesAnalisePendente",
                processosCaptacoesAnalisePendente);
        model.addAttribute("processosCaptacoesAguardandoCorrecao",
                processosCaptacoesAguardandoCorrecao);
        model.addAttribute("processosAguardandoArquivamento",
                processosAguardandoArquivamento);

        model.addAttribute("sucessoObito", sucessoObito);
        model.addAttribute("obitoConfirmado", obitoConfirmado);
        model.addAttribute("obitoRecusado", obitoRecusado);
        model.addAttribute("sucessoArquivamento", sucessoArquivamento);

        // Chama a página.
        return "index";
    }
}
