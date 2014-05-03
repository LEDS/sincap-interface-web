/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phillipe
 */
@Getter
@Setter
public class TelefoneDTO {

    private Long id;
    private String numero;
    private TipoTelefone tipo;
}
