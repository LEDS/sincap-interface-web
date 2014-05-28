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
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadoraGenerica;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadoraGenerica;
import br.ifes.leds.sincap.web.model.Id;
import br.ifes.leds.sincap.web.model.InstituicaoNotificadoraDTO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
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
 * @author Phillipe Lopes
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_INSTITUICAO_NOTIFICADORA_GENERICA)
@SessionScoped
public class InstituicaoNotificadoraGenericaController {

    @Autowired
    AplInstituicaoNotificadoraGenerica aplInstituicaoNotificadoraGenerica;

    @Autowired
    AplEndereco aplEndereco;

    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preencherListaInstituicoesNotificadoras(model);

        model.addAttribute("displayError", "none");
        model.addAttribute("displaySuccess", "none");

        return "lista-instituicao-notificadora";
    }

    @RequestMapping(ContextUrls.ADICIONAR)
    public String adicionarInstituicaoNotificadora(ModelMap model) {
        InstituicaoNotificadoraDTO formularioInstituicaoNotificadora = new InstituicaoNotificadoraDTO();

        preencherEstados(model);

        model.addAttribute("formularioInstituicaoNotificadora", formularioInstituicaoNotificadora);

        return "form-instituicao-notificadora";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String excluirInstituicaoNotificadora(@ModelAttribute Id id, ModelMap model) {

        aplInstituicaoNotificadoraGenerica.delete(id.getId());

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_INSTITUICAO_NOTIFICADORA_GENERICA;
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String editarInstituicaoNotificadora(@ModelAttribute Id id, ModelMap model) {
        Long idInstituicao = id.getId();

        InstituicaoNotificadoraGenerica instituicaoNotificadora = aplInstituicaoNotificadoraGenerica.obter(idInstituicao);
        InstituicaoNotificadoraDTO formularioInstituicaoNotificadora = mapper.map(instituicaoNotificadora, InstituicaoNotificadoraDTO.class);

        preencherEndereco(instituicaoNotificadora.getEndereco(), model);

        model.addAttribute("formularioInstituicaoNotificadora", formularioInstituicaoNotificadora);

        return "form-instituicao-notificadora";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvarInstituicaoNotificadora(@ModelAttribute InstituicaoNotificadoraDTO formularioInstituicaoNotificadora, ModelMap model) {

        InstituicaoNotificadoraGenerica instituicaoNotificadora = mapper.map(formularioInstituicaoNotificadora, InstituicaoNotificadoraGenerica.class);

        aplInstituicaoNotificadoraGenerica.salvar(instituicaoNotificadora);

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_INSTITUICAO_NOTIFICADORA_GENERICA;
    }

    public void preencherListaInstituicoesNotificadoras(ModelMap model) {
        List<InstituicaoNotificadoraGenerica> instituicoesNotificadoras;

        instituicoesNotificadoras = aplInstituicaoNotificadoraGenerica.obterTodos();

        model.addAttribute("instituicoesNotificadoras", instituicoesNotificadoras);
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
