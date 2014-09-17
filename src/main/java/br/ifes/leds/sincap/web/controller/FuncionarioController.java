package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.controleInterno.cln.cdp.AnalistaCNCDO;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Captador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cgt.*;
import br.ifes.leds.sincap.web.utility.Utility;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Set;

/**
 * Created by aleao on 25/08/14.
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO)
@SessionScoped
public class FuncionarioController {

    Utility utility = Utility.INSTANCE;
    @Autowired
    private AplAnalistaCNCDO aplAnalistaCNCDO;
    @Autowired
    private AplNotificador aplNotificador;
    @Autowired
    private AplCaptador aplCaptador;
    @Autowired
    private AplEndereco aplEndereco;
    @Autowired
    private AplBancoOlhos aplBancoOlhos;
    @Autowired
    private AplInstituicaoNotificadora aplInstituicaoNotificadora;
    @Autowired
    private Mapper mapper;


    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<AnalistaCNCDO> listaAnalista = aplAnalistaCNCDO.obter();
        List<Notificador> listaNotificador = aplNotificador.obterTodosNotificadores();
        List<Captador> listaCaptador = aplCaptador.obter();

        model.addAttribute("analistas", listaAnalista);
        model.addAttribute("notificadores", listaNotificador);
        model.addAttribute("captadores", listaCaptador);

        return "funcionario-index";
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_ANALISTA, method = RequestMethod.GET)
    public String cadastrarAnalista(ModelMap model) {
        String titulo = "funcionario.cadastro.analista";
        utility.preencherEstados(model, aplEndereco);
        model.addAttribute("titulo", titulo);
        return "form-cadastro-analista";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_ANALISTA, method = RequestMethod.POST)
    public String salvarAnalista(ModelMap model, @ModelAttribute AnalistaCNCDO analistaCNCDO) {
        aplAnalistaCNCDO.salvar(analistaCNCDO);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.EDITAR + ContextUrls.APP_ANALISTA+"/{idAnalistaCNCO}" ,method = RequestMethod.GET)
    public String editarAnalista(ModelMap model, @PathVariable Long idAnalistaCNCO){
        AnalistaCNCDO analista = aplAnalistaCNCDO.obter(idAnalistaCNCO);
        String titulo = "funcionario.editar.analista";
        model.addAttribute("titulo",titulo);
        utility.preencherEndereco(mapper.map(analista.getEndereco(), EnderecoDTO.class), model, aplEndereco);
        model.addAttribute("analist", analista);
        return "form-cadastro-analista";
    }

    @RequestMapping(value = ContextUrls.APAGAR + ContextUrls.APP_ANALISTA +"/{idAnalistaCNCO}", method = RequestMethod.POST)
    public String apagarAnalista(ModelMap model, @PathVariable Long idAnalistaCNCO){
        AnalistaCNCDO analista = aplAnalistaCNCDO.obter(idAnalistaCNCO);
        aplAnalistaCNCDO.excluir(analista);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_NOTIFICADOR, method = RequestMethod.GET)
    public String cadastrarNotificador(ModelMap model) {
        String titulo = "funcionario.cadastro.notificador";
        model.addAttribute("titulo", titulo);
        utility.preencherEstados(model, aplEndereco);
        List<InstituicaoNotificadora> listaHospitais = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listaHospitais",listaHospitais);
        return "form-cadastro-notificador";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_NOTIFICADOR, method = RequestMethod.POST)
    public String salvarNotificador(ModelMap model, @ModelAttribute Notificador notificador,
                                    @RequestParam("hospitais") List<Long> hospitais) {
        for (Long l:hospitais){
            Set<InstituicaoNotificadora> setInstituicao = notificador.getInstituicoesNotificadoras();
            setInstituicao.add(aplInstituicaoNotificadora.obter(l));
            notificador.setInstituicoesNotificadoras(setInstituicao);
        }
        aplNotificador.salvarNotificador(notificador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.EDITAR + ContextUrls.APP_NOTIFICADOR+"/{idNotificador}" ,method = RequestMethod.GET)
    public String editarNotificador(ModelMap model, @PathVariable Long idNotificador){
        Notificador notificador = aplNotificador.obterNotificador(idNotificador);
        String titulo = "funcionario.editar.notificador";
        model.addAttribute("titulo",titulo);
        model.addAttribute("notificador", notificador);
        utility.preencherEndereco(mapper.map(notificador.getEndereco(), EnderecoDTO.class), model, aplEndereco);
        List<InstituicaoNotificadora> listaHospitais = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();
        model.addAttribute("listaHospitais",listaHospitais);
        return "form-cadastro-notificador";
    }

    @RequestMapping(value = ContextUrls.APAGAR + ContextUrls.APP_NOTIFICADOR +"/{idNotificador}", method = RequestMethod.POST)
    public String apagarNotificador(ModelMap model, @PathVariable Long idNotificador){
        Notificador notificador = aplNotificador.obterNotificador(idNotificador);
        aplNotificador.delete(notificador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.ADICIONAR + ContextUrls.APP_CAPTADOR, method = RequestMethod.GET)
    public String cadastrarCaptador(ModelMap model) {
        String titulo = "funcionario.cadastro.captador";
        model.addAttribute("titulo", titulo);
        utility.preencherEstados(model, aplEndereco);
        utility.getBancoOlhos(model, aplBancoOlhos);
        return "form-cadastro-captador";
    }

    @RequestMapping(value = ContextUrls.SALVAR + ContextUrls.APP_CAPTADOR, method = RequestMethod.POST)
    public String salvarCaptador(ModelMap model, @ModelAttribute Captador captador) {
        aplCaptador.salvar(captador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }

    @RequestMapping(value = ContextUrls.EDITAR + ContextUrls.APP_CAPTADOR+"/{idCaptador}" ,method = RequestMethod.GET)
    public String editarCaptador(ModelMap model, @PathVariable Long idCaptador){
        Captador captador = aplCaptador.obter(idCaptador);
        String titulo = "funcionario.editar.captador";
        model.addAttribute("titulo",titulo);
        model.addAttribute("captador", captador);
        utility.preencherEndereco(mapper.map(captador.getEndereco(), EnderecoDTO.class), model, aplEndereco);
        utility.getBancoOlhos(model, aplBancoOlhos);
        return "form-cadastro-captador";
    }

    @RequestMapping(value = ContextUrls.APAGAR + ContextUrls.APP_CAPTADOR +"/{idCaptador}", method = RequestMethod.POST)
    public String apagarCaptador(ModelMap model, @PathVariable Long idCaptador){
        Captador captador = aplCaptador.obter(idCaptador);
        aplCaptador.exlcuir(captador);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_FUNCIONARIO;
    }
}