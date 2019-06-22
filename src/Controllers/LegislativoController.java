package Controllers;

import Entidades.*;
import Validacao.ValidaSystemController;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controlador responsável por armazenar as propostas legislativas e gerenciar as operações a respeito das votações de
 * tais propostas.
 */
public class LegislativoController implements Serializable {

    private VotacaoController votacaoController;
    private Map<String, ArrayList<String>> comissoes;
    private Map<String, Projeto> projetos;
    private Map<Integer, Integer> codigoProjetosPL;
    private Map<Integer, Integer> codigoProjetosPLP;
    private Map<Integer, Integer> codigoProjetosPEC;
    private AtomicInteger totalDeputados;
    private ValidaSystemController validador;

    /**
     * Conjunto que irá conter informações a respeito dos partidos cadastrados no sistema.
     */
    private HashSet<String> partidosGovernistas;

    public LegislativoController(AtomicInteger totalDeputados) {
        this.votacaoController = new VotacaoController();
        this.comissoes = new HashMap<>();
        this.projetos = new HashMap<>();
        this.codigoProjetosPL = new HashMap<>();
        this.codigoProjetosPLP = new HashMap<>();
        this.codigoProjetosPEC = new HashMap<>();
        this.partidosGovernistas = new HashSet<>();
        this.totalDeputados = totalDeputados;
        this.validador = new ValidaSystemController();
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
        this.validador.validaCadastrarPartido(partido);
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

    public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo, HashMap<String, Pessoa> pessoas) throws ParseException {
        this.validador.validaCadastrarPL(dni, ano, ementa, interesses, url, conclusivo);
        validaCadastraProjeto(dni, pessoas);
        int ordemCodigo;
        if (this.codigoProjetosPL.containsKey(ano)) {
            ordemCodigo = this.codigoProjetosPL.get(ano);
        } else {
            this.codigoProjetosPL.put(ano, 1);
            ordemCodigo = 1;
        }
        String codigo = "PL " + ordemCodigo + "/" + ano;
        ProjetoLei projeto = new ProjetoLei(dni, ano, ementa, interesses, url, conclusivo, codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPL.replace(ano, ordemCodigo + 1);
        return codigo;
    }

    public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos, HashMap<String, Pessoa> pessoas) throws ParseException {
        this.validador.validaCadastrarPLPouPEC(dni, ano, ementa, interesses, url, artigos);
        validaCadastraProjeto(dni, pessoas);
        int ordemCodigo;
        if (this.codigoProjetosPLP.containsKey(ano)) {
            ordemCodigo = this.codigoProjetosPLP.get(ano);
        } else {
            this.codigoProjetosPLP.put(ano, 1);
            ordemCodigo = 1;
        }
        String codigo = "PLP " + ordemCodigo + "/" + ano;
        ProjetoLeiComplementar projeto = new ProjetoLeiComplementar(dni, ano, ementa, interesses, url, artigos.replace(",", ", "), codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPLP.replace(ano, ordemCodigo + 1);
        return codigo;
    }

    public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos, HashMap<String, Pessoa> pessoas) throws ParseException {
        this.validador.validaCadastrarPLPouPEC(dni, ano, ementa, interesses, url, artigos);
        validaCadastraProjeto(dni, pessoas);
        int ordemCodigo;
        if (this.codigoProjetosPEC.containsKey(ano)) {
            ordemCodigo = this.codigoProjetosPEC.get(ano);
        } else {
            this.codigoProjetosPEC.put(ano, 1);
            ordemCodigo = 1;
        }
        String codigo = "PEC " + ordemCodigo + "/" + ano;
        ProjetoEmendaConstitucional projeto = new ProjetoEmendaConstitucional(dni, ano, ementa, interesses, url, artigos.replace(",", ", "), codigo);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPEC.replace(ano, ordemCodigo + 1);
        return codigo;
    }

    public String exibirProjeto(String codigo) {
        return this.projetos.get(codigo).toString();
    }

    public void validaExistenciaProjeto(String codigo) {
        if (!this.projetos.containsKey(codigo))
            throw new NullPointerException("Erro ao votar proposta: projeto inexistente");
    }

    private void validaPropostaEmPlenario(String codigo) {
        if ("plenario".equals(this.projetos.get(codigo).getLocal())) {
            throw new IllegalArgumentException("Erro ao votar proposta: proposta encaminhada ao plenario");
        }
    }

    public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal, HashMap<String, Pessoa> pessoas) {
        validaExistenciaProjeto(codigo);
        validaTramitacao(codigo);
        validaPropostaEmPlenario(codigo);
        Projeto projeto = this.projetos.get(codigo);
        String local = projeto.getLocal();
        if (this.comissoes.containsKey(local)) {
            ArrayList<String> listaDeputado = this.comissoes.get(local);
            return votacaoController.votarComissao(projeto, statusGovernista, listaDeputado, proximoLocal, pessoas, this.totalDeputados.get(), this.partidosGovernistas);
        } else {
            throw new NullPointerException("Erro ao votar proposta: CCJC nao cadastrada");
        }
    }

    private void validaTramitacao(String codigo) {
        String situacao = this.projetos.get(codigo).getSituacaoAtual();
        if ("ARQUIVADO".equals(situacao) || "APROVADO".equals(situacao)) {
            throw new IllegalArgumentException("Erro ao votar proposta: tramitacao encerrada");
        }
    }

    private void validaVotaPlenario(String codigo) {
        if (!"plenario".equals(this.projetos.get(codigo).getLocal())) {
            throw new IllegalArgumentException("Erro ao votar proposta: tramitacao em comissao");
        }
    }

    private void validaQuorumMinimo(boolean resultado) {
        if (!resultado) {
            throw new IllegalArgumentException("Erro ao votar proposta: quorum invalido");
        }
    }

    public boolean votarPlenario(String codigo, String statusGovernista, String presentes, HashMap<String, Pessoa> pessoas) {
        validaTramitacao(codigo);
        validaQuorumMinimo(projetos.get(codigo).quorumMinimo(presentes.split(",").length, this.totalDeputados.get()));
        validaVotaPlenario(codigo);
        Projeto projeto = this.projetos.get(codigo);
        return votacaoController.votarPlenario(projeto, statusGovernista, presentes, pessoas, this.totalDeputados.get(), partidosGovernistas);
    }

    public String exibirTramitacao(String codigo) {
        return this.projetos.get(codigo).getSituacaoAtual();
    }

    public HashSet<Projeto> pegarPropostaRelacionada(HashSet<String> interessesPessoa) {
        HashSet<String> interessesEmComum = new HashSet<>();
        HashSet<Projeto> projetoInteressesEmComum = new HashSet<>();
            for (Projeto projeto : this.projetos.values()) {
                HashSet<String> copiaInteressesProjeto = new HashSet<>(projeto.getInteresses());
                copiaInteressesProjeto.retainAll(interessesPessoa);
                if (copiaInteressesProjeto.size() > interessesEmComum.size()) {
                    interessesEmComum.clear();
                    interessesEmComum.addAll(copiaInteressesProjeto);
                    projetoInteressesEmComum.clear();
                    projetoInteressesEmComum.add(projeto);
                } else
                    if (copiaInteressesProjeto.size() == interessesEmComum.size()) {
                        interessesEmComum.clear();
                        interessesEmComum.addAll(copiaInteressesProjeto);
                        projetoInteressesEmComum.add(projeto);
                    }
            }
            return projetoInteressesEmComum;
    }
}