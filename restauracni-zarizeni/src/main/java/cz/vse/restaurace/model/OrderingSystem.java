package cz.vse.restaurace.model;

import java.util.*;

/**
 * Třída OrderingSystem představuje v aplikaci systém, do kterého se ukládají aktuální objednávky, které uživatel upravuje.
 * Objednávka se do systému přidá při jejím vytvoření a odstraní se z něj při jejím vyřízení.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class OrderingSystem {

    Set<Order> orders;

    /**
     * Konstruktor třídy OrderingSystem, ve kterém se inicializuje množina, která v sobě ukládá aktuální objednávky.
     */
    public OrderingSystem() {
        orders = new HashSet<>();
    }

    /**
     * Metoda addOrder po kontrole, jestli parametr není null, přidá objednávku do systému.
     *
     * @param order Objednávka, která se přidá do systému
     * @return Skutečnost, jestli byla objednávka přidána
     */
    public boolean addOrder(Order order) {
        if (order != null) {
            return orders.add(order);
        }
        return false;
    }

    /**
     * Metoda removeOrder po kontrole, jestli parametr není null, odstraní objednávku ze systému.
     *
     * @param order Objednávka, která se vymaže ze systémů
     * @return Skutečnost, jestli byla objednávka odstraněna
     */
    public boolean removeOrder(Order order) {
        if (order != null) {
            return orders.remove(order);
        }
        return false;
    }

    /**
     * Metoda getOrderByTable vrací objednávku podle stolu, který objednávka upravuje.
     *
     * @param table Instance třídy Table
     * @return Objednávka, kterou získáme podle jejího stolu
     */
    public Order getOrderByOrderTable(Table table) {
        if (table == null) {
            return null;
        }
        for (Order o : orders) {
            if (o.getTable().equals(table)) {
                return o;
            }
        }
        return null;
    }
}

