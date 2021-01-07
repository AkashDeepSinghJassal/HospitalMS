package hospital.ui.view.patient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hospital.model.GENDER;
import hospital.model.Patient;
import hospital.services.PatientSql;
import hospital.ui.main.Main;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientOverviewController {

	private ObservableList<Patient> patientList = FXCollections.observableArrayList();
	Connection conn = Main.conn;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	public PatientOverviewController() {
		try {
			statement = conn.prepareStatement("select id, name, age, gender, contact, address from patient");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Patient patient = PatientSql.generatePatient(resultSet);
				patientList.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private TableView<Patient> patientTable;

	@FXML
	private TableColumn<Patient, SimpleStringProperty> patientIDTableColumn;

	@FXML
	private TableColumn<Patient, SimpleStringProperty> nameTableColumn;

	@FXML
	private TableColumn<Patient, SimpleIntegerProperty> ageTableColumn;

	@FXML
	private TableColumn<Patient, SimpleObjectProperty<GENDER>> genderTableColumn;

	@FXML
	private TableColumn<Patient, SimpleStringProperty> contactTableColumn;

	@FXML
	private TableColumn<Patient, SimpleStringProperty> addressTableColumn;

	@FXML
	private Label IDLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label ageLabel;

	@FXML
	private Label genderLabel;
	@FXML
	private Label contactLabel;

	@FXML
	private Label addressLabel;

	@FXML
	private void initialize() {
		patientTable.setItems(patientList);
		patientIDTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("id"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("name"));
		ageTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleIntegerProperty>("age"));
		genderTableColumn
				.setCellValueFactory(new PropertyValueFactory<Patient, SimpleObjectProperty<GENDER>>("gender"));
		contactTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("contact"));
		addressTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("address"));

		// show empty in personal details
		showPatientDetails(null);
		patientTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));

		// Clear Selection On Opening
		patientTable.getSelectionModel().clearSelection();

		// Clear Selection when clicking on empty rows
		ObjectProperty<TableRow<Patient>> lastSelectedRow = new SimpleObjectProperty<>();

		patientTable.setRowFactory(tableView -> {
			TableRow<Patient> row = new TableRow<Patient>();

			row.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					lastSelectedRow.set(row);
				}
			});
			return row;
		});

		patientTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (lastSelectedRow.get() != null) {
					Bounds boundsOfSelectedRow = lastSelectedRow.get()
							.localToScene(lastSelectedRow.get().getLayoutBounds());
					if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
						patientTable.getSelectionModel().clearSelection();
					}
				}
			}
		});
	}

	@FXML
	void handleAddPatient(ActionEvent event) {
		Patient patient = new Patient();
		if (showPatientDialog(patient, "Add Patient")) {
			if (PatientSql.addPatient(patient) == 1) {
				patient.setId(PatientSql.getIdOfLastPatient());
				patientList.add(patient);
				showPatientDetails(patient);
			}
		}
	}

	@FXML
	void handleDeletePatient(ActionEvent event) {
		Patient deletedPatient = patientTable.getSelectionModel().getSelectedItem();
		if (deletedPatient != null) {
			if (PatientSql.removePatient(deletedPatient.getId()) == 1) {
				patientTable.getItems().remove(deletedPatient);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Patient Selected");
			alert.setContentText("Please select a patient in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	void handleEditPatient(ActionEvent event) {
		Patient p = patientTable.getSelectionModel().getSelectedItem();
		if (p != null) {
			boolean okClicked = showPatientDialog(p, "Edit Patient");
			if (okClicked) {
				if (PatientSql.updatePatient(p) == 1) {
					showPatientDetails(p);
				}

			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Patient Selected");
			alert.setContentText("Please select a patient in the table.");

			alert.showAndWait();
		}
	}

	public boolean showPatientDialog(Patient patient, String header) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientDialog.fxml"));
			VBox aPane = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle(header);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.initOwner(Main.stage);
			Scene scene = new Scene(aPane);
			dialogStage.setScene(scene);

			// Set the patient into the controller.
			PatientDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPatient(patient);
			controller.setHeader(header);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fills all text fields to show details about the patient. If the specified
	 * patient is null, all text fields are cleared.
	 * 
	 * @param patient the patient or null
	 */
	private void showPatientDetails(Patient patient) {
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
}
