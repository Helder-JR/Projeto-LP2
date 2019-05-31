package Entidades;

import java.util.Objects;

/**
 * Representação de um civil, uma função que uma pessoa pode ter. Além de implementar os métodos da interface Função um
 * civil possui um DNI, que será o mesmo que a pessoa que possui essa função tem.
 */
public class Civil implements Funcao {

    /**
     * O DNI do civil.
     */
    private String dni;

    /**
     * Cria um civil com base no DNI da pessoa que exerce esssa função.
     *
     * @param dni o dni do civil.
     */
    public Civil(String dni) {
        this.dni = dni;
    }

    /**
     * Compara esse objeto com outro e verifica se são iguais.
     *
     * @param o o objeto que será comparado a função.
     * @return um booleano true caso os objetos sejam iguais ou false, caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Civil civil = (Civil) o;
        return dni.equals(civil.dni);
    }

    /**
     * Calcula o hashCode de um objeto do tipo Função.
     *
     * @return um inteiro hexadecimal referente ao hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    /**
     * Exibe uma representação textual referente a uma função pública de uma pessoa.
     *
     * @param nome o nome da pessoa.
     * @param estado o estado da pessoa.
     * @param partido o partido a qual a pessoa está afiliada.
     * @param interesses os interesses políticos dessa pessoa.
     * @return uma String referente a representação textual dessa pessoa.
     */
    public String toString(String nome, String estado, String partido, String interesses) {
        if ("".equals(partido.trim()) && !"".equals(interesses.trim())) {
            return String.format("%s - %s (%s) - Interesses: %s", nome, this.dni, estado, interesses);
        }
        if ("".equals(interesses.trim()) && !"".equals(partido.trim())) {
            return String.format("%s - %s (%s) - %s", nome, this.dni, estado, partido);
        }
        if ("".equals(interesses.trim()) && "".equals(partido.trim())) {
            return String.format("%s - %s (%s)", nome, this.dni, estado);
        }
        return String.format("%s - %s (%s) - %s - Interesses: %s", nome, this.dni, estado, partido, interesses);
    }
}
