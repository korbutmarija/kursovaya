package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
public class LogInWindowController {
    @FXML
    private TextField UsernameTField;

    @FXML
    private PasswordField UsPasswordPField;

    @FXML
    private Button UsEnterButton;

    @FXML
    private Button RegButton;

    @FXML
    private Button UsBackButton;

    @FXML
    private TextField AdminNameTField;

    @FXML
    private PasswordField AdPasswordPField;

    @FXML
    private Button AdEnterButton;

    @FXML
    private Button AdBackButton;

    @FXML
    private Label CommentLabel;

    @FXML
    private Label UsCommentLabel;
    public String message;

    @FXML
    void initialize()
    {
        UsEnterButton.setOnAction(event ->
        {
            if(UsernameTField.getText().isEmpty() || UsPasswordPField.getText().isEmpty())
            {
                UsCommentLabel.setText("Не все поля заполнены!");
            }
            else {
                String UserNameText = UsernameTField.getText().trim();
                String UserPassword = UsPasswordPField.getText().trim();
                String clientMessage = "checkLoginClient," + UserNameText + "," + UserPassword;
                try {
                    Client.os.writeObject(clientMessage);
                    message = (String) Client.is.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (message.equals("successClient")) {
                    String clM = "sendUsername," + UserNameText;
                    try {
                        Client.os.writeObject(clM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UsEnterButton.getScene().getWindow().hide();
                    openNewScene("/Client/User/ClientMainWindow.fxml");
                } else if (message.equals("fail"))
                    UsCommentLabel.setText("Неправильно введены логин или пароль!");
            }
        });
        AdEnterButton.setOnAction(event -> {
            if(AdminNameTField.getText().isEmpty() || AdPasswordPField.getText().isEmpty())
            {
                CommentLabel.setText("Не все поля заполнены!");
            }
            else {
                String AdminNameText = AdminNameTField.getText().trim();
                String AdminPassword = AdPasswordPField.getText().trim();
                String clientMessage = "checkLoginAdmin," + AdminNameText + "," + AdminPassword;

                try {
                    Client.os.writeObject(clientMessage);
                    message = (String) Client.is.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (message.equals("successAdmin")) {
                    String clM = "sendUsername," + AdminNameText;
                    try {
                        Client.os.writeObject(clM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AdEnterButton.getScene().getWindow().hide();
                    openNewScene("/Client/Admin/AdminMainWindow.fxml");
                } else if (message.equals("fail"))
                    CommentLabel.setText("Неправильно введены логин или пароль!");
            }
        });

        RegButton.setOnAction(event ->
        {
            openNewScene("/Client/User/RegistrationWindow.fxml");
        });
        UsBackButton.setOnAction(event ->
        {
            UsBackButton.getScene().getWindow().hide();
            openNewScene("/Client/HomePageWindow.fxml");
        });
        AdBackButton.setOnAction(event ->
        {
            AdBackButton.getScene().getWindow().hide();
            openNewScene("/Client/HomePageWindow.fxml");
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
