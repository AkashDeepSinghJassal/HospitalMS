package hospital.ui.view.patient;

import hospital.model.GENDER;
import hospital.model.Patient;
import hospital.ui.main.Main;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PatientSelectorController {
	private ObservableList<Patient> observableList = null;
	private FilteredList<Patient> filteredList = null;
	private SortedList<Patient> sortedList = null;
	private Stage stage = null;
	private Patient patient = null;
	private Boolean isOkClicked = false;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Boolean getIsOkClicked() {
		return isOkClicked;
	}

	public void setIsOkClicked(Boolean isOkClicked) {
		this.isOkClicked = isOkClicked;
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
	private TextField filterTF;
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
		observableList = Main.patientOverviewController.getObservableList();
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
					select.fire();
				}
			});
			return row;
		});

		/* Set focus on TextField on opening */
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				setFocus();
			}
		});

	}

	@FXML
	void cancelModal(ActionEvent event) {
		setIsOkClicked(false);
		getStage().close();
	}

	@FXML
	void selectPatient(ActionEvent event) {
		if (tableView.getSelectionModel().getSelectedItem() != null) {
			setPatient(tableView.getSelectionModel().getSelectedItem());
			setIsOkClicked(true);
			getStage().close();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner((Stage) ((Node) event.getTarget()).getScene().getWindow());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Appointment Selected");
			alert.setContentText("Please select a Appointment in the table.");
			alert.showAndWait();
		}
	}

	/**
	 * Set focus {@link #filterTF} on opening.
	 */
	public void setFocus() {
		filterTF.requestFocus();
	}
}
