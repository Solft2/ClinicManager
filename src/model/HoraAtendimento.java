package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HoraAtendimento {

    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private boolean disponivel;

    public HoraAtendimento(LocalDate data, LocalTime horaInicio, LocalTime horaFim){
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.disponivel = true;
    }
    
    public LocalDate getData(){
        return data;
    }
    public LocalTime getHoraInicio(){
        return horaInicio;
    }
    public LocalTime getHoraFim(){
        return horaFim;
    }
    public boolean isDisponivel(){
        return disponivel;
    }
      public void ocuparHorario() {
        this.disponivel = false;
    }

    public void liberarHorario() {
        this.disponivel = true;
    }


}
