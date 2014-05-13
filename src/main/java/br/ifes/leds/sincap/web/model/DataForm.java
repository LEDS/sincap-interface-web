/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.leds.sincap.web.model;

import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author leds-6752
 */
@Getter
@Setter
public class DataForm {
    private Calendar dataIni;
    private Calendar dataFim;
}
