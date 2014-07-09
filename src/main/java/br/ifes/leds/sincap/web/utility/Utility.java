package br.ifes.leds.sincap.web.utility;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.ui.ModelMap;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Parentesco;

public enum Utility {

    INSTANCE;

    public static Utility getInstance() {
        return INSTANCE;
    }

    public void preencherEndereco(EnderecoDTO endereco, ModelMap model,
            AplEndereco aplEndereco) {
        preencherEstados(model, aplEndereco);
        try {
            preencherCidades(endereco.getEstado(), model, aplEndereco);
            preencherBairros(endereco.getCidade(), model, aplEndereco);
        } catch (NullPointerException e) {
        }

    }

    public void preencherEstados(ModelMap model, AplEndereco aplEndereco) {

        List<Estado> listaEstados;
        List<SelectItem> listaEstadoItem = new ArrayList<>();

        listaEstados = aplEndereco.obterEstadosPorNomePais("Brasil");

        for (Estado estado : listaEstados) {
            SelectItem estadoItem = new SelectItem(estado.getId(),
                    estado.getNome());
            listaEstadoItem.add(estadoItem);
        }

        model.addAttribute("listaEstadoItem", listaEstadoItem);
    }

    public void preencherCidades(Long idEstado, ModelMap model,
            AplEndereco aplEndereco) {
        List<Cidade> listaCidades;
        List<SelectItem> listaCidadeItem = new ArrayList<>();

        listaCidades = aplEndereco.obterCidadesPorEstado(idEstado);

        for (Cidade cidade : listaCidades) {
            SelectItem cidadeItem = new SelectItem(cidade.getId(),
                    cidade.getNome());
            listaCidadeItem.add(cidadeItem);
        }

        model.addAttribute("listaCidadeItem", listaCidadeItem);
    }

    public void preencherBairros(Long idCidade, ModelMap model,
            AplEndereco aplEndereco) {
        List<Bairro> listaBairros;
        List<SelectItem> listaBairroItem = new ArrayList<>();

        listaBairros = aplEndereco.obterBairrosPorCidade(idCidade);

        for (Bairro bairro : listaBairros) {
            SelectItem cidadeItem = new SelectItem(bairro.getId(),
                    bairro.getNome());
            listaBairroItem.add(cidadeItem);
        }

        model.addAttribute("listaBairroItem", listaBairroItem);
    }
    
    public List<SelectItem> getParentescoSelectItem() {
        List<SelectItem> parentescos = new ArrayList<>();
        
        for (Parentesco parentesco : Parentesco.values()) {
            parentescos
                    .add(new SelectItem(parentesco, parentesco.name()));
        }

        return parentescos;
    }
    
    public List<SelectItem> getEstadoCivilSelectItem() {
        List<SelectItem> estadosCivis = new ArrayList<>();
        
        for (EstadoCivil estadoCivil : EstadoCivil.values()) {
            estadosCivis
                    .add(new SelectItem(estadoCivil, estadoCivil.name()));
        }

        return estadosCivis;
    }
}
