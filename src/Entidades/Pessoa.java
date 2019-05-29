package Entidades;

import java.util.Objects;

/**
 * Representação de uma pessoa no E-CO, com informações referentes a nome, DNI, estado, interesses em relação a política,
 * partido a qual está afiliada e sua função, caso exerça cargo político. É possível exibir uma representação textual da mesma e compará-la a outros objetos.
 */
public class Pessoa {

    private String nome;
    private String dni;
    private String estado;
    private String interesses;
    private String partido;
    private Funcao funcao;

    /**
     * Cria uma pessoa com base em seu nome, DNI, estado, interesses e partido.
     *
     * @param nome o nome da pessoa.
     * @param dni o documento nacional de identificação da pessoa.
     * @param estado o estado em que a pessoa reside.
     * @param interesses os interesses políticos dessa pessoa.
     * @param partido o partido político a que essa pessoa está afiliada.
     */
    public Pessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.nome = nome;
        this.dni = dni;
        this.interesses = interesses;
        this.estado = estado;
        this.partido = partido;
        this.funcao = new Civil(dni);
    }

    /**
     * Cria uma pessoa com base em seu nome, DNI, estado e interesses políticos.
     *
     * @param nome o nome da pessoa.
     * @param dni o documento nacional de identificação da pessoa.
     * @param estado o estado em que a pessoa reside.
     * @param interesses os interesses políticos dessa pessoa.
     */
    public Pessoa(String nome, String dni, String estado, String interesses) {
        this(nome, dni, estado, interesses,"");
    }

    /**
     * Altera a função política a qual a pessoa exerce.
     *
     * @param funcao define a função política da pessoa.
     */
    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    /**
     * Compara um objeto do tipo Pessoa com outro objeto e verifica se são iguais.
     *
     * @param o o objeto que séra comparado à pessoa.
     * @return um booleano true caso a comparação resulte em igualdade ou um false, caso negativo.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.nome) &&
                Objects.equals(dni, pessoa.dni) &&
                Objects.equals(estado, pessoa.estado) &&
                Objects.equals(interesses, pessoa.interesses) &&
                Objects.equals(partido, pessoa.partido) &&
                funcao.equals(pessoa.funcao);
    }

    /**
     * Calcula o hashCode de um objeto do tipo Pessoa.
     *
     * @return um inteiro hexadecimal referente ao hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, dni, estado, interesses, partido, funcao);
    }

    /**
     * Exibe uma representação textual referente a uma pessoa.
     *
     * @return a String que representa textualmente essa pessoa.
     */
    public String toString() {
        return this.funcao.toString(this.nome, this.estado, this.partido, this.interesses);
    }
}
