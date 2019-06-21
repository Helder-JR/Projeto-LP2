package Controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;


import static org.junit.jupiter.api.Assertions.*;

class LegislativoControllerTest {

    private LegislativoController controller;
    private PessoaController pessoaController;

    @BeforeEach
    void setUp() throws ParseException {
        controller = new LegislativoController(new AtomicInteger(10));
        pessoaController = new PessoaController();
    }

    // Testes para Cadastrar Partido
    @Test
    void cadastraPartidoTest() {
        assertTrue(controller.cadastrarPartido("ABC"));
    }

    @Test
    void cadastrarPartidoNuloTest() {
        try {
            controller.cadastrarPartido(null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPartidoVazioTest() {
        try {
            controller.cadastrarPartido("     ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    // Testes para Exibir Base
    @Test
    void exibirBase() {
        controller.cadastrarPartido("CBA");
        controller.cadastrarPartido("ABC");
        assertEquals("ABC,CBA", controller.exibirBase());
    }


    // Testes para Cadastrar Comissao
    @Test
    void cadastrarComissaoTemaVazio() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("051444444-0", "30012019");
        try {
            controller.cadastrarComissao("", "051444444-0", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarComissaoTemaNulo() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("051444444-0", "30012019");
        try {
            controller.cadastrarComissao(null, "051444444-0", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarComissaoPoliticoVazio() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("051444444-0", "30012019");
        try {
            controller.cadastrarComissao("Trabalho", "", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarComissaoPoliticoNulo() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("051444444-0", "30012019");
        try {
            controller.cadastrarComissao("Trabalho", null, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarComissaoDniInvalido() throws ParseException {
        try {
            controller.cadastrarComissao("Trabalho", "051222222-A", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarComissaoTemaJaCadastrado() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("051444444-0", "30012019");
        controller.cadastrarComissao("Trabalho", "051444444-0", this.pessoaController.getPessoas());

        pessoaController.cadastrarPessoa("Teste Teste", "051222222-0", "Teste", "Teste", "Teste");
        pessoaController.cadastrarDeputado("051222222-0", "30012019");

        try {
            controller.cadastrarComissao("Trabalho", "051222222-0", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarComissaoPessoaInexistente() throws ParseException {
        try {
            controller.cadastrarComissao("Trabalho", "051111111-0", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarComissaoPessoaNaoEhDeputado() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "051444444-0", "RO", "trabalho", "DEF");
        try {
            controller.cadastrarComissao("Trabalho", "051444444-0", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    // Testes para Exibir Projetos
    @Test
    void ExibirProjetoPL() throws ParseException {
        pessoaController.cadastrarPessoa("Mateus Matia", "061222222-0", "PE", "", "ABC");
        pessoaController.cadastrarDeputado("061222222-0", "29022016");
        controller.cadastrarPL("061222222-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        assertEquals("Projeto de Lei - PL 1/2016 - 061222222-0 - Institui a semana da nutricao nas escolas - Conclusiva - EM VOTACAO (CCJC)", controller.exibirProjeto("PL 1/2016"));

        controller.cadastrarPL("061222222-0", 2016, "Institui a semana do carinho nas escolas", "cidadania,educacao basica", "http://example.com/semana_cidadania", false, this.pessoaController.getPessoas());
        assertEquals("Projeto de Lei - PL 2/2016 - 061222222-0 - Institui a semana do carinho nas escolas - EM VOTACAO (CCJC)", controller.exibirProjeto("PL 2/2016"));
    }

    @Test
    void ExibirProjetoPLP() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "061444444-0", "RO", "trabalho", "DEF");
        pessoaController.cadastrarDeputado("061444444-0", "30012019");
        controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 061444444-0 - Regulamenta a tributacao de apostas eletronicas - 153 - EM VOTACAO (CCJC)", controller.exibirProjeto("PLP 1/2016"));
    }

    @Test
    void ExibirProjetoPEC() throws ParseException {
        pessoaController.cadastrarPessoa("Mateus Matia", "061222222-0", "PE", "", "ABC");
        pessoaController.cadastrarDeputado("061222222-0", "29022016");
        controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        assertEquals("Projeto de Emenda Constitucional - PEC 1/2016 - 061222222-0 - Permite a associacao sindical livre e com estrutura hierarquica - 7, 8 - EM VOTACAO (CCJC)", controller.exibirProjeto("PEC 1/2016"));
    }

    // Testes para Cadastrar Projetos
    @Test
    void CadastrarPlAutorNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPL("", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPL(null, 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlpAutorNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPLP("", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPLP(null, 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPecAutorNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPEC("", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPEC(null, 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlEmentaNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPL("061222222-0", 2016, "", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPL("061222222-0", 2016, null, "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlpEmentaNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 2016, "", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPLP("061444444-0", 2016, null, "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPecEmentaNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPEC("061222222-0", 2016, "", "trabalho", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPEC("061222222-0", 2016, null, "trabalho", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlInteressesNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPL("061222222-0", 2016, "Institui a semana da nutricao nas escolas", "", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPL("061222222-0", 2016, "Institui a semana da nutricao nas escolas", null, "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlpInteressesNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", null, "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPecInteressesNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "", "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", null, "https://example.com/sindicato/algo.html", "7,8", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlUrlNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPL("061222222-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPL("061222222-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", null, true, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlpUrlNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", null, "153", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPecUrlNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", null, "7,8", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPlpArtigoOuVazio() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", null, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void CadastrarPecArtigoNuloOuVazio() throws ParseException {
        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.html", "", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }

        try {
            controller.cadastrarPEC("061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.html", null, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPlDniInvalido() throws ParseException {
        try {
            controller.cadastrarPL(" 061222222-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlpDniInvalido() throws ParseException {
        try {
            controller.cadastrarPLP(" 061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPecDniInvalido() throws ParseException {
        try {
            controller.cadastrarPEC(" 061222222-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.htm", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlPessoInexistente() throws ParseException {
        try {
            controller.cadastrarPL("060000000-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPlpPessoInexistente() throws ParseException {
        try {
            controller.cadastrarPLP("060000000-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPecPessoInexistente() throws ParseException {
        try {
            controller.cadastrarPEC("060000000-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.htm", "7,8", this.pessoaController.getPessoas());
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPlPessoNaoDeputado() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "061444444-0", "RO", "trabalho", "DEF");
        try {
            controller.cadastrarPL("061444444-0", 2016, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlpPessoNaoDeputado() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "061444444-0", "RO", "trabalho", "DEF");
        try {
            controller.cadastrarPLP("061444444-0", 2016, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPecPessoNaoDeputado() throws ParseException {
        pessoaController.cadastrarPessoa("Plipox Poplx", "061444444-0", "RO", "trabalho", "DEF");
        try {
            controller.cadastrarPEC("061444444-0", 2016, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.htm", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlAnoInvalido1() throws ParseException {
        try {
            controller.cadastrarPL("061444444-0", 1987, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlAnoInvalido2() throws ParseException {
        try {
            controller.cadastrarPL("061444444-0", 2050, "Institui a semana da nutricao nas escolas", "saude,educacao basica", "http://example.com/semana_saude", true, this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlpAnoInvalido1() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 1987, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPlpAnoInvalido2() throws ParseException {
        try {
            controller.cadastrarPLP("061444444-0", 2050, "Regulamenta a tributacao de apostas eletronicas", "fiscal,jogos", "https://example.net/jogos%40aposta", "153", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPecAnoInvalido1() throws ParseException {
        try {
            controller.cadastrarPEC("061444444-0", 1987, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.htm", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastrarPecAnoInvalido2() throws ParseException {
        try {
            controller.cadastrarPEC("061444444-0", 2050, "Permite a associacao sindical livre e com estrutura hierarquica", "trabalho", "https://example.com/sindicato/algo.htm", "7,8", this.pessoaController.getPessoas());
        } catch (IllegalArgumentException iae) {
        }
    }
}