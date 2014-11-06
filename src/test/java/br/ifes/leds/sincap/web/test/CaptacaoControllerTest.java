package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static br.ifes.leds.sincap.web.controller.ContextUrls.INDEX;
import static br.ifes.leds.sincap.web.controller.ContextUrls.SALVAR;
import static br.ifes.leds.sincap.web.test.SetUp.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Marcos Dias
 */
public class CaptacaoControllerTest extends AbstractionTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AplProcessoNotificacao aplProcessoNotificacao;

    @Before
    public void before() {
        mockMvc = setUp(webApplicationContext, aplProcessoNotificacao);
        session = criaSessao();
    }

    @Test
    @SneakyThrows
    public void novaCaptacaoTest() {
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarCaptacao(true));

        mockMvc.perform(post(APP_NOTIFICACAO_CAPTACAO + ADICIONAR)
                .param("id", isA(Long.class).toString())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarCaptacao(true))))
                .andExpect(model().attributeExists("listaProblemasLogisticos"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }

    @Test
    @SneakyThrows
    public void editarCaptacaoTest() {
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarCaptacao(true));

        ProcessoNotificacaoDTO processo = criarProcessoComCaptacao(true);

        mockMvc.perform(get(APP_NOTIFICACAO_CAPTACAO + EDITAR + "/" + isA(Long.class)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarCaptacao(true))))
                .andExpect(model().attributeExists("listaProblemasLogisticos"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }

    @Test
    @SneakyThrows
    public void salvarCaptacaoRealizadaTest() {
        ProcessoNotificacaoDTO captacao = criarCaptacao(true);

        mockMvc.perform(post(APP_NOTIFICACAO_CAPTACAO + SALVAR)
                .session(session)
                .param("id", captacao.getId().toString())
                .param("notificador", captacao.getNotificador().toString())
                .param("paciente.nome", "Paciente")
                .param("codigo", captacao.getCodigo())
                .param("captacao.captacaoRealizada", ((Boolean) captacao.getCaptacao().isCaptacaoRealizada()).toString())
                .param("captacao.comentario", captacao.getCaptacao().getComentario())
                .param("dataCaptacao", "3/10/2010")
                .param("horarioCaptacao", "00:00"))
//                302 significa que foi redirecionado. https://en.wikipedia.org/wiki/HTTP_302
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(INDEX + "/?captacaoSucesso=true"));

        ArgumentCaptor<ProcessoNotificacaoDTO> argumentCaptor = ArgumentCaptor.forClass(ProcessoNotificacaoDTO.class);
        verify(aplProcessoNotificacao, times(1)).salvarEntrevista(argumentCaptor.capture(), isA(Long.class));
        argumentCaptor.getValue().setHistorico(null);

        assertThat(argumentCaptor.getValue(), equalTo(captacao));
    }
}
