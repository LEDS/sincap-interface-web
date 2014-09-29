package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao;
import br.ifes.leds.sincap.web.controller.NotificacaoObitoController;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import br.ifes.leds.sincap.web.utility.UtilityWeb;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ObitoControllerTest extends AbstractionTest {

    private MockMvc mockMvc;
    @InjectMocks
    private NotificacaoObitoController obitoController;
    @Mock
    private AplProcessoNotificacao aplProcessoNotificacao;
    @Mock
    private AplCadastroInterno aplCadastroInterno;
    @Mock
    private UtilityWeb utilityWeb;
    private MockHttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(obitoController).build();

        session = new MockHttpSession();
        session.setAttribute("user", UsuarioSessao.builder()
                .idUsuario(1L)
                .cpfUsuario("111.111.111-11")
                .idHospital(1L)
                .nome("Notificador 1")
                .build());
    }

    @Test
    @SneakyThrows
    public void novaNotificacao() {
        mockMvc.perform(get("/obito/adicionar")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("sucessoObito", true))
                .andExpect(view().name("form-notificacao-obito"));
        verify(aplCadastroInterno, times(1)).obterSetorPorHospital(1L);
    }

    @Test
    @SneakyThrows
    public void novaNotificacaoErro() {
        mockMvc.perform(get("/obito/adicionar")
                .session(session)
                .param("erro", "false"))
                .andExpect(model().attribute("sucessoObito", false))
                .andExpect(view().name("form-notificacao-obito"));
    }

    @Test
    @SneakyThrows
    public void salvarNotificacaoSucessoAptoDoacao() {
        mockMvc.perform(post("/obito/salvar")
                .session(session)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("obito.paciente.nome", "hjrasncdnbnsdgç")
                .param("obito.paciente.dataNascimento", "27/09/1991")
                .param("obito.paciente.dataInternacao", "27/01/2013")
                .param("obito.paciente.documentoSocial", "543534242")
                .param("obito.paciente.telefone.numero", "(27) 99991-4100")
                .param("obito.paciente.sexo", "MASCULINO")
                .param("obito.paciente.numeroSUS", "657634234")
                .param("obito.paciente.profissao", "Eletricista")
                .param("obito.paciente.nacionalidade", "Brasileira")
                .param("obito.paciente.numeroProntuario", "90875299")
                .param("obito.paciente.nomeMae", "Mãe de Fulano")
                .param("obito.paciente.endereco.estado", "8")
                .param("obito.paciente.endereco.cidade", "2052")
                .param("obito.paciente.endereco.bairro", "3251")
                .param("obito.paciente.endereco.cep", "29182-527")
                .param("obito.paciente.endereco.logradouro", "")
                .param("obito.dataObito", "27/09/2014 22:30")
                .param("obito.setor", "1")
                .param("obito.primeiraCausaMortis", "Primeira Causa")
                .param("obito.segundaCausaMortis", "Segunda Causa")
                .param("obito.terceiraCausaMortis", "")
                .param("obito.quartaCausaMortis", "")
                .param("obito.aptoDoacao", "true")
                .param("causaNaoDoacao", ""))
                .andExpect(redirectedUrl("/index?sucessoObito=true"));

        ArgumentCaptor<ProcessoNotificacaoDTO> argumentCaptor = ArgumentCaptor.forClass(ProcessoNotificacaoDTO.class);

        verify(aplProcessoNotificacao, times(1)).salvarNovaNotificacao(argumentCaptor.capture(), isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);

        ProcessoNotificacaoDTO notificacaoDTO = argumentCaptor.getValue();

        assertTrue(notificacaoDTO.getObito().getPaciente().getNome().equals("hjrasncdnbnsdgç"));
    }

    @Test
    @SneakyThrows
    public void salvarNotificacaoSucessoInaptoDoacao() {
        mockMvc.perform(post("/obito/salvar")
                .session(session)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("obito.paciente.nome", "hjrasncdnbnsdgç")
                .param("obito.paciente.dataNascimento", "27/09/1991")
                .param("obito.paciente.dataInternacao", "27/01/2013")
                .param("obito.paciente.documentoSocial", "543534242")
                .param("obito.paciente.telefone.numero", "(27) 99991-4100")
                .param("obito.paciente.sexo", "MASCULINO")
                .param("obito.paciente.numeroSUS", "657634234")
                .param("obito.paciente.profissao", "Eletricista")
                .param("obito.paciente.nacionalidade", "Brasileira")
                .param("obito.paciente.numeroProntuario", "90875299")
                .param("obito.paciente.nomeMae", "Mãe de Fulano")
                .param("obito.paciente.endereco.estado", "8")
                .param("obito.paciente.endereco.cidade", "2052")
                .param("obito.paciente.endereco.bairro", "3251")
                .param("obito.paciente.endereco.cep", "29182-527")
                .param("obito.paciente.endereco.logradouro", "")
                .param("obito.dataObito", "27/09/2014 22:30")
                .param("obito.setor", "1")
                .param("obito.primeiraCausaMortis", "Primeira Causa")
                .param("obito.segundaCausaMortis", "Segunda Causa")
                .param("obito.terceiraCausaMortis", "")
                .param("obito.quartaCausaMortis", "")
                .param("obito.aptoDoacao", "false")
                .param("causaNaoDoacao", "1"))
                .andExpect(redirectedUrl("/index?sucessoObito=true"));

        ArgumentCaptor<ProcessoNotificacaoDTO> argumentCaptor = ArgumentCaptor.forClass(ProcessoNotificacaoDTO.class);

        verify(aplProcessoNotificacao, times(1)).salvarNovaNotificacao(argumentCaptor.capture(), isA(Long.class));
        verifyNoMoreInteractions(aplProcessoNotificacao);

        ProcessoNotificacaoDTO notificacaoDTO = argumentCaptor.getValue();

        assertTrue(notificacaoDTO.getObito().getPaciente().getNome().equals("hjrasncdnbnsdgç"));
        assertTrue(1L == notificacaoDTO.getCausaNaoDoacao());
    }

    @Test
    @SneakyThrows
    public void salvarNotificacaoDataInvalida() {
        mockMvc.perform(post("/obito/salvar")
                .session(session)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("obito.paciente.nome", "hjrasncdnbnsdgç")
                .param("obito.paciente.dataNascimento", "27/09/1991")
                .param("obito.paciente.dataInternacao", "27/01/2013")
                .param("obito.paciente.documentoSocial", "543534242")
                .param("obito.paciente.telefone.numero", "(27) 99991-4100")
                .param("obito.paciente.sexo", "MASCULINO")
                .param("obito.paciente.numeroSUS", "657634234")
                .param("obito.paciente.profissao", "Eletricista")
                .param("obito.paciente.nacionalidade", "Brasileira")
                .param("obito.paciente.numeroProntuario", "90875299")
                .param("obito.paciente.nomeMae", "Mãe de Fulano")
                .param("obito.paciente.endereco.estado", "8")
                .param("obito.paciente.endereco.cidade", "2052")
                .param("obito.paciente.endereco.bairro", "3251")
                .param("obito.paciente.endereco.cep", "29182-527")
                .param("obito.paciente.endereco.logradouro", "")
//                Data de óbito sem horário.
                .param("obito.dataObito", "27/09/2014")
                .param("obito.setor", "1")
                .param("obito.primeiraCausaMortis", "Primeira Causa")
                .param("obito.segundaCausaMortis", "Segunda Causa")
                .param("obito.terceiraCausaMortis", "")
                .param("obito.quartaCausaMortis", "")
                .param("obito.aptoDoacao", "true")
                .param("causaNaoDoacao", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void salvarNotificacaoConstraintViolation() {
        when(aplProcessoNotificacao.salvarNovaNotificacao(isA(ProcessoNotificacaoDTO.class), isA(Long.class)))
                .thenThrow(new ConstraintViolationException(new HashSet<ConstraintViolation<?>>()));

        mockMvc.perform(post("/obito/salvar")
                .session(session)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("obito.paciente.nome", "hjrasncdnbnsdgç")
//                Data de nascimento mais recente do que internação
                .param("obito.paciente.dataNascimento", "27/09/1991")
                .param("obito.paciente.dataInternacao", "27/01/2013")
                .param("obito.paciente.documentoSocial", "543534242")
                .param("obito.paciente.telefone.numero", "(27) 99991-4100")
//                Sexo null
                .param("obito.paciente.sexo", "")
//                Numero SUS null
                .param("obito.paciente.numeroSUS", "")
                .param("obito.paciente.profissao", "Eletricista")
                .param("obito.paciente.nacionalidade", "Brasileira")
                .param("obito.paciente.numeroProntuario", "90875299")
                .param("obito.paciente.nomeMae", "Mãe de Fulano")
                .param("obito.paciente.endereco.estado", "8")
                .param("obito.paciente.endereco.cidade", "2052")
                .param("obito.paciente.endereco.bairro", "3251")
                .param("obito.paciente.endereco.cep", "29182-527")
                .param("obito.paciente.endereco.logradouro", "")
                .param("obito.dataObito", "27/09/2014 22:30")
                .param("obito.setor", "1")
                .param("obito.primeiraCausaMortis", "Primeira Causa")
                .param("obito.segundaCausaMortis", "Segunda Causa")
                .param("obito.terceiraCausaMortis", "")
                .param("obito.quartaCausaMortis", "")
                .param("obito.aptoDoacao", "false")
                .param("causaNaoDoacao", "1"))

                .andExpect(view().name("form-notificacao-obito"))
                .andExpect(model().attributeExists("processo"));
    }
}
