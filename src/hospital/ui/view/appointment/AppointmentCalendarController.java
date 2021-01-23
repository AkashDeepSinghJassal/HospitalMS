package hospital.ui.view.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.jfoenix.controls.JFXComboBox;

import hospital.model.AppointmentCalendar;
import hospital.services.AppointmentCalendarSql;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
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
import javafx.util.Callback;

public class AppointmentCalendarController {

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

		ContextMenu existingContextMenu = new ContextMenu();
		MenuItem mi1 = new MenuItem("Edit");
		existingContextMenu.getItems().add(mi1);
		MenuItem mi2 = new MenuItem("Delete");
		existingContextMenu.getItems().add(mi2);
		ContextMenu newContextMenu = new ContextMenu();
		MenuItem mi3 = new MenuItem("New");
		newContextMenu.getItems().add(mi3);
		// appointmentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new
		// EventHandler<MouseEvent>() {

		// @Override
		// public void handle(MouseEvent t) {
		// if (t.getButton() == MouseButton.SECONDARY) {
		// cm.show(appointmentTable, t.getScreenX(), t.getScreenY());
		// }
		// }
		// });

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
											// ContextMenu contextMenu = new ContextMenu();
											// MenuItem menuItem = new MenuItem(cell.getText());
											// contextMenu.getItems().add(menuItem);
											if (cell.getText() == null || cell.getText().equals(""))
												cell.setContextMenu(newContextMenu);
											else
												cell.setContextMenu(existingContextMenu);
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
}
