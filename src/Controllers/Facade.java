package Controllers;

import easyaccept.EasyAccept;

import java.text.ParseException;

public class Facade {

    private CamaraController camaraController;

    public Facade() {
        this.camaraController = new CamaraController();
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.camaraController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.camaraController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    public void cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.camaraController.cadastrarDeputado(dni, dataDeInicio);
    }

    public void cadastrarPartido(String partido) {
        this.camaraController.cadastrarPartido(partido);
    }

    public String exibirPessoa(String dni) {
       return this.camaraController.exibirPessoa(dni);
    }

    public String exibirBase() {
        return this.camaraController.exibirBase();
    }

    public void salvarSistema() {

    }

    public void carregarSistema() {

    }

    public void limparSistema() {

    }

    public static void main(String[] args) {
        args = new String[] {"Controllers.Facade", "acceptance_test/use_case_1.txt",
                                                   "acceptance_test/use_case_2.txt",
                                                   "acceptance_test/use_case_3.txt",
                                                   "acceptance_test/use_case_4.txt"};
        EasyAccept.main(args);
    }
}
