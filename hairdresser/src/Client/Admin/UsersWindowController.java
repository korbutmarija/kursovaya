package Client.Admin;

import Client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Server.Tables.Users;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class UsersWindowController {
    @FXML
    private javafx.scene.control.TableColumn IDColumn;

    @FXML
    private javafx.scene.control.TableColumn UsernameColumn;

    @FXML
    private TableColumn PasswordColumn;

    @FXML
    private Button AddButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button BackButton;
    @FXML
    private TableView InfoTable;

    @FXML
    private Button UpdateButton;

    private ObservableList<Users> UsData= FXCollections.observableArrayList();
    private String row="";
    @FXML
    void initialize()
    {
        table();
        BackButton.setOnAction(event ->
        {
            Stage stage=(Stage)BackButton.getScene().getWindow();
            stage.close();
        });
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
        AddButton.setOnAction(event ->
        {
            openNewScene("/Client/User/RegistrationWindow.fxml");
            table();
        });
        DeleteButton.setOnAction(event ->
        {

            if(!row.equals("")) {
                try {
                    String clm = "sendDeleteUser," + row;

                    Client.os.writeObject(clm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            table();

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
        UsData.clear();
        try {
            String clm = "sendUser";
            Client.os.writeObject(clm);
            String message = (String) Client.is.readObject();
            String messParts[] = message.split(";");
            clm = "sendCountUsers";
            Client.os.writeObject(clm);
            message = (String) Client.is.readObject();
            for (int i = 0; i < Integer.parseInt(message); i++) {
                String[] mesParts = messParts[i].split(",");
                UsData.add(new Users(mesParts[0], mesParts[1], mesParts[2]));

            }
            IDColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("id"));
            UsernameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
            PasswordColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));

            InfoTable.setItems(UsData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
