package cz.vse.restaurace.Controllers;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.*;

/**
 * Třída slouží pro ovládání hlavního okna aplikace pro řízení objednávek,
 * zobrazení historie, přidání sortimentu nebo odhlášení.
 * Spolupracuje s fxml souborem scene_main.fxml.
 *
 * @author Jonáš Matějka
 * @author David Poslušný
 * @author Leon Grytsak
 * @version ZS 2020
 */
public class MainController {
    public ComboBox tables_available;
    public ComboBox tables_occupied;
    public Button btn_createOrder;
    public Button btn_finishOrder;
    public Button btn_editOrder;
    public MenuItem itemTerminate;
    public MenuItem itemHistory;
    public MenuItem itemLogout;
    public MenuItem addMenuItem;
    public MenuBar menuBar;

    private App app;
    private OrderingSystem os;

    /**
     * Metoda init slouží pro načítání aktuálního stavu aplikace do této
     * třídy, abychom mohli pracovat s aktuálními informacemi aplikace.
     *
     * @param app instance třídy App, prezentující aplikaci
     */
    public void init(App app) {
        this.app = app;
        this.os = new OrderingSystem();
        updateTables();
        createOder();
        finishOrder();
        editOrder();
        showHistory();
        closeApp();
        logout();
        addMenuItems();
    }

    /**
     * Metoda updateTables se stará o aktualizaci comboBoxů, které se využívají pro práci se stoly.
     * Do comboBoxů se načítají data z třídy App, kde jsou uloženy aktuální obsazené a volné stoly.
     */
    public void updateTables() {
        Collection<Table> availableTables = app.getAvailableTables();
        Collection<Table> occupiedTables = app.getOccupiedTables();
        tables_available.getItems().clear();
        tables_occupied.getItems().clear();

        for (Table t : availableTables) {
            tables_available.getItems().add(t.getTableNumber());
        }

        for (Table t : occupiedTables) {
            tables_occupied.getItems().add(t.getTableNumber());
        }
    }

    /**
     * Metoda createOrder slouží pro vytvoření objednávky.
     * Datum spuštění aplikace je datum objednávky, tj. aktuální datum.
     * Číslo objednávky se určí tak, že se vybere jedna hodnota z listu čísel od 1-1000 (čísla jsou zamíchána).
     * Po kliknutí na tlačítko se zkontroluje, jestli hodnota v comboBoxu není null a poté se již vytvoří objednávka.
     * Objednávka se přidá do systému a vybraný stůl se nastaví jako obsazený.
     * Zavolá se také metoda updateTables, aby již nebylo možné vybírat stůl, který je obsazený.
     */
    public void createOder() {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = dateFormat.format(date);

            List<Integer> orderIDs = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                orderIDs.add(i);
            }
            Collections.shuffle(orderIDs);

