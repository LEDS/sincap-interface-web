package br.ifes.leds.sincap.web.controller.termos;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.List;

import static br.ifes.leds.sincap.web.controller.ContextUrls.IMPRIMIR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public abstract class TermoTemplate {

    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private Utility utility;
    @Autowired
    private AplEndereco aplEndereco;
    @Autowired
    private AplHospital aplHospital;
    @Autowired
    private AplFuncionario aplFuncionario;

    public abstract String viewBusca();

    public abstract String viewTermo();

    @RequestMapping(method = GET)
    public final String carregarFormTermoDoacao(ModelMap model) {
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterPorPacienteNomeComEntrevistaDoacaoAutorizada("");

        model.addAttribute("listProcessoNotificacao", pn);

        return viewBusca();
    }

    @RequestMapping(method = POST)
    public final String buscarTermoDoacao(ModelMap model, @RequestParam("nome") String nome) {
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterPorPacienteNomeComEntrevistaDoacaoAutorizada(nome);

        model.addAttribute("listProcessoNotificacao", pn);

        return viewBusca();
    }


    @RequestMapping(value = IMPRIMIR + "/{id}", method = GET)
    public final String emitirTermoDoacao(ModelMap model, @PathVariable Long id) {
        ProcessoNotificacaoDTO pn = aplProcessoNotificacao.obter(id);
        String dataObito = utility.calendarDataToString(pn.getObito().getDataObito());
        String horaObito = utility.calendarHoraToString(pn.getObito().getDataObito());
        String cidadePaciente = aplEndereco.obterCidadePorID(pn.getObito().getPaciente().getEndereco().getCidade()).getNome();
        String bairroPaciente = aplEndereco.obterBairroPorID(pn.getObito().getPaciente().getEndereco().getBairro()).getNome();
        String estadoPaciente = aplEndereco.obterEstadosPorID(pn.getObito().getPaciente().getEndereco().getEstado()).getNome();

        Hospital h = aplHospital.obter(pn.getObito().getHospital());
        String hospitalNome = h.getNome();
        String hospitalCidade = h.getEndereco().getCidade().getNome();

        String dataEntrevista = utility.calendarDataToString(pn.getEntrevista().getDataEntrevista());
        String horaEntrevista = utility.calendarHoraToString(pn.getEntrevista().getDataEntrevista());

        //String dataNascimentoResponsavel = utility.calendarDataToString(pn.getEntrevista().getResponsavel().getDataNascimento());
        String cidadeResponsavel = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel().getEndereco().getCidade()).getNome();
        String bairroResponsavel = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel().getEndereco().getBairro()).getNome();
        String estadoResponsavel = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel().getEndereco().getEstado()).getNome();

        if (pn.getEntrevista().getResponsavel2() != null) {
            String cidadeResponsavel2 = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel2().getEndereco().getCidade()).getNome();
            String bairroResponsavel2 = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel2().getEndereco().getBairro()).getNome();
            String estadoResponsavel2 = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel2().getEndereco().getEstado()).getNome();
            String dataNascimentoResponavel2 = utility.calendarDataToString(pn.getEntrevista().getResponsavel2().getDataNascimento());

            Integer idadeResponsavel2 = utility.calculaIdade(pn.getEntrevista().getResponsavel2().getDataNascimento(), Calendar.getInstance());

            model.addAttribute("bairroResponsavel2", bairroResponsavel2);
            model.addAttribute("estadoResponsavel2", estadoResponsavel2);
            model.addAttribute("cidadeResponsavel2", cidadeResponsavel2);
            model.addAttribute("dataNascimentoResponavel2", dataNascimentoResponavel2);
            model.addAttribute("idadeResponsavel2", idadeResponsavel2);
        }
        String nomeFuncinario = aplFuncionario.obter(pn.getEntrevista().getFuncionario()).getNome();

        int idadePaciente = utility.calculaIdade(pn.getObito().getPaciente().getDataNascimento(), pn.getObito().getDataObito());
        Integer idadeResponsavel = utility.calculaIdade(pn.getEntrevista().getResponsavel().getDataNascimento(), Calendar.getInstance());

        model.addAttribute("nomeFuncionario", nomeFuncinario);
        model.addAttribute("bairroResponsavel", bairroResponsavel);
        model.addAttribute("estadoResponsavel", estadoResponsavel);
        model.addAttribute("cidadeResponsavel", cidadeResponsavel);
        //model.addAttribute("dataNascimentoResponsavel", dataNascimentoResponsavel);
        model.addAttribute("idadeResponsavel", idadeResponsavel);
        model.addAttribute("dataEntrevista", dataEntrevista);
        model.addAttribute("horaEntrevista", horaEntrevista);
        model.addAttribute("hospitalNome", hospitalNome);
        model.addAttribute("hospitalCidade", hospitalCidade);
        model.addAttribute("cidadePaciente", cidadePaciente);
        model.addAttribute("bairroPaciente", bairroPaciente);
        model.addAttribute("estadoPaciente", estadoPaciente);
        model.addAttribute("idadePaciente", idadePaciente);
        model.addAttribute("dataObito", dataObito);
        model.addAttribute("horaObito", horaObito);
        model.addAttribute("pn", pn);

        return viewTermo();
    }

}
