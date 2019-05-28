package projeto;

import easyaccept.EasyAccept;

public class Facade {

    private SystemController systemController;

    public Facade() {
        this.systemController = new SystemController();
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.systemController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.systemController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    public void cadastrarDeputado(String dni, String dataDeInicio) {
        this.systemController.cadastrarDeputado(dni, dataDeInicio);
    }

    public void cadastrarPartido(String partido) {
        this.systemController.cadastrarPartido(partido);
    }

    public String exibirPessoa(String dni) {
       return this.systemController.exibirPessoa(dni);
    }

    public String exibirBase() {
        return this.systemController.exibirBase();
    }

    public static void main(String[] args) {
        args = new String[] {"projeto.Facade", "acceptance_test/use_case_1.txt",
                                               "acceptance_test/use_case_2.txt",
                                               "acceptance_test/use_case_3.txt",
                                               "acceptance_test/use_case_4.txt"};
        EasyAccept.main(args);
    }
}
