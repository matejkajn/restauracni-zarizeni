package cz.vse.restaurace.Controllers;

import cz.vse.restaurace.AlertWindow;
import cz.vse.restaurace.model.App;
import cz.vse.restaurace.model.Order;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Třída slouží pro ovládání okna historie objednávek
 * Je využita tedy jako kontroler k souboru scene_history.fxml
 *
 * @author Jonáš Matějka
 * @version ZS 2020
 */
public class HistoryController {
    public Button btn_closeHistory;
    public TextArea order_info;

    private App app;

    /**
     * Metoda init slouží pro načítání aktuálního stavu aplikace do této
     * třídy, abychom mohli pracovat s aktuálními informacemi aplikace.
     *
     * @param app instance třídy App, prezentující aplikaci
     */
    public void init(App app) {
        this.app = app;
        showHistory();
        closeHistory();
    }

    /**
     * Metoda která je volána v initu a slouží pro vypsání historie objednávek
     * do textového pole, která je načítáná z třídy App, kde je ukládána ze souborů
     * nebo vyřízením objednávek.
     */
    public void showHistory() {
        String history = new String();
        for(Order item : app.getFinishedOrders()) {
            history += (item.getOrderInfo() + "\n************************************************************************\n");
        }
        order_info.setText(history);
        if(order_info.getText().equals("")) {
            AlertWindow.displayAlert("Prázdná historie.","Uživatel v miulosti nevyřídil žádnou objednávku!");
        }
    }

    /**
     * Metoda volaná v initu, sloužící pro uzavření okna historie objednávek.
     */
    public void closeHistory() {
        btn_closeHistory.setOnMouseClicked(event -> {
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
    }
}
