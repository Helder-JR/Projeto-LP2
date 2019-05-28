package Validadores;

public class ValidaPessoa {

    public void validaCadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        if (nome == null || "".equals(partido.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
        }
    }
}
