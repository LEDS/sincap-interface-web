/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.Endereco;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.ledsExceptions.CRUDExceptions.BancoOlhosEmUsoException;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.web.model.BancoOlhosForm;
import br.ifes.leds.sincap.web.model.VizualizarBancoOlhosForm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        preecherLista(model);

        return "lista-bancoolhos";
    }

    @RequestMapping(value = ContextUrls.APAGAR + "/{id}", method = RequestMethod.GET)
    public String excluirBancoOlhos(@PathVariable long id, ModelMap model) {

        try {
            aplBancoOlhos.delete(id);
        } catch (BancoOlhosEmUsoException e) {

            model.addAttribute("Error", "Notificação existe");

        }

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_BANCO_DE_OLHOS;
    }

    private void preecherLista(ModelMap model) {

        List<BancoOlhos> bancoOlhos;
        List<VizualizarBancoOlhosForm> listaBancoOlhosForm = new ArrayList<>();

        bancoOlhos = aplBancoOlhos.obter();

        for (BancoOlhos bancoOlho : bancoOlhos) {
            VizualizarBancoOlhosForm bancoOlhosForm = new VizualizarBancoOlhosForm(
                    bancoOlho.getNome(), bancoOlho.getId());
            listaBancoOlhosForm.add(bancoOlhosForm);

        }

        model.addAttribute("listaBancoOlhosForm", listaBancoOlhosForm);

    }

    @RequestMapping(value = ContextUrls.ADICIONAR, method = RequestMethod.GET)
    public String loadFormNovo(ModelMap model) {

        BancoOlhosForm bancoOlhosForm = new BancoOlhosForm();

        preencherEstados(model);

        model.addAttribute("bancoOlhosForm", bancoOlhosForm);

        return "form-bancoolhos";
    }

    @RequestMapping(value = ContextUrls.SALVAR, method = RequestMethod.POST)
    public String addBancoOlhos(@ModelAttribute BancoOlhosForm bancoOlhosForm, ModelMap model)
            throws Exception {

        BancoOlhos bancoOlhos = new BancoOlhos();

        /*preenchendo aba dados gerais*/
        bancoOlhos = preencherAbaDadosGerais(bancoOlhos, bancoOlhosForm);
        /*preenchendo aba endereco*/
        bancoOlhos = preencherAbaEndereco(bancoOlhos, bancoOlhosForm);

        if (bancoOlhos.getId() == null) {
            aplBancoOlhos.cadastrar(bancoOlhos);
        } else {
            aplBancoOlhos.update(bancoOlhos);
        }

        System.out.println("Metodo addBancoOlhos");

        return "redirect:" + ContextUrls.ADMIN + ContextUrls.APP_BANCO_DE_OLHOS;
    }

    @RequestMapping(value = ContextUrls.EDITAR + "/{id}", method = RequestMethod.GET)
    public String editarBancoOlhos(@PathVariable long id, ModelMap model)
            throws Exception {
        //pegando do banco
        BancoOlhos bancoOlhos = aplBancoOlhos.obter(id);
        //jogando para a tela
        BancoOlhosForm bancoOlhosForm = new BancoOlhosForm();

        /*preenchendo os estados*/
        preencherEstados(model);

        //populando a tela de dados gerais
        bancoOlhosForm = popularAbaDadosGerais(bancoOlhosForm, bancoOlhos);
        //populando a tela de endereco
        bancoOlhosForm = popularAbaEndereco(bancoOlhosForm, bancoOlhos);

        model.addAttribute("bancoOlhosForm", bancoOlhosForm);

        return "form-bancoolhos";
    }

    private BancoOlhos preencherAbaDadosGerais(BancoOlhos bancoOlhos, BancoOlhosForm bancoOlhosForm) {

        Set<Telefone> telefones = new HashSet<>();

        bancoOlhos.setNome(bancoOlhosForm.getNome().toUpperCase());
        bancoOlhos.setFantasia(bancoOlhosForm.getFantasia().toUpperCase());
        bancoOlhos.setSigla(bancoOlhosForm.getSigla().toUpperCase());
        telefones.add(stringParaTelefone(bancoOlhosForm.getTelefone(), TipoTelefone.COMERCIAL));
        telefones.add(stringParaTelefone(bancoOlhosForm.getFax(), TipoTelefone.FAX));
        bancoOlhos.setTelefones(telefones);
        bancoOlhos.setEmail(bancoOlhosForm.getEmail().toUpperCase());

        return bancoOlhos;
    }

    private BancoOlhos preencherAbaEndereco(BancoOlhos bancoOlhos, BancoOlhosForm bancoOlhosForm) {

        Endereco endereco = new Endereco();

        endereco.setCEP(bancoOlhosForm.getCep());
        endereco.setEstado(aplEndereco.obterEstadosPorID(bancoOlhosForm.getEstado()));
        endereco.setCidade(aplEndereco.obterCidadePorID(bancoOlhosForm.getCidade()));
        endereco.setBairro(aplEndereco.obterBairroPorID(bancoOlhosForm.getBairro()));
        endereco.setLogradouro(bancoOlhosForm.getLogradouro().toUpperCase());
        endereco.setNumero(bancoOlhosForm.getNumero());
        endereco.setComplemento(bancoOlhosForm.getComplemento().toUpperCase());
        bancoOlhos.setEndereco(endereco);

        return bancoOlhos;
    }

    private void preencherEstados(ModelMap model) {

        List<Estado> listaEstado;
        List<SelectItem> listaEstadoItem = new ArrayList<>();

        listaEstado = aplEndereco.obterEstadosPorNomePais("Brasil");

        for (Estado estado : listaEstado) {
            SelectItem estadoItem = new SelectItem(estado.getId(), estado.getNome());
            listaEstadoItem.add(estadoItem);
        }

        model.addAttribute("listaEstadoItem", listaEstadoItem);

    }

    private Telefone stringParaTelefone(String telefoneStr, TipoTelefone tipo) {

        Telefone telefone = new Telefone();

        telefone.setTipo(tipo);

        telefone.setDdd(telefoneStr.substring(1, 3));
        telefone.setNumero(telefoneStr.substring(4, 8) + telefoneStr.substring(9, 13));

        return telefone;

    }

    private BancoOlhosForm popularAbaDadosGerais(BancoOlhosForm bancoOlhosForm, BancoOlhos bancoOlhos) {

        bancoOlhosForm.setNome(bancoOlhos.getNome());
        bancoOlhosForm.setFantasia(bancoOlhos.getFantasia());
        bancoOlhosForm.setSigla(bancoOlhos.getSigla());

        String[] telefonesStr = new String[2];
        for (Telefone telefone : bancoOlhos.getTelefones()) {
            String tel;
            tel = telefone.getDdd() + telefone.getNumero();
            if (telefone.getTipo().equals(TipoTelefone.COMERCIAL)) {
                telefonesStr[0] = tel;
            } else {
                telefonesStr[1] = tel;
            }
        }

        bancoOlhosForm.setTelefone(telefonesStr[0]);
        bancoOlhosForm.setFax(telefonesStr[1]);
        bancoOlhosForm.setEmail(bancoOlhos.getEmail());

        return bancoOlhosForm;

    }

    private BancoOlhosForm popularAbaEndereco(BancoOlhosForm bancoOlhosForm, BancoOlhos bancoOlhos) {

        bancoOlhosForm.setCep(bancoOlhos.getEndereco().getCEP());
        bancoOlhosForm.setEstado(bancoOlhos.getEndereco().getEstado().getId());
        bancoOlhosForm.setCidade(bancoOlhos.getEndereco().getCidade().getId());
        bancoOlhosForm.setBairro(bancoOlhos.getEndereco().getBairro().getId());
        bancoOlhosForm.setLogradouro(bancoOlhos.getEndereco().getLogradouro());
        bancoOlhosForm.setNumero(bancoOlhos.getEndereco().getNumero());
        bancoOlhosForm.setComplemento(bancoOlhos.getEndereco().getComplemento());

        return bancoOlhosForm;
    }

}
