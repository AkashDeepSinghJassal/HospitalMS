package hospital.ui.view.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
		List<LocalDateTime> item = new ArrayList<LocalDateTime>();
		try {
			PreparedStatement statement = DBUtil.getDBConnection().prepareStatement(
					"select doctor_id, group_concat(date_scheduled) all_appointments from appointment group by doctor_id");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				String[] values = resultSet.getString(2).split(",");
				for (String value : values) {
					item.add(DateTimeUtil.parse(value));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(item.contains(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(12, 15))));
		System.out.println(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(12, 15)));
		System.out.println(item);
		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		ObservableList<LocalDateTime> appointment = FXCollections.observableArrayList();
		appointment.addAll(item);
		data.add(new AppointmentCalendar("001", appointment));
		data.add(new AppointmentCalendar("002", appointment));
		data.add(new AppointmentCalendar("003", appointment));
		data.add(new AppointmentCalendar("004", appointment));
		data.add(new AppointmentCalendar("005", appointment));

		int day = 1;
		LocalDateTime selectedDateTime = LocalDateTime.now();
		LocalDate selectedDate = selectedDateTime.toLocalDate();
		date.setText(selectedDateTime.getYear() + "-" + selectedDateTime.getMonth() + "-"
				+ selectedDateTime.getDayOfMonth());

		@SuppressWarnings("unchecked")
		TableColumn<AppointmentCalendar, String> hours[] = new TableColumn[8];
		for (int j = 1; j <= 8; j++) {
			if (8 + j > 12)
				hours[j - 1] = new TableColumn<AppointmentCalendar, String>((j - 4) + " PM");

			else
				hours[j - 1] = new TableColumn<AppointmentCalendar, String>((8 + j) + " AM");

			hours[j - 1].getStyleClass().add("appointment-column");

			@SuppressWarnings("unchecked")
			TableColumn<AppointmentCalendar, String> minutes[] = new TableColumn[4];
			for (int k = 1; k <= 4; k++) {
				minutes[k - 1] = new TableColumn<AppointmentCalendar, String>("" + (k - 1) * 15);
				minutes[k - 1].getStyleClass().add("appointment-column");
				minutes[k - 1].setMinWidth(100);
				final int colNoI = day - 1;
				final int colNoJ = j - 1;
				final int colNoK = k - 1;

				minutes[k - 1].setCellValueFactory(
						new Callback<CellDataFeatures<AppointmentCalendar, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<AppointmentCalendar, String> p) {
								LocalDateTime temp = LocalDateTime.of(selectedDate,
										LocalTime.of(colNoJ + 9, colNoK * 15));
								if (p.getValue().getAppointments().contains(temp)) {
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
