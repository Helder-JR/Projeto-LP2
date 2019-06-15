package Entidades;

import java.util.Arrays;
import java.util.HashSet;

public abstract class ProjetoLegislativoAbstract implements Projeto {

    protected String autor;
    private int ano;
    protected String codigo;
    protected String ementa;
    private HashSet<String> interesses;
    protected String situacaoAtual;
    protected String local;
    private String enderecoDocumento;

    public ProjetoLegislativoAbstract(String dni, int ano, String ementa, String interesses, String url, String codigo) {
        this.autor = dni;
        this.ano = ano;
        this.codigo = codigo;
        this.ementa = ementa;
        this.interesses = new HashSet<>(Arrays.asList(interesses.split(",")));
        this.situacaoAtual = "EM VOTACAO (CCJC)";
        this.local = "CCJC";
        this.enderecoDocumento = url;
    }

    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String proximoLocal) {
        this.local = proximoLocal;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public HashSet<String> getInteresses() {
        return interesses;
    }

    public String getSituacaoAtual() {
        return situacaoAtual;
    }
}
