package hospital.ui.view.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;

import hospital.model.AppointmentCalendar;
import hospital.util.DBUtil;
import hospital.util.DateTimeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
	private Label date;
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
		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		try {
			PreparedStatement statement = DBUtil.getDBConnection().prepareStatement(
					"select doctor_id, group_concat(patient_id) patients, group_concat(date_scheduled) appointments from appointment group by doctor_id");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AppointmentCalendar calendar = null;
				LinkedHashMap<LocalDateTime, String> hashMap = new LinkedHashMap<LocalDateTime, String>();
				String doctorID = resultSet.getString(1);
				String[] patients = resultSet.getString(2).split(",");
				String[] appointments = resultSet.getString(3).split(",");

				for (int i = 0; i < patients.length; i++) {
					hashMap.put(DateTimeUtil.parse(appointments[i]), patients[i]);
				}
				calendar = new AppointmentCalendar(doctorID, hashMap);
				data.add(calendar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		doctorTable.setItems(data);
		appointmentTable.setItems(data);

		int day = 1;
		LocalDateTime selectedDateTime = LocalDateTime.now();
		LocalDate selectedDate = selectedDateTime.toLocalDate();
		date.setText(selectedDateTime.getYear() + "-" + selectedDateTime.getMonth() + "-"
				+ selectedDateTime.getDayOfMonth());

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
			}
			hours[j].getColumns().addAll(minutes);
		}
		appointmentTable.getColumns().addAll(hours);

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
