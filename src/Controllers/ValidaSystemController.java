package Controllers;

public class ValidaSystemController {

    public void validaCadastrarPartido(String partido) {
        if (partido == null || "".equals(partido.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
        }
    }

    public void validaCadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        
    }
}
