package Entidades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class Aprovacao extends EstrategiaAbstract {

    public String selecionaProjeto(HashSet<Projeto> projetos){
            ArrayList<Projeto> arrayProjetos = new ArrayList<>();
            for (Projeto projeto : projetos) {
                if (projeto.getSituacaoAtual().equals("EM VOTACAO (Plenario - 2o turno)")) {
                    arrayProjetos.add(projeto);
                }
            }
            if (arrayProjetos.isEmpty()) {
                for (Projeto projeto : projetos) {
                    if (projeto.getSituacaoAtual().equals("EM VOTACAO (Plenario - 1o turno)")) {
                        arrayProjetos.add(projeto);
                    }
                }
            } else {
                arrayProjetos.retainAll(projetos);
            }

            if (arrayProjetos.size() == 1) {
                return arrayProjetos.get(0).toString();
            } else {
                return desempatePorData(arrayProjetos);
            }
        }
}
