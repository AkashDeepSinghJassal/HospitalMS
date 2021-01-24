package hospital.ui.view.appointment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.jfoenix.controls.JFXComboBox;

import hospital.model.Appointment;
import hospital.model.AppointmentCalendar;
import hospital.services.AppointmentCalendarSql;
import hospital.services.AppointmentSql;
import hospital.ui.main.Main;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class AppointmentCalendarController {

	public AnchorPane overlay = null;
	private ScrollBar firstScrollBar = null;
	private ScrollBar secondScrollBar = null;
	private LocalDate selectedDate = null;
	private int Day = 1;

	public int getDay() {
		return this.Day;
	}

	public void setDay(int Day) {
		this.Day = Day;
	}

	@FXML
	private JFXComboBox<LocalDate> date;
	@FXML
	private ScrollPane sp1;
	@FXML
	private ScrollPane sp2;
	@FXML
	private TableView<AppointmentCalendar> doctorTable;
	@FXML
	private TableColumn<AppointmentCalendar, String> doctorColumn;
	@FXML
	private TableView<AppointmentCalendar> appointmentTable;

	@FXML
	private void initialize() {
		doctorColumn.setCellValueFactory(new PropertyValueFactory<AppointmentCalendar, String>("doctorID"));
		doctorColumn.getStyleClass().add("doctor-column");

		// DateTimeComparator.getDateOnlyInstance().compare(first, second);
		ObservableList<AppointmentCalendar> data = AppointmentCalendarSql.getAppointmentCalendars();

		doctorTable.setItems(data);
		appointmentTable.setItems(data);

		LocalDateTime selectedDateTime = LocalDateTime.now();
		selectedDate = selectedDateTime.toLocalDate();
		for (int i = 0; i < 30; i++) {
			date.getItems().add(selectedDate.plusDays(i));
		}
		date.setValue(selectedDate);

		doctorColumn.setCellValueFactory(new PropertyValueFactory<AppointmentCalendar, String>("doctorID"));

		@SuppressWarnings("unchecked")
		TableColumn<AppointmentCalendar, String> hours[] = new TableColumn[8];
		for (int j = 0; j < 8; j++) {
			final int HOUR = j + 9;
			if (HOUR > 12)
				hours[j] = new TableColumn<AppointmentCalendar, String>(HOUR - 12 + " PM");
			else if (HOUR == 12)
				hours[j] = new TableColumn<AppointmentCalendar, String>(HOUR + " PM");
			else
				hours[j] = new TableColumn<AppointmentCalendar, String>(HOUR + " AM");

			hours[j].getStyleClass().add("appointment-column");

			@SuppressWarnings("unchecked")
			TableColumn<AppointmentCalendar, String> minutes[] = new TableColumn[4];
			for (int k = 0; k < 4; k++) {
				minutes[k] = new TableColumn<AppointmentCalendar, String>("" + (k) * 15);
				minutes[k].getStyleClass().add("appointment-column");
				minutes[k].setMinWidth(100);
				final int MINUTES = k * 15;

				minutes[k].setCellValueFactory(
						new Callback<CellDataFeatures<AppointmentCalendar, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<AppointmentCalendar, String> p) {
								LocalDateTime temp = LocalDateTime.of(selectedDate, LocalTime.of(HOUR, MINUTES));
								if (p.getValue().getAppointments().containsKey(temp)) {
									return new SimpleStringProperty(p.getValue().getAppointments().get(temp));
								} else {
									return new SimpleStringProperty("");
								}
							}
						});
				minutes[k].setCellFactory(
						new Callback<TableColumn<AppointmentCalendar, String>, TableCell<AppointmentCalendar, String>>() {
							@Override
							public TableCell<AppointmentCalendar, String> call(
									TableColumn<AppointmentCalendar, String> column) {
								final TableCell<AppointmentCalendar, String> cell = new TableCell<>();
								cell.textProperty().bind(cell.itemProperty());
								cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
										if (event.getButton() == MouseButton.SECONDARY) {
											if (cell.getText() == null || cell.getText().equals("")) {
												ContextMenu newContextMenu = new ContextMenu();
												MenuItem newMI = new MenuItem("New");
												newContextMenu.getItems().add(newMI);

												newMI.setOnAction(new EventHandler<ActionEvent>() {
													@Override
													public void handle(ActionEvent event) {
														handleAdd(HOUR, MINUTES, "Add Appointment");
													}
												});

												cell.setContextMenu(newContextMenu);
											} else {
												ContextMenu existingContextMenu = new ContextMenu();
												MenuItem editMI = new MenuItem("Edit");
												existingContextMenu.getItems().add(editMI);
												MenuItem deleteMI = new MenuItem("Delete");
												existingContextMenu.getItems().add(deleteMI);

												editMI.setOnAction(new EventHandler<ActionEvent>() {
													@Override
													public void handle(ActionEvent event) {
														handleEdit(HOUR, MINUTES, "Edit Appointment");
													}
												});

												deleteMI.setOnAction(new EventHandler<ActionEvent>() {
													@Override
													public void handle(ActionEvent event) {
														handleDelete(HOUR, MINUTES);
													}
												});

												cell.setContextMenu(existingContextMenu);
											}
										}
									}
								});
								return cell;
							}
						});
			}
			hours[j].getColumns().addAll(minutes);
		}
		appointmentTable.getColumns().addAll(hours);
		appointmentTable.getSelectionModel().setCellSelectionEnabled(true);

		/* Selection Binding */
		doctorTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			if (appointmentTable.getSelectionModel().getSelectedIndex() == -1 && newVal.intValue() != -1) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						doctorTable.getSelectionModel().clearSelection();
					}
				});
				return;
			}
			if (appointmentTable.getSelectionModel().getSelectedIndex() != newVal.intValue()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						doctorTable.getSelectionModel().select(appointmentTable.getSelectionModel().getSelectedIndex());
						doctorTable.getFocusModel().focus(appointmentTable.getSelectionModel().getSelectedIndex());
					}
				});
			}
		});

		appointmentTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			doctorTable.getSelectionModel().select(newVal.intValue());
		});

	}

	@FXML
	void selectedDateChange(ActionEvent event) {
		selectedDate = date.getValue();
		appointmentTable.refresh();
	}

	public void setScroll() {
		bindScrollBars(doctorTable, appointmentTable, Orientation.VERTICAL);
	}

	void bindScrollBars(TableView<AppointmentCalendar> doctorTable, TableView<AppointmentCalendar> appointmentTable,
			Orientation orientation) {

		for (Node node : doctorTable.lookupAll(".scroll-bar")) {
			if (node instanceof ScrollBar && ((ScrollBar) node).getOrientation().equals(orientation)) {
				firstScrollBar = (ScrollBar) node;
			}
		}
		for (Node node : appointmentTable.lookupAll(".scroll-bar")) {
			if (node instanceof ScrollBar && ((ScrollBar) node).getOrientation().equals(orientation)) {
				secondScrollBar = (ScrollBar) node;
			}
		}
		if (firstScrollBar != null && secondScrollBar != null) {
			firstScrollBar.valueProperty().bindBidirectional(secondScrollBar.valueProperty());
		}

		if (firstScrollBar != null) {
			firstScrollBar.setMaxHeight(0);
			firstScrollBar.setPrefHeight(0);
			firstScrollBar.setMaxWidth(0);
			firstScrollBar.setPrefWidth(0);
			firstScrollBar.setVisible(false);
			firstScrollBar.setOpacity(0);
		}

	}

	public boolean showAppointmentDialog(Appointment appointment, String header, int HOUR, int MINUTES,
			boolean partialAppointment, boolean onlyPatient) {
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
			controller.setAppointment(appointment, partialAppointment);
			controller.setHeader(header);
			if (onlyPatient)
				controller.onlyPatient();

			/* Set the position of the stage */
			dialogStage.setX(100);
			dialogStage.setY(190);

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

	public void handleAdd(final int HOUR, final int MINUTES, String header) {
		LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(HOUR, MINUTES));
		AppointmentCalendar calendar = doctorTable.getSelectionModel().getSelectedItem();
		Appointment appointment = new Appointment();
		appointment.setDoctorID(calendar.getDoctorID());
		appointment.setDate(dateTime);

		if (showAppointmentDialog(appointment, header, HOUR, MINUTES, true, true)) {
			if (AppointmentSql.addAppointment(appointment) > 0) {
				String appointID = AppointmentSql.getIdOfLastAppointment();
				if (appointID != null && !appointID.equals("")) {
					appointment.setId(appointID);
					calendar.getAppointments().put(dateTime, appointment.getPatientID());
					calendar.getIds().put(dateTime, appointID);
				}
				Main.appointmentOverviewController.getObservableList().add(appointment);
				appointmentTable.refresh();
			}
		}
	}

	public void handleEdit(final int HOUR, final int MINUTES, String header) {
		LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(HOUR, MINUTES));
		AppointmentCalendar calendar = doctorTable.getSelectionModel().getSelectedItem();
		Appointment appointment = null;
		appointment = Main.appointmentOverviewController.getAppointment(calendar.getIds().get(dateTime));

		if (showAppointmentDialog(appointment, header, HOUR, MINUTES, false, true)) {
			if (AppointmentSql.updateAppointment(appointment) > 0) {
				calendar.getAppointments().put(dateTime, appointment.getPatientID());
			}
			appointmentTable.refresh();
		}
	}

	public void handleDelete(final int HOUR, final int MINUTES) {
		LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(HOUR, MINUTES));
		AppointmentCalendar calendar = doctorTable.getSelectionModel().getSelectedItem();
		Appointment appointment = null;
		appointment = Main.appointmentOverviewController.getAppointment(calendar.getIds().get(dateTime));
		if (AppointmentSql.removeAppointment(appointment) > 0) {
			Main.appointmentOverviewController.getObservableList().remove(appointment);
			calendar.getAppointments().remove(dateTime);
			calendar.getIds().remove(dateTime);
			appointmentTable.refresh();
		}
	}
}
