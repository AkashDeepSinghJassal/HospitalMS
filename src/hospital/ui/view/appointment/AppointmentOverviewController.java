package hospital.ui.view.appointment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;

import java.io.IOException;
import java.time.LocalDate;

import hospital.model.Appointment;
import hospital.model.Patient;
import hospital.services.PatientSql;
import hospital.ui.main.Main;
import hospital.ui.view.patient.PatientDialogController;
import hospital.util.DateUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class AppointmentOverviewController {

	private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
	@FXML
	private TableView<Appointment> appointmentTable;
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
	private void initialize() {
		appointmentTable.setItems(appointmentList);
		patientIDTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("patientID"));
		appointIDTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("appointID"));
		doctorIDTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleStringProperty>("doctorID"));
		dateTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, SimpleObjectProperty<LocalDate>>("date"));
		
		
		// show empty in personal details
		showAppointmentDetails(null);
		appointmentTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showAppointmentDetails(newValue));

		// Clear Selection On Opening
		appointmentTable.getSelectionModel().clearSelection();

		// Clear Selection when clicking on empty rows
		ObjectProperty<TableRow<Appointment>> lastSelectedRow = new SimpleObjectProperty<>();

		appointmentTable.setRowFactory(tableView -> {
			TableRow<Appointment> row = new TableRow<Appointment>();

			row.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					lastSelectedRow.set(row);
				}
			});
			return row;
		});

		appointmentTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (lastSelectedRow.get() != null) {
					Bounds boundsOfSelectedRow = lastSelectedRow.get()
							.localToScene(lastSelectedRow.get().getLayoutBounds());
					if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
						appointmentTable.getSelectionModel().clearSelection();
					}
				}
			}
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
			// Fill the labels with info from the patient object.
			patientIDLbl.setText(appointment.getPatientID());
			appointIDLbl.setText(appointment.getAppointID());
			patientIDLbl.setText(appointment.getDoctorID());
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
			dialogStage.setScene(scene);

			// Set the appointment into the controller.
			AppointmentDialogController controller = loader.getController();
//			controller.setDialogStage(dialogStage);
//			controller.setAppointment(appointment);
//			controller.setHeader(header);
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
		Appointment p = appointmentTable.getSelectionModel().getSelectedItem();
		if (p != null) {
			boolean okClicked = showAppointmentDialog(p, "Edit Appointment");
			if (okClicked) {
//				if (appointmentSql.updateAppointment(p) == 1) {
					showAppointmentDetails(p);
//				}

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
		Appointment deletedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
		if (deletedAppointment != null) {
//			if (AppointmentSql.removeAppointment(deletedAppointment.getId()) == 1) {
				appointmentTable.getItems().remove(deletedAppointment);
//			}

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
//			if (PatientSql.addPatient(patient) == 1) {
//				patient.setId(PatientSql.getIdOfLastPatient());
				appointmentList.add(appointment);
				showAppointmentDetails(appointment);
//			}
		}
	}
}
