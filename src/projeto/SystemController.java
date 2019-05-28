package projeto;

import java.util.*;

public class SystemController {

    private Map<String, Pessoa> cadastroPessoas;
    private Set<String> partidosGovernistas;

    public SystemController() {
        this.cadastroPessoas = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
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
        this.cadastroPessoas.get(dni).setFuncao("Deputado", dataDeInicio);
        return true;
    }

    public boolean cadastrarPartido(String partido) {
        this.partidosGovernistas.add(partido);
        return true;
    }

    public String exibePessoa(String dni) {
        return this.cadastroPessoas.get(dni).toString();
    }

    public String exibeBase() {
        ArrayList<String> listaPartidos = new ArrayList<>(this.partidosGovernistas);
        listaPartidos.sort(null);
        return String.join(",",listaPartidos);
    }
}
