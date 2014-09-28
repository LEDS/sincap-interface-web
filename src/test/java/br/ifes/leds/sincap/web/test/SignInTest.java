package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.controleInterno.cgd.FuncionarioRepository;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.web.controller.SignInController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SignInTest extends AbstractionTest {

    @InjectMocks
    private SignInController signInController;
    @Mock
    private AplPrincipal aplPrincipal;
    @Mock
    private HttpSession httpSession;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signInController).build();
    }

    @Test
    public void paginaInicial() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"));
    }

    @Test
    public void loginTest() throws Exception {
        String cpf = "111.111.111-11";

        when(aplPrincipal.validarLogin(cpf)).thenReturn(funcionarioRepository.findByCpf(cpf));

        mockMvc.perform(post("/autenticar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", cpf)
                        .param("password", "abc123")
                        .param("hospital", "1")
        )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/j_spring_security_check"));
    }

}
