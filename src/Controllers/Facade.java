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
     * Responsável pela entrada e saída de dados do sistema.
     */
    private Dados dados;

    /**
     * Controller que gerencia os dados do sistema (e que terá seus persistidos).
     */
    private ECOController ecoController;

    /**
     * Objeto que irá receber o caminho para o arquivo que armazenará o estado atual do controller do sistema.
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

    public void cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.ecoController.cadastrarDeputado(dni, dataDeInicio);
    }

    public void cadastrarPartido(String partido) {
        this.ecoController.cadastrarPartido(partido);
    }

    public String exibirPessoa(String dni) {
       return this.ecoController.exibirPessoa(dni);
    }

    public String exibirBase() {
        return this.ecoController.exibirBase();
    }

    public void salvarSistema() {
        this.dados.salvar(this.ecoController,this.save);
    }

    public void carregarSistema() {
        this.ecoController = this.dados.carregar(this.save);
    }

    public void limparSistema() {
        this.ecoController = this.dados.carregar(this.reset);
    }

    public static void main(String[] args) {
        args = new String[] {"Controllers.Facade", "acceptance_test/use_case_1.txt",
                                                   "acceptance_test/use_case_2.txt",
                                                   "acceptance_test/use_case_3.txt",
                                                   "acceptance_test/use_case_4.txt"};
        EasyAccept.main(args);
    }
}
