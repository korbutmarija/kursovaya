package Client.Admin;

import Client.Client;
import Server.Tables.Admins;
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

public class AdminsWindowController {
    @FXML
    private TableView InfoTable;
    @FXML
    private TableColumn UsernameColumn;
    @FXML
    private TableColumn PasswordColumn;
    @FXML
    private TableColumn StatusColumn;
    @FXML
    private TableColumn IDColumn;

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
private String row="";
private String admin;
    private ObservableList<Admins> AdData= FXCollections.observableArrayList();
    @FXML
    void initialize()
    {
        try {
            admin=(String) Client.is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        table();
        BackButton.setOnAction(event ->
        {
            Stage stage=(Stage)BackButton.getScene().getWindow();
            stage.close();
        });
        AddButton.setOnAction(event ->
        {
            openNewScene("/Client/Admin/AddAdminWindow.fxml");
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
                    String clm="sendUserID,"+admin;
                    Client.os.writeObject(clm);
                    String message=(String)Client.is.readObject();
                    if(row.equals(admin)|| row.equals(message))
                    {
                        CommentLabel.setText("Вы не можете удалить сами себя!");
                    }
                    else
                    {clm = "sendDeleteAdmin," + row;

                    Client.os.writeObject(clm);
                    message=(String)Client.is.readObject();
                    CommentLabel.setText(message);
                    }
;                } catch (IOException | ClassNotFoundException e) {
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
        AdData.clear();
        try {
            String clm = "sendAdmin";
            Client.os.writeObject(clm);
            String message = (String) Client.is.readObject();
            String messParts[] = message.split(";");
            clm = "sendCountAdmins";
            Client.os.writeObject(clm);
            message = (String) Client.is.readObject();
            for (int i = 0; i < Integer.parseInt(message); i++) {
                String[] mesParts = messParts[i].split(",");
                AdData.add(new Admins(mesParts[0], mesParts[1], mesParts[2],mesParts[3]));

            }
            IDColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("id"));
            UsernameColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("username"));
            PasswordColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("password"));
            StatusColumn.setCellValueFactory(new PropertyValueFactory<Admins, String>("status"));

            InfoTable.setItems(AdData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
