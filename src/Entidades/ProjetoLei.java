package Entidades;

import static java.lang.Math.floor;

public class ProjetoLei extends ProjetoLegislativoAbstract {

    private boolean conclusivo;

    public ProjetoLei(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo, String codigo) {
        super(dni, ano, ementa, interesses, url, codigo);
        this.conclusivo = conclusivo;
    }

    public String toString() {
        String toString;
        if (this.conclusivo) {
           toString = String.format("Projeto de Lei - %s - %s - %s - Conclusiva - %s", this.codigo, this.autor, this.ementa, this.situacaoAtual);
        } else {
            toString = String.format("Projeto de Lei - %s - %s - %s - %s", this.codigo, this.autor, this.ementa, this.situacaoAtual);
        }
        return toString;
    }

    @Override
    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
        if (this.conclusivo) {
            if (resultado) {
                this.situacaoAtual = "APROVADO";
            } else {
                this.situacaoAtual = "ARQUIVADO";
            }
        } else {
            if (resultado) {
                this.situacaoAtual = "APROVADO " + this.local;
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "REJEITADO " + this.local;
                this.local = proximoLocal;
            }
        }
    }

    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes, int totalVotos) {
        return (totalVotos >= floor(deputadosPresentes/2)+1);
    }
}
