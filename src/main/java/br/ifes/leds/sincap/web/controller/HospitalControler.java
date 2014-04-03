/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.reuse.endereco.cdp.Endereco;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.HospitalEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.model.HospitalForm;
import br.ifes.leds.sincap.web.model.VisualizarHospitalForm;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/hospital")
@SessionScoped
public class HospitalControler {

    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplSetor aplSetor;
    @Autowired
    AplEndereco aplEndereco;

    
    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);
        
        model.addAttribute("displayError", "none");
        model.addAttribute("displaySuccess", "none");

        return "lista-hospital";
    }

    @RequestMapping(value = "/apagar", method = RequestMethod.POST)
    public String excluirHospital(@RequestBody String id, ModelMap model) {
        
        id = id.split("=")[1]; // table1%3A1%3Aid=7

        try{
            aplHospital.delete(Long.parseLong(id));
        }catch(HospitalEmUsoException e){
            
            //model.addAttribute("Error", "Notificaçao existe");
            preecherLista(model);
            
            model.addAttribute("displayError", "block");
            model.addAttribute("displaySuccess", "none");
            
            return "lista-hospital";
        }
        
        return "redirect:/admin/hospital";
    }

    private void preecherLista(ModelMap model) {

        List<Hospital> hospitais = new ArrayList<Hospital>();
        List<VisualizarHospitalForm> listaHospitaisForm = new ArrayList<VisualizarHospitalForm>();

        hospitais = aplHospital.obter();

        for (Hospital hospital : hospitais) {
            VisualizarHospitalForm hospitalForm = new VisualizarHospitalForm(
                    hospital.getNome(), hospital.getId());
            listaHospitaisForm.add(hospitalForm);

        }

        model.addAttribute("listaHospitaisForm", listaHospitaisForm);

    }
    
    
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String loadFormNovo(ModelMap model) {

        HospitalForm hospitalForm = new HospitalForm();

        preencherSetores(model);
        preencherEstados(model);

        model.addAttribute("hospitalForm", hospitalForm);

        return "form-hospital";
    }

    private void preencherEstados(ModelMap model) {

        List<Estado> listaEstado = new ArrayList<Estado>();
        List<SelectItem> listaEstadoItem = new ArrayList<SelectItem>();

        listaEstado = aplEndereco.obterEstadosPorNomePais("Brasil");

        for (Estado estado : listaEstado) {
            SelectItem estadoItem = new SelectItem(estado.getId(), estado.getNome());
            listaEstadoItem.add(estadoItem);
        }

        model.addAttribute("listaEstadoItem", listaEstadoItem);

    }

    /*Define o caminho da url que, quando for requisitada, chamará o método pelo  tipo especificado*/
    @RequestMapping(value = "/novo/add", method = RequestMethod.POST)
    public String addHospital(@ModelAttribute HospitalForm hospitalForm, ModelMap model)
            throws Exception {

        Hospital hospital = new Hospital();

        /*preenchendo aba dados gerais*/
        hospital = preencherAbaDadosGerais(hospital, hospitalForm);
        /*preenchendo aba endereco*/
        hospital = preencherAbaEndereco(hospital, hospitalForm);
        /*preechendo aba setores*/
        hospital = preencherAbaSetores(hospital, hospitalForm);

        if(hospital.getId() == null)
            aplHospital.cadastrar(hospital);
        else
            aplHospital.update(hospital);
        
        System.out.println("Metodo addHospital");
        
        return "redirect:/admin/hospital";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editarHospital(@PathVariable long id, ModelMap model)
            throws Exception {
        //pegando do banco
        Hospital hospital = aplHospital.obter(id);
        //jogando para a tela
        HospitalForm hospitalForm = new HospitalForm();

        /*preenchendo os setores e estados*/
        preencherSetores(model);
        preencherEstados(model);

        //populando a tela de dados gerais
        hospitalForm = popularAbaDadosGerais(hospitalForm, hospital);
        //populando a tela de endereco
        hospitalForm = popularAbaEndereco(hospitalForm, hospital);
        //populando a tela de setores
        hospitalForm = popularAbaSetores(hospitalForm, hospital);

        model.addAttribute("hospitalForm", hospitalForm);

        return "form-hospital";
    }

//    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
//    public String apagarHospital(@PathVariable long id, ModelMap model)
//            throws Exception {
//        //pegando do banco
//        Hospital hospital = aplHospital.obter(id);
//
//        aplHospital.delete(hospital);
//
//        return montaTabelaHospital(model);
//    }

    // Monta a tabela de hospitais na página principal de Hospitais
    private String montaTabelaHospital(ModelMap model) {

        Long quantidade = aplHospital.quantidade();

        Pageable pageable = new PageRequest(0, 50, Sort.Direction.ASC, "nome", "sigla", "cnes");

        List<Hospital> hospitais = aplHospital.obter(pageable);

        model.addAttribute("hospitais", hospitais);
        model.addAttribute("quantidade", quantidade);

        return "form-hospital";
    }

    private void preencherSetores(ModelMap model) {

        List<Setor> listaSetor = new ArrayList<Setor>();

        List<SelectItem> listaSetorForm = new ArrayList<SelectItem>();
        listaSetor = aplSetor.obter();

        for (Setor setor : listaSetor) {
            SelectItem setorItem = new SelectItem(setor.getId(), setor.getNome());
            listaSetorForm.add(setorItem);
        }

        model.addAttribute("listaSetorForm", listaSetorForm);
    }

    private Telefone stringParaTelefone(String telefoneStr, TipoTelefone tipo) {

        Telefone telefone = new Telefone();

        telefone.setTipo(tipo);

        telefone.setDdd(telefoneStr.substring(1, 3));
        telefone.setNumero(telefoneStr.substring(4, 8) + telefoneStr.substring(9, 13));

        return telefone;

    }

    private Hospital preencherAbaDadosGerais(Hospital hospital, HospitalForm hospitalForm) {

        Set<Telefone> telefones = new HashSet<Telefone>();

        hospital.setNome(hospitalForm.getNome().toUpperCase());
        hospital.setFantasia(hospitalForm.getFantasia().toUpperCase());
        hospital.setCnes(hospitalForm.getCnes());
        hospital.setSigla(hospitalForm.getSigla().toUpperCase());
        telefones.add(stringParaTelefone(hospitalForm.getTelefone(), TipoTelefone.COMERCIAL));
        telefones.add(stringParaTelefone(hospitalForm.getFax(), TipoTelefone.FAX));
        hospital.setTelefones(telefones);
        hospital.setEmail(hospitalForm.getEmail().toUpperCase());

        return hospital;
    }

    private Hospital preencherAbaEndereco(Hospital hospital, HospitalForm hospitalForm) {

        Endereco endereco = new Endereco();

        endereco.setCEP(hospitalForm.getCep());
        endereco.setEstado(aplEndereco.obterEstadosPorID(hospitalForm.getEstado()));
        endereco.setCidade(aplEndereco.obterCidadePorID(hospitalForm.getCidade()));
        endereco.setBairro(aplEndereco.obterBairroPorID(hospitalForm.getBairro()));
        endereco.setLogradouro(hospitalForm.getLogradouro().toUpperCase());
        endereco.setNumero(hospitalForm.getNumero());
        endereco.setComplemento(hospitalForm.getComplemento().toUpperCase());
        hospital.setEndereco(endereco);

        return hospital;
    }

    private Hospital preencherAbaSetores(Hospital hospital, HospitalForm hospitalForm) {

        if (hospitalForm.getSetores() != null) {
            for (Long id : hospitalForm.getSetores()) {
                Setor setor = aplSetor.buscarSetor(id);
                hospital.addSetor(setor);
            }
        }

        return hospital;
    }

    private HospitalForm popularAbaDadosGerais(HospitalForm hospitalForm, Hospital hospital) {

        hospitalForm.setNome(hospital.getNome());
        hospitalForm.setFantasia(hospital.getFantasia());
        hospitalForm.setCnes(hospital.getCnes());
        hospitalForm.setSigla(hospital.getSigla());
        String[] telefonesStr = new String[2];
        for (Telefone telefone : hospital.getTelefones()) {
            String tel = "";
            tel = telefone.getDdd() + telefone.getNumero();
            if (telefone.getTipo().equals(TipoTelefone.COMERCIAL)) {
                telefonesStr[0] = tel;
            } else {
                telefonesStr[1] = tel;
            }
        }
        hospitalForm.setTelefone(telefonesStr[0]);
        hospitalForm.setFax(telefonesStr[1]);
        hospitalForm.setEmail(hospital.getEmail());

        return hospitalForm;

    }

    private HospitalForm popularAbaEndereco(HospitalForm hospitalForm, Hospital hospital) {

        hospitalForm.setCep(hospital.getEndereco().getCEP());
        hospitalForm.setEstado(hospital.getEndereco().getEstado().getId());
        hospitalForm.setCidade(hospital.getEndereco().getCidade().getId());
        hospitalForm.setBairro(hospital.getEndereco().getBairro().getId());
        hospitalForm.setLogradouro(hospital.getEndereco().getLogradouro());
        hospitalForm.setNumero(hospital.getEndereco().getNumero());
        hospitalForm.setComplemento(hospital.getEndereco().getComplemento());

        return hospitalForm;
    }

    private HospitalForm popularAbaSetores(HospitalForm hospitalForm, Hospital hospital) {

        int posicao = 0;
        int quantidadeSetores = 0;
        Long[] setores;

        for (Setor setor : hospital.getSetores()) {
            quantidadeSetores++;
        }

        setores = new Long[quantidadeSetores];
        hospitalForm.setSetores(setores);

        for (Setor setor : hospital.getSetores()) {
            hospitalForm.getSetores()[posicao] = setor.getId();
            posicao++;
        }

        return hospitalForm;
    }

}
