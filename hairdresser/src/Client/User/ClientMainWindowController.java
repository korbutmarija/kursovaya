package Client.User;

import Client.Client;
import Server.Tables.Masters;
import Server.Tables.Services;
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

public  class ClientMainWindowController  {
    @FXML
    private Menu ApMenu;

    @FXML
    private MenuItem NailServiceMItem;

    @FXML
    private Button ExitButton;

    @FXML
    private MenuItem HairMasterMItem;

    @FXML
    private MenuItem HairServiceMItem;

    @FXML
    private MenuItem ApMItem;

    @FXML
    private Label UsernameLabel;

    @FXML
    private MenuItem NailMasterMItem;

    @FXML
    private Button AppointmentButton;
    @FXML
    private TableView ServiceTable;
    @FXML
    private TableView MasterTable;
    @FXML
    private TableColumn SNameTColumn;
    @FXML
    private TableColumn PriceTColumn;
    @FXML
    private TableColumn MNameTColumn;
    @FXML
    private TableColumn ExperienceTColumn;

    private ObservableList<Services> serviceHData= FXCollections.observableArrayList();
    private ObservableList<Services> serviceNData= FXCollections.observableArrayList();
    private ObservableList<Masters> masterHData= FXCollections.observableArrayList();
    private ObservableList<Masters> masterNData= FXCollections.observableArrayList();
    @FXML
    void initialize()
    {
        try {
            UsernameLabel.setText((String)Client.is.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ApMItem.setOnAction(event ->
        {
            String clM = "sendUsername," + UsernameLabel.getText();
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/User/UpComingApWindow.fxml");
        });
        HairServiceMItem.setOnAction(event ->
        {
            ServiceTable.setVisible(true);
            MasterTable.setVisible(false);
            try{
                String clm="sendTypeService,"+"Парикмахерская";
                Client.os.writeObject(clm);
                String type=(String)Client.is.readObject();
                clm="sendService,"+type;
                Client.os.writeObject(clm);
                String message=(String)Client.is.readObject();
                String messParts[]=message.split(";");
                clm="sendCountServ,"+type;
                Client.os.writeObject(clm);
                message=(String) Client.is.readObject();
                for (int i = 0; i < Integer.parseInt(message); i++)
                {
                    String[] mesParts = messParts[i].split(",");
                    serviceHData.add(new Services(mesParts[0], mesParts[1]));

                }
                SNameTColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("name"));
                PriceTColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("price"));
                ServiceTable.setItems(serviceHData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        NailServiceMItem.setOnAction(event ->
        {
            ServiceTable.setVisible(true);
            MasterTable.setVisible(false);
            try{
                String clm="sendTypeService,"+"Маникюра и педикюра";
                Client.os.writeObject(clm);
                String type=(String) Client.is.readObject();
                clm="sendService,"+type;
                Client.os.writeObject(clm);
                String message=(String) Client.is.readObject();
                String messParts[]=message.split(";");
                clm="sendCountServ,"+type;
                Client.os.writeObject(clm);
                message=(String) Client.is.readObject();
                for (int i = 0; i < Integer.parseInt(message); i++)
                {
                    String[] mesParts = messParts[i].split(",");
                    serviceNData.add(new Services(mesParts[0], mesParts[1]));

                }
                SNameTColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("name"));
                PriceTColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("price"));
                ServiceTable.setItems(serviceNData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        HairMasterMItem.setOnAction(event ->
        {
            MasterTable.setVisible(true);
            ServiceTable.setVisible(false);
            try{
                String clm="sendTypeService,"+"Парикмахерская";
                Client.os.writeObject(clm);
                String type=(String) Client.is.readObject();
                clm="sendMaster,"+type;
                Client.os.writeObject(clm);
                String message=(String) Client.is.readObject();
                String messParts[]=message.split(";");
                clm="sendCountMaster,"+type;
                Client.os.writeObject(clm);
                message=(String) Client.is.readObject();
                for (int i = 0; i < Integer.parseInt(message); i++)
                {
                    String[] mesParts = messParts[i].split(",");
                    masterHData.add(new Masters(mesParts[0], mesParts[1]));

                }
                MNameTColumn.setCellValueFactory(new PropertyValueFactory<Masters, String>("name"));
                ExperienceTColumn.setCellValueFactory(new PropertyValueFactory<Masters, String>("experience"));
                MasterTable.setItems(masterHData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        NailMasterMItem.setOnAction(event ->
        {
            MasterTable.setVisible(true);
            ServiceTable.setVisible(false);
            try{
                String clm="sendTypeService,"+"Маникюра и педикюра";
                Client.os.writeObject(clm);
                String type=(String) Client.is.readObject();
                clm="sendMaster,"+type;
                Client.os.writeObject(clm);
                String message=(String)Client.is.readObject();
                String messParts[]=message.split(";");
                clm="sendCountMaster,"+type;
                Client.os.writeObject(clm);
                message=(String) Client.is.readObject();
                for (int i = 0; i < Integer.parseInt(message); i++)
                {
                    String[] mesParts = messParts[i].split(",");
                    masterNData.add(new Masters(mesParts[0], mesParts[1]));

                }
                MNameTColumn.setCellValueFactory(new PropertyValueFactory<Masters, String>("name"));
                ExperienceTColumn.setCellValueFactory(new PropertyValueFactory<Masters, String>("experience"));
                MasterTable.setItems(masterNData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        ExitButton.setOnAction(event -> {
            ExitButton.getScene().getWindow().hide();
            openNewScene("/Client/LogInWindow.fxml");
        });
        AppointmentButton.setOnAction(event ->
        {
            String clM = "sendUsername," + UsernameLabel.getText();
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/User/MakingApWindow.fxml");
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