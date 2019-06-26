package Entidades;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Representação da interface responsável por designar a estratégia mais adequada para exibir as propostas legislativas
 * de maior interesse de uma pessoa.
 */
public interface Estrategia extends Serializable {

    /**
     * Seleciona a proposta legislativa mais adequada ao interesse de uma pessoa.
     *
     * @param projetos um conjunto de propostas legislativas que são do interesse de uma pessoa.
     * @return a String com o código referente a essa proposta em específico.
     */
    String selecionaProjeto(HashSet<Projeto> projetos);

}