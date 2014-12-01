package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.*;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatoriosController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    Utility utility;
    @Autowired
    UtilityWeb utilityWeb;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplFuncionario aplFuncionario;
    @Autowired
    AplRelatorio aplRelatorio;

    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA + ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO, method = RequestMethod.GET)
    public String carregarFormTermoDoacao(ModelMap model) {
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterPorPacienteNomeComEntrevistaDoacaoAutorizada("");

        model.addAttribute("listProcessoNotificacao", pn);
        //TODO: Substituir pelo endereco do formulario!
        return "termo-de-autorizacao";
    }

    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA + ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO, method = RequestMethod.POST)
    public String buscarTermoDoacao(ModelMap model, @RequestParam("nome") String nome) {
        List<ProcessoNotificacao> pn = aplProcessoNotificacao.obterPorPacienteNomeComEntrevistaDoacaoAutorizada(nome);

        model.addAttribute("listProcessoNotificacao", pn);

        //TODO: Substituir pelo endereco do formulario!
        return "termo-de-autorizacao";
    }

    @RequestMapping(value = ContextUrls.APP_NOTIFICACAO_ENTREVISTA + ContextUrls.RLT_TERMO_AUTORIZACAO_DOACAO + ContextUrls.IMPRIMIR + "/{id}", method = RequestMethod.GET)
    public String emitirTermoDoacao(ModelMap model, @PathVariable Long id) {
        ProcessoNotificacaoDTO pn = aplProcessoNotificacao.obter(id);
        String dataObito = utility.calendarDataToString(pn.getObito().getDataObito());
        String horaObito = utility.calendarHoraToString(pn.getObito().getDataObito());
        String cidadePaciente = aplEndereco.obterCidadePorID(pn.getObito().getPaciente().getEndereco().getCidade()).getNome();
        String bairroPaciente = aplEndereco.obterBairroPorID(pn.getObito().getPaciente().getEndereco().getBairro()).getNome();
        String estadoPaciente = aplEndereco.obterEstadosPorID(pn.getObito().getPaciente().getEndereco().getEstado()).getNome();

        Hospital h = aplHospital.obter(pn.getObito().getHospital());
        String hospitalNome = h.getNome();
        String hospitalCidade = h.getEndereco().getCidade().getNome();

        String dataEntrevista =utility.calendarDataToString(pn.getEntrevista().getDataEntrevista());
        String horaEntrevista=utility.calendarHoraToString(pn.getEntrevista().getDataEntrevista());

        //String dataNascimentoResponsavel = utility.calendarDataToString(pn.getEntrevista().getResponsavel().getDataNascimento());
        String cidadeResponsavel = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel().getEndereco().getCidade()).getNome();
        String bairroResponsavel = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel().getEndereco().getBairro()).getNome();
        String estadoResponsavel = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel().getEndereco().getEstado()).getNome();

        if (pn.getEntrevista().getResponsavel2() != null) {
            String cidadeResponsavel2 = aplEndereco.obterCidadePorID(pn.getEntrevista().getResponsavel2().getEndereco().getCidade()).getNome();
            String bairroResponsavel2 = aplEndereco.obterBairroPorID(pn.getEntrevista().getResponsavel2().getEndereco().getBairro()).getNome();
            String estadoResponsavel2 = aplEndereco.obterEstadosPorID(pn.getEntrevista().getResponsavel2().getEndereco().getEstado()).getNome();
            String dataNascimentoResponavel2 = utility.calendarDataToString(pn.getEntrevista().getResponsavel2().getDataNascimento());

            Integer idadeResponsavel2=utility.calculaIdade(pn.getEntrevista().getResponsavel2().getDataNascimento(),Calendar.getInstance());

            model.addAttribute("bairroResponsavel2", bairroResponsavel2);
            model.addAttribute("estadoResponsavel2", estadoResponsavel2);
            model.addAttribute("cidadeResponsavel2", cidadeResponsavel2);
            model.addAttribute("dataNascimentoResponavel2",dataNascimentoResponavel2);
            model.addAttribute("idadeResponsavel2",idadeResponsavel2);
        }
        String nomeFuncinario = aplFuncionario.obter(pn.getEntrevista().getFuncionario()).getNome();

        int idadePaciente = utility.calculaIdade(pn.getObito().getPaciente().getDataNascimento(), pn.getObito().getDataObito());
        Integer idadeResponsavel = utility.calculaIdade(pn.getEntrevista().getResponsavel().getDataNascimento(),Calendar.getInstance());

        model.addAttribute("nomeFuncionario", nomeFuncinario);
        model.addAttribute("bairroResponsavel", bairroResponsavel);
        model.addAttribute("estadoResponsavel", estadoResponsavel);
        model.addAttribute("cidadeResponsavel", cidadeResponsavel);
        //model.addAttribute("dataNascimentoResponsavel", dataNascimentoResponsavel);
        model.addAttribute("idadeResponsavel",idadeResponsavel);
        model.addAttribute("dataEntrevista",dataEntrevista);
        model.addAttribute("horaEntrevista",horaEntrevista);
        model.addAttribute("hospitalNome", hospitalNome);
        model.addAttribute("hospitalCidade", hospitalCidade);
        model.addAttribute("cidadePaciente", cidadePaciente);
        model.addAttribute("bairroPaciente", bairroPaciente);
        model.addAttribute("estadoPaciente", estadoPaciente);
        model.addAttribute("idadePaciente", idadePaciente);
        model.addAttribute("dataObito", dataObito);
        model.addAttribute("horaObito", horaObito);
        model.addAttribute("pn", pn);
        //TODO: Substituir pelo endereco do formulario!
        return "form-termo-de-autorizacao";
    }


    @RequestMapping(value = ContextUrls.RLT_TOTAL_DOACAO_INSTITUICAO, method = RequestMethod.GET)
    public String carregarRelatorioIndex(ModelMap model) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        model.addAttribute("listInstituicao", in);

        return "total-doacao-instituicao";
    }

    @RequestMapping(value = ContextUrls.RLT_TOTAL_DOACAO_INSTITUICAO, method = RequestMethod.POST)
    public String ExibirRelatorio(ModelMap model, @RequestParam(value = "hospitais", required = false, defaultValue = "-1") List<Long> lh, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datIni") Calendar dataInicial, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datFim") Calendar dataFinal) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        List<TotalDoacaoInstituicao> listtdi = new ArrayList<>();
        List<InstituicaoNotificadora> listInstituicaoSelected = aplInstituicaoNotificadora.obter(lh);

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);


        model.addAttribute("listInstituicaoSelected", utilityWeb.getLongBooleanMap(in, listInstituicaoSelected));


        if (lh.get(0) == -1) {
            for (InstituicaoNotificadora i : in) {
                TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(i.getId(), dataInicial, dataFinal);
                listtdi.add(tdi);
            }
        } else {
            for (Long i : lh) {
                TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(i, dataInicial, dataFinal);
                listtdi.add(tdi);
            }
        }

        model.addAttribute("listInstituicao", in);
        model.addAttribute("listaTotaldi", listtdi);


        //TODO: Substituir pelo endereco do formulario!
        return "total-doacao-instituicao";
    }

    @RequestMapping(value = ContextUrls.RLT_QUALIFICACAO_RECUSA_FAMILIAR, method = RequestMethod.GET)
    public String carregarRelatorioRecusaIndex(ModelMap model) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        model.addAttribute("listInstituicao", in);

        return "qualificacao-recusa-familiar";
    }

    @RequestMapping(value = ContextUrls.RLT_QUALIFICACAO_RECUSA_FAMILIAR, method = RequestMethod.POST)
    public String ExibirRelatorioRecusa(ModelMap model, @RequestParam(value = "hospitais", required = false, defaultValue = "-1") List<Long> lh, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datIni") Calendar dataInicial, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datFim") Calendar dataFinal) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        List<Long> inlong = aplInstituicaoNotificadora.obter();
        List<InstituicaoNotificadora> listInstituicaoSelected = aplInstituicaoNotificadora.obter(lh);

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        model.addAttribute("dataInicial",dataInicial);
        model.addAttribute("dataFinal",dataFinal);

        if (lh.get(0) == -1) {
            List<QualificacaoRecusaFamiliar> listqrf = aplRelatorio.relatorioQualificacaoRecusa(dataInicial,dataFinal,inlong);
            model.addAttribute("listaTotalrf", listqrf);
        } else {
            List<QualificacaoRecusaFamiliar> listqrf = aplRelatorio.relatorioQualificacaoRecusa(dataInicial,dataFinal,lh);
            model.addAttribute("listaTotalrf", listqrf);
          }


        model.addAttribute("listInstituicaoSelected",utilityWeb.getLongBooleanMap(in,listInstituicaoSelected));

        model.addAttribute("listInstituicao", in);


        return "qualificacao-recusa-familiar";
    }


    @RequestMapping(value = ContextUrls.RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = RequestMethod.GET)
    public String carregarRelatorioIndexNaoDoacao() {
        return "total-nao-doacao-instituicao";
    }

    @RequestMapping(value = ContextUrls.RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = RequestMethod.POST)
    public String ExibirRelatorioNaoDoacao(ModelMap model, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datIni") Calendar dataInicial, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datFim") Calendar dataFinal) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        List<TotalNaoDoacaoInstituicao> listIns = new ArrayList<>();

        for (InstituicaoNotificadora i : in) {
            TotalNaoDoacaoInstituicao tdi = aplRelatorio.relatorioTotalNaoDoacaoInstituicao(i.getId(), dataInicial, dataFinal);
            listIns.add(tdi);
        }

        model.addAttribute("listaTotaldi", listIns);

        //TODO: Substituir pelo endereco do formulario!
        return "total-nao-doacao-instituicao";
    }

    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = RequestMethod.GET)
    public String carregarRelatorioAtividadeMensal(ModelMap model) {
        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listInstituicao", in);
        return "rel-atividade-mensal";
    }

    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = RequestMethod.POST)
    public String ExibirRelatorioAtividadeMensal(ModelMap model, @DateTimeFormat(pattern = "MM/yyyy") @RequestParam("datMes") Calendar dataMes, @RequestParam(value = "hospitais", required = false, defaultValue = "-1") List<Long> lh) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        List<InstituicaoNotificadora> listInstituicaoSelected = aplInstituicaoNotificadora.obter(lh);
        Calendar dataInicial = Calendar.getInstance();
        Calendar dataFinal = Calendar.getInstance();

        dataInicial.set(dataMes.get(Calendar.YEAR),dataMes.get(Calendar.MONTH),dataMes.get(Calendar.DAY_OF_MONTH));
        dataFinal.set(dataMes.get(Calendar.YEAR),dataMes.get(Calendar.MONTH),dataMes.get(Calendar.DAY_OF_MONTH));

        dataInicial.set(Calendar.DAY_OF_MONTH, 1);

        dataFinal.add(Calendar.MONTH, 1);
        dataFinal.set(Calendar.DAY_OF_MONTH, 1);
        dataFinal.add(Calendar.DAY_OF_MONTH, -1);

        List<FaixaEtaria> listaFaixa = aplRelatorio.retornaFaixaEtaria(lh.get(0),dataInicial,dataFinal);
        model.addAttribute("listaFaixa",listaFaixa);

        List<ObitoCardio> liObito = aplRelatorio.retornaObitoCardio(lh.get(0),dataInicial,dataFinal);
        model.addAttribute("liObito",liObito);

        model.addAttribute("datMes", dataMes);
        model.addAttribute("listInstituicao", in);
        model.addAttribute("listInstituicaoSelected",utilityWeb.getLongBooleanMap(in,listInstituicaoSelected));

        return "rel-atividade-mensal";
    }
}
