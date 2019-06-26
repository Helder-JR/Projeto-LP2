package Entidades;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class EstrategiaAbstract implements Estrategia{

    protected String desempatePorData(ArrayList<Projeto> arrayProjetos){
        arrayProjetos.sort(Comparator.comparing(Projeto::getAno).thenComparing(Projeto::getCodigoGlobal));
        return arrayProjetos.get(0).getCodigo();
    }
}