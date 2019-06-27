package Entidades;

import static java.lang.Math.floor;

/**
 * Representação de um Projeto de Lei Complementar. Assim como o projeto de emenda constitucional também possui um
 * atributo relacionado aos artigos, no entanto esses são os artigos complementados por esse projeto.
 */
public class ProjetoLeiComplementar extends ProjetoLegislativoAbstract {

    /**
     * Os artigos da constituição que serão complementados por este projeto.
     */
    private String artigos;

    /**
     * Cria um objeto do tipo Projeto de Lei Complementar.
     *
     * @param dni o DNI da pessoa que porpôs esse projeto.
     * @param ano o ano em que o projeto foi criado/proposto.
     * @param ementa a ementa com a descriçaõ do projeto.
     * @param interesses o conjunto de interesses que o projeto defende.
     * @param url o endereço da internet que contém o documento com a descrição do projeto.
     * @param artigos os artigos da constituição que esse projteo complementa.
     * @param codigo o código que identifica esse projeto de lei complementar.
     * @param codigoGlobal o código que identifica esse projeto de lei dentro do sistema.
     */
    public ProjetoLeiComplementar(String dni, int ano, String ementa, String interesses, String url, String artigos, String codigo, int codigoGlobal) {
        super(dni, ano, ementa, interesses, url, codigo, codigoGlobal);
        this.artigos = artigos;
        this.tipoProjeto = "PLP";
    }

    /**
     * A representação textual do projeto de emenda constitucional, seguindo seguinte formato:
     * "Projeto de Lei Complementar - Código - Autor - Ementa - Artigos - Situação Atual"
     *
     * @return a String que representa a PLP.
     */
    public String toString() {
        return String.format("Projeto de Lei Complementar - %s - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.artigos, this.situacaoAtual);
    }

    /**
     * Verifica se a quantidade de deputados(as) presentes é o necessário para que uma votação seja válida. Para que
     * isso aconteça a quantidade de deputados(as) presentes deve ser maior ou igual a 50% do total, acrescida uma
     * unidade.
     *
     * @param deputadosPresentes a quantidade de deputados(as) presentes atualmente.
     * @param totalDeputados o total de deputados.
     * @return um booleano true caso a quantidade mínima de deputados(as) presentes é o ncecessário pra que a votação
     * seja válida, ou false caso contráiro.
     */
    @Override
    public boolean quorumMinimo(int deputadosPresentes, int totalDeputados) {
        return (deputadosPresentes >= floor(totalDeputados/2)+1);
    }

    /**
     * Verifica se o total de votos que o projeto recebeu é o necessário para que ele seja aprovado. Para que isso
     * acontaça a quantidade total de votos deve ser maior ou igual a 50% da quantidade total de deputados(as),
     * acrescida de uma unidade.
     *
     * @param totalDeputados o total de deputados(as) que participaram da votação.
     * @param totalVotos o total de votos que a proposta legislativa obteve.
     * @return um booleano true caso a quantidade mínima de votos seja atingida, ou false caso contrário.
     */
    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes , int totalDeputados, int totalVotos) {
        return (totalVotos >= floor(totalDeputados/2)+1);
    }
}
