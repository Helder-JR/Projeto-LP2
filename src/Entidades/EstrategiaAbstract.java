package Entidades;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Classe abstrata responsável por implementar a interface Estrategia. Serve de base para as estratégias CONSTITUCIONAL,
 * CONCLUSAO e APROVACAO.
 */
public abstract class EstrategiaAbstract implements Estrategia{

    /**
     * Faz com que duas ou mais estratégias relativamente iguais desempatem de acordo com qual das duas é mais antiga.
     *
     * @param arrayProjetos os projetos que passarão pelo desempate.
     * @return a String referente ao código da proposta escolhida seguindo o critério.
     */
    protected String desempatePorData(ArrayList<Projeto> arrayProjetos){
        arrayProjetos.sort(Comparator.comparing(Projeto::getAno).thenComparing(Projeto::getCodigoGlobal));
        return arrayProjetos.get(0).getCodigo();
    }
}