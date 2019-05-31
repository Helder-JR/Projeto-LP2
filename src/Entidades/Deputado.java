package Entidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Deputado implements Funcao {

    private Date dataDeInicio;
    private int LeisAprovadas;

    public Deputado(String dni, String dataDeInicio) throws ParseException {
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
                dataDeInicio.equals(deputado.dataDeInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataDeInicio, LeisAprovadas);
    }

    public String toString(String nome, String dni, String estado, String partido, String interesses) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String toString = String.format("POL: %s - %s (%s) - %s - Interesses: %s - %s - %s Leis", nome, dni, estado, partido, interesses, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);

        if ("".equals(partido.trim()) && !"".equals(interesses.trim()))
            toString = String.format("POL: %s - %s (%s) - Interesses: %s - %s - %s Leis", nome, dni, estado, interesses, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);

        else if ("".equals(interesses.trim()) && !"".equals(partido.trim()))
            toString = String.format("POL: %s - %s (%s) - %s - %s - %s Leis", nome, dni, estado, partido, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);

        else if ("".equals(interesses.trim()) && "".equals(partido.trim()))
            toString = String.format("POL: %s - %s (%s) - %s - %s Leis", nome, dni, estado, dateFormat.format(this.dataDeInicio), this.LeisAprovadas);

        return toString;
    }
}
