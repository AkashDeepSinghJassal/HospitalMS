package hospital.ui.register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class RegisterController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	void login(ActionEvent event) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
			anchorPane.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

    @FXML
    void register(ActionEvent event) {
    	System.out.println("New Account is Created!!");
//    	try {
//    		AnchorPane root = FXMLLoader.load(getClass().getResource("../register/Register.fxml"));
//    		anchorPane.getScene().setRoot(root);
//    	} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

}
