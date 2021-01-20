package hospital.ui.view.appointment;

import java.util.ArrayList;

import hospital.model.AppointmentCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AppointmentCalendarController {

	private ScrollBar firstScrollBar = null;
	private ScrollBar secondScrollBar = null;
	private int Day = 1;

	public int getDay() {
		return this.Day;
	}

	public void setDay(int Day) {
		this.Day = Day;
	}

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

		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		ArrayList<String> appointment = new ArrayList<String>();
		appointment.add("000");
		appointment.add("001");
		appointment.add("002");
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));

		int day = 1;

		TableColumn hours[] = new TableColumn[8];
		for (int j = 1; j <= 8; j++) {
			if (8 + j > 12)
				hours[j - 1] = new TableColumn((j - 4) + " PM");

			else
				hours[j - 1] = new TableColumn((8 + j) + " AM");

			TableColumn minutes[] = new TableColumn[4];
			for (int k = 1; k <= 4; k++) {
				minutes[k - 1] = new TableColumn<AppointmentCalendar, String>("" + (k - 1) * 15);
				minutes[k - 1].getStyleClass().add("calendarHeader");
				minutes[k - 1].setMinWidth(100);
				final int colNoI = day - 1;
				final int colNoJ = j - 1;
				final int colNoK = k - 1;

				minutes[k - 1].setCellValueFactory(
						new Callback<CellDataFeatures<AppointmentCalendar, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<AppointmentCalendar, String> p) {
								if (p.getValue().getAppointments().contains("" + colNoI + colNoJ + colNoK)) {
									return new SimpleStringProperty("abc");

								} else {
									return new SimpleStringProperty("");
								}
							}
						});
			}

			hours[j - 1].getColumns().addAll(minutes);
		}

		appointmentTable.getColumns().addAll(hours);

		doctorColumn.setCellValueFactory(new PropertyValueFactory<AppointmentCalendar, String>("doctorID"));

		doctorTable.setItems(data);
		appointmentTable.setItems(data);

		/* Selection Binding */
		doctorTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			appointmentTable.getSelectionModel().select(newVal.intValue());
		});

		appointmentTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			doctorTable.getSelectionModel().select(newVal.intValue());
		});

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
