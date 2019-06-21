package Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class PessoaControllerTest {

    private PessoaController pessoaController;

    @BeforeEach
    void setUp() {
        pessoaController = new PessoaController();
    }

    // Testes para Cadastrar Pessoa
    @Test
    void cadastrarPessoaComPartido() {
        assertTrue(pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU"));
    }

    @Test
    void cadastrarPessoaSemPartido() {
        assertTrue(pessoaController.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo"));
    }

    @Test
    void cadastrarPessoaNuloTest() {
        try {
            pessoaController.cadastrarPessoa(null, null, null, null, null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarPessoaVazioTest() {
        try {
            pessoaController.cadastrarPessoa("   ", "   ", "   ", "   ", "    ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraPessoaRepetidaTest() {
        pessoaController.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
        try {
            pessoaController.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraPessoaRepetidaTest2() {
        pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
        try {
            pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraPessoaDniInvalido() {
        try {
            pessoaController.cadastrarPessoa("Thanos", "2345678901", "SP", "Interesses: economia, minerais", "MCU");
        } catch (IllegalArgumentException iae) {
        }
    }

    // Testes para Cadastrar Deputado
    @Test
    void cadastraDeputadoTest() throws ParseException {
        pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
        assertTrue(pessoaController.cadastrarDeputado("234567890-1", "01011019"));
    }

    @Test
    void cadastrarDeputadoNuloTest() throws ParseException {
        try {
            pessoaController.cadastrarDeputado(null, null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastrarDeputadoVazioTest() throws ParseException {
        try {
            pessoaController.cadastrarDeputado("    ", " ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraDeputadoSemPartidoTest() throws ParseException {
        pessoaController.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
        try {
            pessoaController.cadastrarDeputado("123456789-0", "01011019");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraDeputadoPessoaInexistenteTest() throws ParseException {
        try {
            pessoaController.cadastrarDeputado("123456789-0", "01011019");
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void cadastraDeputadoDniInvalidoTest() throws ParseException {
        try {
            pessoaController.cadastrarDeputado("1234567890", "01011019");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraDeputadoDataInvalidaTest() throws ParseException {
        pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
        try {
            pessoaController.cadastrarDeputado("234567890-1", "A01011019");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    void cadastraDeputadoDataFuturaTest() throws ParseException {
        pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
        try {
            pessoaController.cadastrarDeputado("234567890-1", "01013020");
        } catch (IllegalArgumentException iae) {
        }
    }

    // Testes para Exibir Pessoa
    @Test
    void exibirPessoaTest() {
        pessoaController.cadastrarPessoa("Thanos", "234567890-1", "SP", "Interesses: economia, minerais", "MCU");
        assertEquals("Thanos - 234567890-1 (SP) - MCU - Interesses: Interesses: economia, minerais", pessoaController.exibirPessoa("234567890-1"));
    }

    @Test
    void exibirPessoaNuloTest() {
        try {
            pessoaController.exibirPessoa(null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }

    @Test
    void exibirPessoaInexistenteTest() {
        try {
            pessoaController.exibirPessoa("123456789-0");
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {
        }
    }
}