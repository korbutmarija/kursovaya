package Client.Admin;

import Client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddAdminWindowController {
    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PassPField;

    @FXML
    private Label CommentLabel;

    @FXML
    private Button OkButton;

    @FXML
    private Button BackButton;
    @FXML
    void initialize()
    {
        OkButton.setOnAction(event ->
        {
            if (UsernameField.getText().isEmpty() ||PassPField.getText().isEmpty())
            {
                CommentLabel.setText("Все поля не заполнены!");
            }
            else {
                String userName = UsernameField.getText().trim();
                String Password = PassPField.getText().trim();

                String clM = "checkUserInDB," + userName;
                try {
                    Client.os.writeObject(clM);
                    String mes = (String) Client.is.readObject();
                    if (mes.equals("success")) {
                        String clientMessage = "addAdmin," + userName + ","  + Password;
                        try {
                            Client.os.writeObject(clientMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage stage = (Stage) OkButton.getScene().getWindow();
                        stage.close();
                    } else if (mes.equals("fail")) {
                        UsernameField.clear();
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
}
