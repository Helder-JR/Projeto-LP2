package Entidades;

public class Civil implements Funcao {

    public String toString(String nome, String dni, String estado, String partido, String interesses) {
        String toString = String.format("%s - %s (%s) - %s - Interesses: %s", nome, dni, estado, partido, interesses);

        if ("".equals(partido.trim()) && !"".equals(interesses.trim()))
            toString = String.format("%s - %s (%s) - Interesses: %s", nome, dni, estado, interesses);

        else if ("".equals(interesses.trim()) && !"".equals(partido.trim()))
            toString = String.format("%s - %s (%s) - %s", nome, dni, estado, partido);

        else if ("".equals(interesses.trim()) && "".equals(partido.trim()))
            toString = String.format("%s - %s (%s)", nome, dni, estado);

        return toString;
    }
}
