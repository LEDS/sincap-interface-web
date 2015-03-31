package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.QualificacaoRecusaFamiliar;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
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

import static br.ifes.leds.sincap.web.controller.ContextUrls.RLT_QUALIFICACAO_RECUSA_FAMILIAR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class QualificacaoRecusaFamiliarCtrl {

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplRelatorio aplRelatorio;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(value = RLT_QUALIFICACAO_RECUSA_FAMILIAR, method = GET)
    public String carregarRelatorioRecusaIndex(ModelMap model) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        model.addAttribute("listInstituicao", in);

        return "qualificacao-recusa-familiar";
    }

    @RequestMapping(value = RLT_QUALIFICACAO_RECUSA_FAMILIAR, method = POST)
    public String ExibirRelatorioRecusa(
            ModelMap model, HttpServletRequest request, HttpSession session,

            @RequestParam(value = "hospitais", required = false, defaultValue = "-1")
            List<Long> listaHospitais,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datIni") Calendar dataInicial,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datFim")
            Calendar dataFinal
    ) {

        final List<QualificacaoRecusaFamiliar> relatorio = new ArrayList<>();
        final boolean notificador = request.isUserInRole("ROLE_NOTIFICADOR");
        final boolean captador = request.isUserInRole("ROLE_CAPTADOR");
        final boolean analista = request.isUserInRole("ROLE_ANALISTA");
        final List<InstituicaoNotificadora> todasInstituicoesNotif = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        final List<InstituicaoNotificadora> listInstituicaoSelected = aplInstituicaoNotificadora.obter(listaHospitais);

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        if (notificador) {
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataFinal, dataFinal, ((UsuarioSessao) session.getAttribute("user")).getIdHospital()));
        } else if (captador) {
            final Captador captadorObj = aplCaptador.obter(((UsuarioSessao) session.getAttribute("user")).getIdUsuario());
            final List<Hospital> hospitais = captadorObj.getBancoOlhos().getHospitais();
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataFinal, dataInicial, hospitais));
        } else if (analista) {
            preencherRelatorioAnalista(listaHospitais, dataInicial, dataFinal, relatorio);
        }

        model.addAttribute("listaTotalrf", relatorio);
        model.addAttribute("listInstituicaoSelected", utilityWeb.getLongBooleanMap(todasInstituicoesNotif, listInstituicaoSelected));
        model.addAttribute("listInstituicao", todasInstituicoesNotif);

        return "qualificacao-recusa-familiar";
    }

    private void preencherRelatorioAnalista(@RequestParam(value = "hospitais", required = false, defaultValue = "-1") List<Long> listaHospitais, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datIni") Calendar dataInicial, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("datFim") Calendar dataFinal, List<QualificacaoRecusaFamiliar> relatorio) {
        if (listaHospitais.get(0) == -1) {
            final List<Long> todasInstituicoesLong = aplInstituicaoNotificadora.obter();
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, todasInstituicoesLong));
        } else {
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, listaHospitais));
        }
    }

}
