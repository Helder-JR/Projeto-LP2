package Controllers;

import Entidades.*;

import java.io.Serializable;
import java.util.*;

public class LegislativoController implements Serializable {

    private VotacaoController votacaoController;
    private Map<String, ArrayList<String>> comissoes;
    private Map<String, Projeto> projetos;
    private Map<String, Integer> codigoProjetos;
    private int totalDeputados;

    /**
     * Conjunto que irá conter informações a respeito dos partidos cadastrados no sistema.
     */
    private HashSet<String> partidosGovernistas;

    public LegislativoController(int totalDeputados) {
        this.votacaoController = new VotacaoController();
        this.comissoes = new HashMap<>();
        this.projetos = new HashMap<>();
        this.codigoProjetos = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.totalDeputados = totalDeputados;
    }

    /**
     * Cadastra um partido, antes verificando se o nome do partido é uma entrada válida para então o alocar no conjunto.
     *
     * @param partido o partido a ser cadastrado.
     * @return um booleano true caso o partido seja alocado com sucesso, ou false caso não seja.
     * @throws NullPointerException     caso o partido seja uma String nula.
     * @throws IllegalArgumentException caso o partido seja uma String vazia ou composta apenas por espaços.
     */
    public boolean cadastrarPartido(String partido) {
        return this.partidosGovernistas.add(partido);
    }

    /**
     * Exibe uma lista com os partidos cadastrados no sistema, exibidos em ordem lexicográfica.
     *
     * @return a String referente a listagem dos partidos, separados um a um por vírgula.
     */
    public String exibirBase() {
        ArrayList<String> listaPartidos = new ArrayList<String>(this.partidosGovernistas);
        listaPartidos.sort(String::compareTo);
        return String.join(",", listaPartidos);
    }

    public void cadastrarComissao(String tema, String politicos) {
        ArrayList<String> lista = new ArrayList<String>(Arrays.asList(politicos.split(",")));
        if (this.comissoes.containsKey(tema)) {
            throw new IllegalArgumentException("Erro ao cadastrar comissao: tema existente");
        }
        this.comissoes.put(tema, lista);
    }

    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) {
        int ordemCodigo = this.codigoProjetos.get("PL");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoLei projeto = new ProjetoLei(dni, ano, ementa, interesses, url, conclusivo, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PL", ordemCodigo + 1);
        return "";
    }

    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos) {
        int ordemCodigo = this.codigoProjetos.get("PLP");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoLeiComplementar projeto = new ProjetoLeiComplementar(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PLP", ordemCodigo + 1);
        return "";
    }

    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) {
        int ordemCodigo = this.codigoProjetos.get("PEC");
        String codigo = ordemCodigo + "/" + ano;
        ProjetoEmendaConstitucional projeto = new ProjetoEmendaConstitucional(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PEC", ordemCodigo + 1);
        return "";
    }

    public String exibirProjeto(String codigo) {
        return this.projetos.get(codigo).toString();
    }

    public boolean votarComissao(String codigo, String statusGovernista, String comissao, String proximoLocal, HashMap<String, Pessoa> pessoasMap) {
        Projeto projeto = this.projetos.get(codigo);
        return votacaoController.votarComissao(projeto, statusGovernista, this.comissoes.get(comissao), proximoLocal, pessoasMap, this.totalDeputados, partidosGovernistas);
    }

    public boolean votarPlenario(String codigo, String statusGovernista, String presentes, HashMap<String, Pessoa> pessoas) {
        Projeto projeto = this.projetos.get(codigo);
        return votacaoController.votarPlenario(projeto, statusGovernista, presentes, pessoas, this.totalDeputados, partidosGovernistas);
    }

    public String exibirTramitacao(String codigo) {
        return this.projetos.get(codigo).getSituacaoAtual();
    }
}