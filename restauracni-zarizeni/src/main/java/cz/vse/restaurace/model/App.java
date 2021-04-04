package cz.vse.restaurace.model;

import java.util.*;

import cz.vse.restaurace.persistence.JsonPersistence;
import cz.vse.restaurace.persistence.PersistenceException;

/**
 * Třída reprezentující samotnou aplikaci. Uchovává v sobě data, která
 * se mění a jsou využívána v jiných třídách, tudíž jsou nezbytná pro chod
 * aplikace. Instance této třídy se vytvoří při startu a je posílána do
 * dalších tříd, které potřebují přístup ke stavu aplikace.
 *
 * @author Jonáš Matějka
 * @author David Poslušný
 * @version ZS 2020
 */
public class App {

    private List<Food> food;
    private List<Drink> drinks;
    private List<Table> availableTables;
    private List<Table> occupiedTables;

    private Order currentOrder;

    private List<User> users;
    private User currentUser;

    private List<Order> finishedOrders;

    private JsonPersistence jsonPersistence;

    /**
     * Kontruktor třídy.
     */
    public App() {
        this.food = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.availableTables = new ArrayList<>();
        this.occupiedTables = new ArrayList<>();
        this.jsonPersistence = new JsonPersistence();
        this.finishedOrders = new ArrayList<>();
        fillUsersList();
        loadAppData();
    }

