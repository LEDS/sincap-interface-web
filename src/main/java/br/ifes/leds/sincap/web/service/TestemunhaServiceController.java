package br.ifes.leds.sincap.web.service;


import br.ifes.leds.sincap.gerenciaNotificacao.cgd.TestemunhaRepository;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Testemunha;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.TestemunhaDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplEntrevista;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.SessionScoped;
import java.net.URI;

/**
 * @author 20102bsi0553 - 10/09/14
 */
@Controller
@SessionScoped
@RequestMapping("/rest")
public class TestemunhaServiceController {

    @Autowired
    private AplEntrevista aplEntrevista;
    @Autowired
    private TestemunhaRepository testemunhaRepository;
    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TestemunhaDTO getTestemunha() {
        TestemunhaDTO testemunhaDTO = aplEntrevista.getAllEntrevistas().get(0).getTestemunha1();

        return testemunhaDTO;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TestemunhaDTO postTestemunha(@RequestBody TestemunhaDTO testemunhaDTO) {
        //{"documentoSocial":"777777777777","nome":"TestemunhaTeste","telefone":null}
        Testemunha testemunha = mapper.map(testemunhaDTO, Testemunha.class);
        testemunhaRepository.save(testemunha);

        testemunhaDTO = mapper.map(testemunha, TestemunhaDTO.class);

        return testemunhaDTO;
    }

}
