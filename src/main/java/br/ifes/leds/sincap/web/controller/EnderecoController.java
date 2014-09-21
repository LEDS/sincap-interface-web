package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(ContextUrls.APP_ENDERECO)
@SessionScoped
public class EnderecoController {

    @Autowired
    private AplEndereco aplEndereco;
    @Autowired
    private Utility utility;

    @RequestMapping(value = ContextUrls.GET_ESTADOS, method = RequestMethod.GET)
    public ResponseEntity<List<Map>> getEstados() {
        return new ResponseEntity<>(utility.mapList(aplEndereco.obterEstadosPorNomePais("Brasil"), Map.class), HttpStatus.OK);
    }

    @RequestMapping(value = ContextUrls.GET_MUNICIPIOS, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map>> getMunicipios(@RequestParam("estadoId") Long estadoId) {
        return new ResponseEntity<>(utility.mapList(aplEndereco.obterCidadesPorEstado(estadoId), Map.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/getBairros", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map>> getBairros(@RequestParam("cidadeId") Long cidadeId) {
        return new ResponseEntity<>(utility.mapList(aplEndereco.obterBairrosPorCidade(cidadeId), Map.class), HttpStatus.OK);
    }
}
