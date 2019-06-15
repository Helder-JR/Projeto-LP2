package Entidades;

import Validacao.ValidaDeputado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Representação de um(a) deputado(a), uma função que uma pessoa pode exercer. Possui uma data de início do mandato,
 * além da contagem da quantidade de leis que aprovou.
 */
public class Deputado implements Funcao {

    /**
     * Data de início do mandato de deputado(a).
     */
    private Date dataDeInicio;

    /**
     * Quantidade de leis que conseguiu fazer serem aprovadas.
     */
    private Integer leisAprovadas;

    private ValidaDeputado validaDeputado;

    /**
     * Cria um(a) deputado(a) com base na data de início do mandato.
     *
     * @param dataDeInicio a data de início do cargo de deputado(a).
     * @throws ParseException caso a data esteja em um formato inválido.
     */
    public Deputado(String dataDeInicio, Integer leisAprovadas) throws ParseException {
        this.validaDeputado = new ValidaDeputado();
        this.validaDeputado.validaCadastrarDeputado(dataDeInicio);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        this.dataDeInicio = dateFormat.parse(dataDeInicio);
        this.leisAprovadas = leisAprovadas;
    }

    /**
     * Compara a igualdade entre um objeto Deputado com outro objeto.
     *
     * @param o o objeto que será comparado (a)o deputado(a).
     * @return um booleano true caso os objetos sejam iguais ou false, caso não sejam.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deputado deputado = (Deputado) o;
        return leisAprovadas == deputado.leisAprovadas &&
                dataDeInicio.equals(deputado.dataDeInicio);
    }

    /**
     * Calcula o hashCode de um objeto do tipo Deputado.
     *
     * @return um inteiro hexadecimal correspondente ao hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dataDeInicio, leisAprovadas);
    }

    /**
     * Exibe uma representação textual referente a um(a) deputado(a). A representação pode mudar caso os campos
     * referenetes ao partido e/ou interesses estejam vazios.
     *
     * @param nome o nome do(a) deputado(a).
     * @param dni o Documento Nacional de Identificação do(a) deputado(a).
     * @param estado o estado em que o(a) deputado(a) reside.
     * @param partido o partido a qual o(a) deputado(a) está afiliado(a).
     * @param interesses os interesses políticos desse(a) deputado(a).
     * @return a representação em String do objeto Deputado.
     */
    public String toString(String nome, String dni, String estado, String partido, String interesses) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String toString = String.format("POL: %s - %s (%s) - %s - Interesses: %s - %s - %s Leis", nome, dni, estado, partido, interesses, dateFormat.format(this.dataDeInicio), this.leisAprovadas);

        if ("".equals(partido.trim()) && !"".equals(interesses.trim()))
            toString = String.format("POL: %s - %s (%s) - Interesses: %s - %s - %s Leis", nome, dni, estado, interesses, dateFormat.format(this.dataDeInicio), this.leisAprovadas);

        else if ("".equals(interesses.trim()) && !"".equals(partido.trim()))
            toString = String.format("POL: %s - %s (%s) - %s - %s - %s Leis", nome, dni, estado, partido, dateFormat.format(this.dataDeInicio), this.leisAprovadas);

        else if ("".equals(interesses.trim()) && "".equals(partido.trim()))
            toString = String.format("POL: %s - %s (%s) - %s - %s Leis", nome, dni, estado, dateFormat.format(this.dataDeInicio), this.leisAprovadas);

        return toString;
    }

    @Override
    public String getFuncao() {
        return "Deputado";
    }
}
