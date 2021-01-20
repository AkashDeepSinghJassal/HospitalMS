package hospital.ui.view.appointment;

import javafx.application.Platform;
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

public class table {
	@FXML
	private ScrollPane sp1;
	@FXML
	private ScrollPane sp2;
	@FXML
	private TableView<String> doctorTable;
	@FXML
	private TableColumn<String, String> t1c1;
	@FXML
	private TableView<String> appointmentTable;
	@FXML
	private TableColumn<String, String> t2c1;
	@FXML
	private TableColumn<String, String> t2c2;
	@FXML
	private TableColumn<String, String> t2c3;

	@FXML
	private void initialize() {
		t1c1.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
		t2c1.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
		t2c2.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
		t2c3.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
		ObservableList<String> doctorList = FXCollections.observableArrayList();
		ObservableList<String> appointmentList = FXCollections.observableArrayList();
		for (int i = 0; i < 100; i++) {
			doctorList.add("");
			appointmentList.add("");
		}
		doctorTable.setItems(doctorList);
		appointmentTable.setItems(appointmentList);

		doctorTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
			appointmentTable.getSelectionModel().select(newIndex.intValue());
		});

		appointmentTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
			doctorTable.getSelectionModel().select(newIndex.intValue());
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				bindScrollBars(doctorTable, appointmentTable, Orientation.VERTICAL);
			}
		});

	}

	void bindScrollBars(TableView<?> doctorTable, TableView<?> appointmentTable, Orientation orientation) {
		ScrollBar firstScrollBar = null;
		ScrollBar secondScrollBar = null;

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
	}
}
