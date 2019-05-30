package Controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void validaCadastraDeputadoDni(String dni) {
        if (dni == null) {
            throw new NullPointerException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }
        if ("".equals(dni.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
        }

        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: dni invalido");
        }

    }

    public void validaCadastrarDeputado(String dataDeInicio) throws ParseException {
        if (dataDeInicio == null || "".equals(dataDeInicio.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
        }

        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        format.setLenient(false);
        String date = dataDeInicio;

        try {
            format.parse(date);
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
        if (partido == null || "".equals(partido.trim())) {
            throw new IllegalArgumentException("Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
        }
    }
}
