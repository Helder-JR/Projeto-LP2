package Controllers;


import Entidades.Pessoa;
import Entidades.Projeto;
import Validacao.ValidaSystemController;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Controlador que gerencia as entidades presentes no sistema. É possível cadastrar e exibir pessoas (seja um civil ou
 * um(a) deputado(a)), além de partidos.
 */
public class ECOController implements Serializable {

    private LegislativoController legislativoController;
    private PessoaController pessoaController;

    /**
     * Objeto responsável por verificar se os dados a serem passados no cadastro de pessoas e/ou partidos são válidos.
     */
    private ValidaSystemController validador;



    /**
     * Cria um controlador do sistema que será responsável por alocar os cadastros de pessoas e partidos, além do objeto
     * que irá validar as entradas de dados.
     */
    public ECOController() {
        this.pessoaController = new PessoaController();
        this.legislativoController = new LegislativoController(this.pessoaController.getTotalDeputados());
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
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        return pessoaController.cadastrarPessoa(nome, dni, estado, interesses, partido);
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
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        return pessoaController.cadastrarPessoa(nome, dni, estado, interesses);
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
     * @throws NullPointerException caso o DNI seja uma String nula ou a pessoa não esteja cadastrada no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas por espaços e caso a pessoa
     * não esteja afiliada a um partido.
     * @throws ParseException caso a data de início do mandato esteja em um formato inválido.
     */
    public boolean cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.validador.validaCadastraDeputadoDni(dni);
        return pessoaController.cadastrarDeputado(dni, dataDeInicio);
    }

    /**
     * Cadastra um partido, antes verificando se o nome do partido é uma entrada válida para então o alocar no conjunto.
     *
     * @param partido o partido a ser cadastrado.
     * @return um booleano true caso o partido seja alocado com sucesso, ou false caso não seja.
     * @throws NullPointerException caso o partido seja uma String nula.
     * @throws IllegalArgumentException caso o partido seja uma String vazia ou composta apenas por espaços.
     */
    public boolean cadastrarPartido(String partido) {
        this.validador.validaCadastrarPartido(partido);
        return legislativoController.cadastrarPartido(partido);
    }

    /**
     * Exibe uma pessoa presente no cadastro, validando inicialmente se o DNI dessa pessoa é uma String válida e ela
     * esteja cadastrada no mapa. Caso essas condições não sejam satisfeitas exceções serão lançadas.
     *
     * @param dni o DNI da pessoa a ser exibida.
     * @return a String referente a representação dessa pessoa presente no mapa.
     * @throws NullPointerException caso o DNI seja uma String nula ou a pessoa não esteja presente no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas de espaços.
     */
    public String exibirPessoa(String dni) {
        this.validador.validaExibirPessoa(dni);
        return pessoaController.exibirPessoa(dni);
    }

    /**
     * Exibe uma lista com os partidos cadastrados no sistema, exibidos em ordem lexicográfica.
     *
     * @return a String referente a listagem dos partidos, separados um a um por vírgula.
     */
    public String exibirBase() {
        return legislativoController.exibirBase();
    }

    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 2

    /**
     * Cadastra uma comissão, com pase no seu tema e nos políticos que irão a compôr. Inicialmente será validado se o
     * tema e os políticos são entradas válidas para que o cadastro ocorra de fato.
     *
     * @param tema o tema que a comissão irá tratar.
     * @param politicos os políticos que irão fazer parte da comissão.
     * @throws NullPointerException caso o tema ou os políticos sejam Strings nulas.
     * @throws IllegalArgumentException caso o tema e os políticos sejam Strings vazias ou compostas apenas de espaços.
     */
    public void cadastrarComissao(String tema, String politicos) {
        this.validador.validaCadastrarComissao(tema, politicos);
        legislativoController.cadastrarComissao(tema, politicos, this.pessoaController.getPessoas());
    }

