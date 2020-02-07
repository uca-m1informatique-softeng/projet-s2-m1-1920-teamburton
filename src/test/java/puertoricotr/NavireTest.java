package puertoricotr;
import static org.junit.Assert.*;

import org.junit.*;
import puertoricotr.stockageoutilsjeux.*;
public class NavireTest {

    /**
     * Methode chargeeTonneau
     */
    @Test
    public void chargerTonneauTest(){
        Navires navires = new Navires(4);
        navires.chargerTonneau(Constantes.TABAC, 3);
        assertEquals(3, navires.getNbRessource());
        assertEquals(Constantes.TABAC, navires.getNomRessource());
    }


    /**
     * Methode tranfertVersReserve
     */
    @Test
    public void tranfertVersReserveTest(){
        Reserve reserve = new Reserve(10,10,10,10,10);
        Navires navires = new Navires(6);
        navires.chargerTonneau(Constantes.CAFE, 5);
        assertEquals(5, navires.getNbRessource());
        assertEquals(Constantes.CAFE, navires.getNomRessource());

        navires.tranfertVersReserve(reserve);
        assertEquals(15, reserve.getNbMarchandise(Constantes.CAFE));

    }

    /**
     * Methode estComplet
     */
    @Test
    public void estCompletTest(){
        Navires navire = new Navires(4);
        navire.chargerTonneau(Constantes.CAFE, 4);
        assertTrue(navire.estComplet());

    }
}
