package Validacao;

import Controllers.PessoaController;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Classe responsável pela validação das entradas de dados, lançando excessões apropriadas quando necessário.
 */
public class ValidaSystemController implements Serializable {

    /**
     * Objeto responsável por fazer a validação de outros objetos da classe Pessoa.
     */
    private ValidaPessoa validaPessoa;

    /**
     * Objeto responsável por fazer a validação de outros objetos da classe Deputado.
     */
    private ValidaDeputado validaDeputado;


    /**
     * Cria um objeto do tipo ValidaSystemController.
     */
    public ValidaSystemController() {
        this.validaPessoa = new ValidaPessoa();
        this.validaDeputado = new ValidaDeputado();
    }

    /**
     * Verifica se a String inserida como parâmetro é nula, vazia ou composta apenas de espaços.
     *
     * @param entrada  a String que será verificada.
     * @param mensagem a mensagem de erro que será mostrada caso uma exceção seja lançada.
     * @throws NullPointerException     caso a entrada seja nula.
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
     * @param nome   o nome da pessoa que será cadastrada.
     * @param dni    o DNI da pessoa que será cadastrada.
     * @param estado o estado em que essa pessoa reside.
     * @throws NullPointerException     caso o nome, DNI ou estados sejam nulos.
     * @throws IllegalArgumentException caso o nome, DNI ou estados sejam uma String vazia ou composta apenas de espaços
     *                                  ou o DNI esteja em um formato inválido.
     */
    public void validaCadastrarPessoa(String nome, String dni, String estado) {
        validaPessoa.validaCadastrarPessoa(nome, dni, estado);
    }

    /**
     * Valida os dados de entrada necessários para o cadastramento de um(a) deputado(a). Primeiro o DNI será verificado
     * pelo método validaEntradaNulaVazia, e caso uma exceção não seja lançada será verificado se esse DNI está na forma
     * "XXXXXXXXX-X", lançando uma exceção caso não esteja.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @throws NullPointerException     caso o DNI esteja nulo.
     * @throws IllegalArgumentException caso o DNI esteja vazio, seja composto apenas por espaços ou não esteja no
     *                                  formato esperado.
     */
    public void validaCadastraDeputadoDni(String dni) {
        this.validaDeputado.validaCadastraDeputadoDni(dni);
    }

    /**
     * Valida a String referente a data de início do mandato de um(a) deputado(a), necessário para o cadastro do(a)
     * mesmo(a). Inicialmente a data de início é verificada através do método validaEntradaNulaVazia. Caso nenhuma
     * exceção seja lançada o método irá analisar se a data possui uma forma válida e após isso se essa data não está
     * afrente no tempo em relação ao momento do cadastro.
     *
     * @param dataDeInicio a data de início do mandato do(a) deputado(a).
     * @throws ParseException           caso a data informada não esteja em um formato válido.
     * @throws NullPointerException     caso a data de início esteja nula.
     * @throws IllegalArgumentException caso a data de início esteja vazia, esteja composta apenas de espaços ou afrente
     *                                  no tempo em relação ao momento do cadastro.
     */
    public void validaCadastrarDeputado(String dataDeInicio) throws ParseException {
        this.validaDeputado.validaCadastrarDeputado(dataDeInicio);
    }

    /**
     * Valida se a String referente ao partido que será cadastrdo é valida (através do método validaEntradaNulaVazia).
     *
     * @param partido o partido que será cadastrado.
     * @throws NullPointerException     caso a String referente ao partido esteja nula.
     * @throws IllegalArgumentException caso a String referente ao partido esteja vazia ou seja formada apenas por
     *                                  espaços.
     */
    public void validaCadastrarPartido(String partido) {
        validaEntradaNulaVazia(partido, "Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
    }

    /**
     * Verifica se o DNI da pessoa que será exibida é válido, através do método validaEntradaNulaVazia. Após isso, caso
     * o DNI não seja da forma "XXXXXXXXX-X" uma exceção será lançada.
     *
     * @param dni o DNI da pessoa que será exibida.
     * @throws NullPointerException     caso a String referente ao DNI da pessoa esteja nula.
     * @throws IllegalArgumentException caso o DNI da pessoa esteja vazio, seja composto apenas por espaços ou não
     *                                  esteja em um formato válido.
     */
    public void validaExibirPessoa(String dni) {
        validaEntradaNulaVazia(dni, "Erro ao exibir pessoa: dni nao pode ser vazio ou nulo");
        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao exibir pessoa: dni invalido");
        }
    }

