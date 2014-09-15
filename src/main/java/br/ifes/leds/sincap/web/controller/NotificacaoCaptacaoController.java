package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 20102bsi0553
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_CAPTACAO)
@SessionScoped
public class NotificacaoCaptacaoController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    AplCadastroInterno aplCadastroInterno;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;

    @RequestMapping(method = RequestMethod.GET)
    public String loadListEntrevistaAguardandoCaptacao(ModelMap model) {
        List<ProcessoNotificacaoDTO> processos = aplProcessoNotificacao.
                retornarNotificacaoPorEstadoAtual(EstadoNotificacaoEnum.AGUARDANDOCAPTACAO);

        model.addAttribute("listaProcessosNotificacao", processos);
        return "captacao";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.POST)
    public String loadFormCaptacao(ModelMap model, @RequestParam("id") Long id) {
        try {
            ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);
            model.addAttribute("listaProblemasLogisticos", getListaProblemaLogisticoSelectItem(TipoNaoDoacao.PROBLEMAS_LOGISTICOS));
            model.addAttribute("processo", processo);

        } catch (Exception e) {

        }

        return "form-notificacao-captacao";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarCaptacao(HttpSession session,
                                 @ModelAttribute ProcessoNotificacaoDTO processo,
                                 @RequestParam("captacaoRealizada") boolean captacaoRealizada,
                                 @RequestParam("dataCaptacao") String dataCaptacao,
                                 @RequestParam("horarioCaptacao") String horarioCaptacao) throws ParseException {
        //Processo de notificacao vem incompleto, logo, ele deve ser buscado novamente
        try {
            processo.getCaptacao().setDataCaptacao(utilityEntities.stringToCalendar(dataCaptacao, horarioCaptacao));
        } catch (ParseException e) {
            processo.getCaptacao().setDataCaptacao(null);
        }

        processo.getCaptacao().setCaptacaoRealizada(captacaoRealizada);

        aplProcessoNotificacao.salvarCaptacao(processo.getId(), processo.getCaptacao(), ((UsuarioSessao) session.getAttribute("user")).getIdUsuario());
        return "redirect:" + ContextUrls.INDEX;
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
        ProcessoNotificacao processo = aplProcessoNotificacao
                .getProcessoNotificacao(idProcesso);

        // Adiciona o processo ao modelo da página.
        model.addAttribute("processo", processo);
        model.addAttribute("captacao", true);

        return "analise-processo-notificacao";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarCaptacao(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.recusarAnaliseCaptacao(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
    }

    @RequestMapping(value = ContextUrls.CORRIGIR + "/{idProcesso}", method = RequestMethod.GET)
    public String corrigirCaptacao(ModelMap model, @PathVariable Long idProcesso) {
        ProcessoNotificacao processo = aplProcessoNotificacao.getProcessoNotificacao(idProcesso);

        model.addAttribute("listaProblemasLogisticos", getListaProblemaLogisticoSelectItem(TipoNaoDoacao.PROBLEMAS_LOGISTICOS));
        model.addAttribute("processo", processo);
        model.addAttribute("captacao", true);

        return "form-correcao-notificacao-captacao";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarCaptacao(@RequestParam("id") Long idProcesso, HttpSession session) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        aplProcessoNotificacao.confirmarAnaliseCaptacao(idProcesso, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
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
