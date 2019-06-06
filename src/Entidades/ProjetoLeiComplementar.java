package Entidades;

import static java.lang.Math.floor;

public class ProjetoLeiComplementar extends ProjetoLegislativoAbstract {

    private String artigos;

    public ProjetoLeiComplementar(String dni, int ano, String ementa, String interesses, String url, String artigos, String codigo) {
        super(dni, ano, ementa, interesses, url, codigo);
        this.artigos = artigos;
    }

    public String toString() {
        return String.format("Projeto de Lei Complementar - %s - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.artigos, this.situacaoAtual);
    }

    @Override
    public boolean calculaVotoMinimo(int totalDeputados, int totalVotos) {
        return (totalVotos >= floor(totalDeputados/2)+1);
    }
}
