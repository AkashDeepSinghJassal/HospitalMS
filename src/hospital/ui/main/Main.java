package hospital.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static Stage stage = null;
	public static Boolean isWelcomeLoaded = true;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("hospital/ui/css/Main.css");
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			primaryStage.setHeight(1080);
			primaryStage.setWidth(1920);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
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
