package hospital.ui.view.patient;

import hospital.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PatientDetailsController {

	Patient patient;
	@FXML
	private Label IDLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label ageLabel;
	@FXML
	private Label contactLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label genderLabel;

	@FXML
	private void initialize() {
		if (patient != null) {
			// Fill the labels with info from the patient object.
			nameLabel.setText(patient.getName());
			addressLabel.setText(patient.getAddress());
			genderLabel.setText(patient.getGender().toString());
			ageLabel.setText(Integer.toString(patient.getAge()));
			contactLabel.setText(patient.getContact());
			IDLabel.setText(patient.getId());
		} else {
			// patient is null, remove all the text.
			nameLabel.setText("");
			addressLabel.setText("");
			genderLabel.setText("");
			ageLabel.setText("");
			contactLabel.setText("");
			IDLabel.setText("");
		}
	}

	public PatientDetailsController(Patient patient) {
		this.patient = patient;
	}
}
