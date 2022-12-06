package Client.Admin;

import Client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisticWindowController {
    @FXML
    private PieChart SexPChart;
    @FXML
    private PieChart AgePChart;
    @FXML
    private Button OkButton;
    @FXML
    private CheckBox ManCBox;
    @FXML
    private CheckBox WomanCBox;
    @FXML
    void initialize()
    {
        String clm = "sendDataPieS";
        int man = 0;
        int woman = 0;
       // int a12=0,a17=0,a29=0,a64=0,a65=0;

        try {
            Client.os.writeObject(clm);
            String data = (String) Client.is.readObject();
            String[] count = data.split(",");
            man = Integer.parseInt(count[0]);
            woman = Integer.parseInt(count[1]);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Мужчины", man),
                new PieChart.Data("Женщины", woman)
        );
        SexPChart.setData(pieChartData);
        ManCBox.setSelected(true);
        ManCBox.setOnAction(event ->
        {
            statisticAge();
        });
        WomanCBox.setOnAction(event ->
        {
            statisticAge();
        });

        OkButton.setOnAction(event -> {
            Stage stage=(Stage)OkButton.getScene().getWindow();
            stage.close();
        });

    }
    void statisticAge()
    {
        int a12=0,a17=0,a29=0,a64=0,a65=0;
        if( ManCBox.isSelected() ||WomanCBox.isSelected()) {
            try {
                String clm2 = "";
                if (ManCBox.isSelected() && !WomanCBox.isSelected())
                    clm2 = "sendDataPieA,Мужской";
                if (!ManCBox.isSelected() && WomanCBox.isSelected())
                    clm2 = "sendDataPieA,Женский";
                if (ManCBox.isSelected() && WomanCBox.isSelected())
                    clm2 = "sendDataPieA,all";

                Client.os.writeObject(clm2);
                String data2 = (String) Client.is.readObject();
                String[] count = data2.split(",");
                a12 = Integer.parseInt(count[0]);
                a17 = Integer.parseInt(count[1]);
                a29 = Integer.parseInt(count[2]);
                a64 = Integer.parseInt(count[3]);
                a65 = Integer.parseInt(count[4]);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(

                    new PieChart.Data("0-12", a12),
                    new PieChart.Data("13-17", a17),
                    new PieChart.Data("18-29", a29),
                    new PieChart.Data("30-64", a64),
                    new PieChart.Data("65+", a65)
            );
            AgePChart.setData(pieChartData2);
        }
    }

}
