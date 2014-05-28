/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phillipe
 */
@Getter
@Setter
public class EnderecoDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private Long estado;
    private Long cidade;
    private Long bairro;
}
