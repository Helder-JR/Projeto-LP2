package Controllers;

import easyaccept.EasyAccept;
import util.Dados;

import java.io.File;
import java.text.ParseException;

public class Facade {
    private Dados dados;
    private ECOController ECOController;
    private File save;
    private File reset;

    public Facade() {
        this.dados = new Dados();
        this.ECOController = new ECOController();
        this.save = new File("save.data");
        this.reset = new File("reset.data");
        this.dados.salvar(ECOController,this.reset);
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.ECOController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.ECOController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    public void cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.ECOController.cadastrarDeputado(dni, dataDeInicio);
    }

    public void cadastrarPartido(String partido) {
        this.ECOController.cadastrarPartido(partido);
    }

    public String exibirPessoa(String dni) {
       return this.ECOController.exibirPessoa(dni);
    }

    public String exibirBase() {
        return this.ECOController.exibirBase();
    }

    public void salvarSistema() {
        this.dados.salvar(this.ECOController,this.save);
    }

    public void carregarSistema() {
        this.ECOController = this.dados.carregar(this.save);
    }

    public void limparSistema() {
        this.ECOController = this.dados.carregar(this.reset);
    }

    public static void main(String[] args) {
        args = new String[] {"Controllers.Facade", "acceptance_test/use_case_1.txt",
                                                   "acceptance_test/use_case_2.txt",
                                                   "acceptance_test/use_case_3.txt",
                                                   "acceptance_test/use_case_4.txt"};
        EasyAccept.main(args);
    }
}
