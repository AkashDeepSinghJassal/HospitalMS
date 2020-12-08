package ran.front;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HomePageController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Text header;
	@FXML
	private Button click;
	@FXML
	private Button click1;
	@FXML
	Button click2;

	// Event Listener on Button[#click].onAction
	@FXML
	public void jump(ActionEvent event) {

	}

	// Event Listener on Button[#click1].onAction
	@FXML
	public void jumppp(ActionEvent event) {

	}

	@FXML
	public void addDoctor(ActionEvent event) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (!HospitalMS.isWelcomeLoaded) {
			loadWelcomePage();
		}
	}

	void loadWelcomePage() {
		HospitalMS.isWelcomeLoaded = true;

		try {
			Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
			anchorPane.getChildren().setAll(root);

			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), root);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			fadeOut.setDelay(Duration.seconds(1));

			fadeIn.play();

			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});

			fadeOut.setOnFinished((e) -> {
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
					anchorPane.getChildren().setAll(parent);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
