package Entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CivilTest {

    private Civil civil;

    @BeforeEach
    void setUp() {
        this.civil = new Civil();
    }

    @Test
    void toStringCompletoTest() {
        assertEquals("José Santos - 222222222-8 (BA) - PFC - Interesses: seguranca,trabalho" , this.civil.toString("José Santos", "222222222-8", "BA","PFC","seguranca,trabalho"));
    }

    @Test
    void toStringSemPartidoTest() {
        assertEquals("José Santos - 222222222-8 (BA) - Interesses: seguranca,trabalho" , this.civil.toString("José Santos", "222222222-8", "BA","","seguranca,trabalho"));
    }

    @Test
    void toStringSemInteresseTest() {
        assertEquals("José Santos - 222222222-8 (BA) - PFC" , this.civil.toString("José Santos", "222222222-8", "BA","PFC",""));
    }

    @Test
    void toStringSemPartidoSemInteresseTest() {
        assertEquals("José Santos - 222222222-8 (BA)" , this.civil.toString("José Santos", "222222222-8", "BA","",""));
    }
}