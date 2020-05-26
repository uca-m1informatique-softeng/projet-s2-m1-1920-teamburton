package puertoricotr;

import org.junit.Test;
import puertoricotr.stockageoutilsjeux.Reserve;

import static org.junit.Assert.assertEquals;

public class ReserveTest {


    /**
     * Methode ajouterMarchandise
     */
    @Test
    public void ajouterMarchandiseTest(){
        Reserve reserve = new Reserve(0,0,0,0,0);
        reserve.ajouterMarchandise(Constantes.CAFE, 5);

        assertEquals(5, reserve.getNbMarchandise(Constantes.CAFE));
    }


    /**
     * Methode ajouterMarchandise
     */
    @Test
    public void prendreMarchandiseTest() {
        Reserve reserve = new Reserve(10, 10, 10, 10, 6);
        reserve.prendreMarchandise(Constantes.INDIGO, 8);

        assertEquals(2, reserve.getNbMarchandise(Constantes.INDIGO));
    }
}
