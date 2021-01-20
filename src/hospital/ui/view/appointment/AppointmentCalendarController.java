package hospital.ui.view.appointment;

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
		doctorColumn.getStyleClass().add("doctor-column");

		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		ObservableList<String> appointment = FXCollections.observableArrayList();
		appointment.add("000");
		appointment.add("001");
		appointment.add("002");
		appointment.add("003");
		appointment.add("010");
		appointment.add("011");
		appointment.add("012");
		appointment.add("013");
		appointment.add("020");
		appointment.add("021");
		appointment.add("022");
		appointment.add("023");
		appointment.add("030");
		appointment.add("031");
		appointment.add("032");
		appointment.add("033");
		appointment.add("040");
		appointment.add("041");
		appointment.add("042");
		appointment.add("043");
		data.add(new AppointmentCalendar("001", appointment));
		data.add(new AppointmentCalendar("002", appointment));
		data.add(new AppointmentCalendar("003", appointment));
		data.add(new AppointmentCalendar("004", appointment));
		data.add(new AppointmentCalendar("005", appointment));
		data.add(new AppointmentCalendar("006", appointment));
		data.add(new AppointmentCalendar("007", appointment));
		data.add(new AppointmentCalendar("008", appointment));
		data.add(new AppointmentCalendar("009", appointment));
		data.add(new AppointmentCalendar("010", appointment));
		data.add(new AppointmentCalendar("011", appointment));
		data.add(new AppointmentCalendar("012", appointment));
		data.add(new AppointmentCalendar("013", appointment));
		data.add(new AppointmentCalendar("014", appointment));
		data.add(new AppointmentCalendar("015", appointment));
		data.add(new AppointmentCalendar("016", appointment));
		data.add(new AppointmentCalendar("017", appointment));
		data.add(new AppointmentCalendar("018", appointment));
		data.add(new AppointmentCalendar("019", appointment));
		data.add(new AppointmentCalendar("020", appointment));
		data.add(new AppointmentCalendar("021", appointment));
		data.add(new AppointmentCalendar("022", appointment));
		data.add(new AppointmentCalendar("023", appointment));
		data.add(new AppointmentCalendar("024", appointment));
		data.add(new AppointmentCalendar("025", appointment));
		data.add(new AppointmentCalendar("026", appointment));
		data.add(new AppointmentCalendar("027", appointment));
		data.add(new AppointmentCalendar("028", appointment));
		data.add(new AppointmentCalendar("029", appointment));
		data.add(new AppointmentCalendar("030", appointment));

		int day = 1;

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
