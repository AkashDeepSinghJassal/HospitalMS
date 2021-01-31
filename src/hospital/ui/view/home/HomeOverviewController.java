package hospital.ui.view.home;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

import hospital.model.AppointmentCalendar;
import hospital.ui.main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import oracle.net.aso.e;

public class HomeOverviewController {
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis yAxis;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private PieChart pieChart;

	public AnchorPane overlay = null;
	private ObservableList<LocalDate> dayList = FXCollections.observableArrayList();
	private ObservableList<AppointmentCalendar> appointmentCalander;
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
//		updateStatistics();
		// Get an array with the days names.

		// Assign the month names as categories for the horizontal axis.
//        xAxis.setCategories(monthNames);
	}

	public void updateStatistics() {
		appointmentCalander = Main.appointmentCalendarController.getObservableList();
		for (int i = 0; i < 7; i++) {
			dayList.add(LocalDate.now().plusDays(i));
		}
		HashMap<LocalDate, Integer> appointmentCountMap = new HashMap<LocalDate, Integer>();
		for (AppointmentCalendar calender : appointmentCalander) {
			LinkedHashMap<LocalDateTime, String > appointmentMap =  calender.getAppointments();
			for(LocalDateTime localDateTime : appointmentMap.keySet()) {
				LocalDate ld = localDateTime.toLocalDate();
				if(appointmentCountMap.containsKey(ld)) {
					appointmentCountMap.put(ld, appointmentCountMap.get(ld) + 1);
				} else {
					appointmentCountMap.put(ld, 1);
				}
			}
		}
		appointmentCountMap.entrySet().stream().forEach(e -> System.out.println(e));
	}
}
