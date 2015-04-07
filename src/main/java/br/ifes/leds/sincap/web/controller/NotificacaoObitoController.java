package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.dto.SetorDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplNotificador;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Comentario;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplComentario;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author marcosdias
 */
@Controller
@RequestMapping(APP_NOTIFICACAO_OBITO)
@SessionScoped
public class NotificacaoObitoController {

    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private UtilityWeb utilityWeb;
    @Autowired
    private AplComentario aplComentario;
    @Autowired
    private AplNotificador aplNotificador;

    @RequestMapping(value = ADICIONAR, method = GET)
    public String loadFormNovaNotificacao(ModelMap model, HttpSession session,
                                          @RequestParam(value = "erro", defaultValue = "true") boolean sucessoObito) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        utilityWeb.preencherTipoObito(model);
        utilityWeb.preencherEstados(model);
        preencherSetorCausaNDoacao(model, usuarioSessao);
        model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
        model.addAttribute("sucessoObito", sucessoObito);

        return "form-notificacao-obito";
    }

    @DefaultTimeZone
    @RequestMapping(value = EDITAR + "/{idProcesso}", method = GET)
    public String editarNotificacaoObito(ModelMap model, HttpSession session,
                                         @PathVariable Long idProcesso) {

        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        utilityWeb.preencherTipoObito(model);
        utilityWeb.preencherEndereco(processo.getObito().getPaciente().getEndereco(), model);
        preencherSetorCausaNDoacao(model, usuarioSessao);
        model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
        model.addAttribute("comentarios", aplComentario.obterByProcessoNotificacao(idProcesso));
        addAttributesToModel(model, processo);

        return "form-notificacao-obito";
    }

    @DefaultTimeZone
    @RequestMapping(value = SALVAR, method = POST)
    public String salvarFormNovaNotificacao(ModelMap model, HttpSession session,
                                            @Valid @ModelAttribute ProcessoNotificacaoDTO processo,
                                            BindingResult bindingResult) {
        if (!bindingResult.hasErrors()){
            UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
            processo.getObito().setHospital(usuarioSessao.getIdHospital());
            processo.setNotificador(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarNovaNotificacao(processo, usuarioSessao.getIdUsuario());

        } else {
            setUpConstraintViolations(model, session, processo, bindingResult.getFieldErrors());
            model.addAttribute("tipoDocumentos", utilityWeb.getTipoDocumentoComFotoSelectItem());
            utilityWeb.preencherTipoObito(model);
            return "form-notificacao-obito";
        }

        return "redirect:" + INDEX + "?sucessoObito=true";
    }

    private void setUpConstraintViolations(ModelMap model,
                                           HttpSession session,
                                           ProcessoNotificacaoDTO processo,
                                           List<? extends FieldError> errors) {
        utilityWeb.addConstraintViolations(errors, model);
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

    @RequestMapping(value = GET_CONTRA_INDICACAO, method = GET)
    public ResponseEntity<List<Map<String, String>>> getContraIndicacoesMedicas() {
        List<Map<String, String>> contraIndicacoes = new ArrayList<>();

        for (CausaNaoDoacaoDTO c : aplCadastroInterno.obterCausaNaoDoacaoContraIndMedica()) {
            Map<String, String> setor = new HashMap<>();

            setor.put("id", c.getId().toString());
            setor.put("nome", c.getNome());

            contraIndicacoes.add(setor);
        }

        return new ResponseEntity<>(contraIndicacoes, OK);
    }

    @RequestMapping(value = ContextUrls.GET_SETORES, method = GET)
    public ResponseEntity<List<Map<String, String>>> getSetores(HttpSession session) {
        List<Map<String, String>> setores = new ArrayList<>();
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        for (SetorDTO s : aplCadastroInterno.obterSetorPorHospital(usuarioSessao.getIdHospital())) {
            Map<String, String> setor = new HashMap<>();

            setor.put("id", s.getId().toString());
            setor.put("nome", s.getNome());

            setores.add(setor);
        }

        return new ResponseEntity<>(setores, OK);
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
    @RequestMapping(value = APP_ANALISAR + "/{idProcesso}", method = GET)
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
    @RequestMapping(value = APP_ANALISAR + ContextUrls.CONFIRMAR, method = POST)
    public String confirmarAnaliseObito(HttpSession session, @RequestParam("id") long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Confirmar a análise do óbito.
        aplProcessoNotificacao.validarAnaliseObito(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?obitoConfirmado=true";
    }

    /**
     * Recusa a análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + ContextUrls.RECUSAR, method = POST)
    public String recusarAnaliseObito(HttpSession session, @RequestParam
            ("id") Long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Recusar a notificação do óbito.
        aplProcessoNotificacao.recusarAnaliseObito(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX + "?obitoRecusado=true";
    }

    /**
     * Arquiva a notificação na fase de análise.
     *
     * @param idProcesso ID do ProcessoNotificacao
     */
    @RequestMapping(value = APP_ANALISAR + ARQUIVAR, method = POST)
    public String arquivarAnaliseObito(HttpSession session, @RequestParam("id") Long idProcesso) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        // Pega a notificação do banco.
        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(idProcesso);

        // Recusar a notificação do óbito.
        aplProcessoNotificacao.arquivarProcesso(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + INDEX;
    }
}
