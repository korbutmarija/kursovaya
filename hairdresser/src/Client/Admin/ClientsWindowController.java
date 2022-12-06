package Client.Admin;

import Client.Client;
import Server.Tables.Clients;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class ClientsWindowController {
    @FXML
    private TableColumn IDColumn;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn BirthDateColumn;
    @FXML
    private TableColumn SexColumn;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button BackButton;
@FXML
    private Button UpdateButton;
@FXML
    private Label CommentLabel;
@FXML
    private TableView InfoTable;
private String admin;
private String row="";
    private ObservableList<Clients> ClData= FXCollections.observableArrayList();
@FXML
    void initialize()
{

    table();
    BackButton.setOnAction(event ->
    {
        Stage stage=(Stage)BackButton.getScene().getWindow();
        stage.close();
    });
    AddButton.setOnAction(event ->
    {
        openNewScene("/Client/User/RegistrationWindow.fxml");
        table();

    }   );
    InfoTable.setOnMouseClicked(event ->
            {
                InfoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                        //Check whether item is selected and set value of selected item to Label
                        if(InfoTable.getSelectionModel().getSelectedItem() != null)
                        {
                            TableView.TableViewSelectionModel selectionModel = InfoTable.getSelectionModel();
                            ObservableList selectedCells = selectionModel.getSelectedCells();
                            TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                            Object val = tablePosition.getTableColumn().getCellData(newValue);
                            row=(String)val;

                        }
                    }
                });
            }
    );
    DeleteButton.setOnAction(event ->
    {

        if(!row.equals("")) {
            try {
               String clm = "sendDeleteClients," + row;

                    Client.os.writeObject(clm);

                ;                } catch (IOException  e) {
                e.printStackTrace();
            }
        }
        table();
        //DeleteButton.setDisable(true);
    });
    UpdateButton.setOnAction(event ->
    {
        table();
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
    private void table() {
        ClData.clear();
        try {
            String clm = "sendClient";
            Client.os.writeObject(clm);
            String message = (String) Client.is.readObject();
            String messParts[] = message.split(";");
            clm = "sendCountClients";
            Client.os.writeObject(clm);
            message = (String) Client.is.readObject();
            for (int i = 0; i < Integer.parseInt(message); i++) {
                String[] mesParts = messParts[i].split(",");
                ClData.add(new Clients(mesParts[0], mesParts[1], mesParts[2],mesParts[3]));

            }
            IDColumn.setCellValueFactory(new PropertyValueFactory<Clients, String>("id"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<Clients, String>("name"));
            BirthDateColumn.setCellValueFactory(new PropertyValueFactory<Clients, String>("date"));
            SexColumn.setCellValueFactory(new PropertyValueFactory<Clients, String>("sex"));

            InfoTable.setItems(ClData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
