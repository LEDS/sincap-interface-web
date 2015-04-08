package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public abstract class RelatoriosTemplateCtrl {

    protected abstract String view();

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private UtilityWeb utilityWeb;

    @RequestMapping(method = GET)
    final public String carregarPagina(final ModelMap model, final HttpServletRequest request) {
        final List<InstituicaoNotificadora> in = new ArrayList<>();

        if (request.isUserInRole("ROLE_ANALISTA")) {
            in.addAll(aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras());
        }

        model.addAttribute("listInstituicao", in);

        return view();
    }

    @RequestMapping(method = POST)
    final public String exibirRelatorio(
            ModelMap model, HttpServletRequest request, HttpSession session,

            @RequestParam(value = "hospitais", required = false, defaultValue = "-1")
            List<Long> listaHospitais,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datIni") Calendar dataInicial,

            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam("datFim")
            Calendar dataFinal
    ) {
        final boolean notificador = request.isUserInRole("ROLE_NOTIFICADOR");
        final boolean captador = request.isUserInRole("ROLE_CAPTADOR");
        final boolean analista = request.isUserInRole("ROLE_ANALISTA");
        final UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        final List<InstituicaoNotificadora> todasInstituicoesNotif = new ArrayList<>();
        final List<InstituicaoNotificadora> listInstituicaoSelected = new ArrayList<>();

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        if (notificador) {
            casoNotificador(model, usuarioSessao, listaHospitais, dataInicial, dataFinal);
        } else if (captador) {
            casoCaptador(model, usuarioSessao, listaHospitais, dataInicial, dataFinal);
        } else if (analista) {
            todasInstituicoesNotif.addAll(aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras());
            listInstituicaoSelected.addAll(aplInstituicaoNotificadora.obter(listaHospitais));
            casoAnalista(model, usuarioSessao, listaHospitais, dataInicial, dataFinal);
        }

        model.addAttribute("listInstituicaoSelected", utilityWeb.getLongBooleanMap(todasInstituicoesNotif, listInstituicaoSelected));
        model.addAttribute("listInstituicao", todasInstituicoesNotif);

        return view();
    }

    protected abstract void casoAnalista(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal);

    protected abstract void casoCaptador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal);

    protected abstract void casoNotificador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal);

}
