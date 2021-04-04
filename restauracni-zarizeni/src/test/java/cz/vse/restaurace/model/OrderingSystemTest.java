package cz.vse.restaurace.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Testovací třída pro komplexní otestování třídy OrderingSystem.
 *
 * @author David Poslušný
 * @version ZS 2020
 */

public class OrderingSystemTest {

    private Order o1, o2, o3, o4;
    private OrderingSystem os;
    private Table t1, t2, t3;

    /**
     * Metoda setUp připravuje instance, které budou potřebné k testování.
     */
    @Before
    public void setUp() {
        t1 = new Table(1);
        t2 = new Table(2);
        t3 = new Table(3);
        o1 = new Order(1,"23/01/2021",t1);
        o2 = new Order(2,"20/01/2021,",t2);
        o3 = new Order(3,"22/01/2021",t3);
        os = new OrderingSystem();
    }

    /**
     * Metoda testAddOrder testuje funkčnost metody "addOrder".
     */
    @Test
    public void testAddOrder() {
        assertTrue(os.addOrder(o1));
        assertTrue(os.addOrder(o2));
        assertTrue(os.addOrder(o3));
        assertFalse(os.addOrder(o4));
    }

    /**
     * Metoda testRemoveOrder testuje funkčnost metody "removeOrder".
     */
    @Test
    public void testRemoveOrder() {
        os.addOrder(o1);
        os.addOrder(o2);
        os.addOrder(o3);

        assertTrue(os.removeOrder(o1));
        assertFalse(os.removeOrder(o1));
        assertTrue(os.removeOrder(o2));
        assertTrue(os.removeOrder(o3));
        assertFalse(os.removeOrder(o4));
    }

    /**
     * Metoda testGetOrderByOrderTable testuje funkčnost metody "getOrderByOrderTable".
     */
    @Test
    public void testGetOrderByOrderTable() {
        os.addOrder(o1);
        os.addOrder(o2);

        assertEquals(o1,os.getOrderByOrderTable(t1));
        assertEquals(o2,os.getOrderByOrderTable(t2));
    }
}
