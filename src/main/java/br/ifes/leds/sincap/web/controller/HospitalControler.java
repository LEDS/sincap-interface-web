/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
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

import br.ifes.leds.reuse.endereco.cdp.Endereco;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.HospitalEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.model.HospitalDTO;
import br.ifes.leds.sincap.web.model.Id;
import org.dozer.Mapper;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL)
@SessionScoped
public class HospitalControler {

    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplSetor aplSetor;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "lista-hospital";
    }

    @RequestMapping(value = ContextUrls.APAGAR, method = RequestMethod.POST)
    public String excluirHospital(@ModelAttribute Id id, ModelMap model) {

        try {
            aplHospital.delete(id.getId());

            preecherLista(model);

            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "block");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");

        } catch (HospitalEmUsoException e) {

            preecherLista(model);

            model.addAttribute("displayError", "block");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "none");
        }
        return loadForm(model);
    }

    private void preecherLista(ModelMap model) {

        List<Hospital> hospitais;

        hospitais = aplHospital.obter();

        model.addAttribute("listaHospitaisForm", hospitais);
    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovo(ModelMap model) {

        HospitalDTO hospitalForm = new HospitalDTO();

        preencherEstados(model);
        preencherSetores(model);

        model.addAttribute("hospitalForm", hospitalForm);

        return "form-hospital";
    }

    /*Define o caminho da url que, quando for requisitada, chamará o método pelo  tipo especificado*/
    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String addHospital(@ModelAttribute HospitalDTO hospitalForm, ModelMap model)
            throws Exception {

        Hospital hospital = mapper.map(hospitalForm, Hospital.class);

        try {
            aplHospital.cadastrar(hospital);

            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "block");
            model.addAttribute("displayNovoError", "none");

        } catch (Exception e) {
            model.addAttribute("displayError", "none");
            model.addAttribute("displaySuccess", "none");
            model.addAttribute("displayNovoSuccess", "none");
            model.addAttribute("displayNovoError", "block");

            return loadFormNovo(model);
        }

        return loadForm(model);
    }

    @RequestMapping(value = ContextUrls.EDITAR, method = RequestMethod.POST)
    public String editarHospital(@ModelAttribute Id id, ModelMap model)
            throws Exception {

        Hospital hospital = aplHospital.obter(id.getId());

        HospitalDTO hospitalForm = mapper.map(hospital, HospitalDTO.class);

        preencherEndereco(hospital.getEndereco(), model);
        preencherSetores(model);

        model.addAttribute("hospitalForm", hospitalForm);

        return "form-hospital";
    }

    private void preencherSetores(ModelMap model) {

        List<Setor> listaSetor;

        List<SelectItem> listaSetorForm = new ArrayList<>();
        listaSetor = aplSetor.obter();

        for (Setor setor : listaSetor) {
            SelectItem setorItem = new SelectItem(setor.getId(), setor.getNome());
            listaSetorForm.add(setorItem);
        }

        model.addAttribute("listaSetorForm", listaSetorForm);
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
