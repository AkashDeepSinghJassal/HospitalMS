package hospital.ui.view.appointment;

import java.io.IOException;
import java.time.LocalDate;

import hospital.model.Appointment;
import hospital.model.Doctor;
import hospital.model.Patient;
import hospital.services.AppointmentSql;
import hospital.ui.main.Main;
import hospital.util.DateUtil;
import javafx.beans.property.ObjectProperty;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppointmentOverviewController {

	private ObservableList<Appointment> observableList = FXCollections.observableArrayList();
	private FilteredList<Appointment> filteredList = null;
	private SortedList<Appointment> sortedList = null;
	private Patient patient = null;
	private Doctor doctor = null;

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@FXML
	private TableView<Appointment> tableView;
	@FXML
	private TableColumn<Appointment, SimpleStringProperty> appointIDTableColumn;
	@FXML
	private TableColumn<Appointment, SimpleStringProperty> patientIDTableColumn;
	@FXML
	private TableColumn<Appointment, SimpleStringProperty> doctorIDTableColumn;
	@FXML
	private TableColumn<Appointment, SimpleObjectProperty<LocalDate>> dateTableColumn;
	@FXML
	private Label appointIDLbl;
	@FXML
	private Label patientIDLbl;
	@FXML
	private Label dateLbl;
	@FXML
	private Label doctorIDLbl;
	@FXML
	private TextField filterTF;
	@FXML
	private Button edit;

	public AppointmentOverviewController() {
		observableList.addAll(AppointmentSql.getAppointments());
	}

	@FXML
	private void initialize() {
		patientIDTableColumn
				.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("patientID"));
		appointIDTableColumn
				.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("appointID"));
		doctorIDTableColumn
				.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("doctorID"));
		dateTableColumn
				.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleObjectProperty<LocalDate>>("date"));

		/* Filter table */
		filteredList = new FilteredList<Appointment>(observableList, p -> true);
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(appointment -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				String filter = newValue.toLowerCase();
				if (appointment.getID().toLowerCase().contains(filter))
					return true;
				if (appointment.getDoctorID().toLowerCase().contains(filter))
					return true;
				if (appointment.getPatientID().toLowerCase().contains(filter))
					return true;
				return false;
			});
		});
		sortedList = new SortedList<Appointment>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);

		// show empty in personal details
		showAppointmentDetails(null);
		tableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showAppointmentDetails(newValue));

		// Clear Selection On Opening
		tableView.getSelectionModel().clearSelection();

		// Clear Selection when clicking on empty rows
		ObjectProperty<TableRow<Appointment>> lastSelectedRow = new SimpleObjectProperty<>();
		tableView.setRowFactory(tableView -> {
			TableRow<Appointment> row = new TableRow<Appointment>();
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
			TableRow<Appointment> row = new TableRow<Appointment>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					edit.fire();
				}
			});
			return row;
		});
	}

	/**
	 * Fills all text fields to show details about the appointment. If the specified
	 * appointment is null, all text fields are cleared.
	 * 
	 * @param Appointment the appointment or null
	 */
	private void showAppointmentDetails(Appointment appointment) {
		if (appointment != null) {
			// Fill the labels with info from the appointment object.
			patientIDLbl.setText(appointment.getPatientID());
			appointIDLbl.setText(appointment.getID());
			doctorIDLbl.setText(appointment.getDoctorID());
			dateLbl.setText(DateUtil.format(appointment.getDate()));

		} else {
			// appointment is null, remove all the text.
			patientIDLbl.setText("");
			appointIDLbl.setText("");
			doctorIDLbl.setText("");
			dateLbl.setText("");
		}
	}

	public boolean showAppointmentDialog(Appointment appointment, String header) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentDialog.fxml"));
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

			// Set the appointment into the controller.
			AppointmentDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setAppointment(appointment);
			controller.setHeader(header);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleEdit(ActionEvent event) {
		Appointment appointment = tableView.getSelectionModel().getSelectedItem();
		if (appointment != null) {
			boolean okClicked = showAppointmentDialog(appointment, "Edit Appointment");
			if (okClicked) {
				if (AppointmentSql.updateAppointment(appointment) > 0) {
					showAppointmentDetails(appointment);
				}

			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Appointment Selected");
			alert.setContentText("Please select a Appointment in the table.");

			alert.showAndWait();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleDelete(ActionEvent event) {
		Appointment deletedAppointment = tableView.getSelectionModel().getSelectedItem();
		if (deletedAppointment != null) {
			if (AppointmentSql.removeAppointment(deletedAppointment) > 0) {
				int visibleIndex = tableView.getSelectionModel().getSelectedIndex();
				int sourceIndex = sortedList.getSourceIndexFor(observableList, visibleIndex);
				observableList.remove(sourceIndex);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Appointment Selected");
			alert.setContentText("Please select a Appointment in the table.");

			alert.showAndWait();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleAdd(ActionEvent event) {
		Appointment appointment = new Appointment();
		if (showAppointmentDialog(appointment, "Add Appointment")) {
			if (AppointmentSql.addAppointment(appointment) > 0) {
				String appointID = AppointmentSql.getIdOfLastAppointment();
				if (appointID != null && !appointID.equals("")) {
					appointment.setAppointID(appointID);
				}
				observableList.add(appointment);
				showAppointmentDetails(appointment);
			}
		}
	}
}
