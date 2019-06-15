package Entidades;

import Validacao.ValidaPessoa;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representação de uma pessoa no E-CO, com informações referentes a nome, DNI, estado, interesses em relação a política,
 * partido a qual está afiliada e sua função, caso exerça cargo político. É possível exibir uma representação textual da
 * mesma, compará-la a outros objetos, definir sua função pública e recuperar a informação sobre seu partido.
 */
public class Pessoa implements Serializable {

    /**
     * O nome da pessoa.
     */
    private String nome;

    /**
     * O Documento Nacional De Identificação da Pessoa.
     */
    private String dni;

    /**
     * O estado em que a pessoa reside.
     */
    private String estado;

    /**
     * Os interesses políticos da pessoa.
     */
    private String interesses;

    /**
     * O partido a qual a pessoa possivelmente está afiliada.
     */
    private String partido;

    /**
     * A função pública que essa pessoa exerce.
     */
    private Funcao funcao;

    /**
     * Quantidade de leis que conseguiu fazer serem aprovadas.
     */
    private Integer leisAprovadas;

    private ValidaPessoa validaPessoa;

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
        this.validaPessoa = new ValidaPessoa();
        this.validaPessoa.validaCadastrarPessoa(nome, dni, estado);
        this.nome = nome;
        this.dni = dni;
        this.interesses = interesses;
        this.estado = estado;
        this.partido = partido;
        this.funcao = new Civil();
        this.leisAprovadas = 0;
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

    public Set<String> getInteresses() {
        return new HashSet<>(Arrays.asList(this.interesses.split(",")));
    }

    /**
     * Recupera a informação referente ao partido dessa pessoa.
     *
     * @return o partido a qual a pessoa está afiliada.
     */
    public String getPartido() {
        return partido;
    }

    /**
     * Compara um objeto Pessoa a outro objeto e verifica se são iguais.
     *
     * @param o o objeto que séra comparado a pessoa.
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
                Objects.equals(partido, pessoa.partido);
    }

    /**
     * Calcula o hashCode de um objeto do tipo Pessoa.
     *
     * @return um inteiro hexadecimal referente ao hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, dni, estado, interesses, partido);
    }

    /**
     * Exibe uma representação textual referente a uma pessoa.
     *
     * @return a String que representa textualmente essa pessoa.
     */
    public String toString() {
        return this.funcao.toString(this.nome, this.dni, this.estado, this.partido, this.interesses);
    }

    public int getLeisAprovadas() {
        return leisAprovadas;
    }

    public void aprovaLei() {
        this.leisAprovadas += 1;
    }

    public String exibeFuncao() {
        return this.funcao.getFuncao();
    }
}
