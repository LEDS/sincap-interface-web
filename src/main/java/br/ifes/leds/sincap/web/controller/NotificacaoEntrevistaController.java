/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.ViolacaoDeRIException;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.AtualizacaoEstadoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.Utility;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
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
    private UsuarioSessao usuarioSessao;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    private Utility utility = Utility.getInstance();

    @RequestMapping(method = RequestMethod.GET)
    public String loadListObitoAguardandoEntrevista() {
        return "entrevista";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormEntrevista(ModelMap model) {
        try {
            ProcessoNotificacaoDTO processo = new ProcessoNotificacaoDTO();

            utility.preencherEstados(model, aplEndereco);
            model.addAttribute("processo", processo);
            model.addAttribute("listaCausaNaoDoacao", getListaCausaNDoacaoSelectItem(TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS));
            model.addAttribute("listaParentescos", utility.getParentescoSelectItem());
            model.addAttribute("listaEstadosCivis", utility.getEstadoCivilSelectItem());

        } catch (Exception e) {

        }

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarEntrevista(ModelMap model,
            @ModelAttribute ProcessoNotificacaoDTO processo) {
        try {
//            modificaEstadoAtual(processo,
//                    EstadoNotificacaoEnum.AGUARDANDOANALISEENTREVISTA);
            processo.getEntrevista().setFuncionario(usuarioSessao.getIdUsuario());
            aplProcessoNotificacao.salvarEntrevista(processo, usuarioSessao.getIdUsuario());

        } catch (ViolacaoDeRIException e) {

        }

        return "form-entrevista";
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR, method = RequestMethod.GET)
    public String loadAnalisaNotificacaoEntrevista(@ModelAttribute Long id,
            ModelMap model) {
        try {
            ProcessoNotificacaoDTO pnDTO = aplProcessoNotificacao.obter(id);
            modificaEstadoAtual(pnDTO,
                    EstadoNotificacaoEnum.EMANALISEENTREVISTA);
            aplProcessoNotificacao.salvarEntrevista(pnDTO, usuarioSessao.getIdUsuario());

            model.addAttribute("processoNotificacaoDTO", pnDTO);
        } catch (ViolacaoDeRIException e) {
        }
        return "analise-entrevista";
    }

    @RequestMapping(value = ContextUrls.RECUSAR, method = RequestMethod.POST)
    public String recusarEntrevista(
            @ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO) {
        return finalizarAnaliseEntrevista(processoNotificacaoDTO,
                EstadoNotificacaoEnum.AGUARDANDOENTREVISTA);
    }

    @RequestMapping(value = ContextUrls.CONFIRMAR, method = RequestMethod.POST)
    public String confirmarEntrevista(
            @ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO) {
        return finalizarAnaliseEntrevista(processoNotificacaoDTO,
                EstadoNotificacaoEnum.AGUARDANDOCAPTACAO);
    }

    @RequestMapping(value = ContextUrls.ARQUIVAR, method = RequestMethod.POST)
    public String arquivarEntrevista(
            @ModelAttribute ProcessoNotificacaoDTO processoNotificacaoDTO) {
        return finalizarAnaliseEntrevista(processoNotificacaoDTO,
                EstadoNotificacaoEnum.NOTIFICACAOARQUIVADA);
    }

    private String finalizarAnaliseEntrevista(
            ProcessoNotificacaoDTO processoNotificacaoDTO,
            EstadoNotificacaoEnum ESTADO) {
        modificaEstadoAtual(processoNotificacaoDTO, ESTADO);
        try {
            aplProcessoNotificacao.salvarEntrevista(processoNotificacaoDTO, usuarioSessao.getIdUsuario());
        } catch (ViolacaoDeRIException e) {
        }
        return "analise-entrevista";
    }

    private void modificaEstadoAtual(ProcessoNotificacaoDTO pnDTO,
            EstadoNotificacaoEnum ESTADO) {
        AtualizacaoEstadoDTO atualizacaoEstado = new AtualizacaoEstadoDTO();
        atualizacaoEstado.setEstadoNotificacao(ESTADO);
        atualizacaoEstado
                .setFuncionario(pnDTO.getEntrevista().getFuncionario());
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
