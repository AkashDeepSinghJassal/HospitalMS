package hospital.ui.view.appointment;

import com.jfoenix.controls.JFXTextField;

import hospital.model.Appointment;
import hospital.util.DateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppointmentDialogController {
	private Appointment appointment;
	private Stage parentStage;
	private boolean okClicked;
	@FXML
	private Label headLbl;

	@FXML
	private JFXTextField patientID;

	@FXML
	private JFXTextField doctorID;

	@FXML
	private JFXTextField date;

	public void setDialogStage(Stage stage) {
		this.parentStage = stage;
	}
	public void setHeader(String header) {
		headLbl.setText(header);
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
		if(appointment != null) {
			patientID.setText(appointment.getPatientID());
			doctorID.setText(appointment.getDoctorID());
			date.setText(DateUtil.format(appointment.getDate()));
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

        if (patientID.getText() == null || patientID.getText().length() == 0) {
        	// Further constraint may be required
            errorMessage += "No valid patient ID!\n"; 
        }

        if (doctorID.getText() == null || doctorID.getText().length() == 0) {
            errorMessage += "No valid doctor ID!\n"; 
        }        
        if(date.getText() == null || date.getText().length() == 0) {
        	errorMessage += "No valid date!\n";
        	if(!DateUtil.validDate(date.getText())) {
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
	        appointment.setPatientID(patientID.getText());
	        appointment.setDoctorID(doctorID.getText());
	        appointment.setDate(DateUtil.parse(date.getText()));
	        okClicked = true;
	        parentStage.close();
	    }
	}
}
