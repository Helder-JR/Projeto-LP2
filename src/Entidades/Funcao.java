package Entidades;

import java.io.Serializable;

/**
 * Representação da interface de uma função pública que será atribuída a uma pessoa, que será implementada como civil ou
 * como um deputado. Será possível compará-la a outros objetos, calcular seu hashCode e exibir uma representação textual
 * da mesma.
 */
public interface Funcao extends Serializable {

    /**
     * Compara esse objeto com outro e verifica se são iguais.
     *
     * @param o o objeto que será comparado a função.
     * @return um booleano true caso os objetos sejam iguais ou false, caso contrário.
     */
    boolean equals(Object o);

    /**
     * Calcula o hashCode de um objeto do tipo Função.
     *
     * @return um inteiro hexadecimal referente ao hashCode.
     */
    int hashCode();

    /**
     * Exibe uma representação textual referente a uma função pública de uma pessoa.
     *
     * @param nome o nome da pessoa.
     * @param dni o Documento Nacional de Identificação da pessoa.
     * @param estado o estado em que a pessoa reside.
     * @param partido o partido a qual a pessoa está afiliada.
     * @param interesses os interesses políticos dessa pessoa.
     * @return uma String referente a representação textual dessa pessoa.
     */
    String toString(String nome, String dni, String estado, String partido, String interesses);

}
