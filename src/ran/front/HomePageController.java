package ran.front;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class HomePageController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private HBox loader;
	@FXML
	private Circle c1;
	@FXML
	private Circle c2;
	@FXML
	private Circle c3;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (!HospitalMS.isWelcomeLoaded) {
			loadWelcomePage();
		}

		loadSidePane();
		setHamburger();

		hamburger.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 1, 2, 3, 4, MouseButton.PRIMARY, 5, true, true,
				true, true, true, true, true, true, true, true, null));
	}

	void loadWelcomePage() {
		HospitalMS.isWelcomeLoaded = true;

		try {
			Parent newRoot = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
			anchorPane.getChildren().setAll(newRoot);

			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), newRoot);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.interpolatorProperty().set(Interpolator.EASE_OUT);

			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), newRoot);
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
					AnchorPane oldRoot = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
					anchorPane.getChildren().setAll(oldRoot.getChildren());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSidePane() {
		try {
			VBox sidePane = FXMLLoader.load(getClass().getResource("HomePageSidePane.fxml"));
			drawer.setSidePane(sidePane);

			for (Node node : sidePane.getChildren()) {
				if (node.getAccessibleText() != null) {
					switch (node.getAccessibleText()) {
						case "logout":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								loader.toFront();
								RotateTransition r1 = setRotate(c1, true, 360, 1500);
								r1.setOnFinished((e2) -> {
									logout();
								});
								setRotate(c2, true, -360, 2500);
								setRotate(c3, true, 360, 3000);
							});
							break;
						case "exit":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								exit();
							});
							break;
						default:
							break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setHamburger() {
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

	void logout() {
		HospitalMS.isWelcomeLoaded = false;
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			anchorPane.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void exit() {
		System.exit(0);
	}

	RotateTransition setRotate(Circle circle, boolean reverse, int angle, int duration) {
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration), circle);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.setByAngle(angle);
		rotateTransition.setCycleCount(1);
		rotateTransition.play();
		return rotateTransition;
	}
}
