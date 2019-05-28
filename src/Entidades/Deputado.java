package Entidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Deputado implements Funcao {

    private String dni;
    private Date dataDeInicio;
    private int LeisAprovadas;

    public Deputado(String dni, String dataDeInicio) throws ParseException {
        this.dni = dni;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        this.dataDeInicio = dateFormat.parse(dataDeInicio);
        this.LeisAprovadas = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deputado deputado = (Deputado) o;
        return LeisAprovadas == deputado.LeisAprovadas &&
                dni.equals(deputado.dni) &&
                dataDeInicio.equals(deputado.dataDeInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, dataDeInicio, LeisAprovadas);
    }

    public String toString(String nome, String estado, String partido, String interesses) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if ("".equals(partido.trim()) && !"".equals(interesses.trim())) {
            return String.format("POL: %s - %s (%s) - Interesses: %s - %s - %s Leis", nome, this.dni, estado, interesses, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);
        }
        if ("".equals(interesses.trim()) && !"".equals(partido.trim())) {
            return String.format("POL: %s - %s (%s) - %s - %s - %s Leis", nome, this.dni, estado, partido, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);
        }
        return String.format("POL: %s - %s (%s) - %s - Interesses: %s - %s - %s Leis", nome, this.dni, estado, partido, interesses, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);
    }
}
