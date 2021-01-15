package hospital.ui.view.patient;

import java.io.IOException;

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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientOverviewController {

	private ObservableList<Patient> observableList = FXCollections.observableArrayList();
	private FilteredList<Patient> filteredList = null;
	private SortedList<Patient> sortedList = null;
	public AnchorPane overlay = null;

	public ObservableList<Patient> getObservableList() {
		return this.observableList;
	}

	public void setObservableList(ObservableList<Patient> observableList) {
		this.observableList = observableList;
	}

	public FilteredList<Patient> getFilteredList() {
		return this.filteredList;
	}

	public void setFilteredList(FilteredList<Patient> filteredList) {
		this.filteredList = filteredList;
	}

	public SortedList<Patient> getSortedList() {
		return this.sortedList;
	}

	public void setSortedList(SortedList<Patient> sortedList) {
		this.sortedList = sortedList;
	}

	public PatientOverviewController() {
		observableList.addAll(PatientSql.getPatients());
	}

	@FXML
	private TableView<Patient> tableView;
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
	private TextField filterTF;
	@FXML
	private Button edit;
	@FXML
	private Button select;

	@FXML
	private void initialize() {
		patientIDTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("id"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("name"));
		ageTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleIntegerProperty>("age"));
		genderTableColumn
				.setCellValueFactory(new PropertyValueFactory<Patient, SimpleObjectProperty<GENDER>>("gender"));
		contactTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("contact"));
		addressTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, SimpleStringProperty>("address"));

		/* Filter table */
		filteredList = new FilteredList<Patient>(observableList, p -> true);
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(patient -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				String filter = newValue.toLowerCase();
				if (patient.getName().toLowerCase().contains(filter))
					return true;
				if (patient.getId().toLowerCase().contains(filter))
					return true;
				return false;
			});
		});
		sortedList = new SortedList<Patient>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);

		// show empty in personal details
		showPatientDetails(null);
		tableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));

		// Clear Selection On Opening
		tableView.getSelectionModel().clearSelection();

		// Clear Selection when clicking on empty rows
		ObjectProperty<TableRow<Patient>> lastSelectedRow = new SimpleObjectProperty<>();
		tableView.setRowFactory(tableView -> {
			TableRow<Patient> row = new TableRow<Patient>();
			row.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					lastSelectedRow.set(row);
				}
			});
			return row;
		});
		tableView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (lastSelectedRow.get() != null) {
					Bounds boundsOfSelectedRow = lastSelectedRow.get()
							.localToScene(lastSelectedRow.get().getLayoutBounds());
					if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
						tableView.getSelectionModel().clearSelection();
					}
				}
			}
		});

		// Edit on double click
		tableView.setRowFactory(e -> {
			TableRow<Patient> row = new TableRow<Patient>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					edit.fire();
				}
			});
			return row;
		});
	}

	@FXML
	void handleAddPatient(ActionEvent event) {
		Patient patient = new Patient();
		if (showPatientDialog(patient, "Add Patient")) {
			if (PatientSql.addPatient(patient) == 1) {
				patient.setId(PatientSql.getIdOfLastPatient());
				observableList.add(patient);
				showPatientDetails(patient);
			}
		}
	}

	@FXML
	void handleDeletePatient(ActionEvent event) {
		Patient deletedPatient = tableView.getSelectionModel().getSelectedItem();
		if (deletedPatient != null) {
			if (PatientSql.removePatient(deletedPatient.getId()) == 1) {
				int visibleIndex = tableView.getSelectionModel().getSelectedIndex();
				int sourceIndex = sortedList.getSourceIndexFor(observableList, visibleIndex);
				observableList.remove(sourceIndex);
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
		Patient p = tableView.getSelectionModel().getSelectedItem();
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
			scene.setFill(Color.TRANSPARENT);
			dialogStage.setScene(scene);

			// Set the patient into the controller.
			PatientDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPatient(patient);
			controller.setHeader(header);

			overlay.toFront();
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			overlay.toBack();

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

	/**
	 * Set focus {@link #filterTF} on opening.
	 */
	public void setFocus() {
		filterTF.requestFocus();
	}
}
