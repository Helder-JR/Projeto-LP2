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
        this.codigoProjetos.put("PL", 1);
        this.codigoProjetos.put("PLP", 1);
        this.codigoProjetos.put("PEC", 1);
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


    private void validaExistenciaCadastrarComissao(String politicos, HashMap<String, Pessoa> pessoas) {
        for (String politico : politicos.split(",")) {
            if (!pessoas.containsKey(politico)) {
                throw new NullPointerException("Erro ao cadastrar comissao: pessoa inexistente");
            }
            if (!pessoas.get(politico).exibeFuncao().equals("Deputado")) {
                throw new IllegalArgumentException("Erro ao cadastrar comissao: pessoa nao eh deputado");
            }
        }
    }

    private void validaExistenciaCadastrarProjeto(String politicos, HashMap<String, Pessoa> pessoas) {
        if (!pessoas.containsKey(politicos)) {
            throw new NullPointerException("Erro ao cadastrar projeto: pessoa inexistente");
        }
        if (!pessoas.get(politicos).exibeFuncao().equals("Deputado")) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa nao eh deputado");
        }
    }

    public void cadastrarComissao(String tema, String politicos, HashMap<String, Pessoa> pessoas) {
        ArrayList<String> lista = new ArrayList<String>(Arrays.asList(politicos.split(",")));
        if (this.comissoes.containsKey(tema)) {
            throw new IllegalArgumentException("Erro ao cadastrar comissao: tema existente");
        } else {
            ArrayList<String> listaPoliticos = new ArrayList<>(Arrays.asList(politicos.split(",")));
            for (String listaPolitico : listaPoliticos) {
                if (!listaPolitico.matches("\\d{9}[-]\\d")) {
                    throw new IllegalArgumentException("Erro ao cadastrar comissao: dni invalido");
                }
            }
            validaExistenciaCadastrarComissao(politicos, pessoas);
        }
        this.comissoes.put(tema, lista);
    }
    private void validaCadastraProjeto(String dni, HashMap<String, Pessoa> pessoas) {
        if (!pessoas.containsKey(dni)) {
            throw new NullPointerException("Erro ao cadastrar projeto: pessoa inexistente");
        }
        if (!"Deputado".equals(pessoas.get(dni).exibeFuncao())) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa nao eh deputado");
        }
    }

    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo, HashMap<String, Pessoa> pessoas) {
        validaCadastraProjeto(dni,pessoas);
        int ordemCodigo = this.codigoProjetos.get("PL");
        String codigo = "PL " + ordemCodigo + "/" + ano;
        ProjetoLei projeto = new ProjetoLei(dni, ano, ementa, interesses, url, conclusivo, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PL", ordemCodigo + 1);
        return codigo;
    }

    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos, HashMap<String, Pessoa> pessoas) {
        validaCadastraProjeto(dni,pessoas);
        int ordemCodigo = this.codigoProjetos.get("PLP");
        String codigo = "PLP " + ordemCodigo + "/" + ano;
        ProjetoLeiComplementar projeto = new ProjetoLeiComplementar(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PLP", ordemCodigo + 1);
        return codigo;
    }

    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos, HashMap<String, Pessoa> pessoas) {
        validaCadastraProjeto(dni,pessoas);
        int ordemCodigo = this.codigoProjetos.get("PEC");
        String codigo = "PEC " + ordemCodigo + "/" + ano;
        ProjetoEmendaConstitucional projeto = new ProjetoEmendaConstitucional(dni, ano, ementa, interesses, url, artigos, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetos.replace("PEC", ordemCodigo + 1);
        return codigo;
    }

    public String exibirProjeto(String codigo) {
        return this.projetos.get(codigo).toString();
    }

    private void validaVotaComissao(String codigo, String status, String proximoLocal) {

        if (status == null || "".equals(status.trim()))
            throw new IllegalArgumentException("Erro ao votar proposta: status invalido");

        if (proximoLocal == null)
            throw new NullPointerException("Erro ao votar proposta: proximo local vazio");

        if ("".equals(proximoLocal.trim()))
            throw new IllegalArgumentException("Erro ao votar proposta: proximo local vazio");

        if (!"GOVERNISTA".equals(status) && !"OPOSICAO".equals(status) && !"LIVRE".equals(status))
            throw new IllegalArgumentException("Erro ao votar proposta: status invalido");

        if (!this.projetos.containsKey(codigo))
            throw new NullPointerException("Erro ao votar proposta: projeto inexistente");
    }


    public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal, HashMap<String, Pessoa> pessoasMap) {
        validaVotaComissao(codigo, statusGovernista, proximoLocal);
        Projeto projeto = this.projetos.get(codigo);
        String local = projeto.getLocal();
        if (this.comissoes.containsKey(local)) {
            ArrayList<String> listaDeputado = this.comissoes.get(local);
            return votacaoController.votarComissao(projeto, statusGovernista, listaDeputado, proximoLocal, pessoasMap, this.totalDeputados, this.partidosGovernistas);
        } else {
            throw new NullPointerException("Erro ao votar proposta: CCJC nao cadastrada");
        }
    }

    private void validaVotaPlenario(String codigo) {
        if (!"PLENARIO".equals(this.projetos.get(codigo).getLocal())) {
            throw new IllegalArgumentException("Erro ao votar proposta: tramitacao em comissao");
        }
    }

    public boolean votarPlenario(String codigo, String statusGovernista, String presentes, HashMap<String, Pessoa> pessoas) {
        validaVotaPlenario(codigo);
        Projeto projeto = this.projetos.get(codigo);
        return votacaoController.votarPlenario(projeto, statusGovernista, presentes, pessoas, this.totalDeputados, partidosGovernistas);
    }

    public String exibirTramitacao(String codigo) {
        return this.projetos.get(codigo).getSituacaoAtual();
    }
}