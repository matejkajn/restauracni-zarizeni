package cz.vse.restaurace.model;

import java.util.*;

/**
 * Třída Order představuje logiku objednávek v aplikaci.
 * Každá objednávka má svoje číslo, datum kdy byla vytvořena a stůl,ke kterému je přiřazena.
 * Ke každé objednávce lze poté přidávat neomezený počet jídel a nápojů a také poznámku.
 * Úkolem této třídy je tyto informace o objednávce uchovat.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class Order {

    private int orderID;
    private String date;
    private Table table;

    private List<Food> listOfFood;
    private List<Drink> listOfDrinks;
    private String note;

    /**
     * Konstruktor třídy Order.
     * Objednávce se nejprve nastaví základní parametry(číslo objednávky, datum a stůl).
     * Dále se inicializují datové struktury na přidávání jídel, nápoje a poznámka se nastavý na prázdný textový řetězec.
     *
     * @param orderID Číslo objednávky
     * @param date Datum vytvoření objednávky
     * @param table Stůl, který objednávka upravuje
     */
    public Order(int orderID, String date, Table table) {
        this.orderID = orderID;
        this.date = date;
        this.table = table;

        listOfFood = new ArrayList<>();
        listOfDrinks = new ArrayList<>();
        note = "";
    }

    /**
     * Metoda getOrderID vrácí číslo objednávky jako číslo.
     *
     * @return orderID Číslo objednávky
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Metoda getDate vrací datum vytvoření objednávky jako textový řetězec.
     *
     * @return date Datum vytvoření objednávky
     */
    public String getDate() {
        return date;
    }

    /**
     * Metoda getTable vrací stůl, který objednávka upravuje jako instanci třídy Table.
     *
     * @return table Stůl, který objednávky upravuje
     */
    public Table getTable() {
        return table;
    }

    /**
     * Metoda getNote vrací poznámku, který byla k objednávce nastavena jako textový řetězec.
     *
     * @return note Poznámka objednávky
     */
    public String getNote() {
        return note;
    }

    /**
     * Metoda editNote nastaví text poznámky na hodnotu uvedenou v parametru.
     *
     * @param note Nový text poznámky
     */
    public void editNote(String note) {
        this.note = note;
    }

    /**
     * Metoda addDrink přidá nápoj k objednávce, resp. do listu listOfDrinks.
     * Po kontrole, jestli hodnota parametru není null, se vytvoří nová instance třídy Drink a přidá se do listu.
     * Vytváření nové instance třídy Drink nám zajištujě, že lze mít v objednávce více stejných nápojů (například 2 x Pivo)
     *
     * @param drink Nápoj, který chceme k objednávce přidat
     * @return Skutečnost, jestli byl nápoj přidán
     */
    public boolean addDrink(Drink drink) {
        if (drink != null) {
            return listOfDrinks.add(new Drink(drink.getName(), drink.getPrice()));
        }
        return false;
    }

    /**
     * Metoda addFood přidá jídlo k objednávce, resp. do listu listOfFood.
     * Po kontrole, jestli hodnota parametru není null, se vytvoří nová instance třídy Food a přidá se do listu.
     * Vytváření nové instance zde má stejný účel jako u metody addDrink.
     *
     * @param food Jídlo, které chceme k objednávce přidat
     * @return Skutečnost, jestli bylo jídlo přídáno
     */
    public boolean addFood(Food food) {
        if (food != null) {
            return listOfFood.add(new Food(food.getName(),food.getPrice()));
        }
        return false;
    }

    /**
     * Metoda getOrderInfo vrací veškeré info o objednávce jako textový řetězec.
     * Řetězec, který se posíla na výstup metody se inicializuje s povinnými vlastnostmi třídy Order.
     * Dále se řetězec upravuje podle toho jáká je aktuální poznámka a kolik jídel a nápojů je již objednáno.
     *
     * @return ret Složený textový řetězec, který obsahuje všechny vlastnosti a informace o objednávce
     */
    public String getOrderInfo() {
            String ret = "Číslo objednávky: " + getOrderID() + "\nDatum objednávky: " + getDate() + "\nStůl: " + getTable().getTableNumber();

            if (!listOfFood.isEmpty()) {
                ret += "\nJídlo: ";

                for(Food f : listOfFood) {
                    ret += "\n - " + f.getName();
                }
            }

            if (!listOfDrinks.isEmpty()) {
                ret += "\nNápoje: ";

                for (Drink d : listOfDrinks) {
                    ret += "\n - " + d.getName();
                }
            }

            if ("" != getNote()) {
                ret += "\nPoznámka: " + getNote();
            }

            if (calculateTotalPrice() > 0) {
                ret += "\nCena: " + calculateTotalPrice();
            }
            return ret;
    }

    /**
     * Metoda calculateTotalPrice vypočítá celkovou cenu všech jídel a nápojů a tutu hodnotu vrátí jako číslo.
     *
     * @return totalprice Celková cena objedávky
     */
    public Integer calculateTotalPrice() {
        int totalPrice = 0;
        if (!listOfDrinks.isEmpty()) {
            for (Drink d : listOfDrinks) {
                totalPrice += d.getPrice();
            }
        }
        if (!listOfFood.isEmpty()) {
            for (Food f : listOfFood) {
                totalPrice += f.getPrice();
            }
        }
        return totalPrice;
    }

    /**
     * Metoda getFood vrací kolekci jídla, které se nachází v aktuální objednávce.
     *
     * @return Jídlo v objednávce
     */
    public Collection<Food> getFood() {
        return Collections.unmodifiableCollection(listOfFood);
    }

    /**
     * Metoda getDrinks vrací kolekci nápojů, které se nachází v aktuální objednávce.
     *
     * @return Nápoje v objednávce
     */
    public Collection<Drink> getDrinks() {
        return Collections.unmodifiableCollection(listOfDrinks);
    }
}
