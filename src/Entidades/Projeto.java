package Entidades;

import java.util.HashSet;

public interface Projeto {

    String toString();
    HashSet<String> getInteresses();
    String getSituacaoAtual();
    boolean calculaVotoMinimo(int quantidadeDeputados, int totalVotos);
    void setSituacaoAtual(boolean resultado, String proximoLocal);

}
