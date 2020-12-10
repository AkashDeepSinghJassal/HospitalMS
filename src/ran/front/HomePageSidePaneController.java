package ran.front;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HomePageSidePaneController {

	@FXML
	VBox vBox;

	@FXML
	void logout(ActionEvent e) {
		HospitalMS.isWelcomeLoaded = false;
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			vBox.getScene().setRoot(root);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void exit(ActionEvent e) {
		System.exit(0);
	}

}
