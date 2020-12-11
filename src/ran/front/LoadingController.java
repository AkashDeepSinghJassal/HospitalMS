package ran.front;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoadingController implements Initializable {
	@FXML
	private Circle c1;
	@FXML
	private Circle c2;
	@FXML
	private Circle c3;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setRotate(c1, true, 360, 1500);
		setRotate(c2, true, -360, 2500);
		setRotate(c3, true, 360, 3000);
	}

	private void setRotate(Circle circle, boolean reverse, int angle, int duration) {
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration), circle);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.setByAngle(angle);
		rotateTransition.setCycleCount(Timeline.INDEFINITE);
		rotateTransition.play();
	}
}
