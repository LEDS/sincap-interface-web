package br.ifes.leds.sincap.web.controller.relatorios;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplFuncionario;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Obito;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.relatorios.RelatorioCronologia;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplRelatorio;
import br.ifes.leds.sincap.web.annotations.DefaultTimeZone;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(ContextUrls.RELATORIOS)
@SessionScoped
public class RelatoriosController {

    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    Utility utility;
    @Autowired
    UtilityWeb utilityWeb;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplFuncionario aplFuncionario;
    @Autowired
    AplRelatorio aplRelatorio;


    @RequestMapping(value = ContextUrls.RLT_POSSIBILIDADE_DOACAO_TECIDO, method = RequestMethod.GET)
    public String carregarRelatorioCronologia() {

        return "possibilidade-doacao-tecido";
    }

    @DefaultTimeZone
    @RequestMapping(value = ContextUrls.RLT_POSSIBILIDADE_DOACAO_TECIDO, method = RequestMethod.POST)
    public String exibirRelatorioCronologia(ModelMap model, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("dataInicial") Calendar dataInicial, @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam("dataFinal") Calendar dataFinal, HttpSession session) {


        UsuarioSessao usuarioSessao = (UsuarioSessao) session.getAttribute("user");
        long idHosp;
        idHosp = usuarioSessao.getIdHospital();

        List<Obito> obitosHosp = aplRelatorio.todosObitosPorHospital(idHosp, dataInicial, dataFinal);
        List<ProcessoNotificacao> listaProc = new ArrayList<>();
        List<RelatorioCronologia> listaRel = new ArrayList<>();

        for (Obito obito : obitosHosp) {
            listaProc.add(aplProcessoNotificacao.retornaProcesso(obito.getId()));
        }

        for (ProcessoNotificacao processo : listaProc) {
            RelatorioCronologia tdi = aplRelatorio.relatorioCronologia(processo);
            listaRel.add(tdi);
        }

        model.addAttribute("listaTotaldi", listaRel);

        //TODO: Substituir pelo endereco do formulario!
        return "possibilidade-doacao-tecido";
    }
}
