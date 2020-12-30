package hospital.ui.patientView;

import hospital.ui.main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientOverviewController {
	@FXML
	private TableView personTable;
	@FXML
	private TableColumn firstNameColumn;
	@FXML
	private TableColumn lastNameColumn;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label birthdayLabel;

	@FXML
	public void handleEditPerson(ActionEvent event) {

	}

	@FXML
	public void handleDeletePerson(ActionEvent event) {

	}

	@FXML
	public void handleAddPerson(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PatientDialog.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(Main.stage);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
