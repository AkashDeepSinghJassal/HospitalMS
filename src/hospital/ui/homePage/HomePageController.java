package hospital.ui.homePage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import hospital.ui.main.Main;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private AnchorPane drawerOverlay;
	@FXML
	private AnchorPane modelView;
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
		if (!Main.isWelcomeLoaded) {
			loadWelcomePage();
		}
		loadSidePane();
		setHamburger();

		drawerOverlay.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			triggerHamburger();
		});

		triggerHamburger();
	}

	void loadWelcomePage() {
		Main.isWelcomeLoaded = true;

		try {
			Parent newRoot = FXMLLoader.load(getClass().getResource("../welcome/Welcome.fxml"));
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
						case "home":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								triggerHamburger();
								showHome();
							});
							break;
						case "patient":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								triggerHamburger();
								showPatientView();
							});
							break;
						case "doctor":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								triggerHamburger();
								showDoctorView();
							});
							break;
						case "appointment":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								triggerHamburger();
								showAppointmentView();
							});
							break;
						case "logout":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								triggerHamburger();
								loader.toFront();
								setRotate(c1, true, 360, 1500).setOnFinished((e1) -> {
									logout();
								});
								setRotate(c2, true, -360, 2500);
								setRotate(c3, true, 360, 3000);
							});
							break;
						case "exit":
							node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
								((Stage) node.getScene().getWindow()).close();
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

	public void showHome() {
		modelView.getChildren().clear();
	}

	public void showPatientView() {
		AnchorPane root;
		try {
			root = Main.patientViewAnchorPane;
			modelView.getChildren().setAll(root);
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void showAppointmentView() {
		AnchorPane root;
		try {
			root = Main.appointmentViewAnchorPane;
			System.out.println(root);
			modelView.getChildren().setAll(root);
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showDoctorView() {
		AnchorPane root;
		try {
			root = Main.doctorViewAnchorPane;
			modelView.getChildren().setAll(root);
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setRightAnchor(root, 0.0);
			AnchorPane.setBottomAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setHamburger() {
		HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			if (drawer.isOpened()) {
				transition.setRate(-1);

				FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), drawerOverlay);
				fadeOut.setFromValue(1);
				fadeOut.setToValue(0);
				fadeOut.setOnFinished((e2) -> {
					AnchorPane.clearConstraints(drawerOverlay);
				});

				transition.play();
				fadeOut.play();
				drawer.close();
				drawer.setDisable(true);
			} else if (drawer.isClosed()) {
				transition.setRate(1);

				FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), drawerOverlay);
				AnchorPane.setTopAnchor(drawerOverlay, 0.0);
				AnchorPane.setRightAnchor(drawerOverlay, 0.0);
				AnchorPane.setBottomAnchor(drawerOverlay, 0.0);
				AnchorPane.setLeftAnchor(drawerOverlay, 0.0);

				fadeIn.setFromValue(0);
				fadeIn.setToValue(1);

				transition.play();
				fadeIn.play();
				drawer.open();
				drawer.setDisable(false);
			}
		});
	}

	void triggerHamburger() {
		hamburger.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 1, 2, 3, 4, MouseButton.PRIMARY, 5, true, true,
				true, true, true, true, true, true, true, true, null));
	}

	void logout() {
		Main.isWelcomeLoaded = false;
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
			anchorPane.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
