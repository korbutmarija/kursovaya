package Client.User;

import Client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MakingApWindowController {
    @FXML
    private ComboBox TypeCBox;

    @FXML
    private ComboBox ServiceCBox;

    @FXML
    private ComboBox MasterCBox;

    @FXML
    private DatePicker DateDPicker;

    @FXML
    private TextField TimeTField;

    @FXML
    private Label PriceLabel;

    @FXML
    private Label CommentLabel;

    @FXML
    private Button OkButton;

    @FXML
    private Button BackButton;
    private ObservableList<String> typeSv= FXCollections.observableArrayList();
    private ObservableList<String> nameSv= FXCollections.observableArrayList();
    private ObservableList<String> nameM= FXCollections.observableArrayList();
    private String idName,services,master,time,price,username;
    private LocalTime timeC;
    @FXML
    void initialize()
    {

        try {
            username=(String)Client.is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            String clm="sendNameTServ";
            Client.os.writeObject(clm);
            String message=(String)Client.is.readObject();
            String messPart[]=message.split(",");
            clm="sendCountTypeSv";
            Client.os.writeObject(clm);
            message=(String)Client.is.readObject();
            for(int i=0;i<Integer.parseInt(message);i++)
            {
                typeSv.add(messPart[i]);
            }
            TypeCBox.setItems(typeSv);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TypeCBox.setOnAction(event -> {
            idName=(String)TypeCBox.getValue();
        });

       ServiceCBox.setOnMouseClicked(event -> {
           if(!TypeCBox.getSelectionModel().isEmpty())
        {
            nameSv.clear();
            try
            {
                String clm="sendTypeService,"+idName;
                Client.os.writeObject(clm);
                String message=(String)Client.is.readObject();
                String id=message;
                clm="sendNameService,"+id;
                Client.os.writeObject(clm);
                message=(String)Client.is.readObject();
                String messPart[]=message.split(",");
                clm="sendCountServ,"+id;
                Client.os.writeObject(clm);
                message=(String)Client.is.readObject();
                for(int i=0;i<Integer.parseInt(message);i++)
                {
                    nameSv.add(messPart[i]);
                }

                ServiceCBox.setItems(nameSv);


            }catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    });
        MasterCBox.setOnMouseClicked(event -> {
            if(!TypeCBox.getSelectionModel().isEmpty())
            {
                nameM.clear();
                try
                {
                    String clm="sendTypeService,"+idName;
                    Client.os.writeObject(clm);
                    String message=(String)Client.is.readObject();
                    String id=message;
                    clm="sendNameMaster,"+id;
                    Client.os.writeObject(clm);
                    message=(String)Client.is.readObject();
                    String messPart[]=message.split(",");
                    clm="sendCountMaster,"+id;
                    Client.os.writeObject(clm);
                    message=(String)Client.is.readObject();
                    for(int i=0;i<Integer.parseInt(message);i++)
                    {
                        nameM.add(messPart[i]);
                    }

                    MasterCBox.setItems(nameM);


                }catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
TimeTField.setOnMouseClicked(event -> {
    if(!ServiceCBox.getSelectionModel().isEmpty())
{
    try {
        services = (String) ServiceCBox.getValue();
        String clm = "sendPrice," + services;
        Client.os.writeObject(clm);
        String message=(String)Client.is.readObject();
        PriceLabel.setText(message);
        PriceLabel.setVisible(true);
    }catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    });
OkButton.setOnAction(event -> {
    boolean flag=true;
    try {
        timeC = LocalTime.parse(TimeTField.getText());
    }catch(DateTimeException e)
    {
        flag=false;
    }

    if(ServiceCBox.getSelectionModel().isEmpty()||TypeCBox.getSelectionModel().isEmpty()||MasterCBox.getSelectionModel().isEmpty()
    || DateDPicker.getValue()==null || TimeTField.getText().isEmpty() || PriceLabel.getText().isEmpty())
    {
        CommentLabel.setText("Не все поля заполнены!");
    }
    else if(DateDPicker.getValue().compareTo(LocalDate.now())<0 )
        CommentLabel.setText("Этот день уже прошел!");
    else if(DateDPicker.getValue().compareTo(LocalDate.now())==0 && flag==true)
    {
          if(timeC.compareTo(LocalTime.now())<0)
          {
              CommentLabel.setText("Время на выбранную дату прошло!");
          }
    }
    else if(flag==false){
        CommentLabel.setText("Неправильный формат времени!");
    }
    else
    {
       try {
           services = (String) ServiceCBox.getValue();
           master = (String) MasterCBox.getValue();
           LocalDate date = DateDPicker.getValue();
           time = TimeTField.getText();
           price = PriceLabel.getText();
           String cml = "sendMakeAp," +username+","+ services + "," + master + "," + date + "," + time + "," + price;
           Client.os.writeObject(cml);
           String message=(String)Client.is.readObject();
           if(message.equals("true"))
           {
               Stage stage=(Stage)OkButton.getScene().getWindow();
               stage.close();
           }

       }catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
    }
});
BackButton.setOnAction(event ->
{
    Stage stage=(Stage)BackButton.getScene().getWindow();
    stage.close();
});
    }
}
