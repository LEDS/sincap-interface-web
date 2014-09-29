package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.dto.SetorDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author marcosdias
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_OBITO)
@SessionScoped
public class NotificacaoObitoController {

    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovaNotificacao(ModelMap model, HttpSession session,
                                          @RequestParam(value = "erro", defaultValue = "true") boolean sucessoObito) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        utilityWeb.preencherEstados(model);
        preencherSetorCausaNDoacao(model, usuarioSessao);
        model.addAttribute("sucessoObito", sucessoObito);

        return "form-notificacao-obito";
    }

    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.EDITAR + "/{idProcesso}", method = RequestMethod.GET)
    public String editarNotificacaoObito(ModelMap model, HttpSession session,
                                         @PathVariable Long idProcesso) {

        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        utilityWeb.preencherEndereco(processo.getObito().getPaciente().getEndereco(), model);
        preencherSetorCausaNDoacao(model, usuarioSessao);

        addAttributesToModel(model, processo);

        return "form-notificacao-obito";
    }

    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarFormNovaNotificacao(ModelMap model, HttpSession session,
                                            @ModelAttribute ProcessoNotificacaoDTO processo) {
        try {
            UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
            processo.getObito().setHospital(usuarioSessao.getIdHospital());
            processo.setNotificador(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarNovaNotificacao(processo, usuarioSessao.getIdUsuario());
        } catch (ConstraintViolationException e) {
            setUpConstraintViolations(model, session, processo, e);
            return "form-notificacao-obito";
        } catch (TransactionSystemException e) {
            setUpConstraintViolations(model, session, processo, (ConstraintViolationException) e.getRootCause());
            return "form-notificacao-obito";
        }

        return "redirect:" + ContextUrls.INDEX + "?sucessoObito=true";
    }

    private void setUpConstraintViolations(ModelMap model,
                                           HttpSession session,
                                           ProcessoNotificacaoDTO processo,
                                           ConstraintViolationException e) {
        utilityWeb.addConstraintViolations(e, model);
        utilityWeb.preencherEndereco(processo.getObito().getPaciente().getEndereco(), model);
        preencherSetorCausaNDoacao(model, (UsuarioSessao) session.getAttribute("user"));
        addAttributesToModel(model, processo);
    }

    private void addAttributesToModel(ModelMap model, ProcessoNotificacaoDTO processo) {
        model.addAttribute("processo", processo);
    }

    private void preencherSetorCausaNDoacao(ModelMap model, UsuarioSessao usuarioSessao) {

        model.addAttribute("listaSetor", getListaSetoresSelectItem(usuarioSessao));
        model.addAttribute("listaCausaNaoDoacao",
                getListaCausaNDoacaoSelectItem());
    }

    private List<SelectItem> getListaCausaNDoacaoSelectItem() {
        List<CausaNaoDoacaoDTO> listaCausas = aplCadastroInterno.obterCausaNaoDoacaoContraIndMedica();
        List<SelectItem> listaCausasSelIt = new ArrayList<>();

        for (CausaNaoDoacaoDTO causa : listaCausas) {
            listaCausasSelIt.add(new SelectItem(causa.getId(), causa.getNome()));
        }

        return listaCausasSelIt;
    }

    @RequestMapping(value = ContextUrls.GET_CONTRA_INDICACAO, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getContraIndicacoesMedicas() {
        List<Map<String, String>> contraIndicacoes = new ArrayList<>();

        for (CausaNaoDoacaoDTO c : aplCadastroInterno.obterCausaNaoDoacaoContraIndMedica()) {
            Map<String, String> setor = new HashMap<>();

            setor.put("id", c.getId().toString());
            setor.put("nome", c.getNome());

            contraIndicacoes.add(setor);
        }

        return new ResponseEntity<>(contraIndicacoes, HttpStatus.OK);
    }

    @RequestMapping(value = ContextUrls.GET_SETORES, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getSetores(HttpSession session) {
        List<Map<String, String>> setores = new ArrayList<>();
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        for (SetorDTO s : aplCadastroInterno.obterSetorPorHospital(usuarioSessao.getIdHospital())) {
            Map<String, String> setor = new HashMap<>();

            setor.put("id", s.getId().toString());
            setor.put("nome", s.getNome());

            setores.add(setor);
        }

        return new ResponseEntity<>(setores, HttpStatus.OK);
    }

    private List<SelectItem> getListaSetoresSelectItem(UsuarioSessao usuarioSessao) {
        List<SetorDTO> listaSetor = aplCadastroInterno.obterSetorPorHospital(usuarioSessao.getIdHospital());

        List<SelectItem> listaSetorString = new ArrayList<>();

        for (SetorDTO setor : listaSetor) {
            listaSetorString.add(new SelectItem(setor.getId(), setor.getNome()));
        }
        return listaSetorString;
    }

    /**
     * Fornece a página para análise.
     */
    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.APP_ANALISAR + "/{idProcesso}", method = RequestMethod.GET)
    public String analisarObito(ModelMap model, @PathVariable Long idProcesso) {
        // Pega a notificação do banco.
        ProcessoNotificacao processo = aplProcessoNotificacao.getProcessoNotificacao(idProcesso);
        // Adiciona o processo ao modelo da página.
        model.addAttribute("processo", processo);
        model.addAttribute("obito", true);

        return "analise-processo-notificacao";
    }

    /**
     * Confirma a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarAnaliseObito(HttpSession session, @RequestParam("id") long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Confirmar a análise do óbito.
        aplProcessoNotificacao.validarAnaliseObito(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX + "?obitoConfirmado=true";
    }

    /**
     * Recusa a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarAnaliseObito(HttpSession session, @RequestParam
            ("id") Long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Recusar a notificação do óbito.
        aplProcessoNotificacao.recusarAnaliseObito(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX + "?obitoRecusado=true";
    }

    /**
     * Arquiva a notificação na fase de análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = ContextUrls.APP_ANALISAR + ContextUrls.ARQUIVAR, method = RequestMethod.POST)
    public String arquivarAnaliseObito(HttpSession session, @RequestParam("id") Long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Recusar a notificação do óbito.
        aplProcessoNotificacao.arquivarProcesso(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX;
    }
}
