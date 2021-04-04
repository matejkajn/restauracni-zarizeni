package cz.vse.restaurace.Controllers;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Třída slouží pro ovládání okna pro přihlášení do aplikace a
 * také samotné přihlášení do aplikace provádí.
 * Spolupracuje s fxml souborem scene_login.fxml.
 *
 * @author Jonáš Matějka
 * @version ZS 2020
 */
public class LoginController {

    public Button btnOpenRegistration;
    public Button btnLogin;
    public TextField textUserName;
    public PasswordField textPassword;

    private App app;

    /**
     * Metoda init slouží pro načítání aktuálního stavu aplikace do této
     * třídy, abychom mohli pracovat s aktuálními informacemi aplikace.
     *
     * @param app instance třídy App, prezentující aplikaci
     */
    public void init(App app) {
        this.app = app;
        openRegister();
        openMain();
    }

    /**
     * Tato metoda slouží pro otevření okna pro registraci. Metoda je přiřazuje tlačítku Registrace vytvoření
     * nového okna a načtení scene_register.fxml souboru. Metoda také přiřazuje oknu ikonu a posílá do třídy
     * RegisterController aktuální stav aplikace v podobě
     * instace třídy app.
     */
    public void openRegister() {
        btnOpenRegistration.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_register.fxml");
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

            RegisterController registerController = loader.getController();
            registerController.init(app);
            stage.showAndWait();
        });
    }

    /**
     * Metoda slouží pro otevření hlavního okna aplikace načteného z scene_main.fxml souboru.
     * Metoda také kontroluje, zda se šlo uživateli úspěšně přihlásit. Pokud ne tak
     * místo hlavního okna se otevře chybová hláška.
     */
    public void openMain() {
        btnLogin.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_main.fxml");
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
            if(login()) {
                MainController mainController = loader.getController();
                mainController.init(app);
                app.loadOrderHistory();
                ((Node)(event.getSource())).getScene().getWindow().hide();
                stage.show();
            } else {
                AlertWindow.displayAlert("Přihlášení", "Zadali jste špatné Jméno/Heslo.");
            }

        });
    }

    /**
     * Metoda sloužící pro přihlášení uživatele do aplikace. Na základě přihlašovacích údajů
     * metoda zjistí, zda je tento uživatel zaregistrovaný a pokud ano, tak uživatele přihlásí
     * a vrátí hodnotu true.
     *
     * @return skutečnost, zda se přihlášení povedlo, či nikoli
     */
    public boolean login() {
        boolean ret = false;
        User user = new User(textUserName.getText(), textPassword.getText());

        if(app.collectionContainsUser(user)) {
            ret = true;
            app.setCurrentUser(user);
        }
        return ret;
    }
}
