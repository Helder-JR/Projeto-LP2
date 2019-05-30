package Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ECOControllerTest {

    ECOController controller;

    @BeforeEach
    void setUp() throws ParseException {
        controller = new ECOController();
        controller.cadastrarPessoa("Thanos","234567890-1", "PB","Interesses: economia, minerais","MCU");
        controller.cadastrarDeputado("234567890-1", "01011019");
    }

    @Test
    void cadastrarPessoaNulaTest() {
        try {
            controller.cadastrarPessoa(null,null, null,null,null);
            fail("Deveria lançar exceção");
        } catch (NullPointerException npe) {}
    }

    @Test
    void cadastrarPessoa1() {
    }

    @Test
    void cadastrarDeputado() {
    }

    @Test
    void cadastrarPartido() {
    }

    @Test
    void exibirPessoa() {
    }

    @Test
    void exibirBase() {
    }

    @Test
    void salvarSistema() {
    }

    @Test
    void carregarSistema() {
    }

    @Test
    void limparSistema() {
    }
}