package cz.vse.restaurace.main;

import cz.vse.restaurace.Controllers.LoginController;
import cz.vse.restaurace.model.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Třída, která slouží k spuštění aplikace.
 * @author Jonáš Matějka
 * @author David Poslušný
 * @version ZS2020
 */
public class Start extends Application {

    /**
     * Metoda sloužící pro spuštění programu
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Metoda start, která spouští samotný program.
     * Metoda vytváří také první zobrazující okno, které vytváří pomocí souboru scene_login.fxml.
     * Oknu také přiřazuje ikonu a vytváří instanci třídy App, která prezentuje samotnou logiku aplikace.
     * @param stage Scéna, která se při spuštění programu ukáže
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("Restaurační zařízení");

        FXMLLoader loader = new FXMLLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("scene_login.fxml");
        Parent root = loader.load(stream);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("img/icon.png");
        Image imageIcon = new Image(streamIcon);
        stage.getIcons().add(imageIcon);
        
        App app = new App();
        LoginController loginController = loader.getController();
        loginController.init(app);

        stage.show();
    }
}