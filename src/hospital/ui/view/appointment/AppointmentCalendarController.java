package hospital.ui.view.appointment;

import hospital.model.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentCalendarController {
	@FXML
	private TableView<Appointment> tableView;
	@FXML
	private TableColumn<Appointment, String> doctorIDTableColumn;

	@FXML
	private void initialize() {
		// tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		TableColumn days[] = new TableColumn[30];
		for (int i = 1; i <= 30; i++) {
			days[i - 1] = new TableColumn("" + i);
			// days[i - 1].getStyleClass().add("calendarHeader");

			TableColumn hours[] = new TableColumn[8];
			for (int j = 1; j <= 8; j++) {
				if (8 + j > 12)
					hours[j - 1] = new TableColumn((j - 4) + " PM");

				else
					hours[j - 1] = new TableColumn((8 + j) + " AM");

				// hours[j - 1].getStyleClass().add("calendarHeader");

				TableColumn minutes[] = new TableColumn[4];
				for (int k = 1; k <= 4; k++) {
					minutes[k - 1] = new TableColumn("" + (k - 1) * 15);
					// minutes[k - 1].getStyleClass().add("calendarHeader");
					// minutes[k - 1].setCellFactory(tc -> {
					// TableCell cell = new TableCell() {
					// @Override
					// public void updateItem(String item, boolean empty) {
					// super.updateItem(item, empty);
					// this.setText(empty ? "" : item);
					// }
					// };
					// return cell;
					// });
				}

				hours[j - 1].getColumns().addAll(minutes);
			}

			days[i - 1].getColumns().addAll(hours);
		}
		tableView.getColumns().addAll(days);
		tableView.scrollToColumnIndex(30);
	}
}
