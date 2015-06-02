package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.FaixaEtaria;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.NaoDoacaoCIHDOTT;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.ObitoCardio;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.ObitosMeTurno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.*;
import static java.util.Calendar.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatorioAtividadeMensalCtrl {

    @Autowired
    private AplRelatorio aplRelatorio;
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    
    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = GET)
    public String carregarRelatorioAtividadeMensal(ModelMap model) {
        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listInstituicao", in);
        model.addAttribute("renderizarDados",false);
        return "rel-atividade-mensal";
    }

    @RequestMapping(value = ContextUrls.RLT_ATIVIDADE_MENSAL, method = POST)
    public String ExibirRelatorioAtividadeMensal(
            ModelMap model, HttpSession sessao,

            @DateTimeFormat(pattern = "MM/yyyy")
            @RequestParam("datMes")
            Calendar dataMes
    ) {

        UsuarioSessao usuario = (UsuarioSessao) sessao.getAttribute("user");
        Calendar dataInicial = getInstance();
        Calendar dataFinal = getInstance();

        dataInicial.set(YEAR, dataMes.get(YEAR));
        dataInicial.set(MONTH, dataMes.get(MONTH));
        dataFinal.set(YEAR, dataMes.get(YEAR));
        dataFinal.set(MONTH, dataMes.get(MONTH));

        dataInicial.set(DAY_OF_MONTH, 1);

        dataFinal.add(MONTH, 1);
        dataFinal.set(DAY_OF_MONTH, 1);
        dataFinal.add(DAY_OF_MONTH, -1);

        List<FaixaEtaria> listaFaixa = aplRelatorio.retornaFaixaEtaria(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("listaFaixa", listaFaixa);

        List<ObitoCardio> liObito = aplRelatorio.retornaObitoCardio(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("liObito", liObito);

        List<ObitosMeTurno> listaObitosME = aplRelatorio.retornaObitosMeTurno(usuario.getIdHospital(), dataInicial, dataFinal);
        model.addAttribute("listaObitosME", listaObitosME);

        List<NaoDoacaoCIHDOTT> recusaFamiliar = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, RECUSA_FAMILIAR);
        model.addAttribute("listaNaoDoacaoFamiliar", recusaFamiliar);

        List<NaoDoacaoCIHDOTT> problemaMedico = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, CONTRAINDICACAO_MEDICA);
        model.addAttribute("listaProbMedico", problemaMedico);

        List<NaoDoacaoCIHDOTT> problemaEstrutural = aplRelatorio.naoDoacaoMensal(usuario.getIdHospital(), dataInicial, dataFinal, PROBLEMAS_ESTRUTURAIS);
        model.addAttribute("listaProblemaEstrutural", problemaEstrutural);


        model.addAttribute("datMes", dataMes);

        //Permite que as divs sejam renderizadas somente no método POST
        model.addAttribute("renderizarDados",true);

        return "rel-atividade-mensal";
    }
}