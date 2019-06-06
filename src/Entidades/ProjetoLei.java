package Entidades;

import static java.lang.Math.floor;

public class ProjetoLei extends ProjetoLegislativoAbstract {

    private boolean conclusivo;

    public ProjetoLei(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo, String codigo) {
        super(dni, ano, ementa, interesses, url, codigo);
        this.conclusivo = conclusivo;
    }

    public String toString() {
        return String.format("Projeto de Lei Complementar - %s - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.conclusivo, this.situacaoAtual);
    }

    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes, int totalVotos) {
        return (totalVotos >= floor(deputadosPresentes/2)+1);
    }
}
