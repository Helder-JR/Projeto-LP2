package Controllers;

import Entidades.*;
import Validacao.ValidaSystemController;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

import static java.lang.Math.floor;

/**
 * Controlador que gerencia as entidades presentes no sistema. É possível cadastrar e exibir pessoas (seja um civil ou
 * um(a) deputado(a)), além de partidos.
 */
public class ECOController implements Serializable {

    private PessoaController pessoaController;

    private Map<String, ArrayList<String>> comissoes;

    private Map<String, Projeto> projetos;

    private Map<String, Integer> codigoProjetos;

    /**
     * Conjunto que irá conter informações a respeito dos partidos cadastrados no sistema.
     */
    private Set<String> partidosGovernistas;

    /**
     * Objeto responsável por verificar se os dados a serem passados no cadastro de pessoas e/ou partidos são válidos.
     */
    private ValidaSystemController validador;



    /**
     * Cria um controlador do sistema que será responsável por alocar os cadastros de pessoas e partidos, além do objeto
     * que irá validar as entradas de dados.
     */
    public ECOController() {
        this.pessoaController = new PessoaController();
        this.comissoes = new HashMap<>();
        this.projetos = new HashMap<>();
        this.codigoProjetos = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.validador = new ValidaSystemController();
    }

    /**
     * Cadastra uma pessoa com afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @param partido o partido político ao qual essa pessoa está afiliada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        return pessoaController.cadastrarPessoa(nome, dni, estado, interesses, partido);
    }

    /**
     * Cadastra uma pessoa sem afiliação política no mapa de pessoas, inicialmente verificando se as entradas referentes
     * a seu nome, DNI e estado são válidas e então verificando se ela já está cadastrada no sistema.
     *
     * @param nome o nome da pessoa que será cadastrada.
     * @param dni o Documento Nacional de Identificação da pessoa que será cadastrada.
     * @param estado o estado ao qual a pessoa que será cadastrada reside.
     * @param interesses os interesses políticos da pessoa que será cadastrada.
     * @return um booleano true caso a pessoa não esteja cadastrada no mapa de pessoas e seu cadastro seja bem sucedido.
     * @throws NullPointerException caso o nome, DNI ou estado sejam Strings nulas.
     * @throws IllegalArgumentException caso o nome, DNI ou estado sejam Strings vazias ou compostas apenas de espaços,
     * e caso a pessoa já esteja cadastrada.
     */
    public boolean cadastrarPessoa(String nome, String dni, String estado, String interesses) {
        this.validador.validaCadastrarPessoa(nome, dni, estado);
        return pessoaController.cadastrarPessoa(nome, dni, estado, interesses);
    }

    /**
     * Cadastra um(a) deputado(a) no mapa de pessoas, verificando inicialmente se o DNI passado como parâmetro é valido,
     * e depois se há uma pessoa cadastrada com esse DNI no mapa. Após isso será verificada se a data de início do
     * mandato é valida e se esse(a) deputado(a) está afiliado a algum partido.
     *
     * @param dni o DNI da pessoa que será cadastrada como deputado(a).
     * @param dataDeInicio a data de início do mandato dessa pessoa.
     * @return um booleano true caso as entradas sejam válidas, o DNI esteja vinculado a uma pessoa e essa pessoa
     * pertença a um partido.
     * @throws NullPointerException caso o DNI seja uma String nula ou a pessoa não esteja cadastrada no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas por espaços e caso a pessoa
     * não esteja afiliada a um partido.
     * @throws ParseException caso a data de início do mandato esteja em um formato inválido.
     */
    public boolean cadastrarDeputado(String dni, String dataDeInicio) throws ParseException {
        this.validador.validaCadastraDeputadoDni(dni);
        return pessoaController.cadastrarDeputado(dni, dataDeInicio);
    }

    /**
     * Cadastra um partido, antes verificando se o nome do partido é uma entrada válida para então o alocar no conjunto.
     *
     * @param partido o partido a ser cadastrado.
     * @return um booleano true caso o partido seja alocado com sucesso, ou false caso não seja.
     * @throws NullPointerException caso o partido seja uma String nula.
     * @throws IllegalArgumentException caso o partido seja uma String vazia ou composta apenas por espaços.
     */
    public boolean cadastrarPartido(String partido) {
        this.validador.validaCadastrarPartido(partido);
        return this.partidosGovernistas.add(partido);
    }

