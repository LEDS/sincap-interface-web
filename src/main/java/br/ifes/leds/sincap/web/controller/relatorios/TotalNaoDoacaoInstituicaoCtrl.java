package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.TotalNaoDoacaoInstituicao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static br.ifes.leds.sincap.web.controller.ContextUrls.RLT_TOTAL_NAO_DOACAO_INSTITUICAO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class TotalNaoDoacaoInstituicaoCtrl {

    @Autowired
    private AplRelatorio aplRelatorio;
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;

    @RequestMapping(value = RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = GET)
    public String carregarRelatorioIndexNaoDoacao() {
        return "total-nao-doacao-instituicao";
    }

    @RequestMapping(value = RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = POST)
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

}
