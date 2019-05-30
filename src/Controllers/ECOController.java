package Controllers;

import Entidades.Deputado;
import Entidades.Pessoa;

import java.text.ParseException;
import java.util.*;

public class ECOController {

    private Map<String, Pessoa> cadastroPessoas;
    private Set<String> partidosGovernistas;
    private ValidaSystemController validador;

    public ECOController() {
        this.cadastroPessoas = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.validador = new ValidaSystemController();
    }

    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        if (!this.cadastroPessoas.containsKey(dni)) {
            Pessoa pessoa = new Pessoa(nome, dni, estado, interesses, partido);
            this.cadastroPessoas.put(dni, pessoa);
            return true;
        } else {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni ja cadastrado");
        }
    }

    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        if (!this.cadastroPessoas.containsKey(dni)) {
            Pessoa pessoa = new Pessoa(nome, dni, estado, interesses);
            this.cadastroPessoas.put(dni, pessoa);
            return true;
        } else {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni ja cadastrado");
        }
    }

    public boolean cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        validador.validaCadastraDeputadoDni(dni);
        if (this.cadastroPessoas.containsKey(dni)) {
            this.validador.validaCadastrarDeputado(dataDeInicio);
            if (!"".equals(this.cadastroPessoas.get(dni).getPartido())) {
                Deputado funcao = new Deputado(dni,dataDeInicio);
                this.cadastroPessoas.get(dni).setFuncao(funcao);
                return true;
            } else {
                throw new IllegalArgumentException("Erro ao cadastrar deputado: pessoa sem partido");
            }
        } else {
            throw new NullPointerException("Erro ao cadastrar deputado: pessoa nao encontrada");
        }
    }

    public boolean cadastrarPartido(String partido) {
        this.validador.validaCadastrarPartido(partido);
        this.partidosGovernistas.add(partido);
        return true;
    }

    public String exibirPessoa(String dni) {
        this.validador.validaExibirPessoa(dni);
        if (this.cadastroPessoas.containsKey(dni)) {
            return this.cadastroPessoas.get(dni).toString();
        } else {
            throw new NullPointerException("Erro ao exibir pessoa: pessoa nao encontrada");
        }
    }

    public String exibirBase() {
        ArrayList<String> listaPartidos = new ArrayList<>(this.partidosGovernistas);
        listaPartidos.sort(String::compareTo);
        return String.join(",",listaPartidos);
    }

    public void salvarSistema() {

    }

    public void carregarSistema() {

    }

    public void limparSistema() {

    }
}