package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
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

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.PROBLEMAS_LOGISTICOS;
import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author 20102bsi0553
 */
@Controller
@RequestMapping(APP_NOTIFICACAO_CAPTACAO)
@SessionScoped
public class NotificacaoCaptacaoController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    AplCadastroInterno aplCadastroInterno;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(value = ADICIONAR + "/{idProcesso}", method = GET)
    public String loadFormCaptacaoGetMethod(ModelMap model, @PathVariable Long idProcesso) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);
            model.addAttribute("listaProblemasLogisticos", getListaProblemaLogisticoSelectItem(PROBLEMAS_LOGISTICOS));
            model.addAttribute("processo", processo);
        } catch (Exception ignored) {
        }

        return "form-notificacao-captacao";
    }

    @RequestMapping(value = ADICIONAR, method = POST)
    public String loadFormCaptacao(ModelMap model, @RequestParam("id") Long id) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);
            model.addAttribute("listaProblemasLogisticos", getListaProblemaLogisticoSelectItem(PROBLEMAS_LOGISTICOS));
            model.addAttribute("processo", processo);
        } catch (Exception ignored) {
        }

        return "form-notificacao-captacao";
    }

    @RequestMapping(value = SALVAR, method = POST)
    public String salvarCaptacao(HttpSession session, ModelMap model,
                                 @ModelAttribute ProcessoNotificacaoDTO processo,
                                 @RequestParam("captacaoRealizada") boolean captacaoRealizada,
                                 @RequestParam("dataCaptacao") String dataCaptacao,
                                 @RequestParam("horarioCaptacao") String horarioCaptacao) throws ParseException {
        //Processo de notificacao vem incompleto, logo, ele deve ser buscado novamente
        try {
            UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
            processo.getCaptacao().setDataCaptacao(utilityEntities.stringToCalendar(dataCaptacao, horarioCaptacao));
            processo.getCaptacao().setCaptacaoRealizada(captacaoRealizada);

            aplProcessoNotificacao.salvarCaptacao(processo.getId(), processo.getCaptacao(), usuarioSessao.getIdUsuario());
        } catch (ConstraintViolationException e) {
            setUpConstraintViolations(model, processo, captacaoRealizada, dataCaptacao, horarioCaptacao, e);
            return "form-notificacao-captacao";
        } catch (TransactionSystemException e) {
            setUpConstraintViolations(model, processo, captacaoRealizada, dataCaptacao, horarioCaptacao,
                    (ConstraintViolationException) e.getRootCause());
            return "form-notificacao-captacao";
        } catch (ParseException ignored) {
        }

        return "redirect:" + INDEX + "?captacaoSucesso=true";
    }

    private void setUpConstraintViolations(ModelMap model, ProcessoNotificacaoDTO processo, boolean captacaoRealizada,
                                           String dataCaptacao, String horarioCaptacao, ConstraintViolationException e) {

        utilityWeb.addConstraintViolations(e, model);
        addAttributesToModel(processo, model, captacaoRealizada, dataCaptacao, horarioCaptacao);
    }

    private void addAttributesToModel(ProcessoNotificacaoDTO processo, ModelMap model, boolean captacaoRealizada, String dataCaptacao, String horarioCaptacao) {
        model.addAttribute("processo", processo);
        model.addAttribute("captacaoRealizada", captacaoRealizada);
        model.addAttribute("dataCaptacao", dataCaptacao);
        model.addAttribute("horarioCaptacao", horarioCaptacao);
    }

    /**
     * Fornece a página para análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + "/{idProcesso}", method = GET)
    public String analisar(ModelMap model, @PathVariable Long idProcesso) {
        // Pega o processo do banco.
        ProcessoNotificacao processo = aplProcessoNotificacao
                .getProcessoNotificacao(idProcesso);

        // Adiciona o processo ao modelo da página.
        model.addAttribute("processo", processo);
        model.addAttribute("captacao", true);

        return "analise-processo-notificacao";
    }

    @RequestMapping(value = APP_ANALISAR + RECUSAR, method = POST)
    public String recusarCaptacao(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.recusarAnaliseCaptacao(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?captacaoRecusado=true";
    }

    @RequestMapping(value = EDITAR + "/{idProcesso}", method = GET)
    public String editarCaptacao(ModelMap model, @PathVariable Long idProcesso) {
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        model.addAttribute("listaProblemasLogisticos", getListaProblemaLogisticoSelectItem(PROBLEMAS_LOGISTICOS));

        String dataHora = utilityEntities.calendarDataToString(processo.getCaptacao().getDataCaptacao());
        addAttributesToModel(processo, model, processo.getCaptacao().isCaptacaoRealizada(), dataHora, dataHora);

        return "form-notificacao-captacao";
    }

    @RequestMapping(value = APP_ANALISAR + CONFIRMAR, method = POST)
    public String confirmarCaptacao(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.confirmarAnaliseCaptacao(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?captacaoConfirmado=true";
    }

    private List<SelectItem> getListaProblemaLogisticoSelectItem(TipoNaoDoacao tipo) {
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
