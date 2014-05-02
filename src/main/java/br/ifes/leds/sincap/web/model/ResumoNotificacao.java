package br.ifes.leds.sincap.web.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/**
 * Contém as informações principais de uma notificação. Será usado para empacotar
 * os principais dados de uma notificiação em um único objeto. Para evitar que seja
 * necessário percorrer as relações de um objeto notificação.
 * 
 * @author lucas
 */
public class ResumoNotificacao {
    private long id;
    private String codigo;
    private String nomePaciente;
    private String tempo;

    public ResumoNotificacao(long id, String codigo, String nomePaciente, Calendar dataNotificacao) {
        this.id = id;
        this.codigo = codigo;
        this.nomePaciente = nomePaciente;
        
        // Criar o formatador da data.
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        
        // Pega a data de agora.
        long abertura = dataNotificacao.getTimeInMillis();
        long agora = Calendar.getInstance().getTimeInMillis();
                
        // Faz a diferença entre a hora atual e a hora em que foi notificado.
        LocalTime horaInicio = new LocalTime(abertura);
        LocalTime horaAgora = new LocalTime(agora);
        Period periodo = new Period(horaInicio, horaAgora);
        
        // Pega a duração.
        DateTime dataInicio = new DateTime(abertura);
        DateTime dataAgora = new DateTime(agora);
        Duration duration = new Duration(dataInicio, dataAgora);
        
        String horas = String.valueOf(periodo.getHours());
        String minutos = String.valueOf(periodo.getMinutes());
        
        Long dias = new Long(duration.getStandardDays());
        
        if (dias > 0) {
            this.tempo = dias.toString() + " dias " + horas + "h " + minutos + "min";
        } else {
            this.tempo = horas + "h " + minutos + "min";
        }
    }

    public long getId() {
        return id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }
    
    public String getTempo() {
        return tempo;
    }
}
