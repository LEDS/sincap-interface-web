package br.ifes.leds.sincap.web.utility;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Parentesco;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UtilityWeb {

    @Autowired
    private AplEndereco aplEndereco;

    public void addConstraintViolations(ConstraintViolationException e, ModelMap model) {
        ConstraintViolation<?>[] constraintViolations = new ConstraintViolation<?>[e.getConstraintViolations().size()];
        e.getConstraintViolations().toArray(constraintViolations);
        model.addAttribute("constraintViolations", constraintViolations);
        model.addAttribute("erro", true);
    }

    public void preencherEndereco(EnderecoDTO endereco, ModelMap model) {
        preencherEstados(model);
        try {
            preencherCidades(endereco.getEstado(), model);
            preencherBairros(endereco.getCidade(), model);
        } catch (NullPointerException ignored) {
        }

    }

    public void preencherEstados(ModelMap model) {

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

    public void preencherCidades(Long idEstado, ModelMap model) {
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

    public void preencherBairros(Long idCidade, ModelMap model) {
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

    public List<SelectItem> getTipoNaoDoacaoSelectItem() {
        List<SelectItem> tiposNaoDoacao = new ArrayList<>();

        for (TipoNaoDoacao tipoNaoDoacao : TipoNaoDoacao.values()) {
            tiposNaoDoacao
                    .add(new SelectItem(tipoNaoDoacao, tipoNaoDoacao.name()));
        }

        return tiposNaoDoacao;
    }

    public void getBancoOlhos(ModelMap model, AplBancoOlhos aplBancoOlhos) {

        List<BancoOlhos> listaBancoOlhos;
        List<SelectItem> listaBancoOlhosItem = new ArrayList<>();

        listaBancoOlhos = aplBancoOlhos.obter();

        for (BancoOlhos bancoOlhos : listaBancoOlhos) {
            SelectItem bancoItem = new SelectItem(bancoOlhos.getId(),
                    bancoOlhos.getNome());
            listaBancoOlhosItem.add(bancoItem);
        }

        model.addAttribute("listaBancoOlhosItem", listaBancoOlhosItem);
    }

    @SuppressWarnings("unused")
    public void getInstituicaoNotificadora(ModelMap model, AplInstituicaoNotificadora aplInstituicaoNotificadora) {

        List<InstituicaoNotificadora> listaInstituicaoNotificadora;
        List<SelectItem> listaInstituicaoNotificadoraItem = new ArrayList<>();

        listaInstituicaoNotificadora = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        for (InstituicaoNotificadora instituicaoNotificadora : listaInstituicaoNotificadora) {
            SelectItem instituicaoNotificadoraItem = new SelectItem(instituicaoNotificadora.getId(),
                    instituicaoNotificadora.getNome());
            listaInstituicaoNotificadoraItem.add(instituicaoNotificadoraItem);
        }

        model.addAttribute("listaInstituicaoNotificadoraItem", listaInstituicaoNotificadoraItem);
    }
}
