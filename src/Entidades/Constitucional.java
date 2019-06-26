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
