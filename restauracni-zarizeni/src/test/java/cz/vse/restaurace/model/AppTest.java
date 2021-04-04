package cz.vse.restaurace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testovací třída pro otestování třídy App.
 *
 * @author Jonáš Matějka
 * @author David Poslušný
 * @version ZS 2020
 */
public class AppTest {

    private App app;

    private User user1, user2, user3, user1_v2,  user1_v3;
    private Table table1, table2, table3;
    private Order order1, order2, order3;


    /**
     * Metoda, která slouží k incializaci proměnných, které jsou použity v testech.
     */
    @Before
    public void setUp()
    {
        user1 = new User("us1", "pw1");
        user1_v2 = new User("us1","pw2");
        user1_v3 = new User("usv3", "pwv3");
        user2 = new User("us2", "pw2");
        user3 = new User("us3", "pw3");

        table1 = new Table(1);
        table2 = new Table(2);
        table3 = new Table(3);

        order1 = new Order(1,"20/01/2021",table1);
        order2 = new Order(2,"20/01/2021",table2);
        order3 = new Order(3,"20/01/2021",table3);
    }

    /**
     * Metoda sloužící pro otestování přidání uživatele do kolekce.
     */
    @Test
    public void testAddUser() {
        app = new App();

        app.addUser(user1);

        assertTrue(app.collectionContainsUser(user1));
        assertTrue(app.collectionContainsUser(user2));
        assertFalse(app.collectionContainsUser(user1_v2));
    }

    /**
     * Metoda, která testuje kontrolu jestli je v aplikaci zaregistrován
     * uživatel s konkrétním jménem.
     */
    @Test
    public void testCollectionContainsUserName() {
        app = new App();

        app.addUser(user1);
        app.addUser(user2);
        app.addUser(user3);

        assertTrue(app.collectionContainsUserName(user1));
        assertTrue(app.collectionContainsUserName(user1_v2));
        assertFalse(app.collectionContainsUserName(user1_v3));
        assertTrue(app.collectionContainsUserName(user3));
    }

    /**
     * Metoda, která testuje kontrolu jestli je v aplikaci zaregistrován
     * konkrétní uživatel.
     */
    @Test
    public void testCollectionContainsUser() {
        app = new App();

        app.addUser(user1);
        app.addUser(user2);
        app.addUser(user3);

        assertTrue(app.collectionContainsUser(user1));
        assertTrue(app.collectionContainsUser(user2));
        assertTrue(app.collectionContainsUser(user3));
        assertFalse(app.collectionContainsUser(user1_v2));
        assertFalse(app.collectionContainsUser(user1_v3));
    }

    /**
     * Metoda testující přidání vyřízené objednávky do listu.
     */
    @Test
    public void testAddFinishedOrder() {
        app = new App();
        app.addUser(user1);
        app.setCurrentUser(user1);
        assertEquals(0,app.getFinishedOrders().size());

        app.addFinishedOrder(order1);
        app.addFinishedOrder(order2);
        app.addFinishedOrder(order3);
        assertEquals(3,app.getFinishedOrders().size());
    }

    /**
     * Metoda testOccupyTable testuje funkčnost metody "occupyTable".
     */
    @Test
    public void testOccupyTable() {
        app = new App();
        app.occupyTable(table1);
        assertEquals(false,app.getAvailableTables().contains(table1));
        assertEquals(true,app.getOccupiedTables().contains(table1));
    }

    /**
     * Metoda testFreeTable testuje funkčnost metody "freeTable".
     */
    @Test
    public void testFreeTable() {
        app = new App();
        app.occupyTable(table1);
        app.freeTable(table1);
        assertEquals(true, app.getAvailableTables().contains(table1));
        assertEquals(false, app.getOccupiedTables().contains(table2));
    }
}
