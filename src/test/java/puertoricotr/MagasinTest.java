package puertoricotr;

import org.junit.Test;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Reserve;

import static org.junit.jupiter.api.Assertions.*;

public class MagasinTest {

    private Magasin magasin;

    /**
     * Methode estPlein
     */
    @Test
    public void estPleinTest(){

        magasin = new Magasin();

        magasin.ajouterMarchandises(Constantes.MAIS);
        magasin.ajouterMarchandises(Constantes.INDIGO);
        magasin.ajouterMarchandises(Constantes.CAFE);
        magasin.ajouterMarchandises(Constantes.TABAC);

        assertTrue(magasin.estPlein());
    }

    /**
     * Methode getPrix
     */
    @Test
    public void getPrixTest(){

        magasin = new Magasin();

        magasin.ajouterMarchandises(Constantes.MAIS);
        magasin.ajouterMarchandises(Constantes.SUCRE);
        magasin.ajouterMarchandises(Constantes.TABAC);
        magasin.ajouterMarchandises(Constantes.CAFE);

        assertEquals(0, magasin.getPrix(Constantes.MAIS));
        assertEquals(2, magasin.getPrix(Constantes.SUCRE));
        assertEquals(3, magasin.getPrix(Constantes.TABAC));
        assertEquals(4, magasin.getPrix(Constantes.CAFE));

        // Default du switch
        assertEquals(-1, magasin.getPrix("Toto"));
    }


    /**
     * Methode transfertVersReserve
     */
    @Test
    public void transfertVersReserveTest(){

        magasin = new Magasin();
        Reserve reserve = new Reserve(10, 10, 10, 10, 10);

        magasin.ajouterMarchandises(Constantes.MAIS);
        magasin.ajouterMarchandises(Constantes.SUCRE);
        magasin.ajouterMarchandises(Constantes.CAFE);
        magasin.ajouterMarchandises(Constantes.TABAC);

        magasin.tranfertVersReserve(reserve);

        assertEquals(11, reserve.getNbMarchandise(Constantes.MAIS));
        assertEquals(11, reserve.getNbMarchandise(Constantes.SUCRE));
        assertEquals(11, reserve.getNbMarchandise(Constantes.TABAC));
        assertEquals(11, reserve.getNbMarchandise(Constantes.CAFE));

        for (int m = 0; m < magasin.getNbTonneau(); m++) {
            assertNull(magasin.getMarchandises()[m]);
        }
    }

    /**
     * Methode getAffichage
     */
    @Test
    public void getAffichageTest(){
        magasin = new Magasin();

        magasin.ajouterMarchandises(Constantes.MAIS);
        assertNotNull(magasin.getAffichage());
    }
}