    /**
     * Metoda která přidává uživatele do listu zaregistrovaných uživatelů.
     * Její parametr je uživatel, který se do listu přidá, tudíž nově zaregistrovaný.
     * @param user Instance třídy uživatel, která se přidá do listu
     */
    public void addUser(User user) {
        users.add(user);
        try {
            jsonPersistence.saveData(users);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tato metoda kontroluje, zda v listu zaregistrovaných uživatelů je uživatel
     * s konkrétním jménem, který je porovnáván s uživatelem poslaným do parametru
     * této metody.
     * Metoda má zamezit zaregistrování dvou účtů se stejným jménem.
     * @param user Uživatel, kterému kontrolujeme přihlašovací jméno
     * @return Skutečnost, zda list obsahuje uživatele se stejným jménem
     */
    public boolean collectionContainsUserName(User user) {
        boolean ret = false;
        if(users != null) {
            for (User item : users) {
                if (item.getUserName().equals(user.getUserName())) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * Metoda sloužící pro kontrolu zda je uživatel zaregistrovaný a může se přihlásit.
     * Parametr je zadaný uživatel, který je porovnán s jednotlivými zaregistrovanými uživateli.
     * Pokud se shodují, je uživatel přihlášen ve třídě LoginController.
     * @param user Zadaný uživatel
     * @return Skutečnost, zda je uživatel zaregistrovaný
     */
    public boolean collectionContainsUser(User user) {
        boolean ret = false;
        if(users != null) {
            for(User item : users) {
                if ((item.getUserName().equals(user.getUserName())) && (item.getPassword().equals(user.getPassword()))) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * Metoda, která slouží pro naplnění listu zaregistrovaných uživatelů pomocí čtení ze souboru.
     * Metoda na základě údajů v souborů s přihlašovacími údaji vytvoří instance třídy Uživatel
     * pro reprezentaci zaregistrovaných uživatelů.
     */
    public void fillUsersList() {
        try {
            users = jsonPersistence.loadData();
            if(users==null) {
                users = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda obdobná fillUsersList sloužící k naplnění listu vyřízencýh objednávek, které
     * získá ze souboru pomocí třídy JsonPersistence. Metoda naplní list všemi objednávkami,
     * které daný uživatel vyřídil.
     */
    public void loadOrderHistory() {
        try {
            finishedOrders = jsonPersistence.loadUserData(currentUser);
            if(finishedOrders == null) {
                finishedOrders = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda, která vrací odkaz na list vyřízených objednávek.
     * @return Odkaz na list vyřízených objednávek.
     */
    public List<Order> getFinishedOrders() {
        return finishedOrders;
    }

    /**
     * Metoda, která slouží pro přidání vyřízené objednávky do listu
     * vyřízených objednávek.
     * @param order Instance vyřízené objednávky
     */
    public void addFinishedOrder(Order order)
    {
        finishedOrders.add(order);
        try {
            jsonPersistence.saveUserData(finishedOrders, getCurrentUser());
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metoda sloužící k nastavení přihlášeného uživatele.
     * @param user Instance přihlášeného uživatele.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Metoda, která vrátí přihlášeného uživatele.
     * @return Odkaz na instanci třídy přihlášeného uživatele.
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * Metoda loadAppData slouží k načtení veškerých dat s kterými uživatel pracuje (jídlo, nápoje, stoly).
     * Data se načítají ze souboru a v případě, že soubor neexistuji, vytvoří se nový list.
     */
    public void loadAppData() {
        try {
            food = jsonPersistence.loadFoodData();
            drinks = jsonPersistence.loadDrinksData();
            availableTables = jsonPersistence.loadTableData();

            if (food == null) {
                food = new ArrayList<>();
            }
            if (drinks == null) {
                drinks = new ArrayList<>();
            }
            if (availableTables == null) {
                availableTables = new ArrayList<>();
            }

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda getFood vrací aktuální databázi jídel jako kolekci.
     *
     * @return kolekce jídel z databáze
     */
    public Collection<Food> getFood() {
        return Collections.unmodifiableCollection(food);
    }

    /**
     * Metoda getDrinks vrací aktuální databázi nápojů jako kolekci.
     *
     * @return kolekce nápojů z databáze
     */
    public Collection<Drink> getDrinks() {
        return Collections.unmodifiableCollection(drinks);
    }

    /**
     * Metoda getAvailableTables vrací aktuální volné stoly jako kolekci.
     *
     * @return kolekce volných stolů
     */
    public Collection<Table> getAvailableTables() {
        return Collections.unmodifiableCollection(availableTables);
    }

    /**
     * Metoda getOccupiedTables vrací aktuální obsazené stoly jako kolekci.
     *
     * @return kolekce obsazených stolů
     */
    public Collection<Table> getOccupiedTables() {
        return Collections.unmodifiableCollection(occupiedTables);
    }

    /**
     * Metoda occupyTable obsadí stůl, který je zadán jako hodnota parametru,
     * tj. odstraní se z kolekce volných stolů a přidá se do kolekce obsazených stolů.
     *
     * @param table stůl, který chceme obsadit
     */
    public void occupyTable(Table table) {
        occupiedTables.add(table);
        availableTables.remove(table);
    }

    /**
     * Metoda freeTable uvolní stůl, který je zadán jako hodnota parametru,
     * tj. odstraní se z kolekce obsazených stolů a přidá se do kolekce volných stolů.
     *
     * @param table stůl, který chceme uvolnit
     */
    public void freeTable(Table table) {
        occupiedTables.remove(table);
        availableTables.add(table);
    }

    /**
     * Metoda getTableByNumber vrací instanci třídy Table, tj. stůl, podle jeho čísla.
     * Druhý parametr je pouze pomocný, a využívá se k rozlišení volných a obsazených stolů.
     *
     * @param number Číslo stolu
     * @param string pomocný textový řetězec
     * @return Stůl, který má stejné vlastnosti jako parametr
     */
    public Table getTableByNumber(Integer number, String string) {
        if (number != null) {
            if (string.equals("occupied")) {
                for (Table t : occupiedTables) {
                    if (t.getTableNumber() == number) {
                        return t;
                    }
                }
            }
            else if (string.equals("available")) {
                for (Table t : availableTables) {
                    if (t.getTableNumber() == number) {
                        return t;
                    }
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    /**
     * Metoda setCurrentOrder nastaví aktuální objednávku na hodnotu získanou z parametru.
     *
     * @param currentOrder nová aktuální objednávka
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Metoda getCurrentOrder vrací aktuální nabídku se kterou aplikace pracuje, tj. nabídka, kterou uživatel upravuje.
     *
     * @return currentOrder aktuální nabídka
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Metoda getFoodByName vrací jídlo podle jeho jména.
     * V případě, že se zadaný parametr rovná vlastnosti daného jídla, poté se jídlo pošle na výstup.
     *
     * @param string Jméno jídla
     * @return Jídlo, které získáme podle jeho jména
     */
    public Food getFoodByName(String string) {
        if (string != null) {
            for (Food f : food) {
                if (f.getName().equals(string)) {
                    return f;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }

    /**
     * Metoda getDrinkByName vrací nápoj podle jeho jména.
     * V případě, že se zadaný parametr rovná vlastnosti daného nápoje, poté se nápoj pošle na výstup.
     *
     * @param string Jméno nápoje
     * @return Nápoj, který zíksáme podle jeho jména
     */
    public Drink getDrinkByName(String string) {
        if (string != null) {
            for (Drink d : drinks) {
                if (d.getName().equals(string)) {
                    return d;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }
}
