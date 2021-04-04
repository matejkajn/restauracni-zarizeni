package cz.vse.restaurace.Controllers;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Collection;

/**
 * Třída slouží pro ovládání hlavního okna aplikace pro řízení objednávek,
 * zobrazení historie, přidání sortimentu nebo odhlášení.
 * Spolupracuje s fxml souborem scene_main.fxml.
 *
 * @author Jonáš Matějka
 * @author David Poslušný
 * @version ZS 2020
 */
public class OrderController {

    public Button btn_confirmOrder;
    public Label order_text;
    public TextArea order_info;
    public ComboBox food_box;
    public ComboBox drinks_box;
    public Button btn_addFood;
    public Button btn_addDrink;
    public Button btn_addNote;
    public TextArea note_field;

    private App app;
    private Order o;

   /**
     * Metoda init slouží pro načítání aktuálního stavu aplikace do této
     * třídy, abychom mohli pracovat s aktuálními informacemi aplikace.
     *
     * @param app instance třídy App, prezentující aplikaci
     */
    public void init(App app) {
        this.app = app;
        this.o = app.getCurrentOrder();
        showOrderID();
        confirmOrder();
        update();
        addFood();
        addDrinks();
        addNote();
    }

    /**
     * Metoda slouží pro potvrzení úpravy objednávky v tom smyslu, že
     * se zavře oknu, ve kterém se objednávka upravuje.
     */
    public void confirmOrder() {
        btn_confirmOrder.setOnMouseClicked(event -> {
            if(o.getDrinks().isEmpty() && o.getFood().isEmpty()) {
                AlertWindow.displayAlert("Prázdná objednávka", "Objednávka nesmí být prázdná!");
            }
            else {
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });
    }

    /**
     * Metoda update volá ostatní aktualizační metody a vyplňuje textovou oblast order_info s aktuálními informacemi o objednávce.
     */
    public void update() {
            order_info.setText(o.getOrderInfo());
            updateFoodAndDrinks();
    }

    /**
     * Metoda showOrderID přidá k nadpisu objednávky order_text aktuální číslo objednávky.
     */
    public void showOrderID() {
        order_text.setText(order_text.getText() + o.getOrderID());
    }

    /**
     * Metoda updateFoodAndDrinks se stará o aktualizaci comboBoxů, které se využívají pro s jídlem a nápoji.
     * Do comboBoxů se načítají data z třídy App, kde je uložena aktuální databáze jídel a nápojů.
     */
    public void updateFoodAndDrinks() {
        Collection<Food> listOfFood = app.getFood();
        food_box.getItems().clear();

        Collection<Drink> listOfDrinks = app.getDrinks();
        drinks_box.getItems().clear();

        for (Food f : listOfFood) {
            food_box.getItems().add(f.getName());
        }

        for (Drink d : listOfDrinks) {
            drinks_box.getItems().add(d.getName());
        }
    }

    /**
     * Metoda addFood slouží pro přidání jídla do aktuální upravované objednávky.
     * Po stisknutí tlačítka se zkontroluje, jestli bylo v comboBoxu něco vybráno.
     * V případě, že ano, tak se jídlo přidá do datové struktury a zavolá se metoda update.
     */
    public void addFood() {
        btn_addFood.setOnMouseClicked(event -> {
            String s  = String.valueOf(food_box.getValue());
            Food food = app.getFoodByName(s);

            if (food != null) {
                o.addFood(food);
                update();
            }
            else {
                AlertWindow.displayAlert("Výběr jídla", "Vyberte prosím jídlo, které chce přidat.");
            }
        });
    }

    /**
     * Metoda addDrink slouží pro přidání nápojů do aktuální upravované objednávky.
     * Po stisknutí tlačítka se zkontroluje, jestli bylo v comboBoxu něco vybráno.
     * V případě, že ano, tak se nápoj přidá do datové struktury a zavolá se metoda update.
     */
    public void addDrinks() {
        btn_addDrink.setOnMouseClicked(event -> {
            String s = String.valueOf(drinks_box.getValue());
            Drink drink = app.getDrinkByName(s);

            if (drink != null) {
                o.addDrink(drink);
                update();
            }
            else {
                AlertWindow.displayAlert("Výběr nápojů","Vyberte prosím nápoj, které chcte přidat.");
            }
        });
    }

    /**
     * Metoda addNote se stará o úpravu poznámky.
     * Po stisknutí tlačítka se zkontroluje, jesli se v textové oblasti nachází nějaký text.
     * Pokud ano, tak se zavolá metoda ze třídy Order, která nám vymění text poznámky za text uvedený v textové oblasti.
     */
    public void addNote() {
        btn_addNote.setOnMouseClicked(event -> {
            String s = note_field.getText();

            if (s != null && !s.isEmpty()) {
                o.editNote(s);
                update();
            }
            else {
                AlertWindow.displayAlert("Přidání poznámky","Vyplňte prosím text poznámky.");
            }
        });
    }
}
