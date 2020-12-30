package hospital.ui.patientView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PatientDialogController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	@FXML
    void addPatient(ActionEvent event) {
		System.out.println("Add Patient Clicked!!!");
    }

}
