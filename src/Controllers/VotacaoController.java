package Controllers;

import Entidades.Pessoa;
import Entidades.Projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class VotacaoController implements Serializable {

    public boolean votarComissao(Projeto projeto, String statusGovernista, ArrayList<String> deputadosComissao, String proximoLocal, HashMap<String, Pessoa> pessoas, int totalDeputados, HashSet<String> partidosGovernistas) {
        int votos = controlaVoto(statusGovernista, deputadosComissao, projeto, pessoas, partidosGovernistas);
        boolean resultado = (votos >= Math.floor(deputadosComissao.size() / 2) + 1);
        projeto.setSituacaoAtual(resultado, proximoLocal);
        return resultado;
    }

    public boolean votarPlenario(Projeto projeto, String statusGovernista, String presentes, HashMap<String, Pessoa> pessoas, int totalDeputados, HashSet<String> partidosGovernistas) {
        ArrayList<String> deputadosPresentes = new ArrayList<String>(Arrays.asList(presentes.split(",")));
        int votos = controlaVoto(statusGovernista, deputadosPresentes, projeto, pessoas, partidosGovernistas);
        boolean resultado = projeto.calculaVotoMinimo(totalDeputados, votos);
        if (resultado)
            pessoas.get(projeto.getAutor()).aprovaLei();
        return resultado;
    }

    private int controlaVoto(String statusGovernista, ArrayList<String> listaDeputado, Projeto projeto, HashMap<String, Pessoa> pessoas, HashSet<String> partidosGovernistas) {
        int votos = 0;
        switch (statusGovernista) {
            case "GOVERNISTA":
                votos = votarProjetoGovernista(listaDeputado, pessoas, partidosGovernistas);
                break;
            case "OPOSICAO":
                votos = votarProjetoOposicao(listaDeputado, pessoas, partidosGovernistas);
                break;
            case "LIVRE":
                votos = votarProjetoLivre(projeto, listaDeputado, pessoas);
                break;
            default:
        }
        return votos;
    }//auxiliar de menuVoto

    private int votarProjetoGovernista(ArrayList<String> deputadosComissao, HashMap<String, Pessoa> pessoas, HashSet<String> partidosGovernistas) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = pessoas.get(dni);
            if (partidosGovernistas.contains(pessoa.getPartido())) votos++;
        }
        return votos;
    }//auxiliar de menuVoto

    private int votarProjetoOposicao(ArrayList<String> deputadosComissao, HashMap<String, Pessoa> pessoas, HashSet<String> partidosGovernistas) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = pessoas.get(dni);
            if (!partidosGovernistas.contains(pessoa.getPartido())) votos++;
        }
        return votos;
    }//auxiliar de menuVoto

    private int votarProjetoLivre(Projeto projeto, ArrayList<String> deputadosComissao, HashMap<String, Pessoa> pessoas) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = pessoas.get(dni);
            if (votoLivre(pessoa, projeto)) votos++;
        }
        return votos;
    }//auxiliar de votarProjetoLivre

    private boolean votoLivre(Pessoa pessoa, Projeto projeto) {
        HashSet<String> copiaInteresseProjeto = new HashSet<String>(projeto.getInteresses());
        copiaInteresseProjeto.retainAll(pessoa.getInteresses());
        return !copiaInteresseProjeto.isEmpty();
    }

}