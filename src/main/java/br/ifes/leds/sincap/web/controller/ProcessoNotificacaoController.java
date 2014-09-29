package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(ContextUrls.APP_PROCESSO)
public class ProcessoNotificacaoController {

    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;

    @RequestMapping(value = ContextUrls.ARQUIVAR, method = RequestMethod.POST)
    public String arquivarProcesso(ModelMap model, @RequestParam("id") Long id, HttpSession secao) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) secao.getAttribute("user");

        aplProcessoNotificacao.arquivarProcessoNotificacao(id,usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.INDEX + "?sucessoExcluir=true";
    }

    @RequestMapping(value = ContextUrls.EXCLUIR, method = RequestMethod.POST)
    public String excluirProcesso(HttpSession session, @RequestParam("idProcesso") Long id){
        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");

        ProcessoNotificacaoDTO processo = aplProcessoNotificacao.obter(id);

        aplProcessoNotificacao.excluirProcesso(processo, usuarioSessao.getIdUsuario());

        return "redirect:" + ContextUrls.APP_BUSCAR + ContextUrls.BUSCAR_TODOS + "?sucessoExcluir=true";
    }

    @RequestMapping(value = ContextUrls.EXIBIR, method = RequestMethod.GET)
    public String exibirProcesso(ModelMap model, @RequestParam("idProcesso") Long id){

        ProcessoNotificacao processo = aplProcessoNotificacao.getProcessoNotificacao(id);

        model.addAttribute("processo", processo);
        model.addAttribute("obito", true);

        verificaEntrevista(model, processo);
        verificaCaptacao(model, processo);

        return "exibir-processo-notificacao";
    }

    private void verificaCaptacao(ModelMap model, ProcessoNotificacao processo) {
        if(processo.getCaptacao() != null){
            model.addAttribute("entrevista", true);
        }else{
            model.addAttribute("captacao", false);
        }
    }

    private void verificaEntrevista(ModelMap model, ProcessoNotificacao processo) {
        if(processo.getEntrevista() != null){
            model.addAttribute("entrevista", true);
        }else{
            model.addAttribute("entrevista", false);
        }
    }
}
