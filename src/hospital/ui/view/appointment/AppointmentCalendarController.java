package hospital.ui.view.appointment;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentCalendarController {

	public ScrollBar firstScrollBar = null;
	public ScrollBar secondScrollBar = null;

	@FXML
	private ScrollPane sp1;
	@FXML
	private ScrollPane sp2;
	@FXML
	private TableView<A> doctorTable;
	@FXML
	private TableColumn<A, String> t1c1;
	@FXML
	private TableView<B> appointmentTable;
	@FXML
	private TableColumn<B, String> t2c1;
	@FXML
	private TableColumn<B, String> t2c2;
	@FXML
	private TableColumn<B, String> t2c3;

	@FXML
	private void initialize() {
		t1c1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().doctorID));
		t2c1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().a));
		t2c2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().b));
		t2c3.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().c));
		ObservableList<A> doctorList = FXCollections.observableArrayList();
		ObservableList<B> appointmentList = FXCollections.observableArrayList();
		for (int i = 0; i < 100; i++) {
			doctorList.add(new A("" + i));
			appointmentList.add(new B("" + i, "" + i, "" + i));
		}
		doctorTable.setItems(doctorList);
		appointmentTable.setItems(appointmentList);

		doctorTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			appointmentTable.getSelectionModel().select(newVal.intValue());
		});

		appointmentTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			doctorTable.getSelectionModel().select(newVal.intValue());
		});

		// ObservableList<AppointmentCalendar> data =
		// FXCollections.observableArrayList();
		// ArrayList<String> appointment = new ArrayList<String>();
		// appointment.add("000");
		// appointment.add("001");
		// appointment.add("002");
		// data.add(new AppointmentCalendar(appointment));
		// data.add(new AppointmentCalendar(appointment));
		// data.add(new AppointmentCalendar(appointment));
		// data.add(new AppointmentCalendar(appointment));

		// TableColumn days[] = new TableColumn[30];
		// for (int i = 1; i <= 30; i++) {
		// days[i - 1] = new TableColumn("" + i);

		// TableColumn hours[] = new TableColumn[8];
		// for (int j = 1; j <= 8; j++) {
		// if (8 + j > 12)
		// hours[j - 1] = new TableColumn((j - 4) + " PM");

		// else
		// hours[j - 1] = new TableColumn((8 + j) + " AM");

		// TableColumn minutes[] = new TableColumn[4];
		// for (int k = 1; k <= 4; k++) {
		// minutes[k - 1] = new TableColumn<AppointmentCalendar, String>("" + (k - 1) *
		// 15);
		// minutes[k - 1].getStyleClass().add("calendarHeader");

		// final int colNoI = i - 1;
		// final int colNoJ = j - 1;
		// final int colNoK = k - 1;

		// minutes[k - 1].setCellValueFactory(
		// new Callback<CellDataFeatures<AppointmentCalendar, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<AppointmentCalendar,
		// String> p) {
		// if (p.getValue().getAppointments().contains("" + colNoI + colNoJ + colNoK)) {
		// return new SimpleStringProperty("abc");

		// } else {
		// return new SimpleStringProperty("");
		// }
		// }
		// });
		// }

		// hours[j - 1].getColumns().addAll(minutes);
		// }

		// days[i - 1].getColumns().addAll(hours);
		// }
		// tableView.getColumns().addAll(days);

		// doctorIDTableColumn.setCellValueFactory(new
		// PropertyValueFactory<AppointmentCalendar, String>("doctorID"));

		// tableView.setItems(data);
	}

	public void setScroll() {
		bindScrollBars(doctorTable, appointmentTable, Orientation.VERTICAL);
	}

	void bindScrollBars(TableView<A> doctorTable, TableView<B> appointmentTable, Orientation orientation) {

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

	public static class A {
		String doctorID;

		public A(String doctorID) {
			this.doctorID = doctorID;
		}
	}

	public static class B {
		String a, b, c;

		public B(String a, String b, String c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
}
