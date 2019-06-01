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
        this.dados = new Dados();
        this.controller = new ECOController();
        this.saveTest = new File("saveTest.data");
    }

    @Test
    void salvar() {
        assertTrue(this.dados.salvar(this.controller, this.saveTest));
    }

    @Test
    void carregar() {
        assertNotNull(this.dados.carregar(this.saveTest));
    }
}