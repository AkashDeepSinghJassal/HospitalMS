package hospital.ui.view.doctor;

import hospital.model.Doctor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DoctorDetailsController {

	Doctor doctor;
	@FXML
	private Label IDLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label genderLabel;
	@FXML
	private Label ageLabel;
	@FXML
	private Label specialityLabel;
	@FXML
	private Label contactLabel;
	@FXML
	private Label addressLabel;

	@FXML
	private void initialize() {
		if (doctor != null) {
			// Fill the labels with info from the doctor object.
			nameLabel.setText(doctor.getName());
			addressLabel.setText(doctor.getAddress());
			genderLabel.setText(doctor.getGender().toString());
			ageLabel.setText(Integer.toString(doctor.getAge()));
			contactLabel.setText(doctor.getContact());
			IDLabel.setText(doctor.getId());
			specialityLabel.setText(doctor.getSpeciality());
		} else {
			// doctor is null, remove all the text.
			nameLabel.setText("");
			addressLabel.setText("");
			genderLabel.setText("");
			ageLabel.setText("");
			contactLabel.setText("");
			IDLabel.setText("");
			specialityLabel.setText("");
		}
	}

	public DoctorDetailsController(Doctor doctor) {
		this.doctor = doctor;
	}
}
