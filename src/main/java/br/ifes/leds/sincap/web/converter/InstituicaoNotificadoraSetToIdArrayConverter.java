/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.converter;

import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import java.util.HashSet;
import java.util.Set;
import org.dozer.DozerConverter;

/**
 * Classe que converte um <code>Set<InstituicaoNotificadora></code> para um
 * <code>Long[]</code> contendo os ids das Instituições notificadoras.
 *
 * @author phillipe
 */
public class InstituicaoNotificadoraSetToIdArrayConverter extends DozerConverter<Set, Long[]> {

    public InstituicaoNotificadoraSetToIdArrayConverter() {
        super(Set.class, Long[].class);
    }

    @Override
    public Long[] convertTo(Set source, Long[] destination) {
        Long[] instituicoesArray = new Long[source.size()];

        int pos = 0;
        for (InstituicaoNotificadora instituicao : (Set<InstituicaoNotificadora>) source) {
            instituicoesArray[pos] = instituicao.getId();
            pos++;
        }

        return instituicoesArray;
    }

    @Override
    public Set convertFrom(Long[] source, Set destination) {
        Set<InstituicaoNotificadora> instituicoesSet = new HashSet<>();
        InstituicaoNotificadora instituicaoTemp = new InstituicaoNotificadora();

        for (Long id : source) {
            instituicaoTemp.setId(id);
            instituicoesSet.add(instituicaoTemp);
        }

        return instituicoesSet;
    }
}
