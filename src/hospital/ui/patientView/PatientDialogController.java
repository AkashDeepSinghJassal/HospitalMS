package hospital.ui.patientView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PatientDialogController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void addPatient(ActionEvent event) {
		((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
	}

	@FXML
	void closeWindow(ActionEvent event) {
		((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
	}

}
