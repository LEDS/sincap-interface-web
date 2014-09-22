/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Breno Grillo
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_ENTREVISTA)
@SessionScoped
public class NotificacaoEntrevistaController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(method = RequestMethod.GET)
    @DefaultTimeZone
    public String loadListObitoAguardandoEntrevista(ModelMap model) {
        List<ProcessoNotificacaoDTO> processos = aplProcessoNotificacao.retornarNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOENTREVISTA);
        model.addAttribute("listaProcessosNotificacao", processos);
        return "entrevista";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.POST)
    public String loadFormEntrevista(ModelMap model, @RequestParam("id") Long id) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);

            utilityWeb.preencherEstados(model);
            model.addAttribute("processo", processo);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
            model.addAttribute("recusaFamiliar", (long) 0);
            model.addAttribute("problemasEstruturais", (long) 0);

        } catch (Exception ignored) {

        }

        return "form-entrevista";
    }

    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarEntrevista(ModelMap model,
                                   HttpSession session,
                                   @ModelAttribute("processo") ProcessoNotificacaoDTO processo,
                                   @RequestParam("doacaoAutorizada") boolean doacaoAutorizada,
                                   @RequestParam("entrevistaRealizada") boolean entrevistaRealizada,
                                   @RequestParam("dataEntrevista") String dataEntrevista,
                                   @RequestParam("horaEntrevista") String horaEntrevista,
                                   @RequestParam("recusaFamiliar") Long recusaFamiliar,
                                   @RequestParam("problemasEstruturais") Long problemasEstruturais)  {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        try {
            if(entrevistaRealizada){
                processo.setCausaNaoDoacao(recusaFamiliar);
            }
            else{
                processo.setCausaNaoDoacao(problemasEstruturais);
            }

            if(!dataEntrevista.trim().isEmpty() || !horaEntrevista.trim().isEmpty()) {
                processo.getEntrevista().setDataEntrevista(utilityEntities.stringToCalendar(dataEntrevista, horaEntrevista));
            }

            processo.getEntrevista().setEntrevistaRealizada(entrevistaRealizada);
            processo.getEntrevista().setDoacaoAutorizada(doacaoAutorizada);
            processo.getEntrevista().setFuncionario(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarEntrevista(processo.getId(), processo.getEntrevista(), usuarioSessao.getIdUsuario());

        }  catch (ConstraintViolationException e) {
            utilityWeb.addConstraintViolations(e, model);
            model.addAttribute("processo",processo);
            model.addAttribute("doacaoAutorizada",doacaoAutorizada);
            model.addAttribute("entrevistaRealizada",entrevistaRealizada);
            model.addAttribute("dataEntrevista",dataEntrevista);
            model.addAttribute("horaEntrevista",horaEntrevista);
            model.addAttribute("recusaFamiliar",recusaFamiliar);
            model.addAttribute("problemasEstruturais",problemasEstruturais);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
            utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);



            return "form-entrevista";
        } catch (TransactionSystemException e) {
            utilityWeb.addConstraintViolations((ConstraintViolationException) e.getRootCause(), model);
            model.addAttribute("processo",processo);
            model.addAttribute("doacaoAutorizada",doacaoAutorizada);
            model.addAttribute("entrevistaRealizada",entrevistaRealizada);
            model.addAttribute("dataEntrevista",dataEntrevista);
            model.addAttribute("horaEntrevista",horaEntrevista);
            model.addAttribute("recusaFamiliar",recusaFamiliar);
            model.addAttribute("problemasEstruturais",problemasEstruturais);
            model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.RECUSA_FAMILIAR));
            model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
            utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);

            return "form-entrevista";
        } catch (ParseException ignored) {
        }

        return "redirect:" + ContextUrls.INDEX + "?sucessoEntrevista=true&idEntrevista=" + processo.getId();
    }

    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.EDITAR + "/{idProcesso}", method = RequestMethod.GET)
    public String editarNotificacaoEntrevista(ModelMap model,
                                         @PathVariable Long idProcesso) {

        ProcessoNotificacaoDTO processo = aplProcessoNotificacao
                .obter(idProcesso);

        model.addAttribute("dataEntrevista", processo.getEntrevista().getDataEntrevista());
        model.addAttribute("horaEntrevista", processo.getEntrevista().getDataEntrevista());

        utilityWeb.preencherEndereco(processo.getEntrevista().getResponsavel().getEndereco(), model);

        model.addAttribute("listaAspectoEstrutural", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
        model.addAttribute("listaRecusaFamiliar", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.RECUSA_FAMILIAR));
        model.addAttribute("listaParentescos", utilityWeb.getParentescoSelectItem());
        model.addAttribute("listaEstadosCivis", utilityWeb.getEstadoCivilSelectItem());
        model.addAttribute("recusaFamiliar", processo.getCausaNaoDoacao());
        model.addAttribute("problemasEstruturais", processo.getCausaNaoDoacao());
        model.addAttribute("processo", processo);

        model.addAttribute("entrevistaRealizada", processo.getEntrevista().isEntrevistaRealizada());
        model.addAttribute("doacaoAutorizada", processo.getEntrevista().isDoacaoAutorizada());

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarEntrevista(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.recusarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX + "?entrevistaRecusada=true";
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
     * @param idProcesso ID do ProcessoNotificacao
     */
    @DefaultTimeZone
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
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarAnalise(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Confirmar a análise do óbito.
        aplProcessoNotificacao.validarAnaliseEntrevista(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX + "?entrevistaConfirmada=true";
    }
}
