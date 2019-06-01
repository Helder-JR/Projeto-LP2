package Entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {
    private Pessoa p1;

    @BeforeEach
    void initEach() {
        this.p1 = new Pessoa("João Silva", "111111111-9", "PB", "saude,educacao", "PCC");
    }

    @Test
    void testEquals1() {
        Pessoa p2 = new Pessoa("João Silva", "111111111-9", "PB", "saude,educacao", "PCC");
        assertEquals(p1, p2);
    }

    @Test
    void testEquals2() {
        Pessoa p2 = new Pessoa("José Santos", "222222222-8", "BA", "seguranca,trabalho", "PFC");
        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals3() {
        Pessoa p3 = new Pessoa("Maria Araujo", "333333333-7", "PE", "educacao,transporte");
        assertNotEquals(p1, p3);
    }

    @Test
    void testHashCode1() {
        Pessoa p2 = new Pessoa("João Silva", "111111111-9", "PB", "saude,educacao", "PCC");
        assertEquals(Objects.hashCode(p1), Objects.hashCode(p2));
    }

    @Test
    void testHashCode2() {
        Pessoa p2 = new Pessoa("José Santos", "222222222-8", "BA", "seguranca,trabalho", "PFC");
        assertNotEquals(Objects.hashCode(p1), Objects.hashCode(p2));
    }

    @Test
    void testHashCode3() {
        Pessoa p3 = new Pessoa("Maria Araujo", "333333333-7", "PE", "educacao,transporte");
        assertNotEquals(Objects.hashCode(p1), Objects.hashCode(p3));
    }

    @Test
    void testToString1() {
        assertEquals("João Silva - 111111111-9 (PB) - PCC - Interesses: saude,educacao", p1.toString());
    }

    @Test
    void testToString2() {
        Pessoa p2 = new Pessoa("José Santos", "222222222-8", "BA", "seguranca,trabalho", "PFC");
        assertEquals("José Santos - 222222222-8 (BA) - PFC - Interesses: seguranca,trabalho", p2.toString());
    }

    @Test
    void testToString3() {
        Pessoa p3 = new Pessoa("Maria Araujo", "333333333-7", "PE", "educacao,transporte");
        assertEquals("Maria Araujo - 333333333-7 (PE) - Interesses: educacao,transporte", p3.toString());
    }

    @Test
    void testGetPartido1() {
        assertEquals("PCC", p1.getPartido());
    }

    @Test
    void testGetPartido2() {
        Pessoa p2 = new Pessoa("José Santos", "222222222-8", "BA", "seguranca,trabalho", "PFC");
        assertEquals("PFC", p2.getPartido());
    }

    @Test
    void testGetPartido3() {
        Pessoa p3 = new Pessoa("Maria Araujo", "333333333-7", "PE", "educacao,transporte");
        assertEquals("", p3.getPartido());
    }

    @Test
    void testSetFuncao() {
        //
    }
}