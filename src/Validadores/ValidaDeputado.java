package Validadores;

public class ValidaDeputado {

    public void validaCadastrarDeputado(String dni, String dataDeInicio) {
        if (dataDeInicio == null || "".equals(dataDeInicio.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
        }
        if (dni == null || "".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }
    }

}
