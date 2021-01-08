package hospital.ui.view.doctor;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hospital.model.Doctor;
import hospital.model.GENDER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoctorDialogController implements Initializable {

	private Doctor doctor;
	private Stage parentStage;
	private boolean okClicked;
	@FXML
	Label headLbl;
	@FXML
	private TextField name;
	@FXML
	private TextField age;
	@FXML
	private TextField address;
	@FXML
	private TextField contact;
	@FXML
	private TextField speciality;
	@FXML
	private JFXComboBox<GENDER> gender;

	@FXML
	void closeWindow(ActionEvent event) {
		parentStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gender.getItems().add(GENDER.M);
		gender.getItems().add(GENDER.F);
		gender.getItems().add(GENDER.O);
	}

	public void setDialogStage(Stage stage) {
		this.parentStage = stage;
	}

	public void setHeader(String header) {
		headLbl.setText(header);
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
		if (doctor != null) {
			name.setText(doctor.getName());
			age.setText(String.valueOf(doctor.getAge()));
			contact.setText(doctor.getContact());
			gender.setValue(doctor.getGender());
			address.setText(doctor.getAddress());
			speciality.setText(doctor.getSpeciality());
		}
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	void handleOK(ActionEvent event) {
		if (isInputValid()) {
			doctor.setName(name.getText());
			doctor.setAge(Integer.parseInt(age.getText()));
			doctor.setAddress(address.getText());
			doctor.setGender(gender.getValue());
			doctor.setContact(contact.getText());
			doctor.setSpeciality(speciality.getText());
			okClicked = true;
			parentStage.close();
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (name.getText() == null || name.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}

		if (age.getText() == null || age.getText().length() == 0) {
			errorMessage += "No valid age!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				int temp = Integer.parseInt(age.getText());
				if (temp <= 0 || temp >= 130)
					errorMessage += "No vaid age!\n";
			} catch (NumberFormatException e) {
				errorMessage += "No valid age (must be an integer)!\n";
			}
		}

		if (address.getText() == null || address.getText().length() == 0) {
			errorMessage += "No valid address!\n";
		}
		if (contact.getText() == null || contact.getText().length() < 10) {
			errorMessage += "No valid contact!\n";
		}
		if (gender.getValue() == null || gender.getValue().toString().length() == 0) {
			errorMessage += "Select gender!\n";
		}
		if(speciality.getText() == null || speciality.getText().length() != 0) {
			errorMessage += "No valid speciality";
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

}
