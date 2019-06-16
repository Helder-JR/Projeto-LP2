package Entidades;

import static java.lang.Math.floor;

public class ProjetoEmendaConstitucional extends ProjetoLegislativoAbstract {

    private String artigos;

    public ProjetoEmendaConstitucional(String dni, int ano, String ementa, String interesses, String url, String artigos, String codigo) {
        super(dni, ano, ementa, interesses, url, codigo);
        this.artigos = artigos;
    }

    public String toString() {
        return String.format("Projeto de Emenda Constitucional - %s - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.artigos, this.situacaoAtual);
    }

    @Override
    public boolean quorumMinimo(int deputadosPresentes, int totalDeputados) {
        return (deputadosPresentes >= floor(3*totalDeputados/5)+1);
    }

    @Override
    public boolean calculaVotoMinimo(int totalDeputados, int totalVotos) {
        return (totalVotos >= floor(3*totalDeputados/5)+1);
    }
}