    /**
     * Exibe uma pessoa presente no cadastro, validando inicialmente se o DNI dessa pessoa é uma String válida e ela
     * esteja cadastrada no mapa. Caso essas condições não sejam satisfeitas exceções serão lançadas.
     *
     * @param dni o DNI da pessoa a ser exibida.
     * @return a String referente a representação dessa pessoa presente no mapa.
     * @throws NullPointerException caso o DNI seja uma String nula ou a pessoa não esteja presente no mapa.
     * @throws IllegalArgumentException caso o DNI seja uma String vazia ou composta apenas de espaços.
     */
    public String exibirPessoa(String dni) {
        this.validador.validaExibirPessoa(dni);
        return pessoaController.exibirPessoa(dni);
    }

    /**
     * Exibe uma lista com os partidos cadastrados no sistema, exibidos em ordem lexicográfica.
     *
     * @return a String referente a listagem dos partidos, separados um a um por vírgula.
     */
    public String exibirBase() {
        ArrayList<String> listaPartidos = new ArrayList<>(this.partidosGovernistas);
        listaPartidos.sort(String::compareTo);
        return String.join(",",listaPartidos);
    }

    ////---------------------------------////----------------------------------////---------------------------------////
    //Parte 2

    public void cadastrarComissao(String tema, String politicos) {
        ArrayList<String> lista = new ArrayList<>(Arrays.asList(politicos.split(",")));
        this.comissoes.put(tema, lista);
    }

    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) {
        int ordemCodigo = this.codigoProjetos.get("PL");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoLei projeto = new ProjetoLei(dni, ano, ementa, interesses, url, conclusivo, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PL", ordemCodigo+1);
        return "";
    }

    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos) {
        int ordemCodigo = this.codigoProjetos.get("PLP");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoLeiComplementar projeto = new ProjetoLeiComplementar(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PLP", ordemCodigo+1);
        return "";
    }

    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) {
        int ordemCodigo = this.codigoProjetos.get("PEC");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoEmendaConstitucional projeto = new ProjetoEmendaConstitucional(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PEC", ordemCodigo+1);
        return "";
    }

    public String exibirProjeto(String codigo) {
        return this.projetos.get(codigo).toString();
    }


    public boolean votarComissao(String codigo, String statusGovernista, String comissao, String proximoLocal) {
        Projeto projeto = this.projetos.get(codigo);
        ArrayList<String> deputadosComissao = this.comissoes.get(comissao);
        int votos = controlaVoto(statusGovernista,deputadosComissao, projeto);
        return (votos >= floor(deputadosComissao.size()/2)+1);
    }

    private int controlaVoto(String statusGovernista, ArrayList<String> listaDeputado, Projeto projeto) {
        int votos = 0;
        switch (statusGovernista) {
            case "GOVERNISTA":
                votos = votarProjetoGovernista(listaDeputado);
                break;
            case "OPOSICAO":
                votos = votarProjetoOposicao(listaDeputado);
                break;
            case "LIVRE":
                votos = votarProjetoLivre(projeto, listaDeputado);
                break;
            default:
        }
        return votos;
    }

    //auxiliar de menuVoto
    private int votarProjetoGovernista(ArrayList<String> deputadosComissao) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = this.pessoaController.getPessoas().get(dni);
            if (this.partidosGovernistas.contains(pessoa.getPartido())) votos++;
        }
        return votos;
    }

    //auxiliar de menuVoto
    private int votarProjetoOposicao(ArrayList<String> deputadosComissao) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = this.pessoaController.getPessoas().get(dni);
            if (!this.partidosGovernistas.contains(pessoa.getPartido())) votos++;
        }
        return votos;
    }

    //auxiliar de menuVoto
    private int votarProjetoLivre(Projeto projeto, ArrayList<String> deputadosComissao) {
        int votos = 0;
        for (String dni : deputadosComissao) {
            Pessoa pessoa = this.pessoaController.getPessoas().get(dni);
            if (votoLivre(pessoa,projeto)) votos++;
        }
        return votos;
    }

    //auxiliar de votarProjetoLivre
    private boolean votoLivre(Pessoa pessoa, Projeto projeto) {
        HashSet<String> copiaInteresseProjeto = new HashSet<>(projeto.getInteresses());
        copiaInteresseProjeto.retainAll(pessoa.getInteresses());
        return !copiaInteresseProjeto.isEmpty();
    }

    public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
        Projeto projeto = this.projetos.get(codigo);
        ArrayList<String> deputadosPresentes = new ArrayList<>(Arrays.asList(presentes.split(",")));
        int votos = controlaVoto(statusGovernista, deputadosPresentes, projeto);;
        return projeto.calculaVotoMinimo(this.pessoaController.getTotalDeputados(), votos);
    }

    public String exibirTramitacao(String codigo) {
        return this.projetos.get(codigo).getSituacaoAtual();
    }
}
