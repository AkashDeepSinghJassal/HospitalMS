package hospital.ui.main;

import java.io.IOException;
import java.sql.Connection;

import hospital.ui.homePage.HomePageController;
import hospital.ui.view.doctor.DoctorOverviewController;
import hospital.ui.view.patient.PatientOverviewController;
import hospital.util.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static Stage stage = null;
	public static Boolean isWelcomeLoaded = true;
	public static AnchorPane patientViewAnchorPane;
	public static DoctorOverviewController doctorOverviewController;
	public static HomePageController homePageController;
	
	public static Connection conn = null;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Parent root;
		conn = DBUtil.getDBConnection();
		initPatientView();
		doctorOverviewController = new DoctorOverviewController();
		homePageController = new HomePageController();
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
	/*
	 * Loads fxml and controller for patient view 
	 */
	private void initPatientView(){
		PatientOverviewController patientOverviewController;
		FXMLLoader patientOverviewFxmlLoader;
		patientOverviewFxmlLoader = new FXMLLoader(getClass().getResource("../view/patient/PatientOverview.fxml"));
		patientOverviewController = new PatientOverviewController();
		patientOverviewFxmlLoader.setController(patientOverviewController);
		try {
			patientViewAnchorPane = patientOverviewFxmlLoader.load();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
