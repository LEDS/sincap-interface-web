package br.ifes.leds.sincap.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.controleInterno.cln.cdp.dto.SetorDTO;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.CausaNaoDoacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DTO.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.Utility;

/**
 *
 * @author marcosdias
 */
@Controller
@RequestMapping(ContextUrls.APP_NOTIFICACAO_OBITO)
@SessionScoped
public class NotificacaoObitoController {

    @Autowired
    private AplEndereco aplEndereco;
    @Autowired
    private AplCadastroInterno aplCadastroInterno;
    @Autowired
    private UsuarioSessao usuarioSessao;
    @Autowired
    private br.ifes.leds.reuse.utility.Utility utilityEntities;
    private Utility utility = Utility.getInstance();

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovaNotificacao(ModelMap model) {
        ProcessoNotificacaoDTO processo = new ProcessoNotificacaoDTO();

        utility.preencherEstados(model, aplEndereco);

        model.addAttribute("processo", processo);
        model.addAttribute("listaSetor", getListaSetoresSelectItem());
        model.addAttribute("listaCausaNaoDoacao",
                getListaCausaNDoacaoSelectItem());

        return "form-notificacao-obito";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarFormNovaNotificacao(
            @ModelAttribute ProcessoNotificacaoDTO processo,
            @RequestParam("dataNascimento") String dataNascimento,
            @RequestParam("dataInternacao") String dataInternacao,
            @RequestParam("dataObito") String dataObito,
            @RequestParam("horarioObito") String horarioObito)
            throws ParseException {

        processo.getObito().setDataObito(
                utilityEntities.stringToCalendar(dataObito, horarioObito));
        processo.getObito()
                .getPaciente()
                .setDataNascimento(
                        utilityEntities.stringToCalendar(dataNascimento));
        processo.getObito()
                .getPaciente()
                .setDataInternacao(
                        utilityEntities.stringToCalendar(dataInternacao));

        return "form-notificacao-obito";
    }

    private List<SelectItem> getListaCausaNDoacaoSelectItem() {
        List<CausaNaoDoacaoDTO> listaCausas = aplCadastroInterno
                .obterTodosCausaNaoDoacao();
        List<SelectItem> listaCausasSelIt = new ArrayList<>();

        for (CausaNaoDoacaoDTO causa : listaCausas) {
            listaCausasSelIt
                    .add(new SelectItem(causa.getId(), causa.getNome()));
        }

        return listaCausasSelIt;
    }

    private List<SelectItem> getListaSetoresSelectItem() {
        List<SetorDTO> listaSetor = aplCadastroInterno
                .obterSetorPorHospital(usuarioSessao.getIdHospital());

        List<SelectItem> listaSetorString = new ArrayList<>();

        for (SetorDTO setor : listaSetor) {
            listaSetorString
                    .add(new SelectItem(setor.getId(), setor.getNome()));
        }
        return listaSetorString;
    }

    @RequestMapping(value = ContextUrls.APP_ANALISAR, method = RequestMethod.GET)
    public String loadAnalisaNotificacaoObito() {
        return "analise-obito";
    }

}
