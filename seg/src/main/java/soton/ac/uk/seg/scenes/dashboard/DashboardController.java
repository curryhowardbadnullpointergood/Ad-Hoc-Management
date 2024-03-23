package soton.ac.uk.seg.scenes.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class DashboardController implements Initializable{

    @FXML
    private LineChart<?,?> linechart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        
        XYChart.Series series = new XYChart.Series();


    }

    
}
