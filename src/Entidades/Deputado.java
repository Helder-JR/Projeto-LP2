package Entidades;

import java.util.Objects;

public class Deputado implements Funcao {

    private String dni;
    private String dataDeInicio;
    private int LeisAprovadas;

    public Deputado(String dni, String dataDeInicio) {
        this.dni = dni;
        this.dataDeInicio = dataDeInicio;
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

    @Override
    public String toString() {
        return "Deputado{" +
                "dni='" + dni + '\'' +
                ", dataDeInicio='" + dataDeInicio + '\'' +
                ", LeisAprovadas=" + LeisAprovadas +
                '}';
    }
}
