package Entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DeputadoTest {
    private Deputado d1;

    @BeforeEach
    void initEach() throws ParseException {
        this.d1 = new Deputado("111111111-9", "01012018");
    }

    @Test
    void testEquals1() throws ParseException {
        Deputado d2 = new Deputado("222222222-8", "02012018");
        assertNotEquals(d1, d2);
    }

    @Test
    void testEquals2() throws ParseException {
        Deputado d2 = new Deputado("111111111-9", "01012018");
        assertEquals(d1, d2);
    }

    @Test
    void testEquals3() throws ParseException {
        Deputado d2 = new Deputado("222222222-8", "01012018");
        assertNotEquals(d1, d2);
    }

    @Test
    void testEquals4() throws ParseException {
        Deputado d2 = new Deputado("111111111-9", "02012018");
        assertNotEquals(d1, d2);
    }

    @Test
    void testHashCode1() throws ParseException {
        Deputado d2 = new Deputado("222222222-8", "02012018");
        assertNotEquals(Objects.hashCode(d1), Objects.hashCode(d2));
    }

    @Test
    void testHashCode2() throws ParseException {
        Deputado d2 = new Deputado("111111111-9", "01012018");
        assertEquals(Objects.hashCode(d1), Objects.hashCode(d2));
    }

    @Test
    void testHashCode3() throws ParseException {
        Deputado d2 = new Deputado("222222222-8", "01012018");
        assertNotEquals(Objects.hashCode(d1), Objects.hashCode(d2));
    }

    @Test
    void testHashCode4() throws ParseException {
        Deputado d2 = new Deputado("111111111-9", "02012018");
        assertNotEquals(Objects.hashCode(d1), Objects.hashCode(d2));
    }

    @Test
    void testToString1() throws ParseException {
        assertEquals("POL: João Silva - 111111111-9 (PB) - PCC - Interesses: saude,educacao - 01/01/2018 - 0 Leis", d1.toString("João Silva", "111111111-9", "PB", "PCC", "saude,educacao"));
    }

    @Test
    void testToString2() throws ParseException {
        assertEquals("POL: João Silva - 111111111-9 (PB) - PCC - 01/01/2018 - 0 Leis", d1.toString("João Silva", "111111111-9", "PB", "PCC", ""));
    }

    @Test
    void testToString3() throws ParseException {
        assertEquals("POL: João Silva - 111111111-9 (PB) - Interesses: saude,educacao - 01/01/2018 - 0 Leis", d1.toString("João Silva", "111111111-9", "PB", "", "saude,educacao"));
    }

    @Test
    void testToString4() throws ParseException {
        assertEquals("POL: João Silva - 111111111-9 (PB) - 01/01/2018 - 0 Leis", d1.toString("João Silva", "111111111-9", "PB", "", ""));
    }
}
