package ran.front;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableView;

public class AdminUIController1 {

    @FXML
    private Text header;
    @FXML
    private Button click;
    @FXML
    private Button click1;
   
    // Event Listener on Button[#click].onAction
    @FXML
    public void jump(ActionEvent event) {
        System.out.println("click initiated");
    }

    @FXML
    private void jumppp(ActionEvent event) {
    }
}
