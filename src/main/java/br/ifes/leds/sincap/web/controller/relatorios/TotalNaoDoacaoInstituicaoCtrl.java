package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.TotalNaoDoacaoInstituicao;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private AplCaptador aplCaptador;
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;

    @RequestMapping(value = RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = GET)
    public String carregarRelatorioIndexNaoDoacao() {
        return "total-nao-doacao-instituicao";
    }

    @RequestMapping(value = RLT_TOTAL_NAO_DOACAO_INSTITUICAO, method = POST)
    public String ExibirRelatorioNaoDoacao(
            ModelMap model, HttpServletRequest request, HttpSession session,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datIni")
            Calendar dataInicial,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datFim")
            Calendar dataFinal
    ) {

        final List<TotalNaoDoacaoInstituicao> listIns = new ArrayList<>();
        final List<InstituicaoNotificadora> in = new ArrayList<>();
        final UsuarioSessao sessao = (UsuarioSessao) session.getAttribute("user");
        final boolean notificador = request.isUserInRole("ROLE_NOTIFICADOR");
        final boolean captador = request.isUserInRole("ROLE_CAPTADOR");
        final boolean analista = request.isUserInRole("ROLE_ANALISTA");

        if (analista) {
            in.addAll(aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras());
        } else if (notificador) {
            in.add(aplInstituicaoNotificadora.obter(sessao.getIdHospital()));
        } else if (captador) {
            final Captador captadorTmp = aplCaptador.obter(sessao.getIdUsuario());
            in.addAll(captadorTmp.getBancoOlhos().getHospitais());
        }

        for (InstituicaoNotificadora i : in) {
            TotalNaoDoacaoInstituicao tdi = aplRelatorio.relatorioTotalNaoDoacaoInstituicao(i.getId(), dataInicial, dataFinal);
            listIns.add(tdi);
        }

        model.addAttribute("listaTotaldi", listIns);

        //TODO: Substituir pelo endereco do formulario!
        return "total-nao-doacao-instituicao";
    }

}
