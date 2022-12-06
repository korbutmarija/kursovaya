package Client.User;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegistrationWindowController {
    @FXML
    private TextField NameTField;

    @FXML
    private DatePicker BirthDPicker;

    @FXML
    private CheckBox ManCBox;

    @FXML
    private CheckBox WomanCBox;
    @FXML
    private TextField UsernameTField;

    @FXML
    private PasswordField UsPField;

    @FXML
    private Button OkButton;

    @FXML
    private Button BackButton;

    @FXML
    private Label CommentLabel;

    @FXML
    void initialize()
    {

        OkButton.setOnAction(event ->
        {
            if ((!ManCBox.isSelected() &&!WomanCBox.isSelected())|| (ManCBox.isSelected()
                    &&WomanCBox.isSelected())||NameTField.getText().isEmpty() ||UsernameTField.getText().isEmpty()||UsPField.getText().isEmpty()||BirthDPicker.getValue()==null)
            {
                CommentLabel.setText("Все поля не заполнены!");
        }
            else {
                String sex = " ";
                if (ManCBox.isSelected()) sex = "Мужской";
                if (WomanCBox.isSelected()) sex = "Женский";
                String Name = NameTField.getText().trim();
                String userName = UsernameTField.getText().trim();
                String Password = UsPField.getText().trim();
                LocalDate BirthDate = BirthDPicker.getValue();

                String clM = "checkUserInDB," + userName;
                try {
                    Client.os.writeObject(clM);
                    String mes = (String) Client.is.readObject();
                    if (mes.equals("success")) {
                        String clientMessage = "addClient," + Name + "," + BirthDate + "," + sex + "," + userName + "," + Password;
                        try {
                            Client.os.writeObject(clientMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage stage = (Stage) OkButton.getScene().getWindow();
                        stage.close();
                    } else if (mes.equals("fail")) {
                        UsernameTField.clear();
                        CommentLabel.setText("Пользователь с таким логином уже существует!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        BackButton.setOnAction(event ->
        {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
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
