package Client.Admin;

import Client.Client;
import Server.Tables.Appointments;
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

public class ApWindowController {
    @FXML
    private TableColumn IDColumn;

    @FXML
    private TableColumn NameClColumn;
    @FXML
    private TableColumn ServiceColumn;
    @FXML
    private TableColumn MasterColumn;
    @FXML
    private TableColumn DateColumn;
    @FXML
    private TableColumn TimeColumn;
    @FXML
    private TableColumn PriceColumn;
    @FXML
    private TableView InfoTable;
    @FXML
    private Button AddButton;
    @FXML
    private Button BackButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;


    private ObservableList<Appointments> ApData= FXCollections.observableArrayList();
    String row="";
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
            openNewScene("/Client/Admin/AddApWindow.fxml");
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

                    String clm = "sendDeleteAp," + row;

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
        searchButton.setOnAction(event ->
        {
            String word=searchField.getText();
            if(word.equals(""))
                table();
            else {
                ApData.clear();
                String clm = "sendFindAp," +word;
                try {
                    Client.os.writeObject(clm);
                    String message=(String)Client.is.readObject();
                    String size[]=message.split("!");
                    String messPart[]=size[0].split(";");
                    for(int i=0;i<Integer.parseInt(size[1]);i++)
                    {
                        String data[]=messPart[i].split(",");
                        ApData.add(new Appointments(data[0], data[1],data[2], data[3], data[4],data[5],data[6]));
                    }
                    IDColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("id"));
                    NameClColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("name"));
                    ServiceColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointments"));
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
            }
        });
    }
    void table()
    {
        try{
            ApData.clear();

            String clm="sendApp";
            Client.os.writeObject(clm);
            String message=(String)Client.is.readObject();
            String messParts[]=message.split(";");
            clm="sendCountApp";
            Client.os.writeObject(clm);
            message=(String)Client.is.readObject();
            for (int i = 0; i < Integer.parseInt(message); i++)
            {
                String[] mesParts = messParts[i].split(",");
                ApData.add(new Appointments(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4],mesParts[5],mesParts[6]));

            }
            IDColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("id"));
            NameClColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("name"));
            ServiceColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("appointments"));
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
