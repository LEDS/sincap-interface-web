/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.reuse.endereco.cdp.Estado;
import org.dozer.DozerConverter;

/**
 *
 * @author phillipe
 */
public class EstadoToLongIdConverter extends DozerConverter<Estado, Long> {

    public EstadoToLongIdConverter() {
        super(Estado.class, Long.class);
    }

    @Override
    public Long convertTo(Estado source, Long destination) {
        return source.getId();
    }

    @Override
    public Estado convertFrom(Long source, Estado destination) {
        Estado estado = new Estado();
        
        estado.setId(source);
        
        return estado;
    }
}
