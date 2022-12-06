package Client.User;

import Client.Client;
import Server.Tables.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class UpComingApWindowController {
    @FXML
    private TableView InfoTable;
    @FXML
    private TableColumn ServicesColumn;

    @FXML
    private TableColumn MasterColumn;

    @FXML
    private TableColumn DateColumn;

    @FXML
    private TableColumn TimeColumn;

    @FXML
    private TableColumn PriceColumn;
    @FXML
    private Button BackButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Label commentLabel;

    private ObservableList<Appointments> ApData= FXCollections.observableArrayList();
    private String id;
    @FXML
    void initialize()
    {
        try{

            id=(String)Client.is.readObject();
            String clm="sendAp,"+id;
            Client.os.writeObject(clm);
            String message=(String)Client.is.readObject();
            String messParts[]=message.split(";");
            clm="sendCountAp,"+id;
            Client.os.writeObject(clm);
           message=(String)Client.is.readObject();
            for (int i = 0; i < Integer.parseInt(message); i++)
            {
                String[] mesParts = messParts[i].split(",");
                    ApData.add(new Appointments(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4]));

            }
            ServicesColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointments"));
            MasterColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("master"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("date"));
            TimeColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("time"));
            PriceColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("price"));
            InfoTable.setItems(ApData);

        } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
        BackButton.setOnAction(event -> {
            Stage stage=(Stage)BackButton.getScene().getWindow();
            stage.close();
        });
        SaveButton.setOnAction(event ->
        {
            String clm="sendSaveAp,"+id;
            try {
                Client.os.writeObject(clm);

            } catch (IOException e) {
                e.printStackTrace();
            }
            commentLabel.setText("Сохранено");

        });
    }
}
