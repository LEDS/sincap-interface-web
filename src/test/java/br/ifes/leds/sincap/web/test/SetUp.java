package br.ifes.leds.sincap.web.test;

import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.*;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import org.joda.time.DateTime;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static br.ifes.leds.sincap.controleInterno.cln.cdp.Sexo.MASCULINO;
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoDocumentoComFoto.RG;

public abstract class SetUp {

    /**
     * Faz a configuração inicial do contexto do teste.
     *
     * @param applicationContext O contexto web que será utilizado.
     *                           Esse contexto pode ser instânciado da seguinte forma:<br/>
     *                           {@code @Autowired}<br/>
     *                           {@code private WebApplicationContext webApplicationContext;}
     * @param mocks              São os serviços que são instanciados pelo Controller a ser testado.
     * @return o {@code MockMvc} que será utilizado para fazer as requisições {@code HTTP}.
     */
    public static MockMvc setUp(WebApplicationContext applicationContext, Object... mocks) {
        if (mocks.length != 0) {
            Mockito.reset(mocks);
        }
        return MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    /**
     * Cria uma sessão de usuário para testes
     *
     * @return Um objeto {@code MockHttpSession} com atributo "user".
     */
    public static MockHttpSession criaSessao() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", UsuarioSessao.builder()
                .idUsuario(1L)
                .cpfUsuario("111.111.111-11")
                .idHospital(1L)
                .nome("Notificador 1")
                .build());

        return session;
    }

    public static ProcessoNotificacaoDTO criarProcesso() {
        return ProcessoNotificacaoDTO.builder()
                .obito(ObitoDTO.builder()
                        .paciente(PacienteDTO.builder()
                                .endereco(EnderecoDTO.builder()
                                        .estado(8L)
                                        .cidade(2052L)
                                        .bairro(3251L)
                                        .logradouro("Rua Tal")
                                        .cep("29182-527")
                                        .numero("5324")
                                        .build())
                                .nome("Fulano de Tal")
                                .sexo(MASCULINO)
                                .dataNascimento(new DateTime(1991, 9, 27, 0, 0).toCalendar(Locale.getDefault()))
                                .dataInternacao(new DateTime(2014, 1, 10, 0, 0).toCalendar(Locale.getDefault()))
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("15663477")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .build())
                        .setor(1L)
                        .build())
                .entrevista(EntrevistaDTO.builder()
                        .responsavel(ResponsavelDTO.builder()
                                .documentoSocial(DocumentoComFotoDTO.builder()
                                        .documento("54323414")
                                        .tipoDocumentoComFoto(RG)
                                        .build())
                                .nome("Responsavel 1")
                                .build())
                        .testemunha1(TestemunhaDTO.builder()
                                .nome("Testemunha 1")
                                .build())
                        .build())
                .causaNaoDoacao(1L)
                .build();
    }
}
