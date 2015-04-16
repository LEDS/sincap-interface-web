/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.utility.Utility;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Hospital;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplSetor;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author aleao
 */
@Controller
@RequestMapping(ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL)
@SessionScoped
public class HospitalController {
    @Autowired
    private AplHospital aplHospital;
    @Autowired
    private AplSetor aplSetor;
    @Autowired
    private AplBancoOlhos aplBancoOlhos;
    @Autowired
    private UtilityWeb utilityWeb;
    @Autowired
    private Utility utility;
    @Autowired
    private Mapper mapper;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Hospital> listaHospitais = aplHospital.obter();
        model.addAttribute("listaHospitais", listaHospitais);
        return "hospital";
    }
    
    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String adicionar(ModelMap model){
        String titulo = "hospital.cadastro";
        List<BancoOlhos> listaBancoOlhos = aplBancoOlhos.obter();
        List<Setor> listaSetores = aplSetor.obter();
        model.addAttribute("listaBancoOlhosItem",utility.mapList(listaBancoOlhos, SelectItem.class));
        model.addAttribute("titulo",titulo);
        model.addAttribute("listaSetores",listaSetores);
        utilityWeb.preencherEstados(model);
        return "form-hospital";
    }

    @RequestMapping(value = ContextUrls.EDITAR + "/{idHospital}" ,method = RequestMethod.GET)
    public String editar(ModelMap model, @PathVariable Long idHospital){
        Hospital hospital = aplHospital.obter(idHospital);
        List<Setor> listaSetores = aplSetor.obter();
        String titulo = "hospital.editar";
        model.addAttribute("titulo", titulo);
        model.addAttribute("hospital", hospital);
        utilityWeb.preencherEndereco(mapper.map(hospital.getEndereco(), EnderecoDTO.class), model);
        utilityWeb.getBancoOlhos(model, aplBancoOlhos);
        model.addAttribute("listaSetores",listaSetores);
        model.addAttribute("listaSetoresHospital", utilityWeb.getLongBooleanMap(listaSetores, hospital.getSetores()));

        return "form-hospital";
    }


    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String salvar(@ModelAttribute Hospital hospital,@RequestParam("lsetores") List<Long> lsetores){
        Set<Setor> setSetores = new HashSet<>();

        for (Long l:lsetores){
            Setor s = new Setor();
            s.setId(l);
            setSetores.add(s);
        }

        hospital.setSetores(setSetores);

        aplHospital.cadastrar(hospital);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL;
    }

    @RequestMapping(value = ContextUrls.APAGAR + "/{idHospital}", method = RequestMethod.POST)
    public String apagar(@PathVariable Long idHospital){
        Hospital hospital = aplHospital.obter(idHospital);
        aplHospital.exlcuir(hospital);
        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_HOSPITAL;
    }


}
