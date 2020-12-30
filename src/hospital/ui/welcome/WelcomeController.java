package hospital.ui.welcome;

import java.net.URL;
import java.util.ResourceBundle;

import hospital.model.User;
import hospital.ui.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeController implements Initializable {

	Stage primaryStage;
	User user;

	@FXML
	ImageView imageView;

	@FXML
	Text fullName;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		primaryStage = Main.stage;
		user = new User();
		user.setFirstName("abc");
		user.setLastName("def");
		imageView.fitWidthProperty().bind(primaryStage.widthProperty());
		imageView.fitHeightProperty().bind(primaryStage.heightProperty());
		fullName.setText("Welcome, " + user.getFullName());
	}

}