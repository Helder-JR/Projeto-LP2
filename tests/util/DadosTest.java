package util;

import Controllers.ECOController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DadosTest {

    private Dados dados;
    private ECOController controller;
    private File saveTest;

    @BeforeEach
    void setUp() {
        dados = new Dados();
        controller = new ECOController();
        saveTest = new File("saveTest.data");
    }

    @Test
    void salvar() {
        assertTrue(dados.salvar(controller, saveTest));
    }

    @Test
    void carregar() {
        assertNotNull(dados.carregar(saveTest));
    }
}