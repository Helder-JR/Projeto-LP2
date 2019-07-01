package Entidades;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Estratégia que pode ser utilizada para pegar a proposta mais relacionada a uma pessoa. Nesse caso a ordem se dá com
 * as propostas que tem maior aprovação na sua tramitação.
 */
public class Aprovacao extends EstrategiaAbstract {


    /**
     * Seleciona o projeto mais adequado a uma pessoa, de maneira que aquele que estiver mais perto do seu término deve
     * ser escolhido.
     *
     * @param projetos um conjunto de propostas legislativas que são do interesse de uma pessoa.
     * @return a String que representa o código referente a proposta constitucional.
     */
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
            } else
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
