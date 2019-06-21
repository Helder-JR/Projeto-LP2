package Entidades;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Representação da interface de uma proposta legislativa, que poderá tornar-se um projeto de lei, um projeto de lei
 * complementar ou um projeto de emenda constitucional. Basicamente é possível exibir a representação textual dessa
 * proposta, saber os interesses que ela aborda, recuperar e alterar também a informação referente a sua situação atual,
 * local e autor, além de calcular a quantidade mínima de votos para sua aprovação, bem como o quórum mínimo.
 */
public interface Projeto extends Serializable {

    /**
     * Exibe a representação textual referente a uma proposta legislativa, variando de acordo com o tipo de projeto.
     *
     * @return a String que representa a proposta legislativa.
     */
    String toString();

    /**
     * Recupera o conjunto (HashSet) de interesses pertencentes a proposta.
     *
     * @return o conjunto de Strings referentes aos interesses da proposta.
     */
    HashSet<String> getInteresses();

    /**
     * Recupera a informação referente a situação atual a que se encontra a proposta, podendo variar entre "ARQUIVADA",
     * "APROVADA" ou "EM VOTAÇÃO".
     *
     * @return a String que se refere a situação atual da proposta legislativa.
     */
    String getSituacaoAtual();

    /**
     * Calcula se a quantidade de votos da proposta é o mínimo necessário para que ela seja aprovada.
     *
     * @param quantidadeDeputados a quantidade de deputados(as) que fazem parte da proposta.
     * @param totalVotos o total de votos que a proposta legislativa obteve.
     * @return um booleano true caso o mínimo de votos necessários para aprovação seja atingido, do contrário false.
     */
    boolean calculaVotoMinimo(int quantidadeDeputados, int totalVotos);

    /**
     * Altera a situação atual a que a proposta legislativa se encontra, variando de acordo com cada projeto.
     *
     * @param resultado o resultado da votação, sendo true caso a proposta seja aprovada ou false caso contrário.
     * @param proximoLocal o próximo local em que a proposta será votada.
     */
    void setSituacaoAtual(boolean resultado, String proximoLocal);

    /**
     * Recupera a informação referente ao autor da proposta legislativa.
     *
     * @return a String que representa o autor da proposta.
     */
    String getAutor();

    /**
     * Recupera a informação referente ao local onde a proposta será votada.
     *
     * @return a String que representa o local de votação da proposta legislativa.
     */
    String getLocal();

    /**
     * Altera o local referente a onde a proposta será votada.
     *
     * @param proximoLocal o próximo local que irá votar a proposta.
     */
    void setLocal(String proximoLocal);

    /**
     * Define se a quantidade de deputados(as) presentes é o necessário para que a votação seja válida.
     *
     * @param deputadosPresentes a quantidade de deputados(as) presentes atualmente.
     * @param totalDeputados o total de deputados.
     * @return um booleano true caso o quórum mínimo seja atingido, ou false caso contrário.
     */
    boolean quorumMinimo(int deputadosPresentes, int totalDeputados);

    /**
     *
     * @return
     */
    String getTipoProjeto();

    /**
     *
     * @return
     */
    int getAno();

    /**
     *
     * @return
     */
    String getCodigo();
}
