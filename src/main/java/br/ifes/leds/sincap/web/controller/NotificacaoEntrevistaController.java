/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.ViolacaoDeRIException;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Breno Grillo
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_ENTREVISTA)
@SessionScoped
public class NotificacaoEntrevistaController {

    @Autowired
    private AplEndereco aplEndereco;
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    private Utility utility = Utility.getInstance();

    @RequestMapping(method = RequestMethod.GET)
    public String loadListObitoAguardandoEntrevista(ModelMap model) {
        List<ProcessoNotificacaoDTO> processos = aplProcessoNotificacao.retornarNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOENTREVISTA);
        model.addAttribute("listaProcessosNotificacao", processos);
        return "entrevista";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.POST)
    public String loadFormEntrevista(ModelMap model, @RequestParam("id") Long id) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);

            utility.preencherEstados(model, aplEndereco);
            model.addAttribute("processo", processo);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utility.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utility.getEstadoCivilSelectItem());

        } catch (Exception e) {

        }

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarEntrevista(HttpSession session,
                                   @ModelAttribute ProcessoNotificacaoDTO processo,
                                   @RequestParam("doacaoAutorizada") boolean doacaoAutorizada,
                                   @RequestParam("dataDeAbertura") String dataAbertura,
                                   @RequestParam("dataEntrevista") String dataEntrevista,
                                   @RequestParam("horaEntrevista") String horaEntrevista) throws ParseException {
        try {
            UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
            setUpProcesso(processo, doacaoAutorizada, dataAbertura, usuarioSessao);
            try {
                Calendar dataEntrevistaCalendar = utilityEntities.stringToCalendar(dataEntrevista, horaEntrevista);
                processo.getEntrevista().setDataEntrevista(dataEntrevistaCalendar); //Seta a dataEntrevista que estava nula
            } catch (ParseException e) {

            }
            aplProcessoNotificacao.salvarEntrevista(processo, usuarioSessao.getIdUsuario());

        } catch (ViolacaoDeRIException e) {

        }

        return "redirect:" + ContextUrls.INDEX;
    }

    private void setUpProcesso(ProcessoNotificacaoDTO processo, boolean doacaoAutorizada, String dataAbertura, UsuarioSessao usuarioSessao) throws ParseException {
        Calendar dataAberturaCalendar = Calendar.getInstance();
        dataAberturaCalendar.setTime((new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataAbertura)));

        processo.setDataAbertura(dataAberturaCalendar);
        processo.getEntrevista().setFuncionario(usuarioSessao.getIdUsuario());
        processo.getEntrevista().setDoacaoAutorizada(doacaoAutorizada);
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.recusarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.ARQUIVAR, method = RequestMethod.POST)
    public String arquivarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.finalizarProcesso(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
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
     * @param model
     * @param idProcesso ID do ProcessoNotificacao
     * @return
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + "/{idProcesso}", method = RequestMethod.GET)
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
     * @return
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarAnalise(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Confirmar a análise do óbito.
        aplProcessoNotificacao.validarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
    }
}
