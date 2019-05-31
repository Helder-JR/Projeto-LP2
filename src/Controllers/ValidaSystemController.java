package Controllers;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidaSystemController implements Serializable {

    private void validaEntradaNulaVazia(String entrada, String mensagem) {
        if (entrada == null) {
            throw new NullPointerException(mensagem);
        }
        if ("".equals(entrada.trim())) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    public void validaCadastrarPessoa(String nome, String dni, String estado) {
        validaEntradaNulaVazia(nome, "Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(estado, "Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni invalido");
        }
    }

    public void validaCadastraDeputadoDni(String dni) {
        validaEntradaNulaVazia(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: dni invalido");
        }
    }

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

    public void validaCadastrarPartido(String partido) {
        validaEntradaNulaVazia(partido, "Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
    }

    public void validaExibirPessoa(String dni) {
        validaEntradaNulaVazia(dni, "Erro ao exibir pessoa: dni nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao exibir pessoa: dni invalido");
        }
    }
}
