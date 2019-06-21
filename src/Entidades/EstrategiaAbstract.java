package Entidades;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class EstrategiaAbstract implements Estrategia{

    String desempatePorData(ArrayList<Projeto> arrayProjetos){
        arrayProjetos.sort(Comparator.comparing(Projeto::getAno).thenComparing(Projeto::getCodigo));
        return arrayProjetos.get(0).toString();
    }
}