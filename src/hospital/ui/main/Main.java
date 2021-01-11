package hospital.ui.main;

import java.io.IOException;
import java.sql.Connection;

import hospital.ui.homePage.HomePageController;
import hospital.ui.view.appointment.AppointmentOverviewController;
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
	public static AnchorPane doctorViewAnchorPane;
	public static AnchorPane appointmentViewAnchorPane;
	public static HomePageController homePageController;

	public static Connection conn = null;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Parent root;
		conn = DBUtil.getDBConnection();
		initPatientView();
		initDoctorView();
		initAppointmentView();
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
	 * Loads fxml and controller for appointment view
	 */
	private void initAppointmentView() {
		AppointmentOverviewController appointmentOverviewController;
		FXMLLoader appointmentOverviewFxmlLoader;
		try {
			appointmentOverviewFxmlLoader = new FXMLLoader(
					getClass().getResource("../view/appointment/AppointmentOverview.fxml"));
			appointmentOverviewController = new AppointmentOverviewController();
			appointmentOverviewFxmlLoader.setController(appointmentOverviewController);
			try {
				appointmentViewAnchorPane = appointmentOverviewFxmlLoader.load();
			} catch (Exception e) {
				// System.out.println(e);
				e.printStackTrace();
			}

		} catch (Exception e) {
		}
	}

	/*
	 * Loads fxml and controller for patient view
	 */
	private void initPatientView() {
		PatientOverviewController patientOverviewController;
		FXMLLoader patientOverviewFxmlLoader;
		patientOverviewFxmlLoader = new FXMLLoader(getClass().getResource("../view/patient/PatientOverview.fxml"));
		patientOverviewController = new PatientOverviewController();
		patientOverviewFxmlLoader.setController(patientOverviewController);
		try {
			patientViewAnchorPane = patientOverviewFxmlLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * Loads fxml and controller for doctor view
	 */
	private void initDoctorView() {
		DoctorOverviewController doctorOverviewController;
		FXMLLoader doctorOverviewFxmlLoader;
		doctorOverviewController = new DoctorOverviewController();
		doctorOverviewFxmlLoader = new FXMLLoader(getClass().getResource("../view/doctor/DoctorOverview.fxml"));
		doctorOverviewFxmlLoader.setController(doctorOverviewController);
		try {
			doctorViewAnchorPane = doctorOverviewFxmlLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void stop() {
		DBUtil.closeQuietly();
		System.out.println("Connection closed");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
