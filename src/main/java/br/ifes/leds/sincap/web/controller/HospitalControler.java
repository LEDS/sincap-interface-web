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
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.model.HospitalForm;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author 20112BSI0083
 */
@Controller
@RequestMapping("/admin/hospital/novo")
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
    	
    	for(Estado estado : listaEstado){
    		SelectItem estadoItem = new SelectItem(estado.getId(), estado.getNome());
    		listaEstadoItem.add(estadoItem);
    	}
    	
    	model.addAttribute("listaEstadoItem", listaEstadoItem);
		
	}

	/*Define o caminho da url que, quando for requisitada, chamará o método pelo  tipo especificado*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHospital(@ModelAttribute HospitalForm hospitalForm, ModelMap model)
            throws Exception {
        
        Hospital hospital = new Hospital();
        
        /*preenchendo aba dados gerais*/
        hospital = preencherAbaDadosGerais(hospital, hospitalForm);
        /*preenchendo aba endereco*/
        hospital = preencherAbaEndereco(hospital, hospitalForm);
        /*preechendo aba setores*/ 
        hospital = preencherAbaSetores(hospital, hospitalForm);
        
        aplHospital.cadastrar(hospital);
        
        return "redirect:/admin/hospital/novo";
    }
    
    
//    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    public String getHospital(@PathVariable long id, ModelMap model)
//            throws Exception {
//        //pegando do banco
//        Hospital hospital = servico.obter(id);
//        
//        //jogando para a tela
//        HospitalForm hospitalForm = new HospitalForm(); 
//        
//        hospitalForm.setCnes(hospital.getCnes());
//        hospitalForm.setNome(hospital.getNome());
//        hospitalForm.setSigla(hospital.getSigla());
//        hospitalForm.setId(hospital.getId().toString());
//      
//        model.addAttribute("hospital", hospitalForm);
//        
//        return "form-hospital";
//    }
    
    @RequestMapping(value = "/apagar/{id}", method = RequestMethod.GET)
    public String apagarHospital(@PathVariable long id, ModelMap model)
            throws Exception {
        //pegando do banco
        Hospital hospital = aplHospital.obter(id);
        
        aplHospital.delete(hospital);
        
        return montaTabelaHospital(model);
    }
    
    // Monta a tabela de hospitais na página principal de Hospitais
    private String montaTabelaHospital(ModelMap model){
        
        Long quantidade = aplHospital.quantidade();
                
        Pageable pageable = new PageRequest(0,50,Sort.Direction.ASC, "nome","sigla","cnes");
        
        List<Hospital> hospitais = aplHospital.obter(pageable);
        
        model.addAttribute("hospitais", hospitais);
        model.addAttribute("quantidade", quantidade);
        
        
        return "form-hospital";
    }
    
    private void preencherSetores(ModelMap model){
    	
    	List<Setor> listaSetor = new ArrayList<Setor>();
    	
    	List<SelectItem> listaSetorForm = new ArrayList<SelectItem>();
    	listaSetor = aplSetor.obter();
    	
    	for(Setor setor : listaSetor){
    		SelectItem setorItem = new SelectItem(setor.getId(), setor.getNome());
    		listaSetorForm.add(setorItem);
    	}
    	
    	model.addAttribute("listaSetorForm", listaSetorForm);
    }

    private Telefone stringParaTelefone(String telefoneStr, TipoTelefone tipo) {
        
        Telefone telefone = new Telefone();
        
        telefone.setTipo(tipo);
        
        telefone.setDdd(telefoneStr.substring(1, 2));
        telefone.setNumero(telefoneStr.substring(4, 8) + telefoneStr.substring(10, 13));
        
        return telefone;
        
    }

    private Hospital preencherAbaDadosGerais(Hospital hospital, HospitalForm hospitalForm) {
        
        Set<Telefone> telefones = new HashSet<Telefone>();
        
        hospital.setNome(hospitalForm.getNome());
        hospital.setFantasia(hospitalForm.getFantasia());
        hospital.setCnes(hospitalForm.getCnes());
        hospital.setSigla(hospitalForm.getSigla());
        telefones.add(stringParaTelefone(hospitalForm.getTelefone(), TipoTelefone.COMERCIAL));
        telefones.add(stringParaTelefone(hospitalForm.getFax(), TipoTelefone.FAX));
        hospital.setTelefones(telefones);
        hospital.setEmail(hospitalForm.getEmail());
        
        return hospital;
    }

    private Hospital preencherAbaEndereco(Hospital hospital, HospitalForm hospitalForm) {
        
        Endereco endereco = new Endereco();
        
        endereco.setCEP(hospitalForm.getCep());
        endereco.setEstado(aplEndereco.ObterEstadoPorNome(hospitalForm.getEstado()));
        endereco.setCidade(aplEndereco.obterCidadePorNome(hospitalForm.getCidade()));
        endereco.setBairro(aplEndereco.obterBairroPorNome(hospitalForm.getBairro()));
        endereco.setLogradouro(hospitalForm.getLogradouro());
        endereco.setNumero(hospitalForm.getNumero());
        endereco.setComplemento(hospitalForm.getComplemento());
        hospital.setEndereco(endereco);
        
        return hospital;
    }

    private Hospital preencherAbaSetores(Hospital hospital, HospitalForm hospitalForm) {
        
        for(Long id : hospitalForm.getSetores()){
            Setor setor = aplSetor.buscarSetor(id);
            hospital.addSetor(setor);
        }
        
        return hospital;
    }

}
