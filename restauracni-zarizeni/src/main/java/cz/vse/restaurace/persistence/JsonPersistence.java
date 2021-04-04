package cz.vse.restaurace.persistence;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.vse.restaurace.model.*;

/**
 * Tato třída se využívá pro zápis a čtení ze souborů.
 * V aplikaci používáme JSON strukturu souborů a pro zápis
 * a čtení puoužíváme knihovny GSON.
 *
 * @author Jonáš Matějka
 * @author David Poslušný
 * @version ZS 2020
 */
public class JsonPersistence {

    private static final String SAVE_FILE_NAME = "usersdata.json";

    private Gson gson = new Gson();

    /**
     * Metoda slouží pro čtení dat ze souboru, který obsahuje přihlašovací údaje registrovaných účtů.
     * Na základě přihlašovacích údajů se vytvoří instance třídy User, které jsou
     * následně uloženy do listu ve tříde App.
     * @return Načtený list zaregistrovaných uživatelů
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public List<User> loadData() throws PersistenceException {
        checkOrCreateFile("usersdata.json");
        try {
            List<String> lines = Files.readAllLines(Paths.get(SAVE_FILE_NAME));
            String jsonRaw = String.join("\n", lines);
            Type listOfUsersType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfUsersType);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda slouží pro čtení dat ze soubori, který obsahuje všechny vyřízené objednávky účtů.
     * Na základě dat ze souboru se vytvoří instance třídy Order, které jsou následně
     * uloženy do listu ve třídě App.
     * @return Načtený list vyřízených objednávek konkrétního účtu
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public List<Order> loadUserData(User user) throws PersistenceException {
        checkOrCreateDirectory("accountData");
        String fileName = "accountData\\" + user.getUserName() + ".json";
        checkOrCreateFile(fileName);
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            String jsonRaw = String.join("\n", lines);
            Type listOfUsersOrdersType = new TypeToken<List<Order>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfUsersOrdersType);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda slouží pro čtení dat ze souboru, který obsahuje databázi jídel.
     * Na zákaldě dat ze souboru se vytvoří list s instacemi třídy Food,
     * které jsou následně načteny do listu ve třídě App.
     * @return Načtený list jídel
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public List<Food> loadFoodData()  throws PersistenceException {
        checkOrCreateDirectory("appData");
        String fileName = "appData\\" + "food.json";
        checkOrCreateFile(fileName);
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            String jsonRaw = String.join("\n", lines);
            Type listOfFood = new TypeToken<List<Food>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfFood);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda slouží pro čtení dat ze souboru, který obsahuje databázi nápojů.
     * Na zákaldě dat ze souboru se vytvoří list s instancemi třídy Drink,
     * které jsou následně načteny do listu ve třídě App.
     * @return Načtený list nápojů
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public List<Drink> loadDrinksData()  throws PersistenceException {
        checkOrCreateDirectory("appData");
        String fileName = "appData\\" + "drinks.json";
        checkOrCreateFile(fileName);
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            String jsonRaw = String.join("\n", lines);
            Type listOfDrinks = new TypeToken<List<Drink>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfDrinks);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda slouží pro čtení dat ze souboru, který obsahuje databázi stolů.
     * Na základě dat ze souboru se vytvoří list s instancemi třídy Table,
     * které jsou následně načteny do listu volných stolů ve třídě App.
     * @return Načtený list stolů
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public List<Table> loadTableData() throws PersistenceException {
        checkOrCreateFile("appData");
        String fileName = "appData\\" + "tables.json";
        checkOrCreateFile(fileName);
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            String jsonRaw = String.join("\n", lines);
            Type listOfTables = new TypeToken<List<Table>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfTables);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda sloužící pro uložení veškerých vyřízených objednávek uživatele do souboru.
     * Metoda Přijme parametr uživatele, který je aktuálně přihlášen a list objednávek,
     * které daný uživatel vyřídil. Na základě jména uživatele se vytvoří stejnojmenný soubor,
     * do kterého se uloží list ze třídy App, který představuje všechny vyřízené objednávky.
     * Pro lepší rozlišení souborů se tyto soubory ukládájí do složky accountData.
     * @param orders List vyřízených objednávek ze třídy App
     * @param user Uživatel, který je momentálně přihlášen do aplikace
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public void saveUserData(List<Order> orders, User user) throws PersistenceException {
        checkOrCreateDirectory("accountData");
        String fileName = "accountData\\" + user.getUserName() + ".json";
        checkOrCreateFile(fileName);
        String json = gson.toJson(orders);
        try {
            Files.write(Paths.get(fileName), json.getBytes());
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda, která slouží pro uložení přihlašovacích údajů do souboru.
     * Tato metoda vytvoří soubor .json, do kterého uloží list zaregistrovaných
     * uživatelů ze třídy App.
     * @param users List uživatelů, kteří se zaregistrovali
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public void saveData(List<User> users) throws PersistenceException {
        checkOrCreateFile("usersdata.json");
        String json = gson.toJson(users);
        try {
            Files.write(Paths.get(SAVE_FILE_NAME), json.getBytes());
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Metoda sloužící pro kontrolu a vytvoření souborů k ukládání dat.
     * Tato metoda přijme parametr fileName, a zkontroluje jestli existuje
     * soubor s tímto jménem, do kterého by uložila data. Pokud soubor
     * s jménem parametru nexistuje, pak jej vytvoří.
     * @param fileName String, který představuje jméno souboru
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public void checkOrCreateFile(String fileName) throws PersistenceException {
        File file = new File(fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new PersistenceException(e);
            }
        }
    }

    /**
     * Metoda podobná metode checkOrCreateFile, akorát tato metoda kontroluje
     * existenci složky. Metoda zkontroluje jestli existuje složka se jménem
     * přijatého parametru a pokud ne tak jej vytvoří.
     * @param directoryName String parametr představující jméno složky
     * @throws PersistenceException vyjímka, pokud nelze najít soubor
     */
    public void checkOrCreateDirectory(String directoryName) throws PersistenceException {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        path += "\\" + directoryName;

        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }
}
