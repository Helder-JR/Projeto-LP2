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


    private void setSituacaoCCJC(boolean resultado, String proximoLocal) {
        if (conclusivo) {
            if (resultado) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "ARQUIVADO";
            }
        } else {
            if (resultado) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.local = proximoLocal;
            } else {
                this.situacaoAtual = "ARQUIVADO";
            }
        }
    }

    private void setsituacao(boolean resultado, String proximoLocal) {
        if (conclusivo) {
            if (!"-".equals(proximoLocal)) {
                if (resultado) {
                    this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                } else {
                    this.situacaoAtual = "ARQUIVADO";
                }
            } else {
                if (resultado) {
                    this.situacaoAtual = "APROVADO";
                } else {
                    this.situacaoAtual = "ARQUIVADO";
                }
            }
        } else {
            if (!"plenario".equals(this.local)) {
                this.situacaoAtual = String.format("EM VOTACAO (%s)", proximoLocal);
                this.local = proximoLocal;
            } else {
                if (resultado) {
                    this.situacaoAtual = "APROVADO";
                } else {
                    this.situacaoAtual = "ARQUIVADO";
                }
            }

        }
    }

    @Override
    public void setSituacaoAtual(boolean resultado, String proximoLocal) {
        if ("CCJC".equals(this.local)) {
            setSituacaoCCJC(resultado, proximoLocal);
        } else {
            setsituacao(resultado, proximoLocal);
        }
    }

    @Override
    public boolean quorumMinimo(int deputadosPresentes, int totalDeputados) {
        return (deputadosPresentes >= floor(totalDeputados/2)+1);
    }

    @Override
    public boolean calculaVotoMinimo(int deputadosPresentes, int totalVotos) {
        return (totalVotos >= floor(deputadosPresentes/2.0)+1);
    }
}
