package Controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidaSystemController {

    public void validaCadastrarPessoaComPartido(String nome, String dni, String estado, String interesses, String partido) {
        if (nome == null || "".equals(nome.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
        }

        if (dni == null || "".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }

        if (estado == null || "".equals(estado.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
        }
    }

    public void validaCadastrarPessoaSemPartido(String nome, String dni, String estado, String interesses) {
        if (nome == null || "".equals(nome.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
        }

        if (dni == null || "".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }

        if (estado == null || "".equals(estado.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
        }
    }

    public void validaCadastrarDeputado(String dni, String dataDeInicio) {
        if (dni == null || "".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }

        if (dataDeInicio == null || "".equals(dataDeInicio.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
        }

        for (int i = 0; i < dni.length(); i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                throw new IllegalArgumentException("Erro ao cadastrar deputado: dni invalido");
            }
            if (i == 8){ i = 10;}
        }

        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        format.setLenient(false);
        String date = dataDeInicio;

        try {
            format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data invalida");
        }
    }

    public void validaCadastrarPartido(String partido) {
        if (partido == null || "".equals(partido.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
        }
    }
}
