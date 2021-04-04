package cz.vse.restaurace.Controllers;

import com.google.gson.*;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.App;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Třída slouží jako ovladač pro scénu určenou k přidávání položek do menu v aplikaci.
 * Třída je realizována pomocí dvou hlavních metod, jedna pro přidávání jídla, druhá pro přidávání pití.
 * Metody také využívají třídy AlertWindow pro zobrazování chybové hlášky.
 *
 * @author Leon Grytsak
 * @version ZS 2021
 */
public class AddItemController {
    public Button food;
    public Button drink;

    public TextField foodName;
    public TextField foodPrice;
    public TextField drinkName;
    public TextField drinkPrice;

    public App app;

    /**
     * Metoda přijímá aktuální stav aplikace (app) a volá metodu update.
     *
     * @param app aktuální stav třídy App
     */
    public void init(App app) {
        this.app = app;
        update();
    }

    /**
     * Metoda slouží k zavolání ostatních metod, které realizují funkci přidávání položek do menu
     */
    public void update() {
        addFood();
        addDrink();
    }

    /**
     * Metoda prvně zabrání možnost psát do pole 'cena jídla' jakákoliv písmena a znaky pomocí regulárních výrazů.
     * Dále se nastaví akce, která se stane po kliknutí na tlačítko 'Přidat'.
     * Metoda nejprve vloží obsah obou textových polí do proměnných a dále načte veškerý obsah json souboru s jídlem do java třídy 'Objekt'.
     * Ta se překonvertuje do java třídy 'JsonArray', do které se následně přidá další položka tvořená atributy name a price a jejich parametry
     * zadané v textových polích.
     * Celý obsah JsonArray se následně zapíše zpět do souboru, vymažou se textová pole a aktualizuje se seznam položek jídla v aplikaci
     * pomocí opětovného načtení.
     * Uživatel musí vyplnit obě pole, pokud se tak nestane vyskočí chybová hláška o špatném zadání pomocí AlertWindow.
     */
    private void addFood() {
        foodPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                foodPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        food.setOnMouseClicked(event -> {
            if(!foodName.getText().trim().isEmpty() && !foodPrice.getText().trim().isEmpty()) {
            String fName = foodName.getText();

            Integer fPrice = Integer.parseInt(foodPrice.getText());

            String fileName = "appData\\food.json";

            FileReader reader = null;
            try {
                reader = new FileReader(fileName);
                Object obj = JsonParser.parseReader(reader);

                JsonArray jArray = (JsonArray) obj;

                JsonObject foodDetails = new JsonObject();
                foodDetails.addProperty("name",fName);
                foodDetails.addProperty("price",fPrice);

                jArray.add(foodDetails);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(jArray);

                Files.write(Paths.get(fileName), json.getBytes());

                app.loadAppData();
                foodName.clear();
                foodPrice.clear();
            }  catch (IOException e) {
                e.printStackTrace();
            }} else {
                AlertWindow.displayAlert("Chybné zadání","Vyplntě název i cenu jídla.");
            }
        });
    }

    /**
     * Metoda prvně zabrání možnost psát do pole 'cena drinku' jakákoliv písmena a znaky pomocí regulárních výrazů.
     * Dále se nastaví akce, která se stane po kliknutí na tlačítko 'Přidat'.
     * Metoda nejprve vloží obsah obou textových polí do proměnných a dále načte veškerý obsah json souboru s drinky do java třídy 'Objekt'.
     * Ta se překonvertuje do java třídy 'JsonArray', do které se následně přidá další položka tvořená atributy name a price a jejich parametry
     * zadané v textových polích.
     * Celý obsah JsonArray se následně zapíše zpět do souboru, vymažou se textová pole a aktualizuje se seznam položek drinků v aplikaci
     * pomocí opětovného načtení.
     * Uživatel musí vyplnit obě pole, pokud se tak nestane vyskočí chybová hláška o špatném zadání pomocí AlertWindow.
     */
    private void addDrink() {
        drinkPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                foodPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        drink.setOnMouseClicked(event -> {
            if(!drinkName.getText().trim().isEmpty() && !drinkPrice.getText().trim().isEmpty()) {
                String dName = drinkName.getText();

                Integer dPrice = Integer.parseInt(drinkPrice.getText());

                String fileName = "appData\\drinks.json";

                FileReader reader = null;
                try {
                    reader = new FileReader(fileName);
                    Object obj = JsonParser.parseReader(reader);

                    JsonArray jArray = (JsonArray) obj;

                    JsonObject drinkDetails = new JsonObject();
                    drinkDetails.addProperty("name",dName);
                    drinkDetails.addProperty("price",dPrice);

                    jArray.add(drinkDetails);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String json = gson.toJson(jArray);

                    Files.write(Paths.get(fileName), json.getBytes());

                    app.loadAppData();
                    drinkName.clear();
                    drinkPrice.clear();
                }  catch (IOException e) {
                    e.printStackTrace();
                }} else {
                AlertWindow.displayAlert("Chybné zadání","Vyplntě název i cenu pití.");
            }
        });

    }
}
