package Entidades;

import java.util.ArrayList;
import java.util.HashSet;

public class Constitucional extends EstrategiaAbstract {

    public String selecionaProjeto(HashSet<Projeto> projetos) {
        ArrayList<Projeto> arrayProjetos = new ArrayList<>();
        for (Projeto projeto : projetos) {
            if (projeto.getTipoProjeto().equals("PEC")) {
                arrayProjetos.add(projeto);
            }
        }
        if (arrayProjetos.isEmpty()) {
            for (Projeto projeto : projetos) {
                if (projeto.getTipoProjeto().equals("PLP")) {
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
