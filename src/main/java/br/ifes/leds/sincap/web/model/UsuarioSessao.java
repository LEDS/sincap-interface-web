package br.ifes.leds.sincap.web.model;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.ProcessoNotificacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import java.io.Serializable;

/**
 *
 * @author 20102bsi0553
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSessao implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsuario;
    private Long idHospital;
    private Funcionario funcionario;
    private String cpfUsuario;
    private ProcessoNotificacao notificacao;
    private int etapaNotifica;
    private String nome;
}
