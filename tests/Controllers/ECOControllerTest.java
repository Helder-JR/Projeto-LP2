package Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ECOControllerTest {

    private ECOController controller;

    @BeforeEach
    void setUp() throws ParseException {
        controller = new ECOController();
    }

    // Testes para Cadastrar Pessoa
    @Test
    void cadastrarPessoaComPartido() {
        assertTrue(controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU"));
    }

    @Test
    void cadastrarPessoaSemPartido() {
        assertTrue(controller.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo"));
    }

    @Test
    void cadastrarPessoaNuloTest() {
        try {
            controller.cadastrarPessoa(null,null, null,null,null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void cadastrarPessoaVazioTest() {
        try {
            controller.cadastrarPessoa("   ","   ", "   ","   ","    ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraPessoaRepetidaTest() {
        controller.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
        try {
            controller.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraPessoaRepetidaTest2() {
        controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
        try {
            controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraPessoaDniInvalido() {
        try {
            controller.cadastrarPessoa("Thanos","2345678901", "SP","Interesses: economia, minerais","MCU");
        } catch (IllegalArgumentException iae) {}
    }


    // Testes para Cadastrar Deputado
    @Test
    void cadastraDeputadoTest() throws ParseException {
        controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
        assertTrue(controller.cadastrarDeputado("234567890-1", "01011019"));
    }

    @Test
    void cadastrarDeputadoNuloTest() throws ParseException {
        try {
            controller.cadastrarDeputado(null, null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void cadastrarDeputadoVazioTest() throws ParseException {
        try {
            controller.cadastrarDeputado("    ", " ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraDeputadoSemPartidoTest() throws ParseException {
        controller.cadastrarPessoa("Matheus G.", "123456789-0", "PB", "Interesses: ensino superior, cooperativismo");
        try {
            controller.cadastrarDeputado("123456789-0", "01011019");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraDeputadoPessoaInexistenteTest() throws ParseException {
        try {
            controller.cadastrarDeputado("123456789-0", "01011019");
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void cadastraDeputadoDniInvalidoTest() throws ParseException {
        try {
            controller.cadastrarDeputado("1234567890", "01011019");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraDeputadoDataInvalidaTest() throws ParseException {
        controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
        try {
            controller.cadastrarDeputado("234567890-1", "A01011019");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void cadastraDeputadoDataFuturaTest() throws ParseException {
        controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
        try {
            controller.cadastrarDeputado("234567890-1", "01013020");
        } catch (IllegalArgumentException iae) {}
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
        } catch (NullPointerException npe) {}
    }

    @Test
    void cadastrarPartidoVazioTest() {
        try {
            controller.cadastrarPartido("     ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }


    // Testes para Exibir Pessoa
    @Test
    void exibirPessoaTest() {
        controller.cadastrarPessoa("Thanos","234567890-1", "SP","Interesses: economia, minerais","MCU");
        assertEquals("Thanos - 234567890-1 (SP) - MCU - Interesses: Interesses: economia, minerais", controller.exibirPessoa("234567890-1"));
    }
    @Test
    void exibirPessoaNuloTest() {
        try {
            controller.exibirPessoa(null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void exibirPessoaVazioTest() {
        try {
            controller.exibirPessoa("    ");
            fail("Deveria lançar exceção");
        } catch (IllegalArgumentException iae) {}
    }

    @Test
    void exibirPessoaInexistenteTest() {
        try {
            controller.exibirPessoa("123456789-0");
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void exibirPessoaDniInvalidoTest() throws ParseException {
        try {
            controller.exibirPessoa("1234567890");
        } catch (IllegalArgumentException iae) {}
    }


    // Testes para Exibir Base
    @Test
    void exibirBase() {
        controller.cadastrarPartido("CBA");
        controller.cadastrarPartido("ABC");
        assertEquals("ABC,CBA", controller.exibirBase());
    }
}