package hospital.ui.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ImageView imageView;


	@FXML
	void login(ActionEvent event) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("../homePage/HomePage.fxml"));
			anchorPane.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void addAccount(ActionEvent event) {
    	System.out.println("Add account is clicked!");
    	try {
    		AnchorPane root = FXMLLoader.load(getClass().getResource("../register/Register.fxml"));
    		anchorPane.getScene().setRoot(root);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		primaryStage = Main.stage;
//		imageView.fitWidthProperty().bind(primaryStage.widthProperty());
//		imageView.fitHeightProperty().bind(primaryStage.heightProperty());
	}
}