    /**
     * Verifica se o tema e os políticos que irão participar do cadastro de uma comissão são Strings válidas, lançando
     * exceções caso se faça necessário.
     *
     * @param tema o tema que a comissão irá receber.
     * @param politicos os políticos que irão fazer parte da comissão.
     * @throws NullPointerException caso o tema ou os políticos que compões a comissão sejam Strings nulas.
     * @throws IllegalArgumentException caso o tema ou os políticos sejam Strings vazias ou compostas apenas de espaços.
     */
    public void validaCadastrarComissao(String tema, String politicos) {
        validaEntradaNulaVazia(tema, "Erro ao cadastrar comissao: tema nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(politicos, "Erro ao cadastrar comissao: lista de politicos nao pode ser vazio ou nulo");
    }

    /**
     * Verifica se as entradas necessárias para cadastrar um projeto de lei são válidas.
     *
     * @param dni o DNI da pessoa que propôs o projeto de lei.
     * @param ano o ano em que esse projeto de lei foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses defendidos no projetos.
     * @param url o endereço da internet que contém a descriçaõ completa referente ao projeto.
     * @param conclusivo indica se o projeto pode ser aprecisado em comissões sem necessidade de ir ao plenário.
     * @throws NullPointerException caso o DNI, a ementa, os interesses ou a url sejam Strings nulas.
     * @throws IllegalArgumentException caso alguma dessas entradas seja uma String vazia, composta apenas de espaços,
     *                                  ou não esteja em um formato válido, como por exemplo o DNI não ser na forma
     *                                  "XXXXXXXXX-X" ou o ano ser superior ou inferior a criação da proposta.
     * @throws ParseException caso o ano não obedeça um formato válido.
     */
    public void validaCadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) throws ParseException {
        validaEntradaNulaVazia(dni, "Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(ementa, "Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula");
        validaEntradaNulaVazia(interesses, "Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(url, "Erro ao cadastrar projeto: url nao pode ser vazio ou nulo");

        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: dni invalido");
        }

        Date atualDate = new Date();
        Date Ano = new SimpleDateFormat("yyyy").parse(String.valueOf(ano));
        Date Constituicao = new SimpleDateFormat("yyyy").parse("1988");

        if (Ano.after(atualDate)) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: ano posterior ao ano atual");
        }
        if (Ano.before(Constituicao)){
            throw new IllegalArgumentException("Erro ao cadastrar projeto: ano anterior a 1988");
        }
    }

    /**
     * Valida as entradas necesárias para que o cadastro de PEC's ou PLP's sejam bem sucedidos.
     *
     * @param dni o DNI da pessoa que propôs o projeto de lei.
     * @param ano o ano em que esse projeto de lei foi criado/proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses defendidos no projetos.
     * @param url o endereço da internet que contém a descrição completa referente ao projeto.
     * @param artigos os artigos que serão emendados/complementados por estas propostas.
     * @throws NullPointerException caso alguma das entradas seja uma String nula.
     * @throws IllegalArgumentException caso alguma das Strings seja vazia, composta apenas de espaços, o DNI não siga o
     *                                  padrão proposto ou a data seja em algum momento do tempo diferente da real
     *                                  criação da proposta de lei.
     * @throws ParseException caso o ano de criação do projeto não seja uma data válida.
     */
    public void validaCadastrarPLPouPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) throws ParseException {
        validaEntradaNulaVazia(dni, "Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(ementa, "Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula");
        validaEntradaNulaVazia(interesses, "Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(url, "Erro ao cadastrar projeto: url nao pode ser vazio ou nulo");
        validaEntradaNulaVazia(artigos, "Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo");

        if (!dni.matches("\\d{9}[-]\\d")) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: dni invalido");
        }

        Date atualDate = new Date();
        Date Ano = new SimpleDateFormat("yyyy").parse(String.valueOf(ano));
        Date Constituicao = new SimpleDateFormat("yyyy").parse("1988");

        if (Ano.after(atualDate)) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: ano posterior ao ano atual");
        }
        if (Ano.before(Constituicao)){
            throw new IllegalArgumentException("Erro ao cadastrar projeto: ano anterior a 1988");
        }
    }

    /**
     * Verifica se as entradas necessárias para votar uma comissão são válidas, lançando exceções quando necessário.
     *
     * @param codigo o código da proposta legislativa que será votada.
     * @param status o status governista da proposta.
     * @param proximoLocal o próximo local ao qual a proposta será votada.
     * @throws NullPointerException caso alguma das entradas seja nula.
     * @throws IllegalArgumentException caso alguma das entrads seja uma String vazia, composta apenas por espaços ou
     *                                  não siga um padrão válido.
     */
    public void validaVotaComissao(String codigo, String status, String proximoLocal) {
        if (status == null || "".equals(status.trim()))
            throw new IllegalArgumentException("Erro ao votar proposta: status invalido");

        if (proximoLocal == null)
            throw new NullPointerException("Erro ao votar proposta: proximo local vazio");

        if ("".equals(proximoLocal.trim()))
            throw new IllegalArgumentException("Erro ao votar proposta: proximo local vazio");

        if (!"GOVERNISTA".equals(status) && !"OPOSICAO".equals(status) && !"LIVRE".equals(status))
            throw new IllegalArgumentException("Erro ao votar proposta: status invalido");
    }
}
