/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import org.dozer.DozerConverter;

/**
 *
 * @author 20131BSI0173
 */
public class BancoOlhosToLongId extends DozerConverter<BancoOlhos, Long>{
     public BancoOlhosToLongId() {
        super(BancoOlhos.class, Long.class);
    }

    @Override
    public Long convertTo(BancoOlhos source, Long destination) {
        return source.getId();
    }

    @Override
    public BancoOlhos convertFrom(Long source, BancoOlhos destination) {
        BancoOlhos bancoOlhos = new BancoOlhos();

        bancoOlhos.setId(source);

        return bancoOlhos;
    }
    
}
