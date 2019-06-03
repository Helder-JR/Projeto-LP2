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
     * Cadastra uma pessoa que está afiliada a um partido político no sistema.
     *
     * @param nome o nome da pessoa.
     * @param dni o documento nacional de identificação da pessoa.
     * @param estado o estado em que a pessoa reside.
     * @param interesses os interesses políticos dessa pessoa.
     * @param partido o partido político a que essa pessoa está afiliada.
     */
    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.ecoController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    /**
     * Cadastra uma pessoa sem afiliação política no sistema.
     *
     * @param nome o nome da pessoa.
     * @param dni o documento nacional de identificação da pessoa.
     * @param estado o estado em que a pessoa reside.
     * @param interesses os interesses políticos dessa pessoa.
     */
    public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.ecoController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    /**
     * Cadastra um(a) deputado(a) no sistema.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @param dataDeInicio a data de início do mandato dessa pessoa como deputado(a).
     * @throws ParseException caso a data não esteja em um formato válido (dd/MM/yyyy).
     */
    public void cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.ecoController.cadastrarDeputado(dni, dataDeInicio);
    }

    /**
     * Cadastra um partido no sistema.
     * 
     * @param partido o partido que será cadastrado.
     */
    public void cadastrarPartido(String partido) {
        this.ecoController.cadastrarPartido(partido);
    }

    /**
     * Exibe uma pessoa cadastrada no sistema.
     * 
     * @param dni o DNI da pessoa que será exibida.
     */
    public String exibirPessoa(String dni) {
       return this.ecoController.exibirPessoa(dni);
    }

    /**
     * Exibe uma lista em ordem lexicográfica da base partidária cadastrada no sistema.
     * 
     * @return a String referente a lista de partidos cadastrados no sistema.
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
        this.ecoController = this.dados.carregar(this.reset);
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
                                                   "acceptance_test/use_case_4.txt"};
        EasyAccept.main(args);
    }
}
