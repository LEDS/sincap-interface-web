package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.QualificacaoRecusaFamiliar;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class QualificacaoRecusaFamiliarCtrl {

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private AplRelatorio aplRelatorio;
    @Autowired
    private UtilityWeb utilityWeb;

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

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        if (lh.get(0) == -1) {
            List<QualificacaoRecusaFamiliar> listqrf = aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, inlong);
            model.addAttribute("listaTotalrf", listqrf);
        } else {
            List<QualificacaoRecusaFamiliar> listqrf = aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, lh);
            model.addAttribute("listaTotalrf", listqrf);
        }


        model.addAttribute("listInstituicaoSelected", utilityWeb.getLongBooleanMap(in, listInstituicaoSelected));

        model.addAttribute("listInstituicao", in);


        return "qualificacao-recusa-familiar";
    }

}
