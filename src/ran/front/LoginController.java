package ran.front;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private Stage primaryStage = null;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ImageView imageView;

	@FXML
	void login(ActionEvent event) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
			anchorPane.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		primaryStage = HospitalMS.stage;
		imageView.fitWidthProperty().bind(primaryStage.widthProperty());
		imageView.fitHeightProperty().bind(primaryStage.heightProperty());
	}
}
