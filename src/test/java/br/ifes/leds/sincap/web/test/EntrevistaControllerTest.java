package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static br.ifes.leds.sincap.web.controller.ContextUrls.*;
import static br.ifes.leds.sincap.web.test.SetUp.criaSessao;
import static br.ifes.leds.sincap.web.test.SetUp.criarProcesso;
import static br.ifes.leds.sincap.web.test.SetUp.setUp;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        // TODO: Terminar o teste.
        mockMvc.perform(post(APP_NOTIFICACAO_ENTREVISTA + SALVAR)
                .session(session));
    }
}
