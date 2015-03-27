package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.*;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
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
        //TODO: Substituir pelo endereco do formulario!
        return "form-termo-de-autorizacao";
    }

    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = RequestMethod.GET)
    public String carregarRelatorioAtividadeMensal(ModelMap model) {
        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listInstituicao", in);
        return "rel-atividade-mensal";
    }

    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = RequestMethod.POST)
    public String ExibirRelatorioAtividadeMensal(ModelMap model, HttpSession sessao, @DateTimeFormat(pattern = "MM/yyyy") @RequestParam("datMes") Calendar dataMes) {

        UsuarioSessao usuario = (UsuarioSessao) sessao.getAttribute("user");
        Calendar dataInicial = Calendar.getInstance();
        Calendar dataFinal = Calendar.getInstance();

        dataInicial.set(dataMes.get(Calendar.YEAR), dataMes.get(Calendar.MONTH), dataMes.get(Calendar.DAY_OF_MONTH));
        dataFinal.set(dataMes.get(Calendar.YEAR), dataMes.get(Calendar.MONTH), dataMes.get(Calendar.DAY_OF_MONTH));

        dataInicial.set(Calendar.DAY_OF_MONTH, 1);

        dataFinal.add(Calendar.MONTH, 1);
        dataFinal.set(Calendar.DAY_OF_MONTH, 1);
        dataFinal.add(Calendar.DAY_OF_MONTH, -1);

        List<FaixaEtaria> listaFaixa = aplRelatorio.retornaFaixaEtaria(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("listaFaixa", listaFaixa);

        List<ObitoCardio> liObito = aplRelatorio.retornaObitoCardio(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("liObito", liObito);

        List<ObitosMeTurno> listaObitosME = aplRelatorio.retornaObitosMeTurno(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("listaObitosME", listaObitosME);

        List<NaoDoacaoCIHDOTT> recusaFamiliar = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, TipoNaoDoacao.RECUSA_FAMILIAR);
        model.addAttribute("listaNaoDoacaoFamiliar", recusaFamiliar);

        List<NaoDoacaoCIHDOTT> problemaMedico = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, TipoNaoDoacao.CONTRAINDICACAO_MEDICA);
        model.addAttribute("listaProbMedico", problemaMedico);

        List<NaoDoacaoCIHDOTT> problemaEstrutural = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS);
        model.addAttribute("listaProblemaEstrutural", problemaEstrutural);


        model.addAttribute("datMes", dataMes);

        return "rel-atividade-mensal";
    }
}
