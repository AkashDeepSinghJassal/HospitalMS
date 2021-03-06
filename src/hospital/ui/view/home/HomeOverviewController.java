package hospital.ui.view.home;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;

import hospital.model.AppointmentCalendar;
import hospital.ui.main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomeOverviewController {
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private PieChart pieChart;
	@FXML
	private Label caption;
	@FXML
	private AnchorPane homeAnchor;
	@FXML
	private Label appointmentLbl;

	@FXML
	private Label doctorLbl;

	@FXML
	private Label patientLbl;

	public AnchorPane overlay = null;
	private ObservableList<LocalDate> dayList;
	private ObservableList<AppointmentCalendar> appointmentCalander;
	private int totalAppointAvail;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// updateStatistics();
		caption.setLayoutX(-100);
		caption.setLayoutY(-100);
		pieChart.setLabelLineLength(10);
		pieChart.setLegendSide(Side.LEFT);
	}

	public void updateStatistics() {
		// update label
		int totalPatients = Main.patientOverviewController.getObservableList().size();
		patientLbl.setText(totalPatients + "");

		int totalDoctors = Main.doctorOverviewController.getObservableList().size();
		doctorLbl.setText(totalDoctors + "");
		// 8 hrs per 15 min
		totalAppointAvail = totalDoctors * 32;
		int totalAppoint = Main.appointmentOverviewController.getObservableList().size();
		appointmentLbl.setText(totalAppoint + "");

		barChart.getData().clear();
		LocalDate nowDate = LocalDate.now();
		dayList = FXCollections.observableArrayList();
		appointmentCalander = Main.appointmentCalendarController.getObservableList();
		HashMap<LocalDate, Integer> appointmentCountMap = new HashMap<LocalDate, Integer>();
		for (int i = 0; i < 7; i++) {
			dayList.add(nowDate.plusDays(i));
			appointmentCountMap.put(nowDate.plusDays(i), 0);
		}
		for (AppointmentCalendar calender : appointmentCalander) {
			LinkedHashMap<LocalDateTime, String> appointmentMap = calender.getAppointments();
			for (LocalDateTime localDateTime : appointmentMap.keySet()) {
				LocalDate ld = localDateTime.toLocalDate();
				if (ChronoUnit.DAYS.between(nowDate, ld) >= 7) {
					continue;
				}
				if (appointmentCountMap.containsKey(ld)) {
					appointmentCountMap.put(ld, appointmentCountMap.get(ld) + 1);
				} else {
					appointmentCountMap.put(ld, 1);
				}
			}
		}
		// appointmentCountMap.entrySet().stream().forEach(e -> System.out.println(e));
		// configure bar chart
		buildBarChart(appointmentCountMap);

	}

	private void buildBarChart(HashMap<LocalDate, Integer> appointmentCountMap) {
		ObservableList<String> dates = FXCollections.observableArrayList();
		for (int i = 0; i < dayList.size(); i++) {
			dates.add(dayList.get(i).toString());
		}
		xAxis.setCategories(dates);
		xAxis.setLabel("Dates");
		xAxis.setTickLabelRotation(30);
		yAxis.setLabel("Count");
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Appointments");
		for (int i = 0; i < dayList.size(); i++) {
			if (appointmentCountMap.get(dayList.get(i)) == null) {
				series.getData().add(new XYChart.Data<String, Integer>(dates.get(i), 0));
			} else {
				series.getData()
						.add(new XYChart.Data<String, Integer>(dates.get(i), appointmentCountMap.get(dayList.get(i))));
			}
		}
		barChart.getData().add(series);
		if (!series.getData().isEmpty()) {
			buildPieChart(series.getData().get(0).getYValue(), totalAppointAvail, series.getData().get(0).getXValue());
		}
		for (XYChart.Data<String, Integer> data : series.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {

				caption.setMinWidth(30);
				caption.setLayoutX(e.getSceneX());
				caption.setLayoutY(e.getSceneY() - 75);
				caption.setText(data.getYValue().toString());
				// buildPieChart(data.getYValue(), totalAppointAvail);
			});
			data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				buildPieChart(data.getYValue(), totalAppointAvail, data.getXValue());
			});
			data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {

				caption.setLayoutX(-100);
				caption.setLayoutY(-100);
				caption.setText("");
			});
		}
	}

	private void buildPieChart(int filled, int vacant, String dataTitle) {
		pieChart.getData().clear();
		homeAnchor.layout();
		ObservableList<PieChart.Data> pieChartData = FXCollections
				.observableArrayList(new PieChart.Data("Filled", filled), new PieChart.Data("Vacant", vacant));
		pieChart.setTitle(dataTitle);
		pieChart.getData().addAll(pieChartData);
		int total = filled + vacant;
		for (PieChart.Data data : pieChart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
				caption.setLayoutX(e.getSceneX());
				caption.setLayoutY(e.getSceneY());
				caption.setText(String.format("%.1f %s", (double) data.getPieValue() * 100 / total, "%"));
				caption.setMinWidth(75);
			});
			data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
				caption.setLayoutX(-100);
				caption.setLayoutY(-100);
			});
		}
	}
}
