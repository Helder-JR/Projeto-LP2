package Controllers;

import Entidades.Deputado;
import Entidades.Pessoa;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Controlador que gerencia as entidades presentes no sistema. É possível cadastrar e exibir pessoas (seja um civil ou
 * um(a) deputado(a)) e partidos.
 */
public class ECOController implements Serializable {

    /**
     * Mapa que irá conter informações a respeito das pessoas cadastradas no sistema.
     */
    private Map<String, Pessoa> cadastroPessoas;

    /**
     * Conjunto que irá conter informações a respeito dos partidos cadastrados no sistema.
     */
    private Set<String> partidosGovernistas;

    /**
     * Objeto responsável por verificar se os dados a serem passados no cadastro de pessoas e/ou partidos são válidos.
     */
    private ValidaSystemController validador;

    /**
     * Cria um controlador do sistema que será responsável por alocar os cadastros de pessoas e partidos, além do objeto
     * que irá validar as entradas de dados.
     */
    public ECOController() {
        this.cadastroPessoas = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.validador = new ValidaSystemController();
    }

    /**
     * Cadastra uma pessoa com afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @param partido o partido político ao qual essa pessoa está afiliada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throw IllegalArgumentException caso a pessoa já esteja cadastrada.
     */
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

    /**
     * Cadastra uma pessoa sem afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throw IllegalArgumentException caso a pessoa já esteja cadastrada.
     */
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

    /**
     * Cadastra um(a) deputado(a) no mapa de pessoas, verificando inicialmente se o DNI passado como parâmetro é valido,
     * e depois se há uma pessoa cadastrada com esse DNI no mapa. Após isso será verificada se a data de início do
     * mandato é valida e se esse(a) deputado(a) está afiliado a algum partido.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @param dataDeInicio a data de início do mandato dessa pessoa.
     * @return um booleano true caso as entradas sejam válidas, o DNI esteja vinculado a uma pessoa e essa pessoa
     * pertença a um partido.
     * @throws IllegalArgumentException caso a pessoa não esteja afiliada a um partido.
     * @throws NullPointerException caso a pessoa não esteja cadastrada no mapa.
     * @throws ParseException caso a data de início do mandato esteja em um formato inválido.
     */
    public boolean cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.validador.validaCadastraDeputadoDni(dni);
        if (this.cadastroPessoas.containsKey(dni)) {
            this.validador.validaCadastrarDeputado(dataDeInicio);
            if (!"".equals(this.cadastroPessoas.get(dni).getPartido())) {
                Deputado funcao = new Deputado(dataDeInicio);
                this.cadastroPessoas.get(dni).setFuncao(funcao);
                return true;
            } else {
                throw new IllegalArgumentException("Erro ao cadastrar deputado: pessoa sem partido");
            }
        } else {
            throw new NullPointerException("Erro ao cadastrar deputado: pessoa nao encontrada");
        }
    }

    /**
     * Cadastra um partido, antes verificando se o nome do partido é uma entrada válida para então o alocar no conjunto.
     *
     * @param partido o partido a ser cadastrado.
     * @return
     */
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
}
