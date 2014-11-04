package br.ifes.leds.sincap.web.test;

import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgd.BairroRepository;
import br.ifes.leds.reuse.endereco.cgd.CidadeRepository;
import br.ifes.leds.reuse.endereco.cgd.EstadoRepository;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Sexo;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.DocumentoComFoto;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.*;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil.SOLTEIRO;
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Parentesco.PAIS;
import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static br.ifes.leds.sincap.web.test.SetUp.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Phillipe Lopes
 */
public class EntrevistaControllerTest extends AbstractionTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private BairroRepository bairroRepository;

    @Before
    public void before() {
        mockMvc = setUp(webApplicationContext, aplProcessoNotificacao);
        session = criaSessao();
    }

    @Test
    @SneakyThrows
    public void novaEntrevistaTest() {
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcesso());

        mockMvc.perform(post(APP_NOTIFICACAO_ENTREVISTA + ADICIONAR)
                .param("id", isA(Long.class).toString())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarProcesso())))
                .andExpect(model().attributeExists("listaEstadoItem", "listaAspectoEstrutural", "listaRecusaFamiliar", "listaParentescos", "listaEstadosCivis"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }

    @Test
    @SneakyThrows
    public void editarEntrevistaTest() {
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcesso());

        mockMvc.perform(get(APP_NOTIFICACAO_ENTREVISTA + EDITAR + "/" + isA(Long.class)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarProcesso())))
                .andExpect(model().attributeExists("listaEstadoItem", "listaAspectoEstrutural", "listaRecusaFamiliar", "listaParentescos", "listaEstadosCivis", "recusaFamiliar", "problemasEstruturais"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }

    @Test
    @SneakyThrows
    public void salvarEntrevistaSemErroTest() {
        ProcessoNotificacaoDTO entrevista = criaEntrevista();

        mockMvc.perform(post(APP_NOTIFICACAO_ENTREVISTA + SALVAR)
                .session(session)
                .param("paciente.nome", "Paciente")
                .param("id", entrevista.getId().toString())
                .param("dataAbertura", "27/09/2014 10:24")
                .param("entrevista.dataCadastro", "27/09/2014 12:12")
                .param("entrevista.entrevistaRealizada", ((Boolean) entrevista.getEntrevista().isEntrevistaRealizada()).toString())
                .param("entrevista.doacaoAutorizada", ((Boolean) entrevista.getEntrevista().isDoacaoAutorizada()).toString())
                .param("dataEntrevista", "27/09/2014")
                .param("horaEntrevista", "11:11")
                .param("entrevista.responsavel.nome", entrevista.getEntrevista().getResponsavel().getNome())
                .param("entrevista.responsavel.documentoSocial", entrevista.getEntrevista().getResponsavel().getDocumentoSocial().getDocumento())
                .param("entrevista.responsavel.parentesco", entrevista.getEntrevista().getResponsavel().getParentesco().toString())
                .param("entrevista.responsavel.estadoCivil", entrevista.getEntrevista().getResponsavel().getEstadoCivil().toString())
                .param("entrevista.responsavel.telefone.numero", entrevista.getEntrevista().getResponsavel().getTelefone().getNumero())
                .param("entrevista.responsavel.telefone2.numero", entrevista.getEntrevista().getResponsavel().getTelefone2().getNumero())
                .param("entrevista.responsavel.sexo", "MASCULINO")
                .param("entrevista.responsavel.profissao", entrevista.getEntrevista().getResponsavel().getProfissao())
                .param("entrevista.responsavel.religiao", entrevista.getEntrevista().getResponsavel().getReligiao())
                .param("entrevista.responsavel.grauEscolaridade", entrevista.getEntrevista().getResponsavel().getGrauEscolaridade())
                .param("entrevista.responsavel.endereco.numero", entrevista.getEntrevista().getResponsavel().getEndereco().getNumero())
                .param("entrevista.responsavel.endereco.cep", entrevista.getEntrevista().getResponsavel().getEndereco().getCep())
                .param("entrevista.responsavel.endereco.complemento", entrevista.getEntrevista().getResponsavel().getEndereco().getComplemento())
                .param("entrevista.responsavel.endereco.logradouro", entrevista.getEntrevista().getResponsavel().getEndereco().getLogradouro())
                .param("entrevista.responsavel.endereco.estado", entrevista.getEntrevista().getResponsavel().getEndereco().getEstado().toString())
                .param("entrevista.responsavel.endereco.cidade", entrevista.getEntrevista().getResponsavel().getEndereco().getCidade().toString())
                .param("entrevista.responsavel.endereco.bairro", entrevista.getEntrevista().getResponsavel().getEndereco().getBairro().toString())
                .param("entrevista.responsavel.nacionalidade", entrevista.getEntrevista().getResponsavel().getNacionalidade())
                .param("entrevista.testemunha1.nome", entrevista.getEntrevista().getTestemunha1().getNome())
                .param("entrevista.testemunha1.documentoSocial", entrevista.getEntrevista().getTestemunha1().getDocumentoSocial().getDocumento())
                .param("entrevista.testemunha2.nome", entrevista.getEntrevista().getTestemunha2().getNome())
                .param("entrevista.testemunha2.documentoSocial", entrevista.getEntrevista().getTestemunha2().getDocumentoSocial().getDocumento()))
//                302 significa que foi redirecionado. https://en.wikipedia.org/wiki/HTTP_302
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(INDEX + "?sucessoEntrevista=true&idEntrevista=" + entrevista.getId()));

        ArgumentCaptor<ProcessoNotificacaoDTO> argumentCaptor = ArgumentCaptor.forClass(ProcessoNotificacaoDTO.class);
        verify(aplProcessoNotificacao, times(1)).salvarEntrevista(argumentCaptor.capture(), isA(Long.class));
        argumentCaptor.getValue().setHistorico(null);

        assertThat(argumentCaptor.getValue(), equalTo(entrevista));
    }

    private ProcessoNotificacaoDTO criaEntrevista() {
        return ProcessoNotificacaoDTO.builder()
                .id(253L)
                .dataAbertura(new DateTime(2014, 9, 27, 10, 24).toCalendar(Locale.getDefault()))
                .entrevista(EntrevistaDTO.builder()
                        .dataCadastro(new DateTime(2014, 9, 27, 12, 12).toCalendar(Locale.getDefault()))
                        .entrevistaRealizada(true)
                        .doacaoAutorizada(true)
                        .dataEntrevista(new DateTime(2014, 9, 27, 11, 11).toCalendar(Locale.getDefault()))
                        .responsavel(ResponsavelDTO.builder()
                                .nome("Responsável")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("654654564")
                                        .build())
                                .parentesco(PAIS)
                                .estadoCivil(SOLTEIRO)
                                .telefone(Telefone.builder()
                                        .numero("(27)99999-9999")
                                        .build())
                                .telefone2(Telefone.builder()
                                        .numero("(27)3333-3333")
                                        .build())
                                .sexo(Sexo.MASCULINO)
                                .profissao("Profissão")
                                .religiao("Religião")
                                .grauEscolaridade("Grau de ecolaridade")
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
                        .testemunha1(TestemunhaDTO.builder()
                                .nome("Testemunha 1")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("12345645646")
                                        .build())
                                .build())
                        .testemunha2(TestemunhaDTO.builder()
                                .nome("Testemunha 2")
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("6765452")
                                        .build())
                                .build())
                        .funcionario(((UsuarioSessao) session.getAttribute("user")).getIdUsuario())
                        .build())
                .build();
    }
}
