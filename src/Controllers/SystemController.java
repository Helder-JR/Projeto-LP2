package Controllers;

import Entidades.Deputado;
import Entidades.Pessoa;
import Validadores.ValidaSystemController;

import java.util.*;

public class SystemController {

    private Map<String, Pessoa> cadastroPessoas;
    private Set<String> partidosGovernistas;
    private ValidaSystemController validador;

    public SystemController() {
        this.cadastroPessoas = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.validador = new ValidaSystemController();
    }

    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        Pessoa pessoa = new Pessoa(nome, dni, estado, interesses, partido);
        this.cadastroPessoas.put(dni, pessoa);
        return true;
    }

    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        Pessoa pessoa = new Pessoa(nome, dni, estado, interesses);
        this.cadastroPessoas.put(dni, pessoa);
        return true;
    }

    public boolean cadastrarDeputado(String dni, String dataDeInicio) {
        Deputado funcao = new Deputado(dni,dataDeInicio);
        this.cadastroPessoas.get(dni).setFuncao(funcao);
        return true;
    }

    public boolean cadastrarPartido(String partido) {
        validador.validaCadastrarPartido(partido);
        this.partidosGovernistas.add(partido);
        return true;
    }

    public String exibirPessoa(String dni) {
        return this.cadastroPessoas.get(dni).toString();
    }

    public String exibirBase() {
        ArrayList<String> listaPartidos = new ArrayList<>(this.partidosGovernistas);
        listaPartidos.sort(null);
        return String.join(",",listaPartidos);
    }
}
