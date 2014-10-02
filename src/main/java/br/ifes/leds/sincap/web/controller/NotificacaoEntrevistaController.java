/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS;
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.RECUSA_FAMILIAR;
import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Breno Grillo
 */
@Controller
@RequestMapping(APP_NOTIFICACAO_ENTREVISTA)
@SessionScoped
public class NotificacaoEntrevistaController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(value = ADICIONAR, method = POST)
    public String loadFormEntrevista(ModelMap model, @RequestParam("id") Long id) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);

            utilityWeb.preencherEstados(model);
            model.addAttribute("processo", processo);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
            model.addAttribute("recusaFamiliar", (long) 0);
            model.addAttribute("problemasEstruturais", (long) 0);

        } catch (Exception ignored) {

        }

        return "form-entrevista";
    }

    @DefaultTimeZone
    @RequestMapping(value = SALVAR, method = POST)
    public String salvarEntrevista(ModelMap model,
                                   HttpSession session,
                                   @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("dataEntrevista") LocalDate dataEntrevista,
                                   @DateTimeFormat(pattern = "HH:mm") @RequestParam("horaEntrevista") LocalTime horaEntrevista,
                                   @RequestParam("recusaFamiliar") Long recusaFamiliar,
                                   @RequestParam("problemasEstruturais") Long problemasEstruturais,
                                   @RequestParam("paciente.nome") String nomePaciente,
                                   @Valid @ModelAttribute("processo") ProcessoNotificacaoDTO processo,
                                   BindingResult bindingResult) {

        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        processo.getEntrevista().setDataEntrevista(dataEntrevista.toDateTime(horaEntrevista).toCalendar(Locale.getDefault()));

        if (processo.getEntrevista().isEntrevistaRealizada()) {
            processo.setCausaNaoDoacao(recusaFamiliar);
        } else {
            processo.setCausaNaoDoacao(problemasEstruturais);
        }

        if (!bindingResult.hasErrors()) {
            processo.getEntrevista().setFuncionario(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarEntrevista(processo, usuarioSessao.getIdUsuario());
        } else {
            model.addAttribute("nomePaciente", nomePaciente);
            addAtributosIniciais(model, processo);
            utilityWeb.addConstraintViolations(bindingResult.getFieldErrors(), model);
            utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);

            return "form-entrevista";
        }

        return "redirect:" + INDEX + "?sucessoEntrevista=true&idEntrevista=" + processo.getId();
    }

    private void addAtributosIniciais(ModelMap model, ProcessoNotificacaoDTO processo) {
        model.addAttribute("processo", processo);
        model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(PROBLEMAS_ESTRUTURAIS));
        model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(RECUSA_FAMILIAR));
        model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
        model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
    }

    @DefaultTimeZone
    @RequestMapping(value = EDITAR + "/{idProcesso}", method = GET)
    public String editarNotificacaoEntrevista(ModelMap model,
                                              @PathVariable Long idProcesso) {

        ProcessoNotificacaoDTO processo = aplProcessoNotificacao
                .obter(idProcesso);

        utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);

        model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(PROBLEMAS_ESTRUTURAIS));
        model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(RECUSA_FAMILIAR));
        model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
        model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
        model.addAttribute("recusaFamiliar", processo.getCausaNaoDoacao());
        model.addAttribute("problemasEstruturais", processo.getCausaNaoDoacao());
        model.addAttribute("processo", processo);

        model.addAttribute("entrevistaRealizada", processo.getEntrevista().isEntrevistaRealizada());
        model.addAttribute("doacaoAutorizada", processo.getEntrevista().isDoacaoAutorizada());

        return "form-entrevista";
    }

    @RequestMapping(value = APP_ANALISAR + RECUSAR, method = POST)
    public String recusarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.recusarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?entrevistaRecusada=true";
    }

    @RequestMapping(value = APP_ANALISAR + ARQUIVAR, method = POST)
    public String arquivarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.finalizarProcesso(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX;
    }

    private List<SelectItem> getListaCausaNDoacaoSelectItem(TipoNaoDoacao tipo) {
        List<CausaNaoDoacaoDTO> listaCausas = aplCadastroInterno
                .obterCausaNaoDoacao(tipo);
        List<SelectItem> listaCausasSelIt = new ArrayList<>();

        for (CausaNaoDoacaoDTO causa : listaCausas) {
            listaCausasSelIt
                    .add(new SelectItem(causa.getId(), causa.getNome()));
        }

        return listaCausasSelIt;
    }

    /**
     * Fornece a página para análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @DefaultTimeZone
    @RequestMapping(value = APP_ANALISAR + "/{idProcesso}", method = GET)
    public String analisar(ModelMap model, @PathVariable Long idProcesso) {
        // Pega o processo do banco.
        ProcessoNotificacao processo = aplProcessoNotificacao.getProcessoNotificacao(idProcesso);

        // Adiciona o processo ao modelo da página.
        model.addAttribute("processo", processo);
        model.addAttribute("entrevista", true);

        return "analise-processo-notificacao";
    }

    /**
     * Confirma a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + CONFIRMAR, method = POST)
    public String confirmarAnalise(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Confirmar a análise do óbito.
        aplProcessoNotificacao.validarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?entrevistaConfirmada=true";
    }
}
