package Controllers;

import easyaccept.EasyAccept;
import util.Dados;
import java.io.File;
import java.text.ParseException;

/**
 * Fachada do sistema, onde o programa será iniciado. É possivel executar ações relacionadas ao cadastramento e exibição
 * de pessoas, partidos, bases governistas e deputados(as), além de carregar, armazenar e carregar o estado atual do
 * sistema.
 */
public class Facade {

    /**
     * Objeto esponsável pela persistência em arquivos das informçaões referentes ao sistema.
     */
    private Dados dados;

    /**
     * Controlador que irá gerenciar os dados do sistema (e que terá seus dados armazenados em arquivos).
     */
    private ECOController ecoController;

    /**
     * Objeto que irá receber o nome da pasta que irá salvar os arquivos do sistema.
     */
    private File folder;
    /**
     * Objeto que irá receber o caminho para o arquivo que armazenará o estado atual do controlador do sistema.
     */
    private File save;

    /**
     * Objeto que irá armazenar o caminho para o arquivo que contém o controller do sistema em seu estado inicial.
     */
    private File reset;

    /**
     * Cria a fachada (onde será instanciado o controller e definidos os caminhos para os arquivos do sistema).
     */
    public Facade() {
        this.dados = new Dados();
        this.ecoController = new ECOController();
        this.folder = new File("files");
        if (!this.folder.exists()) {
            this.folder.mkdir();
        }
        if (this.folder.exists() && this.folder.isDirectory()) {
            this.save = new File("files/save.data");
            this.reset = new File("files/reset.data");
        }
        this.dados.salvar(this.ecoController,this.reset);
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
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.ecoController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    /**
     * Cadastra uma pessoa sem afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.ecoController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    /**
     * Cadastra um(a) deputado(a) no mapa de pessoas, verificando inicialmente se o DNI passado como parâmetro é valido,
     * e depois se há uma pessoa cadastrada com esse DNI no mapa. Após isso será verificada se a data de início do
     * mandato é valida e se esse(a) deputado(a) está afiliado a algum partido.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @param dataDeInicio a data de início do mandato dessa pessoa.
     * pertença a um partido.
     * @throws NullPointerException caso o DNI seja uma String nula ou a pessoa não esteja cadastrada no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas por espaços e caso a pessoa
     * não esteja afiliada a um partido.
     * @throws ParseException caso a data de início do mandato esteja em um formato inválido.
     */
    public void cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.ecoController.cadastrarDeputado(dni, dataDeInicio);
    }

    /**
     * Cadastra um partido, antes verificando se o nome do partido é uma entrada válida para então o alocar no conjunto.
     *
     * @param partido o partido a ser cadastrado.
     * @throws NullPointerException caso o partido seja uma String nula.
     * @throws IllegalArgumentException caso o partido seja uma String vazia ou composta apenas por espaços.
     */
    public void cadastrarPartido(String partido) {
        this.ecoController.cadastrarPartido(partido);
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
        return this.ecoController.exibirPessoa(dni);
    }

    /**
     * Exibe uma lista com os partidos cadastrados no sistema, exibidos em ordem lexicográfica.
     *
     * @return a String referente a listagem dos partidos, separados um a um por vírgula.
     */
    public String exibirBase() {
        return this.ecoController.exibirBase();
    }

    /**
     * Armazena em um arquivo o estado atual do controlador do sistema.
     */
    public void salvarSistema() {
        this.dados.salvar(this.ecoController,this.save);
    }

    /**
     * Carrega a partir de um arquivo um objeto controlador do sistema.
     */
    public void carregarSistema() {
        this.ecoController = this.dados.carregar(this.save);
    }

    /**
     * Retorna (a partir de um arquivo) o controlador do sistema para o seu estado inicial.
     */
    public void limparSistema() {
        this.ecoController = this.dados.limparSistema(this.save, this.reset);
    }

    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 2

    /**
     * Cadastra uma comissão no sistema, fazendo primeiramente a validação das entradas para que o cadastro possa de
     * fato ocorrer.
     *
     * @param tema o tema que a comissão irá tratar.
     * @param politicos os políticos que farão parte dessa comissão.
     * @throws NullPointerException caso o tema ou os políticos sejam Strings nulas.
     * @throws IllegalArgumentException caso o tema e os políticos sejam Strings vazias ou compostas apenas de espaços.
     */
    public void cadastrarComissao(String tema, String politicos) {
        this.ecoController.cadastrarComissao(tema, politicos);
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
        return this.ecoController.cadastrarPL(dni, ano, ementa, interesses, url, conclusivo);
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
        return this.ecoController.cadastrarPLP(dni, ano, ementa, interesses, url, artigos);
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
        return this.ecoController.cadastrarPEC(dni, ano, ementa, interesses, url, artigos);
    }

    /**
     * Exibe uma proposta legislativa, levando em consideração seu código como base da consulta.
     *
     * @param codigo o código que servirá para a consulta da proposta legislativa no sistema.
     * @return a representação em String da proposta consultada.
     */
    public String exibirProjeto(String codigo) {
        return this.ecoController.exibirProjeto(codigo);
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
        return this.ecoController.votarComissao(codigo, statusGovernista, proximoLocal);

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
        return this.ecoController.votarPlenario(codigo, statusGovernista, presentes);
    }


    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 3
    public void configurarEstrategiaPropostaRelacionada(String dni, String estrategia){
        this.ecoController.configurarEstrategia(dni, estrategia);
    }

    public String pegarPropostaRelacionada(String dni){
        return this.ecoController.pegarPropostaRelacionada(dni);
    }

    /**
     * Exibe o status de tramitação de uma proposta legislativa através de seu código.
     *
     * @param codigo o código referente a proposta.
     * @return a String referente a situação atual em que se encontra a proposta.
     */

    public String exibirTramitacao(String codigo) {
        return this.ecoController.exibirTramitacao(codigo);
    }

    /**
     * Método por onde todo o programa se inicia, carregando e executando os testes de aceitação.
     *
     * @param args a classe responsável pelo início do sistema e os caminhos para os testes de aceitação.
     */
    public static void main(String[] args) {
        args = new String[] {"Controllers.Facade",
                "acceptance_test/use_case_1.txt",
                "acceptance_test/use_case_2.txt",
                "acceptance_test/use_case_3.txt",
                "acceptance_test/use_case_4.txt",
                "acceptance_test/use_case_5.txt",
                "acceptance_test/use_case_6.txt",
                "acceptance_test/use_case_7.txt",
                "acceptance_test/use_case_8.txt",
                "acceptance_test/use_case_9.txt"};
        EasyAccept.main(args);
    }
}
