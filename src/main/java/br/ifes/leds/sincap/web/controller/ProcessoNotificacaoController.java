package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String arquivarProcesso(@RequestParam("id") Long id, HttpSession secao) {
        UsuarioSessao usuarioSessao = (UsuarioSessao) secao.getAttribute("user");
        aplProcessoNotificacao.arquivarProcessoNotificacao(id,usuarioSessao.getIdUsuario());
        return "redirect:" + ContextUrls.INDEX + "/?sucessoArquivamento=true";
    }

    @RequestMapping(value = ContextUrls.EXCLUIR, method = RequestMethod.POST)
    public String excluirProcesso(@RequestParam("id") Long id){

        aplProcessoNotificacao.excluirProcesso(id);
        return "redirect:" + ContextUrls.APP_BUSCAR + ContextUrls.BUSCAR_TODOS + "/?sucessoExcluir=true";
    }
}
