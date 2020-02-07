package puertoricotr;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import puertoricotr.stockageoutilsjeux.Banque;
import org.junit.*;

/**
 * Unit test for simple App.
 */
public class BanqueTest
{
    /**
     *Methode test setDoublon
     */
    @Test
    public void setDoublon() {
        Banque banque = new Banque(12,10,25);
        banque.setNbDoublon(5);
        assertEquals(5, banque.getNbDoublon());
    }

    /**
     *Methode test getDoublon
     */
    @Test
    public void getDoublon() {
        Banque banque = new Banque(12,10,25);
        banque.setNbDoublon(5);
        assertEquals(5, banque.getNbDoublon());
    }

    /**
     *Methode test setNbPointVictoire
     */
    @Test
    public void setNbPointVictoire() {
        Banque banque = new Banque(12,10,25);
        banque.setNbPointVictoire(2);
        assertEquals(2, banque.getNbPointVictoire());
    }

    /**
     *Methode test getNbPointVictoire
     */
    @Test
    public void getNbPointVictoire() {
        Banque banque = new Banque(12,10,25);
        banque.setNbColon(5);
        assertEquals(5, banque.getNbColon());
    }


    /**
     *Methode test setColon
     */
    @Test
    public void setColon() {
        Banque banque = new Banque(12,10,25);
        banque.setNbColon(2);
        assertEquals(2, banque.getNbColon());
    }

    /**
     *Methode test getColon
     */
    @Test
    public void getColon() {
        Banque banque = new Banque(12,10,25);
        banque.setNbColon(5);
        assertEquals(5, banque.getNbColon());
    }

    /**
     *Methode test getAffichage
     */
    @Test
    public void getAffichage(){
        Banque banque = new Banque(12,10,25);
        assertNotNull(banque.getAffichage());
    }

}
