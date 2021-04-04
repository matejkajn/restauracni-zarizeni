package cz.vse.restaurace.model;

/**
 * Třída představuje logiku jídla v aplikaci. Každé jídlo má dvě vlastnosti, název a cenu.
 * Jídlo se společně s nápoji používá jako položka ve fiktivním menu restauračního systému a uživatel jej přidává do objednávky.
 * Poznámka: metody setName() a setPrice() byly odstraněny, protože s v projektu nevyužívají.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class Food {

    private String name;
    private int price;

    /**
     * Konstruktor třídy Food. Vytvoří se konkrétní jídlo a nastaví se mu jeho vlastnosti.
     *
     * @param name Název jídla
     * @param price Cena jídla
     */
    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Metoda getName vrací jméno jídla jako textový řetězec.
     *
     * @return name Jméno jídla
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda getPrice vrací cenu jídla jako číslo.
     *
     * @return price Cena jídla
     */
    public int getPrice() {
        return price;
    }
}
