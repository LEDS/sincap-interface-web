package br.ifes.leds.sincap.web.test;

import br.ifes.leds.sincap.web.utility.UtilityWeb;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class UtilityWebTest extends AbstractionTest {

    @Autowired
    UtilityWeb utilityWeb;

    @Test
    public void getIdadeTest() {
        Date data1 = new LocalDate(1989,12,21).toDate();
        Date data2 = new LocalDate(2014,10,7).toDate();
        Date data3 = new LocalDate(2014,12,22).toDate();
        assertThat(utilityWeb.getIdade(data1,data2), equalTo(24));
        assertThat(utilityWeb.getIdade(data1, data3), equalTo(25));
        assertThat(utilityWeb.getIdade(data1,data3), not(24));
    }

}
