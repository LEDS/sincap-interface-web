/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Endereco;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.BancoOlhosEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.web.model.BancoOlhosDTO;
import br.ifes.leds.sincap.web.model.Id;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_BANCO_DE_OLHOS)
public class BancoOlhosController {

    @Autowired
    AplBancoOlhos aplBancoOlhos;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "lista-bancoolhos";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String excluirBancoOlhos(@ModelAttribute Id id, ModelMap model) {

        try {
            aplBancoOlhos.delete(id.getId());
        } catch (BancoOlhosEmUsoException e) {
            model.addAttribute("Error", "Notificação existe");
        }

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_BANCO_DE_OLHOS;
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovo(ModelMap model) {

        BancoOlhosDTO bancoOlhosForm = new BancoOlhosDTO();

        preencherEstados(model);

        model.addAttribute("bancoOlhosForm", bancoOlhosForm);

        return "form-bancoolhos";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String addBancoOlhos(@ModelAttribute BancoOlhosDTO bancoOlhosForm, ModelMap model) {

        BancoOlhos bancoOlhos = mapper.map(bancoOlhosForm, BancoOlhos.class);

        aplBancoOlhos.cadastrar(bancoOlhos);

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_BANCO_DE_OLHOS;
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String editarBancoOlhos(@ModelAttribute Id id, ModelMap model) {

        BancoOlhos bancoOlhos = aplBancoOlhos.obter(id.getId());
        BancoOlhosDTO bancoOlhosForm;

        preencherEndereco(bancoOlhos.getEndereco(), model);

        bancoOlhosForm = mapper.map(bancoOlhos, BancoOlhosDTO.class);

        model.addAttribute("bancoOlhosForm", bancoOlhosForm);

        return "form-bancoolhos";
    }

    private void preecherLista(ModelMap model) {

        List<BancoOlhos> listaBancoOlhos;

        listaBancoOlhos = aplBancoOlhos.obter();

        model.addAttribute("listaBancoOlhosForm", listaBancoOlhos);

    }

    private void preencherEndereco(Endereco endereco, ModelMap model) {
        preencherEstados(model);
        try {
            preencherCidades(endereco.getEstado().getId(), model);
            preencherBairros(endereco.getCidade().getId(), model);
        } catch (NullPointerException e) {
        }

    }

    private void preencherEstados(ModelMap model) {

        List<Estado> listaEstados;
        List<SelectItem> listaEstadoItem = new ArrayList<>();

        listaEstados = aplEndereco.obterEstadosPorNomePais("Brasil");

        for (Estado estado : listaEstados) {
            SelectItem estadoItem = new SelectItem(estado.getId(), estado.getNome());
            listaEstadoItem.add(estadoItem);
        }

        model.addAttribute("listaEstadoItem", listaEstadoItem);
    }

    private void preencherCidades(Long idEstado, ModelMap model) {
        List<Cidade> listaCidades;
        List<SelectItem> listaCidadeItem = new ArrayList<>();

        listaCidades = aplEndereco.obterCidadesPorEstado(idEstado);

        for (Cidade cidade : listaCidades) {
            SelectItem cidadeItem = new SelectItem(cidade.getId(), cidade.getNome());
            listaCidadeItem.add(cidadeItem);
        }

        model.addAttribute("listaCidadeItem", listaCidadeItem);
    }

    private void preencherBairros(Long idCidade, ModelMap model) {
        List<Bairro> listaBairros;
        List<SelectItem> listaBairroItem = new ArrayList<>();

        listaBairros = aplEndereco.obterBairrosPorCidade(idCidade);

        for (Bairro bairro : listaBairros) {
            SelectItem cidadeItem = new SelectItem(bairro.getId(), bairro.getNome());
            listaBairroItem.add(cidadeItem);
        }

        model.addAttribute("listaBairroItem", listaBairroItem);
    }
}
