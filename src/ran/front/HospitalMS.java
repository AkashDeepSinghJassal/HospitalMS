package ran.front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HospitalMS extends Application {
	public static Stage stage = null;
	public static Boolean isWelcomeLoaded = false;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("AdminUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
