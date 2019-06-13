package Controllers;

import Entidades.Deputado;
import Entidades.Pessoa;
import Validacao.ValidaDeputado;
import Validacao.ValidaPessoa;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class PessoaController implements Serializable {
    /**
     * Mapa que irá conter informações a respeito das pessoas cadastradas no sistema.
     */
    private HashMap<String, Pessoa> pessoas;

    private ValidaPessoa validaPessoa;

    private ValidaDeputado validaDeputado;

    private int totalDeputados;

    public PessoaController() {
        this.pessoas = new HashMap<>();
        this.totalDeputados = 0;
        this.validaPessoa = new ValidaPessoa();
        this.validaDeputado = new ValidaDeputado();
    }

    public HashMap<String, Pessoa> getPessoas() {
        return pessoas;
    }

    public int getTotalDeputados() {
        return totalDeputados;
    }

    /**
     * Cadastra uma pessoa com afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome       o nome da pessoa que será cadastrada.
     * @param dni        o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado     o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @param partido    o partido político ao qual essa pessoa está afiliada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throws NullPointerException     caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     *                                  e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.validaPessoa.validaCadastrarPessoa(nome, dni, estado);
        if (!this.pessoas.containsKey(dni)) {
            Pessoa pessoa = new Pessoa(nome, dni, estado, interesses, partido);
            this.pessoas.put(dni, pessoa);
            return true;
        } else {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni ja cadastrado");
        }
    }

    /**
     * Cadastra uma pessoa sem afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome       o nome da pessoa que será cadastrada.
     * @param dni        o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado     o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throws NullPointerException     caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     *                                  e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.validaPessoa.validaCadastrarPessoa(nome, dni, estado);
        if (!this.pessoas.containsKey(dni)) {
            Pessoa pessoa = new Pessoa(nome, dni, estado, interesses);
            this.pessoas.put(dni, pessoa);
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
     * @param dni          o DNI da pessoa que será cadastrada como deputado(a).
     * @param dataDeInicio a data de início do mandato dessa pessoa.
     * @return um booleano true caso as entradas sejam válidas, o DNI esteja vinculado a uma pessoa e essa pessoa
     * pertença a um partido.
     * @throws NullPointerException     caso o DNI seja uma String nula ou a pessoa não esteja cadastrada no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas por espaços e caso a pessoa
     *                                  não esteja afiliada a um partido.
     * @throws ParseException           caso a data de início do mandato esteja em um formato inválido.
     */
    public boolean cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.validaDeputado.validaCadastraDeputadoDni(dni);
        if (this.pessoas.containsKey(dni)) {
            validaDeputado.validaCadastrarDeputado(dataDeInicio);
            if (!"".equals(this.pessoas.get(dni).getPartido())) {
                Deputado funcao = new Deputado(dataDeInicio);
                this.pessoas.get(dni).setFuncao(funcao);
                this.totalDeputados += 1;
                return true;
            } else {
                throw new IllegalArgumentException("Erro ao cadastrar deputado: pessoa sem partido");
            }
        } else {
            throw new NullPointerException("Erro ao cadastrar deputado: pessoa nao encontrada");
        }
    }

    /**
     * Exibe uma pessoa presente no cadastro, validando inicialmente se o DNI dessa pessoa é uma String válida e ela
     * esteja cadastrada no mapa. Caso essas condições não sejam satisfeitas exceções serão lançadas.
     *
     * @param dni o DNI da pessoa a ser exibida.
     * @return a String referente a representação dessa pessoa presente no mapa.
     * @throws NullPointerException     caso o DNI seja uma String nula ou a pessoa não esteja presente no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas de espaços.
     */
    public String exibirPessoa(String dni) {
        if (this.pessoas.containsKey(dni)) {
            return this.pessoas.get(dni).toString();
        } else {
            throw new NullPointerException("Erro ao exibir pessoa: pessoa nao encontrada");
        }
    }
}