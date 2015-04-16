package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.TotalNaoDoacaoInstituicao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static br.ifes.leds.sincap.web.controller.ContextUrls.RELATORIOS;
import static br.ifes.leds.sincap.web.controller.ContextUrls.RLT_TOTAL_NAO_DOACAO_INSTITUICAO;

@Controller
@RequestMapping(RELATORIOS + RLT_TOTAL_NAO_DOACAO_INSTITUICAO)
@SessionScoped
public class TotalNaoDoacaoInstituicaoCtrl extends RelatoriosTemplateCtrl {

    @Autowired
    private AplRelatorio aplRelatorio;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;

    @Override protected String view() {
        return "total-nao-doacao-instituicao";
    }

    @Override
    protected void casoAnalista(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final List<InstituicaoNotificadora> instituicoes = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        final List<TotalNaoDoacaoInstituicao> listIns = new ArrayList<>();

        for (InstituicaoNotificadora instituicao : instituicoes) {
            TotalNaoDoacaoInstituicao tdi = aplRelatorio.relatorioTotalNaoDoacaoInstituicao(instituicao.getId(), dataInicial, dataFinal);
            listIns.add(tdi);
        }

        model.addAttribute("listaTotaldi", listIns);
    }

    @Override
    protected void casoCaptador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final Captador captadorTmp = aplCaptador.obter(usuarioSessao.getIdUsuario());
        final List<TotalNaoDoacaoInstituicao> listIns = new ArrayList<>();

        for (Hospital hospital : captadorTmp.getBancoOlhos().getHospitais()) {
            TotalNaoDoacaoInstituicao tdi = aplRelatorio.relatorioTotalNaoDoacaoInstituicao(hospital.getId(), dataInicial, dataFinal);
            listIns.add(tdi);
        }

        model.addAttribute("listaTotaldi", listIns);
    }

    @Override
    protected void casoNotificador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final List<TotalNaoDoacaoInstituicao> listIns = new ArrayList<>();
        listIns.add(aplRelatorio.relatorioTotalNaoDoacaoInstituicao(usuarioSessao.getIdHospital(), dataInicial, dataFinal));
        model.addAttribute("listaTotaldi", listIns);
    }
}
