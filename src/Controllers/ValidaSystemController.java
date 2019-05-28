package Controllers;

public class ValidaSystemController {

    public void validaCadastrarPartido(String partido) {
        if (partido == null || "".equals(partido.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
        }
    }

    public void validaCadastrarDeputado(String dni, String dataDeInicio) {
        if (dni == null || "".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }

        if (dataDeInicio == null || "".equals(dataDeInicio.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
        }
    }
}
