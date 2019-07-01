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

    /**
     * O objeto controlador que irá realizar as operações referentes ao seu uso.
     */
    private VotacaoController votacaoController;

    /**
     * O mapa com as comissões cadastradas no sistema.
     */
    private Map<String, ArrayList<String>> comissoes;

    /**
     * O mapa que contém as propostas de lei cadastradas.
     */
    private Map<String, Projeto> projetos;

    /**
     * O mapa que contém os códigos dos projetos de lei.
     */
    private Map<Integer, Integer> codigoProjetosPL;

    /**
     * O mapa que contém os códigos dos projetos de lei complementares.
     */
    private Map<Integer, Integer> codigoProjetosPLP;

    /**
     * O mapa que contém os códigos dos projetos de emenda constitucionais.
     */
    private Map<Integer, Integer> codigoProjetosPEC;

    /**
     * O código global que guarda a quantidade de propostas cadastradas.
     */
    private int codigoGlobal;

    /**
     * Armazena a quantidade total de deputados.ss
     */
    private AtomicInteger totalDeputados;

    /**
     * Objeto responsável por fazer a validação das entradas de dados.
     */
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

    /**
     * Valida a existência de políticos necessária para cadastrar uma comissão.
     *
     * @param politicos os políticos que participarão da comissão.
     * @param pessoas as pessoas que possivelmente serão políticos a serem cadastrados na comissão.
     * @throws NullPointerException caso a pessoa não exista no cadastro.
     * @throws IllegalArgumentException caso a pessoa específica não seja um(a) deputado(a).
     */
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

    /**
     * Cadastra uma comissão no controlador.
     *
     * @param tema o tema que fará parte da comissão.
     * @param politicos os políticos que irão compôr a comissão.
     * @param pessoas as pessoas que possivelmente serão políticos a serem cadaastrados na comissão.
     * @throws IllegalArgumentException caso já exista comissão com o tema escolhido.
     * @throws IllegalArgumentException caso o DNI não seja válido.
     */
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

    /**
     * Validação necessária para cadastrar uma proposta legislativa.
     *
     * @param dni o DNI da pessoa que criou a proposta.
     * @param pessoas o mapa de pessoas.
     * @throws NullPointerException caso a pessoa não esteja cadstrada no mapa.
     * @throws IllegalArgumentException Caso a pessoa não seja um(a) deputado(a).
     */
    private void validaCadastraProjeto(String dni, HashMap<String, Pessoa> pessoas) {
        if (!pessoas.containsKey(dni)) {
            throw new NullPointerException("Erro ao cadastrar projeto: pessoa inexistente");
        }
        if (!"Deputado".equals(pessoas.get(dni).exibeFuncao())) {
            throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa nao eh deputado");
        }
    }

    /**
     * Cadastra um projeto de lei.
     *
     * @param dni o DNI da pessoa que criou/propôs o projeto de lei.
     * @param ano o ano em que o projeto de lei foi criado/proposto.
     * @param ementa a ementa com uma descrição sobre do que se trata o projeto.
     * @param interesses os interesses relacionados a esse projeto de lei.
     * @param url o endereço da internet que contém o arquivo com a descrição completa do projeto de lei.
     * @param conclusivo indica se o projeto pode ser aprecisado em comissões sem necessidade de ir ao plenário.
     * @param pessoas o mapa com as pessoas cadastradas.
     * @return o código em String referente ao projeto de lei cadastrado.
     * @throws NullPointerException     caso o DNI, a ementa, os interesses ou a url sejam Strings nulas.
     * @throws IllegalArgumentException caso alguma dessas entradas seja uma String vazia, composta apenas de espaços,
     *                                  ou não esteja em um formato válido, como por exemplo o DNI não ser na forma
     *                                  "XXXXXXXXX-X" ou o ano ser superior ou inferior a criação da proposta.
     * @throws ParseException caso o ano não seja uma data válida.
     */
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
        ProjetoLei projeto = new ProjetoLei(dni, ano, ementa, interesses, url, conclusivo, codigo, this.codigoGlobal);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPL.replace(ano, ordemCodigo + 1);
        this.codigoGlobal++;
        return codigo;
    }

    /**
     *
     * @param dni o DNI da pessoa que propôs o projeto.
     * @param ano o ano em que o projeto foi proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param interesses os interesses defendidos no projeto.
     * @param url o endereço da internet que contém a descrição completa referente ao projeto.
     * @param artigos os artigos que serão complementados por esse projeto.
     * @param pessoas o mapa com as pessoas que possivelmente faram parte do projeto
     * @return o código em String referente ao projeto de lei cadastrado.
     * @throws NullPointerException     caso alguma das entradas seja uma String nula.
     * @throws IllegalArgumentException caso alguma das Strings seja vazia, composta apenas de espaços, o DNI não siga o
     *                                  padrão proposto ou a data seja em algum momento do tempo diferente da real
     *                                  criação da proposta de lei.
     * @throws ParseException           caso o ano de criação do projeto não seja uma data válida.
     */
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
        ProjetoLeiComplementar projeto = new ProjetoLeiComplementar(dni, ano, ementa, interesses, url, artigos.replace(",", ", "), codigo, this.codigoGlobal);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPLP.replace(ano, ordemCodigo + 1);
        this.codigoGlobal++;
        return codigo;
    }

    /**
     * Cadastra um projeto de emenda constitucioal no sistema.
     *
     * @param dni o DNI da pessoa que criou/propôs o projeto.
     * @param ano o ano em que o projeto foi proposto.
     * @param ementa a ementa com a descrição do projeto.
     * @param url o endereço da internet com a descrição completa referente ao projeto.
     * @param artigos os artigos que serão emendados por esse projeto.
     * @param pessoas o mapa com as pessoas cadastradas no sistema.
     * @return o código em String referente ao projeto cadastrado.
     * @throws NullPointerException     caso alguma das entradas seja uma String nula.
     * @throws IllegalArgumentException caso alguma das Strings seja vazia, composta apenas de espaços, o DNI não siga o
     *                                  padrão proposto ou a data seja em algum momento do tempo diferente da real
     *                                  criação da proposta de lei.
     * @throws ParseException           caso o ano de criação do projeto não seja uma data válida.
     */
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
        ProjetoEmendaConstitucional projeto = new ProjetoEmendaConstitucional(dni, ano, ementa, interesses, url, artigos.replace(",", ", "), codigo, this.codigoGlobal);
        this.projetos.put(codigo, projeto);
        this.codigoProjetosPEC.replace(ano, ordemCodigo + 1);
        this.codigoGlobal++;
        return codigo;
    }

    /**
     * Exibe uma proposta legislativa.
     *
     * @param codigo o código referente a proposta a ser exibida.
     * @return a String referente a representação da proposta.
     */
    public String exibirProjeto(String codigo) {
        return this.projetos.get(codigo).toString();
    }

    /**
     * Validação necessária para verificar a existência de uma proposta legislativa.
     *
     * @param codigo o código referente a proposta que será validada.
     * @throws NullPointerException caso o projeto não exista.
     */
    public void validaExistenciaProjeto(String codigo) {
        if (!this.projetos.containsKey(codigo))
            throw new NullPointerException("Erro ao votar proposta: projeto inexistente");
    }

    /**
     * Validação para a votação de uma proposta que talvez tenha sido enviada ao plenário.
     *
     * @param codigo o código referente a proposta que será validada.
     * @throws IllegalArgumentException caso a proposta tenha sido encaminhada ao plenário.
     */
    private void validaPropostaEmPlenario(String codigo) {
        if ("plenario".equals(this.projetos.get(codigo).getLocal())) {
            throw new IllegalArgumentException("Erro ao votar proposta: proposta encaminhada ao plenario");
        }
    }

    /**
     * Vota uma comissão.
     *
     * @param codigo o código da proposta.
     * @param statusGovernista o status governista.
     * @param proximoLocal o próximo local onde a proposta será votada.
     * @param pessoas o mapa de pessoas cadastradas.
     * @return um booleano true caso a proposta seja aprovada ou false do contrário.
     */
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

    /**
     * Valida a tramitação de uma proposta.
     *
     * @param codigo o código referente ao projeto legislativo.
     */
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
        if (this.projetos.containsKey(codigo)) {
            return this.projetos.get(codigo).getTramitacao();
        } throw new NullPointerException("Erro ao exibir tramitacao: projeto inexistente");
    }

    public HashSet<Projeto> pegarPropostaRelacionada(HashSet<String> interessesPessoa) {
        HashSet<String> interessesEmComum = new HashSet<>();
        HashSet<Projeto> projetoInteressesEmComum = new HashSet<>();
            for (Projeto projeto : this.projetos.values()) {
                HashSet<String> copiaInteressesProjeto = new HashSet<>(projeto.getInteresses());
                copiaInteressesProjeto.retainAll(interessesPessoa);
                if (copiaInteressesProjeto.size() > 0 && copiaInteressesProjeto.size() > interessesEmComum.size() && !"-".equals(projeto.getLocal())) {
                    interessesEmComum.clear();
                    interessesEmComum.addAll(copiaInteressesProjeto);
                    projetoInteressesEmComum.clear();
                    projetoInteressesEmComum.add(projeto);
                } else
                if (copiaInteressesProjeto.size() > 0 && copiaInteressesProjeto.size() == interessesEmComum.size() && !"-".equals(projeto.getLocal())) {
                    interessesEmComum.addAll(copiaInteressesProjeto);
                    projetoInteressesEmComum.add(projeto);
                }
            }
            return projetoInteressesEmComum;
    }
}