package Entidades;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Estratégia que pode ser utilizada para pegar a proposta mais relacionada a uma pessoa. Nesse caso a ordem segue com
 * PEC's, depois PLP's e por último PL's.
 */
public class Constitucional extends EstrategiaAbstract {

    /**
     * Seleciona o projeto mais adequado a uma pessoa, seguindo a regra de PEC primeiro, PLP em segundo e PL por último.
     *
     * @param projetos um conjunto de propostas legislativas que são do interesse de uma pessoa.
     * @return a String que representa o código referente a proposta constitucional.
     */
    public String selecionaProjeto(HashSet<Projeto> projetos) {
        ArrayList<Projeto> arrayProjetos = new ArrayList<>();
        for (Projeto projeto : projetos) {
            if (projeto.getTipoProjeto().equals("PEC")) {
                arrayProjetos.add(projeto);
            }
        }
        if (arrayProjetos.size() == 1) {
            return arrayProjetos.get(0).getCodigo();
        } else if (arrayProjetos.size() > 1) {
            return desempatePorData(arrayProjetos);
        } else {
            for (Projeto projeto : projetos) {
                if (projeto.getTipoProjeto().equals("PLP")) {
                    arrayProjetos.add(projeto);
                }
            }
            if (arrayProjetos.size() == 1) {
                return arrayProjetos.get(0).getCodigo();
            } else if (arrayProjetos.size() > 1) {
                return desempatePorData(arrayProjetos);
            } else {
                arrayProjetos.addAll(projetos);
            }
        }
        if (arrayProjetos.size() == 1) {
            return arrayProjetos.get(0).getCodigo();
        } else {
            return desempatePorData(arrayProjetos);
        }
    }
}
