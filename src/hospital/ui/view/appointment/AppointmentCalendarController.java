package hospital.ui.view.appointment;

import java.util.ArrayList;

import hospital.model.AppointmentCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AppointmentCalendarController {
	@FXML
	private TableView<AppointmentCalendar> tableView;
	@FXML
	private TableColumn<AppointmentCalendar, String> doctorIDTableColumn;

	@FXML
	private void initialize() {
		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		ArrayList<String> appointment = new ArrayList<String>();
		appointment.add("000");
		appointment.add("001");
		appointment.add("002");
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));
		data.add(new AppointmentCalendar(appointment));

		TableColumn days[] = new TableColumn[30];
		for (int i = 1; i <= 30; i++) {
			days[i - 1] = new TableColumn("" + i);

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

					final int colNoI = i - 1;
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

			days[i - 1].getColumns().addAll(hours);
		}
		tableView.getColumns().addAll(days);

		doctorIDTableColumn.setCellValueFactory(new PropertyValueFactory<AppointmentCalendar, String>("doctorID"));

		tableView.setItems(data);
	}
}
