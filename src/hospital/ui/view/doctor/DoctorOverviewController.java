package hospital.ui.view.doctor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hospital.model.Doctor;
import hospital.model.GENDER;
import hospital.services.DoctorSql;
import hospital.ui.main.Main;
import hospital.util.DBUtil;
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

public class DoctorOverviewController {

	private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
	Connection conn = DBUtil.getDBConnection();
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	public DoctorOverviewController() {
		doctorList.addAll(DoctorSql.getDoctors());
	}

	@FXML
	private TableView<Doctor> doctorTable;

	@FXML
	private TableColumn<Doctor, SimpleStringProperty> doctorIDTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleStringProperty> nameTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleIntegerProperty> ageTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleObjectProperty<GENDER>> genderTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleStringProperty> contactTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleStringProperty> addressTableColumn;

	@FXML
	private TableColumn<Doctor, SimpleStringProperty> specialityTableColumn;

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
	private Label specialityLabel;

	@FXML
	private void initialize() {
		doctorTable.setItems(doctorList);
		doctorIDTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("id"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("name"));
		ageTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleIntegerProperty>("age"));
		genderTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleObjectProperty<GENDER>>("gender"));
		contactTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("contact"));
		addressTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("address"));
		specialityTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("speciality"));

		// lastNameColumn.setCellValueFactory(cellData ->
		// cellData.getValue().lastNameProperty());
		// show empty in personal details
		showDoctorDetails(null);
		doctorTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDoctorDetails(newValue));

		// Clear Selection On Opening
		doctorTable.getSelectionModel().clearSelection();

		ObjectProperty<TableRow<Doctor>> lastSelectedRow = new SimpleObjectProperty<>();

		doctorTable.setRowFactory(tableView -> {
			TableRow<Doctor> row = new TableRow<Doctor>();

			row.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					lastSelectedRow.set(row);
				}
			});
			return row;
		});

		doctorTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (lastSelectedRow.get() != null) {
					Bounds boundsOfSelectedRow = lastSelectedRow.get()
							.localToScene(lastSelectedRow.get().getLayoutBounds());
					if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
						doctorTable.getSelectionModel().clearSelection();
					}
				}
			}
		});
	}

	@FXML
	void handleAddDoctor(ActionEvent event) {
		Doctor doctor = new Doctor();
		if (showDoctorDialog(doctor, "Add Doctor")) {
			if (DoctorSql.addDoctor(doctor) == 1) {
				doctor.setId(DoctorSql.getIdOfLastDoctor());
				doctorList.add(doctor);
				showDoctorDetails(doctor);
			}
		}
	}

	@FXML
	void handleDeleteDoctor(ActionEvent event) {
		Doctor deletedDoctor = doctorTable.getSelectionModel().getSelectedItem();
		if (deletedDoctor != null) {
			if (DoctorSql.removeDoctor(deletedDoctor.getId()) == 1) {
				doctorTable.getItems().remove(deletedDoctor);
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Doctor Selected");
			alert.setContentText("Please select a doctor in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	void handleEditDoctor(ActionEvent event) {
		Doctor d = doctorTable.getSelectionModel().getSelectedItem();
		if (d != null) {
			boolean okClicked = showDoctorDialog(d, "Edit Doctor");
			if (okClicked) {
				if (DoctorSql.updateDoctor(d) == 1) {
					showDoctorDetails(d);
				}
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.stage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Doctor Selected");
			alert.setContentText("Please select a doctor in the table.");

			alert.showAndWait();
		}
	}

	public boolean showDoctorDialog(Doctor doctor, String header) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorDialog.fxml"));
			VBox aPane = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle(header);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.initOwner(Main.stage);
			Scene scene = new Scene(aPane);
			dialogStage.setScene(scene);

			// Set the doctor into the controller.
			DoctorDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setDoctor(doctor);
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
	 * Fills all text fields to show details about the doctor. If the specified
	 * doctor is null, all text fields are cleared.
	 * 
	 * @param doctor the doctor or null
	 */
	private void showDoctorDetails(Doctor doctor) {
		if (doctor != null) {
			// Fill the labels with info from the doctor object.
			nameLabel.setText(doctor.getName());
			addressLabel.setText(doctor.getAddress());
			genderLabel.setText(doctor.getGender().toString());
			ageLabel.setText(Integer.toString(doctor.getAge()));
			contactLabel.setText(doctor.getContact());
			IDLabel.setText(doctor.getId());
			specialityLabel.setText(doctor.getSpeciality());
		} else {
			// doctor is null, remove all the text.
			nameLabel.setText("");
			addressLabel.setText("");
			genderLabel.setText("");
			ageLabel.setText("");
			contactLabel.setText("");
			IDLabel.setText("");
			specialityLabel.setText("");
		}
	}
}
