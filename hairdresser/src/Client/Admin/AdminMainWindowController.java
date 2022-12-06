package Client.Admin;

import Client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMainWindowController {
    @FXML
    private Button UsersButton;

    @FXML
    private Button AdminsButton;

    @FXML
    private Button AppointmentsButton;

    @FXML
    private Button ClientsButton;

    @FXML
    private Button StatisticsButton;

    @FXML
    private Button ExitButton;
    @FXML
    private Label AdminLabel;

    @FXML
    void initialize()
    {
        try {
            AdminLabel.setText((String) Client.is.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        UsersButton.setOnAction(event ->
        {
            openNewScene("/Client/Admin/UsersWindow.fxml");
        });

        AdminsButton.setOnAction(event ->
        {
            String clM = "sendUsername," + AdminLabel.getText();
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/Admin/AdminsWindow.fxml");
        });

        ClientsButton.setOnAction(event -> {

            openNewScene("/Client/Admin/ClientsWindow.fxml");
        });

        AppointmentsButton.setOnAction(event ->
        {
            openNewScene("/Client/Admin/ApWindow.fxml");
        });
        StatisticsButton.setOnAction(event ->
        {
            openNewScene("/Client/Admin/StatisticWindow.fxml");
        });
        ExitButton.setOnAction(event -> {
            ExitButton.getScene().getWindow().hide();
            openNewScene("/Client/LogInWindow.fxml");
        });
    }
    public void openNewScene(String window)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
