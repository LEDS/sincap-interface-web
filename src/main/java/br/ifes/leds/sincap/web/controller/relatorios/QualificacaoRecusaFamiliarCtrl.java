package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCaptador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.QualificacaoRecusaFamiliar;
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
import static br.ifes.leds.sincap.web.controller.ContextUrls.RLT_QUALIFICACAO_RECUSA_FAMILIAR;

@Controller
@RequestMapping(RELATORIOS + RLT_QUALIFICACAO_RECUSA_FAMILIAR)
@SessionScoped
public class QualificacaoRecusaFamiliarCtrl extends RelatoriosTemplateCtrl {

    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplRelatorio aplRelatorio;

    @Override
    protected void casoAnalista(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final ArrayList<QualificacaoRecusaFamiliar> relatorio = new ArrayList<>();
        if (listaHospitais.get(0) == -1) {
            final List<Long> todasInstituicoesLong = aplInstituicaoNotificadora.obter();
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, todasInstituicoesLong));
        } else {
            relatorio.addAll(aplRelatorio.relatorioQualificacaoRecusa(dataInicial, dataFinal, listaHospitais));
        }

        model.addAttribute("listaTotalrf", relatorio);
    }

    @Override
    protected void casoCaptador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final Captador captador = aplCaptador.obter((usuarioSessao.getIdUsuario()));
        final List<Hospital> hospitais = captador.getBancoOlhos().getHospitais();
        final List<QualificacaoRecusaFamiliar> relatorio = aplRelatorio.relatorioQualificacaoRecusa(dataFinal, dataInicial, hospitais);

        model.addAttribute("listaTotalrf", relatorio);
    }

    @Override
    protected void casoNotificador(ModelMap model, UsuarioSessao usuarioSessao, List<Long> listaHospitais, Calendar dataInicial, Calendar dataFinal) {
        final List<QualificacaoRecusaFamiliar> relatorio = aplRelatorio.relatorioQualificacaoRecusa(dataFinal, dataFinal, usuarioSessao.getIdHospital());

        model.addAttribute("listaTotalrf", relatorio);
    }

    @Override protected String view() {
        return "qualificacao-recusa-familiar";
    }
}
