package Controllers;

import easyaccept.EasyAccept;
import util.Dados;
import java.io.File;
import java.text.ParseException;

public class Facade {
    private Dados dados;
    private ECOController ecoController;
    private File save;
    private File reset;

    public Facade() {
        this.dados = new Dados();
        this.ecoController = new ECOController();
        this.save = new File("save.data");
        this.reset = new File("reset.data");
        this.dados.salvar(this.ecoController,this.reset);
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.ecoController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

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
