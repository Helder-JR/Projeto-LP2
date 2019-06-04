package Validacao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidaDeputado implements Serializable {

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
     * Valida os dados de entrada necessários para o cadastramento de um(a) deputado(a). Primeiro o DNI será verificado
     * pelo método validaEntradaNulaVazia, e caso uma exceção não seja lançada será verificado se esse DNI está na forma
     * "XXXXXXXXX-X", lançando uma exceção caso não esteja.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @throws NullPointerException caso o DNI esteja nulo.
     * @throws IllegalArgumentException caso o DNI esteja vazio, seja composto apenas por espaços ou não esteja no
     * formato esperado.
     */
    public void validaCadastraDeputadoDni(String dni) {
        validaEntradaNulaVazia(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: dni invalido");
        }
    }

    /**
     * Valida a String referente a data de início do mandato de um(a) deputado(a), necessário para o cadastro do(a)
     * mesmo(a). Inicialmente a data de início é verificada através do método validaEntradaNulaVazia. Caso nenhuma
     * exceção seja lançada o método irá analisar se a data possui uma forma válida e após isso se essa data não está
     * afrente no tempo em relação ao momento do cadastro.
     *
     * @param dataDeInicio a data de início do mandato do(a) deputado(a).
     * @throws ParseException caso a data informada não esteja em um formato válido.
     * @throws NullPointerException caso a data de início esteja nula.
     * @throws IllegalArgumentException caso a data de início esteja vazia, esteja composta apenas de espaços ou afrente
     * no tempo em relação ao momento do cadastro.
     */
    public void validaCadastrarDeputado(String dataDeInicio) throws ParseException {
        validaEntradaNulaVazia(dataDeInicio, "Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");

        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        format.setLenient(false);

        try {
            format.parse(dataDeInicio);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data invalida");
        }

        Date atualDate = new Date();
        Date inicioDate = new SimpleDateFormat("ddMMyyyy").parse(dataDeInicio);

        if (inicioDate.after(atualDate)) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data futura");
        }
    }

}
