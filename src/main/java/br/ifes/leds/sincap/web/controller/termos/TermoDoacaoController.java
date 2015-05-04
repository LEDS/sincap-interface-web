package br.ifes.leds.sincap.web.controller.termos;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.faces.bean.SessionScoped;

import java.util.List;

import static br.ifes.leds.sincap.web.controller.ContextUrls.*;

@Controller
@RequestMapping(RELATORIOS + APP_NOTIFICACAO_ENTREVISTA + RLT_TERMO_AUTORIZACAO_DOACAO)
@SessionScoped
public class TermoDoacaoController extends TermoTemplate {

    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;

    @Override
    public String viewBusca() {
        return "termo-de-autorizacao";
    }

    @Override
    public String viewTermo() {
        return "form-termo-de-autorizacao";
    }

    @Override
    public List<ProcessoNotificacao> consultaBancoDados(String string) {
        return aplProcessoNotificacao.obterPorPacienteNomeComEntrevistaDoacaoAutorizada(string);
    }
}
