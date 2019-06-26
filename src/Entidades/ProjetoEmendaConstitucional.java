package Entidades;

import static java.lang.Math.floor;

/**
 * Representação de um projeto de emenda constitucional. Possui além dos atributos de uma proposta legislativa os
 * artigos a qual essa P.E.C. emenda na constituição.
 */
public class ProjetoEmendaConstitucional extends ProjetoLegislativoAbstract {

    /**
     * Os artigos da constituição que vão ser emendados.
     */
    private String artigos;

    /**
     * Cria um objeto do tipo Projeto de Emenda Constitucional.
     *
     * @param dni o DNI da pessoa que criou esse projeto.
     * @param ano o ano em que o projeto foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses tratados no projeto.
     * @param url o endereço da internet que contém o documento com a descrição do projeto.
     * @param artigos os artigos da constituição que serão complementados pelo projeto.
     * @param codigo o código que serve para identificar esse projeto.
     */
    public ProjetoEmendaConstitucional(String dni, int ano, String ementa, String interesses, String url, String artigos, String codigo, int codigoGlobal) {
        super(dni, ano, ementa, interesses, url, codigo, codigoGlobal);
        this.artigos = artigos;
        this.tipoProjeto = "PEC";
    }

    /**
     * A representação textual do projeto de emenda constitucional, seguindo seguinte formato:
     * "Projeto de Emenda Constitucional - Código - Autor - Ementa - Artigos - Situação Atual"
     *
     * @return a String que representa a PEC.
     */
    public String toString() {
        return String.format("Projeto de Emenda Constitucional - %s - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.artigos, this.situacaoAtual);
    }

    /**
     * Verifica se a quantidade de deputados(as) presentes é o necessário para que uma votação seja válida. Para que
     * isso aconteça a quantidade de deputados(as) presentes deve ser maior ou igual a 3/5 do total, acrescida uma
     * unidade.
     *
     * @param deputadosPresentes a quantidade de deputados(as) presentes atualmente.
     * @param totalDeputados o total de deputados.
     * @return um booleano true caso a quantidade mínima de deputados(as) presentes é o ncecessário pra que a votação
     * seja válida, ou false caso contráiro.
     */
    @Override
    public boolean quorumMinimo(int deputadosPresentes, int totalDeputados) {
        return (deputadosPresentes >= floor(3*totalDeputados/5)+1);
    }

    /**
     * Verifica se o total de votos que o projeto recebeu é o necessário para que ele seja aprovado. Para que isso
     * acontaça a quantidade total de votos deve ser maior ou igual a 3/5 da quantidade total de deputados(as),
     * acrescida de uma unidade.
     *
     * @param totalDeputados o total de deputados(as) que participaram da votação.
     * @param totalVotos o total de votos que a proposta legislativa obteve.
     * @return um booleano true caso a quantidade mínima de votos seja atingida, ou false caso contrário.
     */
    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes , int totalDeputados, int totalVotos) {
        return (totalVotos >= floor(3*totalDeputados/5)+1);
    }
}
