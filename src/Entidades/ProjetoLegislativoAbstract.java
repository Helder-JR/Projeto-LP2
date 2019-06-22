package Entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Classe abstrata responsável por implementar a inteface projeto. Serve de base para a PEC, PL e PLP.
 */
public abstract class ProjetoLegislativoAbstract implements Projeto {

    /**
     * O autor da proposta legislativa.
     */
    protected String autor;

    /**
     * O ano em que essa proposta foi criada.
     */
    private int ano;

    /**
     * O código referentea a proposta legislativa.
     */
    protected String codigo;

    /**
     * A ementa com a descrição sobre do que a proposta se trata.
     */
    protected String ementa;

    /**
     * O conjunto de interesses que a proposta trata.
     */
    private HashSet<String> interesses;

    /**
     * A situação atual em que a proposta legislativa se encontra.
     */
    protected String situacaoAtual;

    /**
     * O local (comissão / plenário) onde a proposta irá ser votada.
     */
    protected String local;

    /**
     * O endereço na internet que possui um documento que contém o teor, a lei e a justificativa referente a proposta.
     */
    private String enderecoDocumento;

    /**
     * Uma lista com as tramitações que a proposta possui.
     */
    protected List<String> tramitacao;

    /**
     *
     */
    protected String tipoProjeto;

    /**
     * Cria uma proposta legislativa.
     *
     * @param dni o DNI da pessoa que criou a proposta.
     * @param ano o ano em que a proposta foi criada.
     * @param ementa a ementa que descreve a proposta.
     * @param interesses os interesses que a proposta trata.
     * @param url o enderaço na internet para o documento que contém a descrição completa da proposta.
     * @param codigo o código que identifica essa proposta.
     */
    public ProjetoLegislativoAbstract(String dni, int ano, String ementa, String interesses, String url, String codigo) {
        this.autor = dni;
        this.ano = ano;
        this.codigo = codigo;
        this.ementa = ementa;
        this.interesses = new HashSet<>(Arrays.asList(interesses.split(",")));
        this.situacaoAtual = "EM VOTACAO (CCJC)";
        this.local = "CCJC";
        this.enderecoDocumento = url;
        this.tramitacao = new ArrayList<>();
    }

    /**
     * Altera a situação atual do projeto de lei, com base no local onde está sendo votado.
     *
     * @param resultado o resultado da votação, sendo true caso a proposta seja aprovada ou false caso contrário.
     * @param proximoLocal o próximo local em que a proposta será votada.
     */
    @Override
    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
        if ("CCJC".equals(this.local)) {
            setSituacaoCCJC(resultado, proximoLocal);
        } else {
            setSituacao(resultado, proximoLocal);
        }
    }

    /**
     * Altera a situação atual em que se encontra a proposta.
     *
     * @param resultado o resultado da votação, sendo true caso a proposta seja aprovada ou false caso contrário.
     * @param proximoLocal o próximo local em que a proposta será votada.
     */
    private void setSituacaoCCJC(boolean resultado, String proximoLocal) {
        if ("CCJC".equals(this.local)) {
            if (resultado) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.tramitacao.add("APROVADO (CCJC)");
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "ARQUIVADO";
                this.tramitacao.add("REJEITADO (CCJC)");
            }
        }
    }

    private void setSituacaoAtualNormal(boolean resultado, String proximoLocal) {
        if (!"plenario".equals(proximoLocal)) {
            this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
            this.local = proximoLocal;
        } else {
            this.situacaoAtual = "EM VOTACAO (Plenario - 1o turno)";
            this.local = "plenario";
        }
    }

    private void setSituacao(boolean resultado, String proximoLocal) {
        if (!"plenario".equals(this.local)) {
            setSituacaoAtualNormal(resultado, proximoLocal);

        } else {
            if ("EM VOTACAO (Plenario - 2o turno)".equals(this.situacaoAtual)) {
                if (resultado) {
                    this.situacaoAtual = "APROVADO";
                    this.tramitacao.add(String.format("APROVADO (%s - 2o turno)", this.local));
                    this.local = "-";
                } else {
                    this.situacaoAtual = "ARQUIVADO";
                }
            } else {
                if (resultado) {
                    this.situacaoAtual = "EM VOTACAO (Plenario - 2o turno)";
                    this.local = "plenario";
                } else {
                    this.situacaoAtual = "ARQUIVADO";
                }
            }
        }
    }

    /**
     * Recupera a informação referente ao local em que a proposta é votada.
     *
     * @return a String que representa o local de votação da proposta.
     */
    public String getLocal() {
        return this.local;
    }

    /**
     * Altera o local em que a proposta será votada.
     *
     * @param proximoLocal o próximo local (comissão ou plenário) que irá votar a proposta.
     */
    public void setLocal(String proximoLocal) {
        this.local = proximoLocal;
    }

    /**
     * Recupera a informação referente a pessoa autora da proposta legislativa.
     *
     * @return a String que representa o autor da proposta.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * Recupera a informação referente ao conjunto de interesses que fazem parte da proposta.
     *
     * @return um HashSet de Strings com os interesses defendidos pela proposta.
     */
    @Override
    public HashSet<String> getInteresses() {
        return this.interesses;
    }

    /**
     * Recupera a informação referente a situação atual em que a proposta se encontra.
     *
     * @return a String que representa a situação atual da proposta.
     */
    public String getSituacaoAtual() {
        return this.situacaoAtual;
    }

    /**
     *
     * @return
     */
    public String getTipoProjeto(){
        return this.tipoProjeto;
    }

    /**
     *
     * @return
     */
    public int getAno() {
        return this.ano;
    }

    /**
     *
     * @return
     */
    public String getCodigo() {
        return this.codigo;
    }
}
