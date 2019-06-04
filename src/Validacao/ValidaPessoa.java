package Validacao;

import java.io.Serializable;

public class ValidaPessoa implements Serializable {

    /**
     * Verifica se a String inserida como parâmetro é nula, vazia ou composta apenas de espaços.
     *
     * @param entrada a String que será verificada.
     * @param mensagem a mensagem de erro que será mostrada caso uma exceção seja lançada.
     * @throws NullPointerException caso a entrada seja nula.
     * @throws IllegalArgumentException caso a entrada seja uma String vazia ou composta apenas de espaços.
     */
    private void validaEntradaNulaVazia(String entrada, String mensagem) {
        if (entrada == null) {
            throw new NullPointerException(mensagem);
        }
        if ("".equals(entrada.trim())) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    /**
     * Valida os dados de entrada necessários para o cadastramento de uma pessoa. Primeiro cada um dos parâmetros será
     * verificado (através do método validaEntradaNulaVazia) e então caso o DNI não esteja no formato "XXXXXXXXX-X" uma
     * exceção será lançada.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o DNI da pessoa que será cadastrada.
     * @param estado o estado em que essa pessoa reside.
     * @throws NullPointerException caso o nome, DNI ou estados sejam nulos.
     * @throws IllegalArgumentException caso o nome, DNI ou estados sejam uma String vazia ou composta apenas de espaços
     * ou o DNI esteja em um formato inválido.
     */
    public void validaCadastrarPessoa(String nome, String dni, String estado) {
        validaEntradaNulaVazia(nome, "Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(estado, "Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni invalido");
        }
    }

}
