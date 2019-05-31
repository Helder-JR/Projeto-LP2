package Entidades;

/**
 * Representação de um civil, uma função que uma pessoa pode ter. Além de implementar os métodos da interface Função um
 * civil possui um DNI, que será o mesmo que a pessoa que possui essa função tem.
 */
public class Civil implements Funcao {

    /**
     * Exibe uma representação textual referente a uma função pública de uma pessoa.
     *
     * @param nome o nome da pessoa.
     * @param estado o estado da pessoa.
     * @param partido o partido a qual a pessoa está afiliada.
     * @param interesses os interesses políticos dessa pessoa.
     * @return uma String referente a representação textual dessa pessoa.
     */
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
