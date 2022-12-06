package Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageWindowController {
    @FXML
private Button EnterMainButton;

    @FXML
    void initialize()
    {
        Client.Connect();
        EnterMainButton.setOnAction(event -> {
            EnterMainButton.getScene().getWindow().hide();
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
        stage.show();
    }
}
