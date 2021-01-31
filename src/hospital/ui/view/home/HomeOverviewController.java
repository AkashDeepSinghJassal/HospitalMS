package hospital.ui.view.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

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
    private ObservableList<String> dayList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with the days names.
        
        // Assign the month names as categories for the horizontal axis.
//        xAxis.setCategories(monthNames);
    }
}
