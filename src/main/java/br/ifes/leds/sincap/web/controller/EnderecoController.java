package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.web.model.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(ContextUrls.APP_ENDERECO)
@SessionScoped
public class EnderecoController {

    @Autowired
    private AplEndereco aplEndereco;

    @RequestMapping(value = ContextUrls.GET_ESTADOS, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getEstados() {
        List<Map<String, String>> estados = new ArrayList<>();

        for (Estado e: aplEndereco.obterEstadosPorNomePais("Brasil")) {
            Map<String, String> estado = new HashMap<>();
            estado.put("id", e.getId().toString());
            estado.put("nome", e.getNome());
            estado.put("sigla", e.getSigla());
            estados.add(estado);
        }

        return new ResponseEntity<List<Map<String, String>>>(estados, HttpStatus.OK);
    }

    @RequestMapping(value = ContextUrls.GET_MUNICIPIOS, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getMunicipios(
            @RequestBody String estadoId) {

        List<Mensagem> listaMensagemMunicipios = new ArrayList<Mensagem>();

        try {

            List<Cidade> listaCidades = aplEndereco.obterCidadesPorEstado(Long
                    .parseLong(estadoId));

            for (Cidade cidade : listaCidades) {

                Mensagem mensagem = new Mensagem();

                mensagem.setDado(cidade.getNome());
                mensagem.setId(cidade.getId().toString());

                listaMensagemMunicipios.add(mensagem);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<List<Mensagem>>(listaMensagemMunicipios,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Mensagem>>(listaMensagemMunicipios,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/getBairros", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getBairros(
            @RequestBody String cidadeId) {

        List<Mensagem> listaMensagemBairros = new ArrayList<Mensagem>();

        try {

            List<Bairro> listaBairros = aplEndereco.obterBairrosPorCidade(Long
                    .parseLong(cidadeId));

            for (Bairro bairro : listaBairros) {

                Mensagem mensagem = new Mensagem();

                mensagem.setDado(bairro.getNome());
                mensagem.setId(bairro.getId().toString());

                listaMensagemBairros.add(mensagem);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<List<Mensagem>>(listaMensagemBairros,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Mensagem>>(listaMensagemBairros,
                HttpStatus.OK);

    }
}
