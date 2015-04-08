package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.TotalDoacaoInstituicao;
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
import static br.ifes.leds.sincap.web.controller.ContextUrls.RLT_TOTAL_DOACAO_INSTITUICAO;

@Controller
@RequestMapping(RELATORIOS + RLT_TOTAL_DOACAO_INSTITUICAO)
@SessionScoped
public class TotalDoacaoInstituicaoCtrl extends RelatoriosTemplateCtrl {

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplRelatorio aplRelatorio;

    @Override protected String view() {
        return "total-doacao-instituicao";
    }

    @Override
    protected void casoAnalista(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final List<TotalDoacaoInstituicao> totaisDoacaoInstituicao = new ArrayList<>();

        if (listaHospitais.get(0) == -1) {
            final List<Long> todasInstituicoesLong = aplInstituicaoNotificadora.obter();
            preencherRelatorioAnalista(dataInicial, dataFinal, totaisDoacaoInstituicao, todasInstituicoesLong);
        } else {
            preencherRelatorioAnalista(dataInicial, dataFinal, totaisDoacaoInstituicao, listaHospitais);
        }

        model.addAttribute("listaTotaldi", totaisDoacaoInstituicao);
    }

    @Override
    protected void casoCaptador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final Captador captador = aplCaptador.obter(usuarioSessao.getIdUsuario());
        final ArrayList<TotalDoacaoInstituicao> totaisDoacaoInstituicao = new ArrayList<>();

        for (Hospital hospital : captador.getBancoOlhos().getHospitais()) {
            final TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(hospital.getId(), dataInicial, dataFinal);
            totaisDoacaoInstituicao.add(tdi);
        }

        model.addAttribute("listaTotaldi", totaisDoacaoInstituicao);
    }

    @Override
    protected void casoNotificador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final TotalDoacaoInstituicao relatorio = aplRelatorio.relatorioTotalDoacaoInstituicao(usuarioSessao.getIdHospital(), dataInicial, dataFinal);
        final ArrayList<TotalDoacaoInstituicao> totaisDoacaoInstituicao = new ArrayList<>();

        totaisDoacaoInstituicao.add(relatorio);

        model.addAttribute("listaTotaldi", totaisDoacaoInstituicao);
    }

    private void preencherRelatorioAnalista(
            Calendar dataInicial, Calendar dataFinal, List<TotalDoacaoInstituicao> totaisDoacaoInstituicao, List<Long> todasInstituicoesLong
    ) {
        for (Long id : todasInstituicoesLong) {
            TotalDoacaoInstituicao tdi = aplRelatorio.relatorioTotalDoacaoInstituicao(id, dataInicial, dataFinal);
            totaisDoacaoInstituicao.add(tdi);
        }
    }
}
