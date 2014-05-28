package br.ifes.leds.sincap.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDTO {

    private Long id;
    private String nome;
    private String fantasia;
    private String cnes;
    private String sigla;
    private String email;
    private TelefoneDTO[] telefones;
    private EnderecoDTO endereco;
    private Long[] setores;
}
