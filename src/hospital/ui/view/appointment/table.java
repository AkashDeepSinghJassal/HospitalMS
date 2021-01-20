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

		firstScrollBar.setMaxHeight(0);
		firstScrollBar.setPrefHeight(0);
		firstScrollBar.setMaxWidth(0);
		firstScrollBar.setPrefWidth(0);
		firstScrollBar.setVisible(false);
		firstScrollBar.setOpacity(0);

		System.out.println(secondScrollBar.getWidth());
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
