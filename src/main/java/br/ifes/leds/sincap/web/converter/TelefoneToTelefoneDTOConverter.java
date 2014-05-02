/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.web.model.TelefoneDTO;
import org.dozer.DozerConverter;

/**
 *
 * @author phillipe
 */
public class TelefoneToTelefoneDTOConverter extends DozerConverter<Telefone, TelefoneDTO> {

    public TelefoneToTelefoneDTOConverter() {
        super(Telefone.class, TelefoneDTO.class);
    }

    @Override
    public TelefoneDTO convertTo(Telefone source, TelefoneDTO destination) {
        TelefoneDTO telefoneDTO = new TelefoneDTO();

        telefoneDTO.setId(source.getId());
        telefoneDTO.setNumero(source.getDdd() + source.getNumero());
        telefoneDTO.setTipo(source.getTipo());

        return telefoneDTO;
    }

    @Override
    public Telefone convertFrom(TelefoneDTO source, Telefone destination) {
        Telefone telefone = new Telefone();

        telefone.setTipo(source.getTipo());
        telefone.setId(source.getId());
        telefone.setDdd(source.getNumero().substring(1, 3));
        telefone.setNumero(source.getNumero().substring(4, 8) + source.getNumero().substring(9, 13));

        return telefone;
    }

}
