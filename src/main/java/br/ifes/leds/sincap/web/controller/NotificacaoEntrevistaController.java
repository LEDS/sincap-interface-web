/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.ViolacaoDeRIException;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Comentario;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ComentarioDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.dozer.Mapper;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private Mapper mapper;

    @RequestMapping(value = ADICIONAR + "/{idProcesso}", method = GET)
    public String loadFormEntrevistaGetMethod(ModelMap model, @PathVariable Long idProcesso) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

            utilityWeb.preencherEstados(model);
            model.addAttribute("processo", processo);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
            model.addAttribute("recusaFamiliar", (long) 0);
            model.addAttribute("problemasEstruturais", (long) 0);
            model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
            model.addAttribute("grauEscolaridade", utilityWeb.getEscolaridadeSelectItem());
            model.addAttribute("menorIdade",utilityWeb.getIdade(processo.getObito().getPaciente().getDataNascimento().getTime(),processo.getObito().getDataObito().getTime())< 18);

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
                                   @RequestParam(value = "recusaFamiliar", defaultValue = "") Long recusaFamiliar,
                                   @RequestParam(value = "problemasEstruturais", defaultValue = "") Long problemasEstruturais,
                                   @ModelAttribute("processo") ProcessoNotificacaoDTO processo,
                                   @RequestParam(value = "descricaoComentario",defaultValue = "") String descricaoComentario) {

        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        if(!descricaoComentario.isEmpty()) {
            String momento = EstadoNotificacaoEnum.AGUARDANDOENTREVISTA.toString();
            ComentarioDTO comentario = utilityWeb.criarComentario(momento, descricaoComentario, usuarioSessao);
            processo.getComentarios().add(comentario);
        }

        if (dataEntrevista != null && horaEntrevista != null) {
            processo.getEntrevista().setDataEntrevista(dataEntrevista.toDateTime(horaEntrevista).toCalendar(Locale.getDefault()));
        }

        if (!processo.getEntrevista().isEntrevistaRealizada()) {
            processo.setCausaNaoDoacao(problemasEstruturais);
            processo.getEntrevista().setResponsavel(null);
            processo.getEntrevista().setResponsavel2(null);
            processo.getEntrevista().setTestemunha1(null);
            processo.getEntrevista().setTestemunha2(null);
            processo.getEntrevista().setDataEntrevista(null);
        } else if (!processo.getEntrevista().isDoacaoAutorizada()) {
            processo.setCausaNaoDoacao(recusaFamiliar);
            processo.getEntrevista().setResponsavel(null);
            processo.getEntrevista().setResponsavel2(null);
            processo.getEntrevista().setTestemunha1(null);
            processo.getEntrevista().setTestemunha2(null);
        }

        try {
            processo.getEntrevista().setFuncionario(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarEntrevista(processo, usuarioSessao.getIdUsuario());
        } catch (ViolacaoDeRIException e) {
            addAtributosIniciais(model, processo);
            utilityWeb.addConstraintViolations(e.getConstraintViolations(), model);
            if (processo.getEntrevista().getResponsavel() != null && processo.getEntrevista().getResponsavel().getEndereco() != null) {
                utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);
            }
            model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
            model.addAttribute("grauEscolaridade", utilityWeb.getEscolaridadeSelectItem());

            return "form-entrevista";
        }

        if(processo.getEntrevista().isDoacaoAutorizada()) {
            return "redirect:" + INDEX + "?sucessoEntrevista=true&idEntrevista=" + processo.getId()  + "&realizada=true";

        } else {
            return "redirect:" + INDEX + "?doacaoNaoAutorizada=true&idEntrevista=" + processo.getId()  + "&realizada=" + processo.getEntrevista().isEntrevistaRealizada();
        }
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

        if (processo.getEntrevista().getResponsavel() != null && processo.getEntrevista().getResponsavel().getEndereco() != null) {
            utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);
        } else {
            utilityWeb.preencherEstados(model);
        }

        model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(PROBLEMAS_ESTRUTURAIS));
        model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(RECUSA_FAMILIAR));
        model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
        model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
        model.addAttribute("recusaFamiliar", processo.getCausaNaoDoacao());
        model.addAttribute("problemasEstruturais", processo.getCausaNaoDoacao());
        model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
        model.addAttribute("processo", processo);

        model.addAttribute("entrevistaRealizada", processo.getEntrevista().isEntrevistaRealizada());
        model.addAttribute("doacaoAutorizada", processo.getEntrevista().isDoacaoAutorizada());

        if (processo.getObito().getPaciente().getDataNascimento() != null) {
            model.addAttribute("menorIdade", utilityWeb.getIdade(processo.getObito().getPaciente().getDataNascimento().getTime(), processo.getObito().getDataObito().getTime()) < 18);
        }
        model.addAttribute("grauEscolaridade", utilityWeb.getEscolaridadeSelectItem());

        return "form-entrevista";
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
        if (processo.getObito().getPaciente().getDataNascimento() != null) {
            model.addAttribute("menorIdade", utilityWeb.getIdade(processo.getObito().getPaciente().getDataNascimento().getTime(), processo.getObito().getDataObito().getTime()) < 18);
        }

        return "analise-processo-notificacao";
    }

    /**
     * Confirma a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + CONFIRMAR, method = POST)
    public String confirmarAnalise(@RequestParam("id") Long idProcesso, HttpSession session,
                                   @RequestParam(value = "descricaoComentario",defaultValue = "") String descricaoComentario) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        if(!descricaoComentario.isEmpty()) {
            String momento = EstadoNotificacaoEnum.EMANALISEENTREVISTA.toString();
            ComentarioDTO comentario = utilityWeb.criarComentario(momento, descricaoComentario, usuarioSessao);

            aplProcessoNotificacao.salvarComentario(idProcesso, mapper.map(comentario, Comentario.class));
        }

        aplProcessoNotificacao.validarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?entrevistaConfirmada=true";
    }

    /**
     * Recusa a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + RECUSAR, method = POST)
    public String recusarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session,
                                    @RequestParam(value = "descricaoComentario",defaultValue = "") String descricaoComentario) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        if(!descricaoComentario.isEmpty()) {
            String momento = EstadoNotificacaoEnum.EMANALISEENTREVISTA.toString();
            ComentarioDTO comentario = utilityWeb.criarComentario(momento, descricaoComentario, usuarioSessao);

            aplProcessoNotificacao.salvarComentario(idProcesso, mapper.map(comentario, Comentario.class));
        }

        aplProcessoNotificacao.recusarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());
        return "redirect:" + INDEX + "?entrevistaRecusada=true";
    }

    /**
     * Envia o processo para arquivamento
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + ARQUIVAR, method = POST)
    public String arquivarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session,
                                     @RequestParam(value = "descricaoComentario",defaultValue = "") String descricaoComentario) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        if(!descricaoComentario.isEmpty()) {
            String momento = EstadoNotificacaoEnum.EMANALISEENTREVISTA.toString();
            ComentarioDTO comentario = utilityWeb.criarComentario(momento, descricaoComentario, usuarioSessao);

            aplProcessoNotificacao.salvarComentario(idProcesso, mapper.map(comentario, Comentario.class));
        }
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
}
