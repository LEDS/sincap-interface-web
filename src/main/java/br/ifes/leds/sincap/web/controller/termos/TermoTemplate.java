package br.ifes.leds.sincap.web.controller.termos;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.gerenciaNotificacao.cgd.ProcessoNotificacaoRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.ifes.leds.sincap.web.controller.ContextUrls.IMPRIMIR;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public abstract class TermoTemplate {

    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private ProcessoNotificacaoRepository processoNotificacaoRepository;
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

    public abstract List<ProcessoNotificacao> consultaBancoDados(String string);

    @RequestMapping(method = GET)
    public final String carregarFormTermoDoacao(ModelMap model) {
        List<ProcessoNotificacao> pn = consultaBancoDados("");

        model.addAttribute("listProcessoNotificacao", pn);

        return viewBusca();
    }

    @RequestMapping(method = POST)
    public final String buscarTermoDoacao(ModelMap model, @RequestParam("nome") String nome) {
        List<ProcessoNotificacao> pn = consultaBancoDados(nome);

        model.addAttribute("listProcessoNotificacao", pn);

        return viewBusca();
    }


    @RequestMapping(value = IMPRIMIR + "/{id}", method = GET)
    public final String emitirTermoDoacao(ModelMap model, @PathVariable Long id) {
        ProcessoNotificacaoDTO pn = aplProcessoNotificacao.obter(id);
        final ProcessoNotificacao pnCompleto = processoNotificacaoRepository.findOne(id);
        String dataObito = utility.calendarDataToString(pn.getObito().getDataObito());
        String horaObito = utility.calendarHoraToString(pn.getObito().getDataObito());
        String cidadePaciente = aplEndereco.obterCidadePorID(pn.getObito().getPaciente().getEndereco().getCidade()).getNome();
        String bairroPaciente = aplEndereco.obterBairroPorID(pn.getObito().getPaciente().getEndereco().getBairro()).getNome();
        String estadoPaciente = aplEndereco.obterEstadosPorID(pn.getObito().getPaciente().getEndereco().getEstado()).getNome();

        Hospital h = aplHospital.obter(pn.getObito().getHospital());
        String hospitalNome = h.getNome();
        String hospitalCidade = h.getEndereco().getCidade().getNome();

        String dataEntrevista = utility.calendarDataToString(pn.getEntrevista().getDataEntrevista());
        final String horaEntrevista = utility.calendarHoraToString(pn.getEntrevista().getDataEntrevista());

        if(pn.getEntrevista().getResponsavel()!= null){
            String dataNascimentoResponsavel = utility.calendarToString(pn.getEntrevista().getResponsavel().getDataNascimento());
            String cidadeResponsavel = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel().getEndereco().getCidade()).getNome();
            String bairroResponsavel = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel().getEndereco().getBairro()).getNome();
            String estadoResponsavel = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel().getEndereco().getEstado()).getNome();
            Integer idadeResponsavel = utility.calculaIdade(pn.getEntrevista().getResponsavel().getDataNascimento(), Calendar.getInstance());
            model.addAttribute("bairroResponsavel", bairroResponsavel);
            model.addAttribute("estadoResponsavel", estadoResponsavel);
            model.addAttribute("cidadeResponsavel", cidadeResponsavel);
            model.addAttribute("dataNascimentoResponsavel", dataNascimentoResponsavel);
            model.addAttribute("idadeResponsavel", idadeResponsavel);
        }else{
            model.addAttribute("bairroResponsavel", "");
            model.addAttribute("estadoResponsavel", "");
            model.addAttribute("cidadeResponsavel", "");
            model.addAttribute("dataNascimentoResponsavel", "");
            model.addAttribute("idadeResponsavel", "");
        }


        if (pn.getEntrevista().getResponsavel2() != null) {
            String cidadeResponsavel2 = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel2().getEndereco().getCidade()).getNome();
            String bairroResponsavel2 = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel2().getEndereco().getBairro()).getNome();
            String estadoResponsavel2 = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel2().getEndereco().getEstado()).getNome();
            String dataNascimentoResponavel2 = utility.calendarToString(pn.getEntrevista().getResponsavel2().getDataNascimento());

            Integer idadeResponsavel2 = utility.calculaIdade(pn.getEntrevista().getResponsavel2().getDataNascimento(), Calendar.getInstance());

            model.addAttribute("bairroResponsavel2", bairroResponsavel2);
            model.addAttribute("estadoResponsavel2", estadoResponsavel2);
            model.addAttribute("cidadeResponsavel2", cidadeResponsavel2);
            model.addAttribute("dataNascimentoResponavel2", dataNascimentoResponavel2);
            model.addAttribute("idadeResponsavel2", idadeResponsavel2);
        }
        String nomeFuncinario = aplFuncionario.obter(pn.getEntrevista().getFuncionario()).getNome();

        int idadePaciente = utility.calculaIdade(pn.getObito().getPaciente().getDataNascimento(), pn.getObito().getDataObito());


        model.addAttribute("nomeFuncionario", nomeFuncinario);


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
        model.addAttribute("hospital","evangelico");
        model.addAttribute("pn", pn);


        final Map<String, String> recusaFamiliarDataAtual = new HashMap<String, String>(){{
            final Calendar hoje = Calendar.getInstance();
            final String dataAtual = hoje.get(DAY_OF_MONTH) + "/" + (hoje.get(MONTH)+1) + "/" + hoje.get(YEAR);
            put("recusaFamiliar", pnCompleto.getCausaNaoDoacao() != null ? pnCompleto.getCausaNaoDoacao().getNome() : "");
            put("dataAtual", dataAtual);
        }};

        model.addAttribute("recusaEData", recusaFamiliarDataAtual);

        return viewTermo();
    }

}