package hospital.ui.view.appointment;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppointmentDialogController {
	@FXML
	private Label headLbl;

	@FXML
	private JFXTextField patientID;

	@FXML
	private JFXTextField doctorID;

	@FXML
	private JFXTextField date;

	@FXML
	void closeWindow(ActionEvent event) {

	}

	@FXML
	void handleOK(ActionEvent event) {

	}
}
