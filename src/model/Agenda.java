
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private ProfissionalSaude responsavel;
    private List<HoraAtendimento> hora;

    public Agenda(ProfissionalSaude responsavel) {
        this.responsavel = responsavel;
        this.hora = new ArrayList<>();
    }
    
    public List<HoraAtendimento> getHora() {
        return new ArrayList<>(hora);
    }

    public ProfissionalSaude getResponsavel() {
        return responsavel;
    }



    public boolean adicionarHorario(HoraAtendimento novoHorario) {

    for (HoraAtendimento h : hora) {

        if (h.getData().equals(novoHorario.getData())) {

            if (novoHorario.getHoraInicio().isBefore(h.getHoraFim()) &&
                novoHorario.getHoraFim().isAfter(h.getHoraInicio())) {

                return false;
            }
        }
    }

    hora.add(novoHorario);
    return true;
}

public boolean verificarDisponibilidade(LocalDate data, LocalTime inicio, LocalTime fim) {

    if (!responsavel.getStatus()) {
        return false;
    }

    for (HoraAtendimento h : hora) {

        if (h.getData().equals(data) &&
            h.isDisponivel() &&
            !inicio.isBefore(h.getHoraInicio()) &&
            !fim.isAfter(h.getHoraFim())) {

            return true;
        }
    }

    return false;
}

public List<HoraAtendimento> listarHorariosDisponiveis() {

    List<HoraAtendimento> disponiveis = new ArrayList<>();

    if (!responsavel.getStatus()) {
        return disponiveis;
    }

    for (HoraAtendimento h : hora) {
        if (h.isDisponivel()) {
            disponiveis.add(h);
        }
    }

    return disponiveis;
}
public boolean ocuparHorario(LocalDate data, LocalTime inicio, LocalTime fim) {

    for (HoraAtendimento h : hora) {

        if (h.getData().equals(data) &&
            h.isDisponivel() &&
            h.getHoraInicio().equals(inicio) &&
            h.getHoraFim().equals(fim)) {

            h.ocuparHorario();
            return true;
        }
    }

    return false;
}

}