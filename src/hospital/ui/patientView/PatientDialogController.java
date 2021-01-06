package hospital.ui.patientView;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hospital.model.GENDER;
import hospital.model.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientDialogController implements Initializable {

	private Patient patient;
	private Stage parentStage;
	private String header;
	private boolean okClicked;
	private 
	@FXML
	Label headLbl;
	@FXML
	TextField name;
	@FXML
	TextField age;
	@FXML
	TextField address;
	@FXML
	TextField contact;
	@FXML
	JFXComboBox<GENDER> gender;
	
	@FXML
	void closeWindow(ActionEvent event) {
		parentStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gender.getItems().add(GENDER.M);
		gender.getItems().add(GENDER.F);
		gender.getItems().add(GENDER.O);
		headLbl.setText(header);
	}


	public void setDialogStage(Stage stage) {
		this.parentStage = stage;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		if(patient != null) {
			name.setText(patient.getName());
			age.setText(String.valueOf(patient.getAge()));
			contact.setText(patient.getContact());
			gender.setValue(patient.getGender());
			address.setText(patient.getAddress());
		}
	}


	public boolean isOkClicked() {
		return okClicked;
	}
    @FXML
    void handleOK(ActionEvent event) {
        if (isInputValid()) {
            patient.setName(name.getText());
            patient.setAge(Integer.parseInt(age.getText()));
            patient.setAddress(address.getText());
            patient.setGender(gender.getValue());
            patient.setContact(contact.getText());
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
                if(temp <= 0 || temp >= 130)
                	errorMessage += "No vaid age!\n";
            } catch (NumberFormatException e) {
                errorMessage += "No valid age (must be an integer)!\n"; 
            }
        }

        if (address.getText() == null || address.getText().length() == 0) {
            errorMessage += "No valid address!\n"; 
        }
        if (contact.getText() == null || contact.getText().length() != 10) {
        	errorMessage += "No valid contact!\n"; 
        }
        if (gender.getValue() == null || gender.getValue().toString().length() == 0) {
        	errorMessage += "Select gender!\n"; 
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
