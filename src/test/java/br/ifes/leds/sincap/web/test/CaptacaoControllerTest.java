package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static br.ifes.leds.sincap.web.controller.ContextUrls.ADICIONAR;
import static br.ifes.leds.sincap.web.controller.ContextUrls.APP_NOTIFICACAO_CAPTACAO;
import static br.ifes.leds.sincap.web.controller.ContextUrls.EDITAR;
import static br.ifes.leds.sincap.web.test.SetUp.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcessoComCaptacao(true));

        mockMvc.perform(post(APP_NOTIFICACAO_CAPTACAO + ADICIONAR)
                .param("id", isA(Long.class).toString())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarProcessoComCaptacao(true))))
                .andExpect(model().attributeExists("listaProblemasLogisticos"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }

    @Test
    @SneakyThrows
    public void editarCaptacaoTest() {
        when(aplProcessoNotificacao.obter(isA(Long.class))).thenReturn(criarProcessoComCaptacao(true));

        mockMvc.perform(get(APP_NOTIFICACAO_CAPTACAO + EDITAR + "/" + isA(Long.class)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("processo", equalTo(criarProcessoComCaptacao(true))))
                .andExpect(model().attributeExists("listaProblemasLogisticos"));

        verify(aplProcessoNotificacao, times(1)).obter(isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);
    }
}
