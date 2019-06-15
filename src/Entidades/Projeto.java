package Entidades;

import java.io.Serializable;
import java.util.HashSet;

public interface Projeto extends Serializable {

    String toString();
    HashSet<String> getInteresses();
    String getSituacaoAtual();
    boolean calculaVotoMinimo(int quantidadeDeputados, int totalVotos);
    void setSituacaoAtual(boolean resultado, String proximoLocal);

}
