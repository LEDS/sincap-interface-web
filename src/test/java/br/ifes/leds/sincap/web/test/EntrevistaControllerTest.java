//package br.ifes.leds.sincap.web.test;
//
//import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
//import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
//import lombok.SneakyThrows;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
//import static br.ifes.leds.sincap.web.test.SetUp.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.Matchers.isA;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * @author Phillipe Lopes
// */
//public class EntrevistaControllerTest extends AbstractionTest {
//
//    private MockMvc mockMvc;
//    private MockHttpSession session;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    @Autowired
//    private AplProcessoNotificacao aplProcessoNotificacao;
//
//
//    @Before
//    public void before() {
//        mockMvc = setUp(webApplicationContext, aplProcessoNotificacao);
//        session = criaSessao();
//    }
//
//    @Test
//    @SneakyThrows
//    public void novaEntrevistaTest() {
//        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcesso());
//
//        mockMvc.perform(post(APP_NOTIFICACAO_ENTREVISTA + ADICIONAR)
//                .param("id", isA(Long.class).toString())
//                .session(session))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("processo", equalTo(criarProcesso())))
//                .andExpect(model().attributeExists("listaEstadoItem", "listaAspectoEstrutural", "listaRecusaFamiliar", "listaParentescos", "listaEstadosCivis"));
//
//        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
//        verifyNoMoreInteractions(aplProcessoNotificacao);
//    }
//
//    @Test
//    @SneakyThrows
//    public void editarEntrevistaTest() {
//        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcesso());
//
//        mockMvc.perform(get(APP_NOTIFICACAO_ENTREVISTA + EDITAR + "/" + isA(Long.class)))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("processo", equalTo(criarProcesso())))
//                .andExpect(model().attributeExists("listaEstadoItem", "listaAspectoEstrutural", "listaRecusaFamiliar", "listaParentescos", "listaEstadosCivis", "recusaFamiliar", "problemasEstruturais"));
//
//        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
//        verifyNoMoreInteractions(aplProcessoNotificacao);
//    }
//
//    @Test
//    @SneakyThrows
//    public void salvarEntrevistaSemErroTest() {
//        ProcessoNotificacaoDTO entrevista = criaEntrevista();
//
//        mockMvc.perform(post(APP_NOTIFICACAO_ENTREVISTA + SALVAR)
//                .session(session)
//                .param("paciente.nome", "Paciente")
//                .param("id", entrevista.getId().toString())
//                .param("dataAbertura", "27/09/2014 10:24")
//                .param("entrevista.dataCadastro", "27/09/2014 12:12")
//                .param("entrevista.entrevistaRealizada", ((Boolean) entrevista.getEntrevista().isEntrevistaRealizada()).toString())
//                .param("entrevista.doacaoAutorizada", ((Boolean) entrevista.getEntrevista().isDoacaoAutorizada()).toString())
//                .param("dataEntrevista", "27/09/2014")
//                .param("horaEntrevista", "11:11")
//                .param("entrevista.responsavel.nome", entrevista.getEntrevista().getResponsavel().getNome())
//                .param("entrevista.responsavel.dataNascimento", "27/09/2000")
//                .param("entrevista.responsavel.documentoSocial.documento", entrevista.getEntrevista().getResponsavel().getDocumentoSocial().getDocumento())
//                .param("entrevista.responsavel.documentoSocial.tipoDocumentoComFoto", entrevista.getEntrevista().getResponsavel().getDocumentoSocial().getTipoDocumentoComFoto().toString())
//                .param("entrevista.responsavel.parentesco", entrevista.getEntrevista().getResponsavel().getParentesco().toString())
//                .param("entrevista.responsavel.estadoCivil", entrevista.getEntrevista().getResponsavel().getEstadoCivil().toString())
//                .param("entrevista.responsavel.telefone.numero", entrevista.getEntrevista().getResponsavel().getTelefone().getNumero())
//                .param("entrevista.responsavel.telefone2.numero", entrevista.getEntrevista().getResponsavel().getTelefone2().getNumero())
//                .param("entrevista.responsavel.sexo", entrevista.getEntrevista().getResponsavel().getSexo().toString())
//                .param("entrevista.responsavel.profissao", entrevista.getEntrevista().getResponsavel().getProfissao())
//                .param("entrevista.responsavel.religiao", entrevista.getEntrevista().getResponsavel().getReligiao())
//                .param("entrevista.responsavel.grauEscolaridade", entrevista.getEntrevista().getResponsavel().getGrauEscolaridade().toString())
//                .param("entrevista.responsavel.endereco.numero", entrevista.getEntrevista().getResponsavel().getEndereco().getNumero())
//                .param("entrevista.responsavel.endereco.cep", entrevista.getEntrevista().getResponsavel().getEndereco().getCep())
//                .param("entrevista.responsavel.endereco.complemento", entrevista.getEntrevista().getResponsavel().getEndereco().getComplemento())
//                .param("entrevista.responsavel.endereco.logradouro", entrevista.getEntrevista().getResponsavel().getEndereco().getLogradouro())
//                .param("entrevista.responsavel.endereco.estado", entrevista.getEntrevista().getResponsavel().getEndereco().getEstado().toString())
//                .param("entrevista.responsavel.endereco.cidade", entrevista.getEntrevista().getResponsavel().getEndereco().getCidade().toString())
//                .param("entrevista.responsavel.endereco.bairro", entrevista.getEntrevista().getResponsavel().getEndereco().getBairro().toString())
//                .param("entrevista.responsavel.nacionalidade", entrevista.getEntrevista().getResponsavel().getNacionalidade())
//                .param("entrevista.testemunha1.nome", entrevista.getEntrevista().getTestemunha1().getNome())
//                .param("entrevista.testemunha1.documentoSocial.documento", entrevista.getEntrevista().getTestemunha1().getDocumentoSocial().getDocumento())
//                .param("entrevista.testemunha1.documentoSocial.tipoDocumentoComFoto", entrevista.getEntrevista().getTestemunha1().getDocumentoSocial().getTipoDocumentoComFoto().toString())
//                .param("entrevista.testemunha2.nome", entrevista.getEntrevista().getTestemunha2().getNome())
//                .param("entrevista.testemunha2.documentoSocial.documento", entrevista.getEntrevista().getTestemunha2().getDocumentoSocial().getDocumento())
//                .param("entrevista.testemunha2.documentoSocial.tipoDocumentoComFoto", entrevista.getEntrevista().getTestemunha2().getDocumentoSocial().getTipoDocumentoComFoto().toString())
//                .param("entrevista.responsavel2.nome", entrevista.getEntrevista().getResponsavel2().getNome())
//                .param("entrevista.responsavel2.dataNascimento", "27/09/2000")
//                .param("entrevista.responsavel2.documentoSocial.documento", entrevista.getEntrevista().getResponsavel2().getDocumentoSocial().getDocumento())
//                .param("entrevista.responsavel2.documentoSocial.tipoDocumentoComFoto", entrevista.getEntrevista().getResponsavel2().getDocumentoSocial().getTipoDocumentoComFoto().toString())
//                .param("entrevista.responsavel2.parentesco", entrevista.getEntrevista().getResponsavel2().getParentesco().toString())
//                .param("entrevista.responsavel2.estadoCivil", entrevista.getEntrevista().getResponsavel2().getEstadoCivil().toString())
//                .param("entrevista.responsavel2.telefone.numero", entrevista.getEntrevista().getResponsavel2().getTelefone().getNumero())
//                .param("entrevista.responsavel2.telefone2.numero", entrevista.getEntrevista().getResponsavel2().getTelefone2().getNumero())
//                .param("entrevista.responsavel2.sexo", entrevista.getEntrevista().getResponsavel2().getSexo().toString())
//                .param("entrevista.responsavel2.endereco.cep", entrevista.getEntrevista().getResponsavel2().getEndereco().getCep())
//                .param("entrevista.responsavel2.nacionalidade", entrevista.getEntrevista().getResponsavel2().getNacionalidade())
//                .param("entrevista.responsavel2.endereco.logradouro", entrevista.getEntrevista().getResponsavel2().getEndereco().getLogradouro())
//                .param("entrevista.responsavel2.endereco.estado", entrevista.getEntrevista().getResponsavel2().getEndereco().getEstado().toString())
//                .param("entrevista.responsavel2.endereco.cidade", entrevista.getEntrevista().getResponsavel2().getEndereco().getCidade().toString())
//                .param("entrevista.responsavel2.endereco.bairro", entrevista.getEntrevista().getResponsavel2().getEndereco().getBairro().toString()))
////                302 significa que foi redirecionado. https://en.wikipedia.org/wiki/HTTP_302
//        .andExpect(status().is(302))
//                .andExpect(redirectedUrl(INDEX + "?sucessoEntrevista=true&idEntrevista=" + entrevista.getId()));
//
//        ArgumentCaptor<ProcessoNotificacaoDTO> argumentCaptor = ArgumentCaptor.forClass(ProcessoNotificacaoDTO.class);
//        verify(aplProcessoNotificacao, times(1)).salvarEntrevista(argumentCaptor.capture(), isA(Long.class));
//        argumentCaptor.getValue().setHistorico(null);
//
//        assertThat(argumentCaptor.getValue(), equalTo(entrevista));
//    }
//}
