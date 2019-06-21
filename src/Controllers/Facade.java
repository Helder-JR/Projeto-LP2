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
     * Objeto que irá receber o caminho para o arquivo que armazenará o estado atual do controlador do sistema.
     */
    private File save;

    /**
     * Objeto que irá armazenar o caminho para o arquivo que contém o controller do sistema em seu estado inicial.
     */
    private File reset;

    /**
     * Cria a fachada (onde serão definidos os caminhos para os arquivos do sistema).
     */
    public Facade() {
        this.dados = new Dados();
        this.ecoController = new ECOController();
        this.save = new File("save.data");
        this.reset = new File("reset.data");
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
        this.ecoController = this.dados.limparSistema(this.save, this.reset, this.ecoController);
    }

    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 2

    public void cadastrarComissao(String tema, String politicos) {
        this.ecoController.cadastrarComissao(tema, politicos);
    }

    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) throws ParseException {
        return this.ecoController.cadastrarPL(dni, ano, ementa, interesses, url, conclusivo);
    }

    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos) throws ParseException {
        return this.ecoController.cadastrarPLP(dni, ano, ementa, interesses, url, artigos);
    }

    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) throws ParseException {
        return this.ecoController.cadastrarPEC(dni, ano, ementa, interesses, url, artigos);
    }

    public String exibirProjeto(String codigo) {
        return this.ecoController.exibirProjeto(codigo);
    }

    public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal) {
        return this.ecoController.votarComissao(codigo, statusGovernista, proximoLocal);

    }

    public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
        return this.ecoController.votarPlenario(codigo, statusGovernista, presentes);
    }


    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 3
    public void configurarEstrategiaPropostaRelacionada(String dni, String estrategia){
        this.ecoController.configurarEstrategia(dni, estrategia);
    }

    //public String pegarPropostaRelacionada(String dni){
    //
    //}

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
        args = new String[] {"Controllers.Facade", "acceptance_test/use_case_1.txt",
                "acceptance_test/use_case_2.txt",
                "acceptance_test/use_case_3.txt",
                "acceptance_test/use_case_4.txt",
                "acceptance_test/use_case_5.txt",
                "acceptance_test/use_case_6.txt",
                "acceptance_test/use_case_7.txt"};
        EasyAccept.main(args);
    }
}
