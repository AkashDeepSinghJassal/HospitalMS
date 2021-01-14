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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	private AnchorPane homeView;
	@FXML
	private AnchorPane patientView;
	@FXML
	private AnchorPane doctorView;
	@FXML
	private AnchorPane appointmentView;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private AnchorPane loader;
	@FXML
	private Circle c1;
	@FXML
	private Circle c2;
	@FXML
	private Circle c3;

	@FXML
	void keyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			triggerHamburger();
		}
	}

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
			AnchorPane welcomeBackground = new AnchorPane();
			AnchorPane.setTopAnchor(welcomeBackground, 0.0);
			AnchorPane.setRightAnchor(welcomeBackground, 0.0);
			AnchorPane.setBottomAnchor(welcomeBackground, 0.0);
			AnchorPane.setLeftAnchor(welcomeBackground, 0.0);
			welcomeBackground.setStyle("-fx-background-color: #FFF");
			anchorPane.getChildren().add(welcomeBackground);
			anchorPane.getChildren().add(newRoot);

			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), newRoot);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.interpolatorProperty().set(Interpolator.EASE_OUT);
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), newRoot);
			FadeTransition fadeOutWelcome = new FadeTransition(Duration.seconds(1), welcomeBackground);
			fadeOut.setFromValue(1);
			fadeOutWelcome.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOutWelcome.setToValue(0);
			fadeOut.setCycleCount(1);
			fadeOutWelcome.setCycleCount(1);
			fadeOut.setDelay(Duration.seconds(1));
			fadeOutWelcome.setDelay(Duration.seconds(1.5));
			fadeIn.interpolatorProperty().set(Interpolator.EASE_OUT);
			fadeIn.play();

			fadeIn.setOnFinished((e) -> {
				initializeViews();
				fadeOut.play();
				fadeOutWelcome.play();
			});
			fadeOut.setOnFinished((e) -> {
				anchorPane.getChildren().remove(welcomeBackground);
				anchorPane.getChildren().remove(newRoot);
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

	private void initializeViews() {
		AnchorPane patientRoot;
		try {
			patientRoot = Main.patientViewAnchorPane;
			patientView.getChildren().setAll(patientRoot);
			AnchorPane.setTopAnchor(patientRoot, 0.0);
			AnchorPane.setRightAnchor(patientRoot, 0.0);
			AnchorPane.setBottomAnchor(patientRoot, 0.0);
			AnchorPane.setLeftAnchor(patientRoot, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AnchorPane doctorRoot;
		try {
			doctorRoot = Main.doctorViewAnchorPane;
			doctorView.getChildren().setAll(doctorRoot);
			AnchorPane.setTopAnchor(doctorRoot, 0.0);
			AnchorPane.setRightAnchor(doctorRoot, 0.0);
			AnchorPane.setBottomAnchor(doctorRoot, 0.0);
			AnchorPane.setLeftAnchor(doctorRoot, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AnchorPane appointmentRoot;
		try {
			appointmentRoot = Main.appointmentViewAnchorPane;
			appointmentView.getChildren().setAll(appointmentRoot);
			AnchorPane.setTopAnchor(appointmentRoot, 0.0);
			AnchorPane.setRightAnchor(appointmentRoot, 0.0);
			AnchorPane.setBottomAnchor(appointmentRoot, 0.0);
			AnchorPane.setLeftAnchor(appointmentRoot, 0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showHome() {
		homeView.toFront();
	}

	public void showPatientView() {
		patientView.toFront();
	}

	public void showDoctorView() {
		doctorView.toFront();
	}

	public void showAppointmentView() {
		appointmentView.toFront();
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
