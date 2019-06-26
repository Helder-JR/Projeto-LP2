package Entidades;

import java.util.ArrayList;
import java.util.HashSet;

public class Aprovacao extends EstrategiaAbstract {

    @Override
    public String selecionaProjeto(HashSet<Projeto> projetos) {
        ArrayList<Projeto> arrayProjetos = new ArrayList<>();
        int max = 0;
        for (Projeto projeto : projetos) {
            int cont = 0;
            for (String tramitacao : projeto.getTramitacao().split(", ")) {
                if (tramitacao.split(" ")[0].equals("APROVADO")) {
                    cont++;
                }
            }
            if (cont > max) {
                max = cont;
                arrayProjetos.clear();
                arrayProjetos.add(projeto);
            }
            if (cont == max) {
                arrayProjetos.add(projeto);
            }
        }
        if (arrayProjetos.size() == 1) {
            return arrayProjetos.get(0).getCodigo();
        } else {
            return desempatePorData(arrayProjetos);
        }
    }
}
