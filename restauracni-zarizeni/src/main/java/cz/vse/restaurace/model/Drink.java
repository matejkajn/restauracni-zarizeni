package cz.vse.restaurace.model;

/**
 * Třída představuje logiku nápojů v aplikaci. Každý nápoj má dvě vlastnosti, název a cenu.
 * Nápoje se společně s jídlem používají jako položky v fiktivním menu restauračního systému a uživatel je přidává do objednávky.
 * Poznámka: metody setName() a setPrice() byly odstraněny, protože s v projektu nevyužívají.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class Drink {

    private String name;
    private int price;

    /**
     * Konstruktor třídy Drink. Vytvoří se konkrétní nápoj a nastaví se jeho vlastnosti.
     *
     * @param name Název nápoje
     * @param price Cena nápoje
     */
    public Drink(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Metoda getName vrací jméno nápoje jako textový řetězec.
     *
     * @return name Jméno nápoje
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda getPrice vrací cenu nápoje jako číslo.
     *
     * @return price Cena nápoje
     */
    public int getPrice() {
        return price;
    }
}