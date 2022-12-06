package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ServerThread extends Thread {
    private InetAddress addr;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private String clientMessage;
    private int counter;
    private DBConnection mdbc;
    private Statement stmt;
    private UsersTable usersTable;
    private AppointmentsTable ApTable;

    public ServerThread(Socket s, int counter) throws IOException, SQLException, ClassNotFoundException {
        this.counter = counter;
        this.os = new ObjectOutputStream(s.getOutputStream());
        this.is = new ObjectInputStream(s.getInputStream());
        this.addr = s.getInetAddress();
        this.mdbc = new DBConnection();
        Connection conn = this.mdbc.getDbConnection();


        try {
            this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            this.usersTable = new UsersTable(this.stmt, this.mdbc);
            this.ApTable = new AppointmentsTable(this.stmt, this.mdbc);
        } catch (SQLException e5) {
            System.out.println(e5);
        }
    }

    public void writeObj(String str) {
        this.clientMessage = str;

        try {
            this.os.writeObject(this.clientMessage);
        } catch (IOException e3) {
            System.err.println("I/О thread error" + e3);
        }
    }

    public void run()
    {
        boolean i = false;
        String messageToClient = "";
        String str;
        int temp=0;
        String ThreadStop = "";

        try {
            System.out.println("Сервер ожидает действий от клиента");

            while (!ThreadStop.equals("Exit")) {
                str = (String) this.is.readObject();
                System.out.println(str);
                String[] messageParts = str.split(",");
                switch (messageParts[0])
                {
                    case "checkLoginClient":
                        String UserLogin = messageParts[1];
                        String UserPassword = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginClient(UserLogin, UserPassword);
                        this.writeObj(messageToClient);
                        break;
                    case "checkLoginAdmin":
                        String UserLogin1 = messageParts[1];
                        String UserPassword1 = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginAdmin(UserLogin1, UserPassword1);
                        this.writeObj(messageToClient);
                        break;
                    case "addClient":
                        this.usersTable.AddClient(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5]);
                        break;
                    case "addAdmin":
                        this.usersTable.addAdmin(messageParts[1], messageParts[2]);
                        break;
                    case "checkUserInDB":
                        messageToClient = this.usersTable.checkUserInDB(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendUsername":
                        messageToClient = this.ApTable.getUsername(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendUser":
                        messageToClient = this.usersTable.getUser();
                        this.writeObj(messageToClient);
                        break;
                    case "sendAdmin":
                        messageToClient = this.usersTable.getAdmin();
                        this.writeObj(messageToClient);
                        break;
                    case "sendClient":
                        messageToClient = this.usersTable.getClient();
                        this.writeObj(messageToClient);
                        break;
                    case "sendAp":
                        messageToClient=this.ApTable.getAP(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendService":
                        messageToClient=this.ApTable.getService(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendMaster":
                        messageToClient=this.ApTable.getMaster(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendTypeService":
                        messageToClient=this.ApTable.getTypeService(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendNameTServ":
                        messageToClient=this.ApTable.getNameTService();
                        this.writeObj(messageToClient);
                        break;
                    case "sendNameService":
                        messageToClient=this.ApTable.getNameService(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendPrice":
                        messageToClient=this.ApTable.getPrice(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendDeleteUser":
                        this.usersTable.deleteUser(messageParts[1]);
                        break;
                    case "sendMakeAp":
                        messageToClient=this.ApTable.AddAp(messageParts[1],messageParts[2],messageParts[3],messageParts[4],messageParts[5],messageParts[6]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendMakeApp":
                        messageToClient=this.ApTable.AddApp(messageParts[1],messageParts[3],messageParts[4],messageParts[5],messageParts[6],messageParts[7]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendNameMaster":
                        messageToClient=this.ApTable.getNameMaster(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountAp":
                        messageToClient=Integer.toString(this.ApTable.countOfAppointments(messageParts[1]));
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountTypeSv":
                        messageToClient=Integer.toString(this.ApTable.countOfTypeSv());
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountServ":
                        messageToClient=Integer.toString(this.ApTable.countOfServices(messageParts[1]));
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountApp":
                        messageToClient=Integer.toString(this.ApTable.countOfApp());
                        this.writeObj(messageToClient);
                        break;
                    case "sendUserID":
                        messageToClient=Integer.toString(this.usersTable.getIdUser(messageParts[1]));
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountMaster":
                        messageToClient=Integer.toString(this.ApTable.countOfMasters(messageParts[1]));
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountUsers":
                        messageToClient=this.usersTable.getCountUsers();
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountAdmins":
                    messageToClient=this.usersTable.CountAdmins();
                    this.writeObj(messageToClient);
                    break;
                    case "sendNameClient":
                        messageToClient=this.usersTable.getNameClient();
                        this.writeObj(messageToClient);
                        break;
                    case "sendCountClients":
                    messageToClient=this.usersTable.CountClients();
                    this.writeObj(messageToClient);
                    break;
                    case "sendDeleteAdmin":
                        messageToClient=this.usersTable.deleteAdmin(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendApp":
                        messageToClient=this.ApTable.getApp();
                        this.writeObj(messageToClient);
                        break;
                    case "sendSaveAp":
                       this.ApTable.saveAp(messageParts[1]);
                        break;
                    case "sendDeleteClients":
                        this.usersTable.deleteClient(messageParts[1]);
                        break;
                    case "sendDeleteAp":
                        this.ApTable.deleteAp(messageParts[1]);
                        break;
                    case "sendDataPieS":
                        messageToClient=this.usersTable.getDataPieS();
                        this.writeObj(messageToClient);
                        break;
                    case "sendDataPieA":
                        messageToClient=this.usersTable.getDataPieA(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendFindAp":
                        messageToClient=this.ApTable.findAp(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;

                }}}catch (Exception var17) {
            System.err.println("Соединение закрыто");
        } finally {
            this.disconnect();
        }}

    private void disconnect() {
        try {
            if (this.os != null) {
                this.os.close();
            }
            if (this.is != null) {
                this.is.close();
            }
            System.out.println(this.addr.getHostName() + " Закрытие соединения " + this.counter + "го клиента");
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
