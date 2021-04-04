package cz.vse.restaurace.persistence;

/**
 * Třída pro vyjímky, které nastávájí pří práci se soubory
 * @author Jonáš Matějka
* @version ZS 2020
 */
public class PersistenceException extends Exception {

    /**
     * Pokud ve tříde JsonPersistence dojde k nějaké vyjímce tato
     * metoda pomůže vypsat její detaily.
     * @param cause vyjímka, která nastala ve tříde JsonPersistence
     */
    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
