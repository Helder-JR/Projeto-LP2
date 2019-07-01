package Entidades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Estratégia que pode ser utilizada para pegar a proposta mais relacionada a uma pessoa. Nesse caso a ordem segue que
 * as propostas que estão mais próximas do término devem ser exibidas primeiro.
 */
public class Conclusao extends EstrategiaAbstract {

    /**
     * Seleciona o projeto mais adequado a uma pessoa, de forma que o que estiver mais perto do término deve ser o
     * escolhido.
     *
     * @param projetos um conjunto de propostas legislativas que são do interesse de uma pessoa.
     * @return a String que representa o código referente a proposta constitucional.
     */
    @Override
    public String selecionaProjeto(HashSet<Projeto> projetos) {
        ArrayList<Projeto> arrayProjetos = new ArrayList<>();
        for (Projeto projeto : projetos) {
            if (projeto.getSituacaoAtual().equals("EM VOTACAO (Plenario - 2o turno)") || projeto.getSituacaoAtual().equals("EM VOTACAO (Plenario)")) {
                arrayProjetos.add(projeto);
            }
        }
        if (arrayProjetos.size() == 1) {
            return arrayProjetos.get(0).getCodigo();
        } else if (arrayProjetos.size() > 1) {
            return desempatePorData(arrayProjetos);
        } else {
            for (Projeto projeto : projetos) {
                if (projeto.getSituacaoAtual().equals("EM VOTACAO (Plenario - 1o turno)")) {
                    arrayProjetos.add(projeto);
                }
            }
            if (arrayProjetos.size() == 1) {
                return arrayProjetos.get(0).getCodigo();
            } else if (arrayProjetos.size() > 1) {
                return desempatePorData(arrayProjetos);
            } else {
                int max = 0;
                for (Projeto projeto : projetos) {

                    if (projeto.getTramitacao().split(", ").length == max) {
                        arrayProjetos.add(projeto);
                    } else
                    if (projeto.getTramitacao().split(", ").length > max) {
                        arrayProjetos.clear();
                        arrayProjetos.add(projeto);
                        max = projeto.getTramitacao().split(", ").length;
                    }
                }
            }
        }
        if (arrayProjetos.size() == 1) {
            return arrayProjetos.get(0).getCodigo();
        } else {
            return desempatePorData(arrayProjetos);
        }
    }
}
