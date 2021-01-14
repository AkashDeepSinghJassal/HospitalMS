package hospital.ui.view.doctor;

import hospital.model.Doctor;
import hospital.model.GENDER;
import hospital.ui.main.Main;
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

public class DoctorSelectorController {
	private ObservableList<Doctor> observableList = null;
	private FilteredList<Doctor> filteredList = null;
	private SortedList<Doctor> sortedList = null;
	private Doctor doctor = null;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@FXML
	private TableView<Doctor> tableView;
	@FXML
	private TableColumn<Doctor, SimpleStringProperty> doctorIDTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleStringProperty> nameTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleIntegerProperty> ageTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleObjectProperty<GENDER>> genderTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleStringProperty> specialityTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleStringProperty> contactTableColumn;
	@FXML
	private TableColumn<Doctor, SimpleStringProperty> addressTableColumn;
	@FXML
	private TextField filterTF;
	@FXML
	private Button select;

	@FXML
	private void initialize() {
		doctorIDTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("id"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("name"));
		ageTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleIntegerProperty>("age"));
		genderTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleObjectProperty<GENDER>>("gender"));
		specialityTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("speciality"));
		contactTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("contact"));
		addressTableColumn.setCellValueFactory(new PropertyValueFactory<Doctor, SimpleStringProperty>("address"));

		/* Filter table */
		observableList = Main.doctorOverviewController.getObservableList();
		filteredList = new FilteredList<Doctor>(observableList, p -> true);
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(doctor -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				String filter = newValue.toLowerCase();
				if (doctor.getName().toLowerCase().contains(filter))
					return true;
				if (doctor.getId().toLowerCase().contains(filter))
					return true;
				return false;
			});
		});
		sortedList = new SortedList<Doctor>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);
		// System.out.println(observableList);
		// System.out.println(filteredList);
		// System.out.println(sortedList);

		// Clear Selection On Opening
		tableView.getSelectionModel().clearSelection();

		// Clear Selection when clicking on empty rows
		ObjectProperty<TableRow<Doctor>> lastSelectedRow = new SimpleObjectProperty<>();
		tableView.setRowFactory(tableView -> {
			TableRow<Doctor> row = new TableRow<Doctor>();
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
			TableRow<Doctor> row = new TableRow<Doctor>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					select.fire();
				}
			});
			return row;
		});
	}

	@FXML
	void cancelModal(ActionEvent event) {
		((Stage) ((Node) event.getTarget()).getScene().getWindow()).close();
	}

	@FXML
	void selectDoctor(ActionEvent event) {
		if (tableView.getSelectionModel().getSelectedItem() != null) {
			setDoctor(tableView.getSelectionModel().getSelectedItem());
			cancelModal(event);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner((Stage) ((Node) event.getTarget()).getScene().getWindow());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Appointment Selected");
			alert.setContentText("Please select a Appointment in the table.");
			alert.showAndWait();
		}
	}

}
