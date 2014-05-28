/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.reuse.endereco.cdp.Cidade;
import org.dozer.DozerConverter;

/**
 *
 * @author phillipe
 */
public class CidadeToLongIdConverter extends DozerConverter<Cidade, Long> {

    public CidadeToLongIdConverter() {
        super(Cidade.class, Long.class);
    }

    @Override
    public Long convertTo(Cidade source, Long destination) {
        return source.getId();
    }

    @Override
    public Cidade convertFrom(Long source, Cidade destination) {
        Cidade cidade = new Cidade();

        cidade.setId(source);

        return cidade;
    }
}
