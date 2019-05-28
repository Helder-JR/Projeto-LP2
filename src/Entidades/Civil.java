package Entidades;

import java.util.Objects;

public class Civil implements Funcao {

    private String dni;

    public Civil(String dni) {
        this.dni = dni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Civil civil = (Civil) o;
        return dni.equals(civil.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    public String toString(String nome, String estado, String partido, String interesses) {
        if ("".equals(partido.trim()) && !"".equals(interesses.trim())) {
            return String.format("POL: %s - %s (%s) - Interesses: %s", nome, this.dni, estado, interesses);
        }
        if ("".equals(interesses.trim()) && !"".equals(partido.trim())) {
            return String.format("POL: %s - %s (%s) - %s", nome, this.dni, estado, partido);
        }
        return String.format("POL: %s - %s (%s) - %s - Interesses: %s", nome, this.dni, estado, partido, interesses);
    }
}
