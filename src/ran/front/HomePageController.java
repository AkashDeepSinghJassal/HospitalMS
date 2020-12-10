package ran.front;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomePageController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXDrawer drawer;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (!HospitalMS.isWelcomeLoaded) {
			loadWelcomePage();
		}

		try {
			VBox sidePane = FXMLLoader.load(getClass().getResource("HomePageSidePane.fxml"));
			drawer.setSidePane(sidePane);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.close();
			} else {
				drawer.open();
			}
		});
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
			fadeIn.interpolatorProperty().set(Interpolator.EASE_OUT);

			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			fadeOut.setDelay(Duration.seconds(1));
			fadeIn.interpolatorProperty().set(Interpolator.EASE_IN);

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
