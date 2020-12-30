package hospital.ui.patientView;

import java.net.URL;
import java.util.ResourceBundle;

import hospital.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientDialogController implements Initializable {

	public static User user;
	@FXML
	TextField name;
	@FXML
	TextField age;
	@FXML
	TextField address;
	@FXML
	TextField contact;

	@FXML
	void addPatient(ActionEvent event) {
		user = new User(name.getText(), "", age.getText(), address.getText(), contact.getText());
		((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
	}

	@FXML
	void closeWindow(ActionEvent event) {
		((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
