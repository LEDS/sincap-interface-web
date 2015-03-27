package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.TotalDoacaoInstituicao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class TotalDoacaoInstituicaoCtrl {

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private UtilityWeb utilityWeb;
    @Autowired
    private AplRelatorio aplRelatorio;

    @RequestMapping(value = ContextUrls.RLT_TOTAL_DOACAO_INSTITUICAO, method = RequestMethod.GET)
    public String carregarRelatorioIndex(ModelMap model) {

        List<InstituicaoNotificadora> in = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        model.addAttribute("listInstituicao", in);

        return "total-doacao-instituicao";
    }

    @RequestMapping(value = ContextUrls.RLT_TOTAL_DOACAO_INSTITUICAO, method = RequestMethod.POST)
    public String exibirRelatorio(ModelMap model, HttpServletRequest request, HttpSession session,

                                  @RequestParam(value = "hospitais", required = false, defaultValue = "-1")
                                  List<Long> listaHospitais,

                                  @DateTimeFormat(pattern = "dd/MM/yyyy")
                                  @RequestParam("datIni")
                                  Calendar dataInicial,

                                  @DateTimeFormat(pattern = "dd/MM/yyyy")
                                  @RequestParam("datFim")
                                  Calendar dataFinal
    ) {

        List<InstituicaoNotificadora> instituicaoNotificadoras = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        List<TotalDoacaoInstituicao> totaisDoacaoInstituicao = new ArrayList<>();
        List<InstituicaoNotificadora> listInstituicaoSelected = aplInstituicaoNotificadora.obter(listaHospitais);
        final boolean hospitalNaoSelecionado = listaHospitais.get(0) == -1;

        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);

        model.addAttribute("listInstituicaoSelected", utilityWeb.getLongBooleanMap(instituicaoNotificadoras, listInstituicaoSelected));

        if (request.isUserInRole("ROLE_ANALISTA")) {
            adicionarInstituicoesAnalista(listaHospitais, dataInicial, dataFinal, instituicaoNotificadoras, totaisDoacaoInstituicao, hospitalNaoSelecionado);
        } else if (request.isUserInRole("ROLE_NOTIFICADOR")){
            final TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(((UsuarioSessao) session.getAttribute("user")).getIdHospital(), dataInicial, dataFinal);
            totaisDoacaoInstituicao.add(tdi);
        } else if (request.isUserInRole("ROLE_CAPTADOR")) {
            final Captador captador = aplCaptador.obter(((UsuarioSessao) session.getAttribute("user")).getIdUsuario());

            for (Hospital hospital : captador.getBancoOlhos().getHospitais()) {
                final TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(hospital.getId(), dataInicial, dataFinal);
                totaisDoacaoInstituicao.add(tdi);
            }
        }

        model.addAttribute("listInstituicao", instituicaoNotificadoras);
        model.addAttribute("listaTotaldi", totaisDoacaoInstituicao);


        //TODO: Substituir pelo endereco do formulario!
        return "total-doacao-instituicao";
    }

    private void adicionarInstituicoesAnalista(List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal, List<InstituicaoNotificadora> instituicaoNotificadoras, List<TotalDoacaoInstituicao> totaisDoacaoInstituicao, boolean hospitalNaoSelecionado) {
        if (hospitalNaoSelecionado) {
            for (InstituicaoNotificadora i : instituicaoNotificadoras) {
                TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(i.getId(), dataInicial, dataFinal);
                totaisDoacaoInstituicao.add(tdi);
            }
        } else {
            for (Long i : listaHospitais) {
                TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(i, dataInicial, dataFinal);
                totaisDoacaoInstituicao.add(tdi);
            }
        }
    }
}
