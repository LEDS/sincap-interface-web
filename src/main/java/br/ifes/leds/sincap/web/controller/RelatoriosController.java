package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by Home on 18/09/2014.
 */
@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatoriosController {
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    Utility utility;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplFuncionario aplFuncionario;

    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA+ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO, method = RequestMethod.GET)
    public String carregarFormTermoDoacao(ModelMap model){
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterTodasNotificacoes();

        model.addAttribute("listProcessoNotificacao", pn);
        //TODO: Substituir pelo endereco do formulario!
        return "termo-de-autorizacao";
    }
    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA+ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO, method = RequestMethod.POST)
    public String buscarTermoDoacao(ModelMap model, @RequestParam("nome") String nome){
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterPorPacienteNome(nome);

        model.addAttribute("listProcessoNotificacao", pn);

        //TODO: Substituir pelo endereco do formulario!
        return "termo-de-autorizacao";
    }
    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA+ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO+ContextUrls.IMPRIMIR+"/{id}", method = RequestMethod.GET)
    public String emitirTermoDoacao(ModelMap model, @PathVariable Long id) {
        ProcessoNotificacaoDTO pn = aplProcessoNotificacao.obter(id);
        String dataObito = utility.calendarDataToString(pn.getObito().getDataObito());
        String horaObito = utility.calendarHoraToString(pn.getObito().getDataObito());
        String dataNascimento = utility.calendarDataToString(pn.getObito().getPaciente().getDataNascimento());
        String cidadePaciente = aplEndereco.obterCidadePorID(pn.getObito().getPaciente().getEndereco().getCidade()).getNome();
        String bairroPaciente = aplEndereco.obterBairroPorID(pn.getObito().getPaciente().getEndereco().getBairro()).getNome();
        String estadoPaciente = aplEndereco.obterEstadosPorID(pn.getObito().getPaciente().getEndereco().getEstado()).getNome();

        Hospital h = aplHospital.obter(pn.getObito().getHospital());
        String hospitalNome = h.getNome();
        String hospitalCidade = h.getEndereco().getCidade().getNome();

        String cidadeResponsavel = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel().getEndereco().getCidade()).getNome();
        String bairroResponsavel = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel().getEndereco().getBairro()).getNome();
        String estadoResponsavel = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel().getEndereco().getEstado()).getNome();

        String cidadeResponsavel2 = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel2().getEndereco().getCidade()).getNome();
        String bairroResponsavel2 = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel2().getEndereco().getBairro()).getNome();
        String estadoResponsavel2 = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel2().getEndereco().getEstado()).getNome();

        String nomeFuncinario = aplFuncionario.obter(pn.getEntrevista().getFuncionario()).getNome();

        int idadePaciente = utility.calculaIdade(pn.getObito().getPaciente().getDataNascimento(), pn.getObito().getDataObito());

        model.addAttribute("nomeFuncionario", nomeFuncinario);
        model.addAttribute("bairroResponsavel",bairroResponsavel);
        model.addAttribute("estadoResponsavel",estadoResponsavel);
        model.addAttribute("cidadeResponsavel", cidadeResponsavel);
        model.addAttribute("bairroResponsavel2",bairroResponsavel2);
        model.addAttribute("estadoResponsavel2",estadoResponsavel2);
        model.addAttribute("cidadeResponsavel2", cidadeResponsavel2);
        model.addAttribute("hospitalNome", hospitalNome);
        model.addAttribute("hospitalCidade", hospitalCidade);
        model.addAttribute("cidadePaciente",cidadePaciente);
        model.addAttribute("bairroPaciente",bairroPaciente);
        model.addAttribute("estadoPaciente",estadoPaciente);
        model.addAttribute("idadePaciente", idadePaciente);
        model.addAttribute("dataObito", dataObito);
        model.addAttribute("horaObito", horaObito);
        model.addAttribute("dataNascimento", dataNascimento);
        model.addAttribute("pn", pn);
        //TODO: Substituir pelo endereco do formulario!
        return "form-termo-de-autorizacao";
    }

}
