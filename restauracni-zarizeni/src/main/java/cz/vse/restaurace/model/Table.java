package cz.vse.restaurace.model;

/**
 * Třída Table v aplikaci představuje stůl, na kterém se vytvářejí objednávky.
 * Stůl může být buď obsazený nebo volná a o stolu se eviduje pouze jeho číslo.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class Table {

    private int tableNumber;

    /**
     * Konstruktor třídy Table, který jednotlivým stolům nastaví jejich číslo.
     *
     * @param tableNumber Číslo stolu
     */
    public Table(int tableNumber) {
       this.tableNumber = tableNumber;
    }

    /**
     * Metoda getTableNumber vrací číslo stolu jako číslo.
     *
     * @return tableNumber Číslo stolu
     */
    public int getTableNumber() {
        return tableNumber;
    }
}
