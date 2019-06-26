package Entidades;

import java.io.Serializable;
import java.util.HashSet;

public interface Estrategia extends Serializable {



    String selecionaProjeto(HashSet<Projeto> projetos);

}