/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import org.dozer.DozerConverter;

/**
 *
 * @author phillipe
 */
public class SetorToLongIdConverter extends DozerConverter<Setor, Long> {

    public SetorToLongIdConverter() {
        super(Setor.class, Long.class);
    }

    @Override
    public Long convertTo(Setor source, Long destination) {
        return source.getId();
    }

    @Override
    public Setor convertFrom(Long source, Setor destination) {
        Setor setor = new Setor();

        setor.setId(source);

        return setor;
    }
}
