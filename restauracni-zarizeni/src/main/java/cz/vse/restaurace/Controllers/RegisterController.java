package cz.vse.restaurace.Controllers;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Třída slouží pro ovládání okna pro registraci uživatele a
 * také samotné registrování zprotředkuje. Třída je zobrazena
 * pomocí fxml souboru scene_register.fxml.
 *
 * @author Jonáš Matějka
 * @version ZS 2020
 */
public class RegisterController {
    public Button btnRegistrate;
    public TextField textUserNameRegister;
    public PasswordField textPasswordRegister;

    private App app;

    /**
     * Metoda init slouží pro načítání aktuálního stavu aplikace do této
     * třídy, abychom mohli pracovat s aktuálními informacemi aplikace.
     *
     * @param app instance třídy App, prezentující aplikaci
     */
    public void init(App app) {
        this.app = app;

        openLogin();
    }

    /**
     * Metoda sloužící pro vrácení uživatele na přihlašovací obrazovku, pokud
     * se uživatel správně zaregistruje. Také schová okno pro registraci.
     */
    public void openLogin() {
        btnRegistrate.setOnMouseClicked(event -> {
                    if(register()) {
                        AlertWindow.displayAlert("Registrace", "Registrace proběhla úspěšně.");
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                });
    }

    /**
     * Metoda, která slouží pro registrování uživatele a ohlídání náležitostí, které jsou potřeba,
     * aby byl hráč zaregistrován. Pokud se hráč zaregistruje správně metoda vrátí hodnotu true,
     * aby došlo k zobrazení přihlašovací obrazovky a zároveň vytvoří uživatetele, kterého
     * přidá do listu registrovaných uživatelů v třídě App.
     * @return skutečnost, zda došlo ke správnému zaregistrování uživatele
     */
    public boolean register() {
            boolean ret = false;
            if(textUserNameRegister.getText().equals("") | textPasswordRegister.getText().equals("")) {
                AlertWindow.displayAlert("Pozor!", "Musíte zadat jméno a heslo účtu!");
                return ret;
            }
            User user = new User(textUserNameRegister.getText(),textPasswordRegister.getText());

            if(app.collectionContainsUserName(user)) {
                AlertWindow.displayAlert("Registrace", "Toto jméno je již obsazené.");
                return ret;
            } else {
                app.addUser(user);
                ret = true;
            }
            return ret;
        }
}
