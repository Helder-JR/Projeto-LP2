package Entidades;

import java.util.HashSet;

public interface Projeto {

    String toString();
    HashSet<String> getInteresses();
    String getSituacaoAtual();

}
