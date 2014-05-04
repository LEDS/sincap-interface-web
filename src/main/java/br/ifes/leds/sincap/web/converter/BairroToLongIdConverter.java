/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import org.dozer.DozerConverter;

/**
 *
 * @author phillipe
 */
public class BairroToLongIdConverter extends DozerConverter<Bairro, Long> {

    public BairroToLongIdConverter() {
        super(Bairro.class, Long.class);
    }

    @Override
    public Long convertTo(Bairro source, Long destination) {
        return source.getId();
    }

    @Override
    public Bairro convertFrom(Long source, Bairro destination) {
        Bairro bairro = new Bairro();
        
        bairro.setId(source);
        
        return bairro;
    }
}