    /**
     * Cadastra um projeto de lei no sistema. verificando inicialmente se as entradas são válidas.
     *
     * @param dni o DNI da pessoa que criou o projeto de lei.
     * @param ano o ano em que esse projeto de lei foi criado/proposto.
     * @param ementa a ementa que possui a descrição do projeto.
     * @param interesses o conjunto de interesses que fazem parte do projeto.
     * @param url o endereço da internet que possui o documento com a descrição completa do projeto.
     * @param conclusivo o estado de tramitação do projeto.
     * @return o código referente a esse projeto de lei.
     * @throws NullPointerException caso uma das entradas seja nula.
     * @throws IllegalArgumentException caso alguma das entradas seja uma String vazia ou composta apenas por espaços.
     * @throws ParseException caso o ano de criação do projeto seja inválido.
     */
    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) throws ParseException {
        this.validador.validaCadastrarPL(dni, ano, ementa, interesses, url, conclusivo);
        return legislativoController.cadastrarPL(dni, ano, ementa, interesses, url, conclusivo, this.pessoaController.getPessoas());
    }

    /**
     * Cadastra um projeto de lei complementar no sistema, inicialmente validando se as entradas são válidas para que o
     * cadastro ocorra de fato.
     *
     * @param dni o DNI da pessoa que propôs o projeto.
     * @param ano o ano em que o projeto foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses a que aborda o projeto.
     * @param url o endereço da internet que contém o documento com a descrição completa do projeto.
     * @param artigos os artigos que são complementados por este projeto.
     * @return o código referente ao projeto.
     * @throws NullPointerException caso alguma das entradas seja nula.
     * @throws IllegalArgumentException caso alguma das entradas seja uma String vazia ou composta apenas de espaços.
     * @throws ParseException caso o ano em que o projeto tenha sido criado não seja uma data válida.
     */
    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos) throws ParseException {
        this.validador.validaCadastrarPLPouPEC(dni, ano, ementa, interesses, url, artigos);
        return legislativoController.cadastrarPLP(dni, ano, ementa, interesses, url, artigos, this.pessoaController.getPessoas());
    }

    /**
     * Cadastra um projeto de emenda constitucional no sistema, validando as entradas antes do cadastro em si.
     *
     * @param dni o DNI da pessoa que criou o projeto.
     * @param ano o ano em que o projeto foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses abordados pelo projeto.
     * @param url o endereço da internet que contém o documento com a descrição completa do projeto.
     * @param artigos os artigos que são emendados pelo projeto.
     * @return o código referente ao projeto.
     * @throws NullPointerException caso alguma das entradas seja nula.
     * @throws IllegalArgumentException caso alguma das entradas seja uma String vazia ou composta apenas de espaços.
     * @throws ParseException caso o ano de criação do projeto não seja uma data válida.
     */
    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) throws ParseException {
        this.validador.validaCadastrarPLPouPEC(dni, ano, ementa, interesses, url, artigos);
        return legislativoController.cadastrarPEC(dni, ano, ementa, interesses, url, artigos, this.pessoaController.getPessoas());
    }

    /**
     * Exibe uma proposta legislativa, levando em consideração seu código como base da consulta.
     *
     * @param codigo o código que servirá para a consulta da proposta legislativa no sistema.
     * @return a representação em String da proposta consultada.
     */
    public String exibirProjeto(String codigo) {
        return legislativoController.exibirProjeto(codigo);
    }

    /**
     * Vota uma proposta por uma comissão, considerando inicialmente a validação das entradas para que a votação em si
     * ocorra.
     *
     * @param codigo o código referente a proposta legislativa a ser votada.
     * @param statusGovernista a situação de apoio que a proposta possui (GOVERNISTA, OPOSIÇÃO ou LIVRE).
     * @param proximoLocal o próximo local onde a proposta será votada.
     * @throws NullPointerException caso alguma das entradas seja nula.
     * @throws IllegalArgumentException caso alguma das entradas seja vazia ou composta apenas por espaços.
     * @return um booleano true caso a votação seja aprovada ou false caso contrário.
     */
    public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal) {
        this.validador.validaVotaComissao(codigo, statusGovernista, proximoLocal);
        return legislativoController.votarComissao(codigo, statusGovernista, proximoLocal, this.pessoaController.getPessoas());
    }

    /**
     * Vota uma proposta em plenário, levando em consideração inicialmente a validação das entradas, de modo a serem
     * lançadas exceções caso se faça necessário.
     *
     * @param codigo o código da proposta que será votada.
     * @param statusGovernista a situação de apoio que a proposta possui (GOVERNISTA, OPOSIÇÃO ou LIVRE).
     * @param presentes os(as) deputados(as) presentes no momento da votação.
     * @return um booleano true caso a votação seja aprovada ou false caso contrário.
     */
    public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
        return legislativoController.votarPlenario(codigo, statusGovernista, presentes, this.pessoaController.getPessoas());
    }

    /**
     * Exibe o status de tramitação de uma proposta legislativa através de seu código.
     *
     * @param codigo o código referente a proposta.
     * @return a String referente a situação atual em que se encontra a proposta.
     */
    public String exibirTramitacao(String codigo) {
        return legislativoController.exibirTramitacao(codigo);
    }

    /**
     *
     * @param dni
     * @param estrategia
     */
    public void configurarEstrategia(String dni, String estrategia){

    }

    public String pegarPropostaRelacionada(String dni) {
        Pessoa pessoa = this.pessoaController.getPessoa(dni);
        HashSet<String> interessesPessoa = pessoa.getInteresses();
        HashSet<Projeto> projetoInteressesEmComum = this.legislativoController.pegarPropostaRelacionada(interessesPessoa);
        if (projetoInteressesEmComum.size() == 0)
            return "Sem interesses em comum";
        if (projetoInteressesEmComum.size() == 1) {
            for (Projeto projeto : projetoInteressesEmComum) return projeto.toString();
        } else {
            return pessoa.escolheProjeto(projetoInteressesEmComum);
        }
        throw new IllegalArgumentException("Algo errado");
    }
}