            btn_createOrder.setOnMouseClicked(event -> {
                Integer tableNumber = getCurrentTableNumber(tables_available);
                Table currentTable = app.getTableByNumber(tableNumber,"available");

                if (currentTable != null) {
                    int orderID = orderIDs.get(1);
                    orderIDs.remove(1);
                    Order order = new Order(orderID, dateString, currentTable);
                    os.addOrder(order);
                    app.occupyTable(currentTable);
                    updateTables();
                }
                else {
                    AlertWindow.displayAlert("Vytváření objednávky","Vyberte prosím volný stůl.");
                }
            });
    }

    /**
     * Metoda finishOrder vyřizuje objednávky.
     * Po kliknutí na tlačítko se opět zkontroluje, jestli byl nějaký obsazený stůl vybrán.
     * V případě, že ano, objednávka se vyřídí. Přídá se tedy do vyřízených objednávek a odstraní se ze systému.
     * Konkrétní stůl se opět uvolní a zavolá se metoda updateTables.
     */
    public void finishOrder() {
            btn_finishOrder.setOnMouseClicked(event -> {
                Integer tableNumber = getCurrentTableNumber(tables_occupied);
                Table currentTable = app.getTableByNumber(tableNumber,"occupied");

                if (currentTable != null) {
                    Order order = os.getOrderByOrderTable(currentTable);
                    app.addFinishedOrder(order);
                    os.removeOrder(order);
                    app.freeTable(currentTable);
                    updateTables();
                }
                else {
                    AlertWindow.displayAlert("Vyřízení objednávky","Vyberte prosím upravovaný stůl.");
                }
            });
        }

    /**
     * Metoda sloužící pro ukončení aplikace
     */
    public void closeApp() {
        itemTerminate.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Metoda slouží pro vytvoření a zobrazení okna pro úpravu objednávky.
     * Metoda ošetřuje, aby v combo boxu byl vybrán stůl s aktivní objednávkou,
     * jinak nelze objednávku upravit.
     * Okno pro úpravu objednávky je vytvářeno pomocí scene_order.fxml souboru.
     */
    public void  editOrder() {
            btn_editOrder.setOnMouseClicked(event -> {
                Integer tableNumber = getCurrentTableNumber(tables_occupied);
                Table currentTable = app.getTableByNumber(tableNumber,"occupied");

                if (currentTable != null) {
                    Order o = os.getOrderByOrderTable(currentTable);
                    app.setCurrentOrder(o);
                    FXMLLoader loader = new FXMLLoader();
                    InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_order.fxml");
                    Parent root = null;
                    try {
                        root = loader.load(stream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("Restaurační zařízení");
                    stage.initModality(Modality.APPLICATION_MODAL);

                    InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("img/icon.png");
                    Image imageIcon = new Image(streamIcon);
                    stage.getIcons().add(imageIcon);

                    OrderController orderController = loader.getController();
                    orderController.init(app);
                    stage.showAndWait();
            } else {
                    AlertWindow.displayAlert("Pozor!","Musí být vybrán stůl pro úpravu objednávky.");
                }
        });
    }

    /**
     * Metoda, která slouží pro otevření okna historie objednávek, která je
     * zobrazována pomocí scene_history.fxml souboru.
     */
    public void  showHistory() {
        itemHistory.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_history.fxml");
            Parent root = null;
            try {
                root = loader.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Restaurační zařízení");
            stage.initModality(Modality.APPLICATION_MODAL);

            InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("img/icon.png");
            Image imageIcon = new Image(streamIcon);
            stage.getIcons().add(imageIcon);

            HistoryController historyController = loader.getController();
            historyController.init(app);
            stage.showAndWait();
        });
    }

    /**
     * Metoda, která slouží pro odhlášení uživatele z aplikace, tak že
     * uživatele přemístí opět na oknu přihlášení.
     * Pro zobrazení okna příhlášení se využije soubor scene_login.fxml.
     */
    public void logout() {
        itemLogout.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_login.fxml");
            Parent root = null;
            try {
                root = loader.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Restaurační zařízení");

            InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("img/icon.png");
            Image imageIcon = new Image(streamIcon);
            stage.getIcons().add(imageIcon);


            AlertWindow.displayAlert("Odhlášení", "Došlo k odhlášení uživatele.\nBudete přesměrováni na přihlašovací obrazovku.");
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.hide();
            LoginController loginController = loader.getController();
            loginController.init(app);
            stage.show();
        });
    }

    /**
     * Metoda slouží k otevření nové scény, určené pro přidávání položek do menu.
     * Metoda inicializuje ovladač fxml scény a přeposílá mu momentální stav aplikace (app).
     */
    public void addMenuItems() {
        addMenuItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_add_menu_item.fxml");
            Parent root = null;
            try {
                root = loader.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Přidání položek do menu");

            InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("img/icon.png");
            Image imageIcon = new Image(streamIcon);
            stage.getIcons().add(imageIcon);

            AddItemController addItemController = loader.getController();
            addItemController.init(app);
            stage.showAndWait();
        });
    }

    /**
     * Metoda getCurrentTableNumber je pomocná metoda, která vrací aktuální vybranou položku v comboBoxu, který byl předán v parametru.
     * V případě comboBoxů, které pracují se stoly, je aktuální vybraná položka číslo daného stolu.
     *
     * @param comboBox comboBox, z kterého chceme získat aktuální hodnotu
     * @return Aktuální vybraná položka z comboBoxu
     */
    private Integer getCurrentTableNumber(ComboBox comboBox) {
        return (Integer) comboBox.getValue();
    }
}