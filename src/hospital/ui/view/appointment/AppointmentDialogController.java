package hospital.ui.view.appointment;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import hospital.model.Appointment;
import hospital.model.Doctor;
import hospital.model.Patient;
import hospital.ui.main.Main;
import hospital.ui.view.doctor.DoctorSelectorController;
import hospital.ui.view.patient.PatientSelectorController;
import hospital.util.DateTimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppointmentDialogController {
	private Appointment appointment;
	private Stage parentStage;
	private Patient patient = null;
	private Doctor doctor = null;
	private boolean okClicked;

	@FXML
	private Label headLbl;
	@FXML
	private JFXTextField date;
	@FXML
	private JFXButton patientButton;
	@FXML
	private JFXButton doctorButton;

	@FXML
	void selectDoctor(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../doctor/DoctorSelector.fxml"));
		DoctorSelectorController controller = new DoctorSelectorController();
		loader.setController(controller);
		AnchorPane aPane = null;
		try {
			aPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT);
		dialogStage.initOwner(parentStage);
		Scene scene = new Scene(aPane);
		scene.setFill(Color.TRANSPARENT);
		controller.setStage(dialogStage);
		dialogStage.setScene(scene);
		dialogStage.setX(800);
		dialogStage.setY(190);
		dialogStage.showAndWait();

		if (controller.getIsOkClicked()) {
			doctor = controller.getDoctor();
			if (doctor != null)
				doctorButton.setText(doctor.getId());
		}
	}

	@FXML
	void selectPatient(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../patient/PatientSelector.fxml"));
		PatientSelectorController controller = new PatientSelectorController();
		loader.setController(controller);
		AnchorPane aPane = null;
		try {
			aPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initStyle(StageStyle.TRANSPARENT);
		dialogStage.initOwner(parentStage);
		Scene scene = new Scene(aPane);
		scene.setFill(Color.TRANSPARENT);
		controller.setStage(dialogStage);
		dialogStage.setScene(scene);
		dialogStage.setX(800);
		dialogStage.setY(190);
		dialogStage.showAndWait();

		if (controller.getIsOkClicked()) {
			patient = controller.getPatient();
			if (patient != null)
				patientButton.setText(patient.getId());
		}
	}

	public void setDialogStage(Stage stage) {
		this.parentStage = stage;
	}

	public void setHeader(String header) {
		headLbl.setText(header);
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
		if (appointment != null && appointment.getId() != null) {
			String patientID = appointment.getPatientID();
			this.patient = Main.patientOverviewController.getPatient(patientID);
			patientButton.setText(this.patient.getId());

			String doctorID = appointment.getDoctorID();
			this.doctor = Main.doctorOverviewController.getDoctor(doctorID);
			doctorButton.setText(this.doctor.getId());

			date.setText(DateTimeUtil.format(appointment.getDate()));
		}
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (patient == null || patient.getId() == null) {
			// Further constraint may be required
			errorMessage += "No valid patient ID!\n";
		}

		if (doctor == null || doctor.getId() == null) {
			// Further constraint may be required
			errorMessage += "No valid doctor ID!\n";
		}

		if (date.getText() == null || date.getText().length() == 0) {
			errorMessage += "No valid date!\n";
			if (!DateTimeUtil.validDate(date.getText())) {
				errorMessage += "Wrong format for date selected";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(parentStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	@FXML
	void closeWindow(ActionEvent event) {
		parentStage.close();
	}

	@FXML
	void handleOK(ActionEvent event) {
		if (isInputValid()) {
			appointment.setPatientID(patientButton.getText());
			appointment.setDoctorID(doctorButton.getText());
			appointment.setDate(DateTimeUtil.parse(date.getText()));
			okClicked = true;
			parentStage.close();
		}
	}
}
