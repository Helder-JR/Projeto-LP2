package Entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class ProjetoLegislativoAbstract implements Projeto {

    protected String autor;
    private int ano;
    protected String codigo;
    protected String ementa;
    private HashSet<String> interesses;
    protected String situacaoAtual;
    protected String local;
    private String enderecoDocumento;
    protected List<String> tramitacao;

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

    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
        if ("CCJC".equals(this.local)) {
            if (resultado) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "ARQUIVADO";
            }
        } else {
            if (!"plenario".equals(this.local)) {
                if (!"plenario".equals(proximoLocal)) {
                    this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                    this.local = proximoLocal;
                } else {
                    this.situacaoAtual = "EM VOTACAO (Plenario - 1o turno)";
                    this.local = "plenario";
                }
            } else {
                if ("EM VOTACAO (Plenario - 2o turno)".equals(this.situacaoAtual)) {
                    if (resultado) {
                        this.situacaoAtual = "APROVADO";
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
