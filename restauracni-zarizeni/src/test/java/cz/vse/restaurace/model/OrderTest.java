package cz.vse.restaurace.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Testovací třída pro komplexní otestování třídy Order.
 *
 * @author David Poslušný
 * @version ZS 2020
 */


public class OrderTest {

    private Order order;

    private Food f1, f2, f3;
    private Drink d1, d2, d3;
    private Table t1, t2;

    /**
     * Metoda setUp připravuje instance, které budou potřebné k testování.
     */
    @Before
    public void setUp() {
        f1 = new Food("Jídlo 1",100);
        f2 = new Food("Jídlo 2", 50);
        d1 = new Drink("Nápoj 1", 25);
        d2 = new Drink("Nápoj 2", 50);
        t1 = new Table(1);
        t2 = new Table(2);
        order = new Order(1,"23/01/2021",t1);
    }

    /**
     * Metoda testEditNote testuje funkčnost metody "editNote".
     */
    @Test
    public void testEditNote() {
        String note = "Poznámka";
        assertEquals("",order.getNote());
        order.editNote(note);
        assertEquals("Poznámka", order.getNote());
    }

    /**
     * Metoda testAddFood testuje funkčnost metody "addFood".
     */
    @Test
    public void testAddFood() {
        assertTrue(order.addFood(f1));
        assertTrue(order.addFood(f2));
        assertTrue(order.addFood(f1));
        assertTrue(order.addFood(f2));
        assertFalse(order.addFood(f3));
    }

    /**
     * Metoda testAddDrink testuje funkčnost metody "addDrink".
     */
    @Test
    public void testAddDrink() {
        assertTrue(order.addDrink(d1));
        assertTrue(order.addDrink(d2));
        assertTrue(order.addDrink(d1));
        assertTrue(order.addDrink(d2));
        assertFalse(order.addDrink(d3));
    }

    /**
     * Metoda testGetOrderInfo testuje funkčnost metody "getOrderInfo".
     */
    @Test
    public void testGetOrderInfo() {
        String orderID = "Číslo objednávky: 1";
        String date = "Datum objednávky: 23/01/2021";
        String table = "Stůl: 1";
        assertEquals(orderID + "\n" + date + "\n" + table, order.getOrderInfo());

        order.addFood(f1);
        String food = "Jídlo: ";
        String foodItem = " - " + f1.getName();
        String price = "Cena: " + f1.getPrice();
        assertEquals(orderID + "\n" + date + "\n" + table + "\n" + food + "\n" + foodItem + "\n" + price,order.getOrderInfo());
    }

    /**
     * Metoda testCalculateTotalPrice testuje funkčnost metody "calculateTotalPrice".
     */
    @Test
    public void testCalculateTotalPrice() {
        order.addFood(f1);
        order.addFood(f2);
        order.addDrink(d1);
        order.addDrink(d2);

        assertEquals(new Integer(225),order.calculateTotalPrice());
    }

}
