package cz.vse.restaurace;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.lang.invoke.LambdaConversionException;

/**
 * Tato třída slouží pro vyskakovací a upozorňovací okénko,
 * které naše aplikace využívá při některých situací.
 * Například když se hráč špatně přihlásí, vyskočí mu okno s
 * upozorněním, že zadal špatné jméno nebo heslo.
 *
 * @author Jonáš Matějka
 * @version ZS 2020
 */
public class AlertWindow {

    /**
     * Jediná metoda v této třídě, která provádí samotné zobrazení vyskakovacího okna.
     * Do metody se pošle string title, který je titulkem vyskakovacího okna a
     * další parametr message slouží pro napsání zprávy, kterou okno bude obsahovat.
     * @param title String, který bude sloužit jako titulek okna
     * @param message String, který bude sloužit jako zpráva, kterou okno bude ukazovat
     */
    public static void displayAlert(String title, String message) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(320);
        stage.setHeight(150);
        stage.setTitle(title);
        stage.setResizable(false);

        Label lblMessage = new Label();
        lblMessage.setText(message);
        lblMessage.setTextAlignment(TextAlignment.CENTER);
        lblMessage.setFont(Font.font("Segoe UI", 13));
        Button btnClose = new Button("Ok");
        btnClose.setPrefWidth(90);
        btnClose.setPrefHeight(15);
        btnClose.setFont(Font.font("Segoe UI", 13));
        btnClose.setOnAction(event -> { stage.close(); });

        VBox layout = new VBox(20);
        layout.setPrefWidth(220);
        layout.setPrefHeight(110);
        layout.getChildren().addAll(lblMessage, btnClose);
        layout.setAlignment(Pos.CENTER);

        InputStream streamIcon = AlertWindow.class.getClassLoader().getResourceAsStream("img/warning.png");
        Image imageIcon = new Image(streamIcon);
        stage.getIcons().add(imageIcon);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
