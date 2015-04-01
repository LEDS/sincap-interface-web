package br.ifes.leds.sincap.web.test;

import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgd.BairroRepository;
import br.ifes.leds.reuse.endereco.cgd.CidadeRepository;
import br.ifes.leds.reuse.endereco.cgd.EstadoRepository;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Sexo;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Escolaridade;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.*;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.joda.time.DateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Locale;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil.SOLTEIRO;
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Parentesco.PAIS;
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoDocumentoComFoto.RG;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml", "classpath:spring-web-context.xml"})
@WebAppConfiguration
@ActiveProfiles("prod")
public abstract class AbstractionTest {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private BairroRepository bairroRepository;

    MockHttpSession session = SetUp.criaSessao();

    protected ProcessoNotificacaoDTO criaEntrevista() {
        return ProcessoNotificacaoDTO.builder()
                .id(253L)
                .dataAbertura(new DateTime(2014, 9, 27, 10, 24).toCalendar(Locale.getDefault()))
                .entrevista(EntrevistaDTO.builder()
                        .dataCadastro(new DateTime(2014, 9, 27, 12, 12).toCalendar(Locale.getDefault()))
                        .entrevistaRealizada(true)
                        .doacaoAutorizada(true)
                        .dataEntrevista(new DateTime(2014, 9, 27, 11, 11).toCalendar(Locale.getDefault()))
                        .responsavel(ResponsavelDTO.builder()
                                .grauEscolaridade(Escolaridade.ENSINO_SUPERIOR_INCOMPLETO)
                                .nome("Responsável")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("65165325864")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .sexo(Sexo.FEMININO)
                                .parentesco(PAIS)
                                .estadoCivil(SOLTEIRO)
                                .dataNascimento(new DateTime(2000, 9, 27, 0, 0).toCalendar(Locale.getDefault()))
                                .telefone(Telefone.builder()
                                        .numero("(27)99999-9999")
                                        .build())
                                .telefone2(Telefone.builder()
                                        .numero("(27)3333-3333")
                                        .build())
                                .profissao("Profissão")
                                .endereco(EnderecoDTO.builder()
                                        .numero("123")
                                        .cep("29182-527")
                                        .complemento("Complemento")
                                        .logradouro("Logradouro")
                                        .estado(estadoRepository.findBySigla("ES").getId())
                                        .cidade(cidadeRepository.findByEstado_SiglaAndNome("ES", "Serra").getId())
                                        .bairro(bairroRepository.findByCidade_Estado_SiglaAndCidade_NomeAndNome("ES", "Serra", "Nova Almeida").getId())
                                        .build())
                                .nacionalidade("Brasileira")
                                .build())
                        .responsavel2(ResponsavelDTO.builder()
                                .nome("Responsável")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("65165325864")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .sexo(Sexo.FEMININO)
                                .parentesco(PAIS)
                                .estadoCivil(SOLTEIRO)
                                .dataNascimento(new DateTime(2000, 9, 27, 0, 0).toCalendar(Locale.getDefault()))
                                .telefone(Telefone.builder()
                                        .numero("(27)99999-9999")
                                        .build())
                                .telefone2(Telefone.builder()
                                        .numero("(27)3333-3333")
                                        .build())
                                .endereco(EnderecoDTO.builder()
                                        .cep("29182-527")
                                        .logradouro("Logradouro")
                                        .estado(estadoRepository.findBySigla("ES").getId())
                                        .cidade(cidadeRepository.findByEstado_SiglaAndNome("ES", "Serra").getId())
                                        .bairro(bairroRepository.findByCidade_Estado_SiglaAndCidade_NomeAndNome("ES", "Serra", "Nova Almeida").getId())
                                        .build())
                                .nacionalidade("Brasileira")
                                .build())
                        .testemunha1(TestemunhaDTO.builder()
                                .nome("Testemunha 1")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("12345645646")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .build())
                        .testemunha2(TestemunhaDTO.builder()
                                .nome("Testemunha 2")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("6765452")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .build())
                        .funcionario(((UsuarioSessao) session.getAttribute("user")).getIdUsuario())
                        .build())
                .build();
    }

    protected ProcessoNotificacaoDTO criarCaptacao(boolean captacaoRealizada){
        ProcessoNotificacaoDTO processo = this.criaEntrevista();

        processo.setCaptacao(CaptacaoDTO.builder()
                .captacaoRealizada(captacaoRealizada)
                .comentario("Comentario")
                .dataCaptacao(new DateTime(2010, 10, 3, 1, 0).toCalendar(Locale.getDefault()))
            .build());

        return processo;
    }
}
