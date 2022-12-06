package Server;
import java.io.*;
import java.sql.*;

public class AppointmentsTable extends DataTable implements ResultFromTable{
    public AppointmentsTable (Statement stmt, DBConnection mdbc) {
        super(stmt, mdbc);
        try {
            connection=mdbc.getDbConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    private static Connection connection;



    public ResultSet getResultFromTable(String table) {
        ResultSet rs = null;

        try
        {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }
    public String getUsername(String username)
    {
        return username;
    }
    public String getTypeService(String typeService)
    {
        String id="";
        ResultSet rs = this.getResultFromTable(Const.TYPESERVICE_TABLE);
        try {
            while (rs.next())
            {
                String idS=rs.getString(Const.TS_ID);
                String name=rs.getString(Const.TS_NAME);
                if(typeService.equals(name))
                {
                    id=idS;
                }
            }
        }catch (Exception var12)
        {
            System.out.println("exception in getTypeService");
        } finally {
            this.mdbc.close(rs);
        }
        return id;
    }
    public String getNameTService()
    {
        String name="";
        ResultSet rs = this.getResultFromTable(Const.TYPESERVICE_TABLE);
        try {
            while (rs.next())
            {
                String nameS=rs.getString(Const.TS_NAME);

                    name+=nameS+",";

            }
        }catch (Exception var12)
        {
            System.out.println("exception in getNameTService");
        } finally {
            this.mdbc.close(rs);
        }
        return name;
    }
    public String getNameService(String type)
    {
        String name="";
        ResultSet rs = this.getResultFromTable(Const.SERVICE_TABLE);
        try {
            while (rs.next())
            {
                String nameS=rs.getString(Const.SERVICE_NAME);
                String typeS=rs.getString(Const.SERVICE_TYPEID);
                if(typeS.equals(type))
                name+=nameS+",";

            }
        }catch (Exception var12)
        {
            System.out.println("exception in getNameService");
        } finally {
            this.mdbc.close(rs);
        }
        return name;
    }
    public String getPrice(String name)
    {
        String price="";
        ResultSet rs = this.getResultFromTable(Const.SERVICE_TABLE);
        try {
            while (rs.next())
            {
                String nameS=rs.getString(Const.SERVICE_NAME);
                String priceS=rs.getString(Const.SERVICE_PRICE);
                if(name.equals(nameS))
                    price=priceS;

            }
        }catch (Exception var12)
        {
            System.out.println("exception in getPrice");
        } finally {
            this.mdbc.close(rs);
        }
        return price;
    }
    public String AddAp(String client,String services, String master, String date, String time, String price)
    {


        String mes="false";
        int clientID=getIdUser(client);
        int masterID=getIdMaster(master);

        try {

            String insert = "INSERT INTO " + Const.APPOINTMENT_TABLE + "(" +
                Const.APPOINTMENT_CLIENT + "," + Const.APPOINTMENT_APPOINTMENTS + ","
                + Const.APPOINTMENT_MASTER+ ","  +Const.APPOINTMENT_DATE+ "," + Const.APPOINTMENT_TIME+
                "," + Const.APPOINTMENT_PRICE+")" +
                "VALUES (" +clientID+","+ this.quotate(services) + ","+masterID+"," + this.quotate(date) +"," + this.quotate(time)+"," +price+ ")";

            this.stmt.executeUpdate(insert);
            mes="true";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mes;
    }
    public String AddApp(String name,String services, String master, String date, String time, String price)
    {


        String mes="false";
        String clientID[]=name.split(",");
        int masterID=getIdMaster(master);
        try {

            String insert = "INSERT INTO " + Const.APPOINTMENT_TABLE + "(" +
                    Const.APPOINTMENT_CLIENT + "," + Const.APPOINTMENT_APPOINTMENTS + ","
                    + Const.APPOINTMENT_MASTER+ ","  +Const.APPOINTMENT_DATE+ "," + Const.APPOINTMENT_TIME+
                    "," + Const.APPOINTMENT_PRICE+")" +
                    "VALUES (" +clientID[0]+","+ this.quotate(services) + ","+masterID+"," + this.quotate(date) +"," + this.quotate(time)+"," +price+ ")";
PreparedStatement pstmt=connection.prepareStatement(insert);
            this.stmt.executeUpdate(insert);
            mes="true";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mes;
    }
    public String getNameMaster(String type)
    {
        String name="";
        ResultSet rs = this.getResultFromTable(Const.MASTER_TABLE);
        try {
            while (rs.next())
            {
                String nameS=rs.getString(Const.MASTER_NAME);
                String typeS=rs.getString(Const.MASTER_TYPEID);
                if(typeS.equals(type))
                    name+=nameS+",";

            }
        }catch (Exception var12)
        {
            System.out.println("exception in getNameMaster");
        } finally {
            this.mdbc.close(rs);
        }
        return name;
    }
    public String getService(String id)
    {
        String service="";
        ResultSet rs = this.getResultFromTable(Const.SERVICE_TABLE);
        try {
            while (rs.next())
            {
                String master="";
                String typeService=rs.getString(Const.SERVICE_TYPEID);
                String name=rs.getString(Const.SERVICE_NAME);
                String price = rs.getString(Const.SERVICE_PRICE);
                if(typeService.equals(id))
                {
                    service+=name+","+price+";";
                }
            }
        }catch (Exception var12)
        {
            System.out.println("exception in getService");
        } finally {
            this.mdbc.close(rs);
        }
        return service;
    }
    public String getMaster(String id)
    {
        String master="";
        ResultSet rs = this.getResultFromTable(Const.MASTER_TABLE);
        try {
            while (rs.next())
            {
                String typeService=rs.getString(Const.MASTER_TYPEID);
                String name=rs.getString(Const.MASTER_NAME);
                String experience = rs.getString(Const.MASTER_EXPERIENCE);
                if(typeService.equals(id))
                {
                    master+=name+","+experience+";";
                }
            }
        }catch (Exception var12)
        {
            System.out.println("exception in getService");
        } finally {
            this.mdbc.close(rs);
        }
        return master;
    }
    public String getAP(String username)
    {
        String select = "SELECT * FROM hairdresser.appointments";
        int id=getIdUser(username);
        String appointment="";
        ResultSet rs ;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs= pstmt.executeQuery();
            while (rs.next())
            {
                String master="";
                int client = rs.getInt(Const.APPOINTMENT_CLIENT);
                String services = rs.getString(Const.APPOINTMENT_APPOINTMENTS);
                int masterID=rs.getInt(Const.APPOINTMENT_MASTER);
                String date=rs.getString(Const.APPOINTMENT_DATE);
                String time=rs.getString(Const.APPOINTMENT_TIME);
                String price=rs.getString(Const.APPOINTMENT_PRICE);
                if(client==id)
                {
                    master=getMaster(masterID);
                    appointment+=services+","+master+","+date+","+time+","+price+";";
                }

            }
        }catch (Exception var12)
        {
            System.out.println("exception in Table of appointments");
        }

        return appointment;
    }
    public String getApp()
    {
        String select = "SELECT * FROM hairdresser.appointments";
        String select2="SELECT * FROM hairdresser."+Const.CLIENT_TABLE;
        String appointment="";
        String idC="";
        String nameC="";
        int size=0;
        ResultSet rs,rs2 ;

        try {

            PreparedStatement pstmt2=connection.prepareStatement(select2);

            rs2=pstmt2.executeQuery();
            while(rs2.next())
            {
                idC+=rs2.getString(Const.CLIENT_ID)+",";
                nameC+=rs2.getString(Const.CLIENT_NAME)+",";
                size++;
            }
            String messPart[]=idC.split(",");
            String namePart[]=nameC.split(",");
            for(int i=0;i<size;i++){
                PreparedStatement pstmt=connection.prepareStatement(select);
                rs= pstmt.executeQuery();
            while (rs.next())
            {
                String master="";
                String client = rs.getString(Const.APPOINTMENT_CLIENT);
                String id=rs.getString(Const.APPOINTMENT_ID);
                String services = rs.getString(Const.APPOINTMENT_APPOINTMENTS);
                int masterID=rs.getInt(Const.APPOINTMENT_MASTER);
                String date=rs.getString(Const.APPOINTMENT_DATE);
                String time=rs.getString(Const.APPOINTMENT_TIME);
                String price=rs.getString(Const.APPOINTMENT_PRICE);
                if(client.equals(messPart[i])) {
                    master = getMaster(masterID);
                    appointment +=id +","+namePart[i]+","+services + "," + master + "," + date + "," + time + "," + price + ";";

                }
            }}
        }catch (Exception var12)
        {
            System.out.println("exception in Table of appointments");
        }

        return appointment;
    }
    String findAp(String word)
    {
        String message="";
        String data = getApp();
        String rows[]=data.split(";");
        int count=countOfApp();
        int number=0;
        for(int i=0;i<count;i++)
        {
            String ap[]=rows[i].split(",");
            for(int k=0;k<7;k++)
            {
                if(word.equals(ap[k]))
                {
                    message+=rows[i]+";";
                    number++;
                    break;
                }
            }

        }
        message+="!"+number;
return message;
    }
    public void saveAp(String id)
    {

        String mess=getAP(id);
        String meesPart[]=mess.split(";");
        int count=countOfAppointments(id);

        save(false,"Мои записи\r\n");
        for(int i=0;i<count;i++) {
            String value[]=meesPart[i].split(",");
            String text = "Услуги: " +value[0]+ "\r\n" ;
            save(true, text);
            String text1 = "Мастер: " + value[1] +"\r\n" ;
            save(true, text1);
            String text2 = "Дата: " + value[2]+ "\r\n" ;
            save(true, text2);
            String text3 = "Время: " + value[3]+"\r\n" ;
            save(true, text3);
            String text4 = "Стоимость: " + value[4]+"\r\n" ;
            save(true, text4);
            String line="----------------------------------\r\n";
            save(true,line);
        }
    }
    public void save (Boolean bool, String text)
    {
        try(FileOutputStream fos=new FileOutputStream("D://Мои документы//Рабочий стол//Курсач//Hairdresser//appointment.txt", bool))
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
    public int getIdUser(String username)
    {
        ResultSet resultSet;
        int id = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';");
            while (resultSet.next())
                id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }
    public int getIdMaster(String name)
    {

        int id = 0;
String select="SELECT * FROM hairdresser."+ Const.MASTER_TABLE;
        try {
            ResultSet rs;
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String nameS=rs.getString(Const.MASTER_NAME);
                if(name.equals(nameS))
                id = rs.getInt(Const.MASTER_ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }
    public String getMaster(int masterID)
    {
        String master="";
        ResultSet resultSet;
        try {
            resultSet = stmt.executeQuery("SELECT " + Const.MASTER_NAME + " FROM " + Const.MASTER_TABLE + " WHERE " + Const.MASTER_ID + " LIKE '" + masterID + "';");
            while (resultSet.next())
                master= resultSet.getString(Const.MASTER_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return master;
    }
public int countOfAppointments(String id)
{
    int count=0;
    int idAp=getIdUser(id);
    ResultSet resultSet;
    try {
       //resultSet = stmt.executeQuery("SELECT COUNT(*) FROM " + Const.APPOINTMENT_TABLE + ";");
        resultSet =this.getResultFromTable(Const.APPOINTMENT_TABLE);
        while (resultSet.next())
        {
            int clientId=resultSet.getInt(Const.APPOINTMENT_CLIENT);
            if(idAp==clientId)
            count++;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return count;
}
    public int countOfApp()
    {
        String select="SELECT * FROM hairdresser."+Const.APPOINTMENT_TABLE;
        ResultSet rs;
        int count=0;
        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs= pstmt.executeQuery();
            //resultSet = stmt.executeQuery("SELECT COUNT(*) FROM " + Const.APPOINTMENT_TABLE + ";");
            while (rs.next())
            {
                    count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public int countOfServices(String id)
    {
        int count=0;
        ResultSet resultSet;
            resultSet =this.getResultFromTable(Const.SERVICE_TABLE);
            try {  while (resultSet.next())
            {
                String idS=resultSet.getString(Const.SERVICE_TYPEID);
                if(idS.equals(id))
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.mdbc.close(resultSet);
        }
        return count;
    }
    public int countOfMasters(String id)
    {
        int count=0;
        ResultSet resultSet;
        resultSet =this.getResultFromTable(Const.MASTER_TABLE);
        try {  while (resultSet.next())
        {
            String idS=resultSet.getString(Const.MASTER_TYPEID);
            if(idS.equals(id))
                count++;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.mdbc.close(resultSet);
        }
        return count;
    }
    public int countOfTypeSv()
    {
        int count=0;
        ResultSet resultSet;
        resultSet =this.getResultFromTable(Const.TYPESERVICE_TABLE);
        try {
            while (resultSet.next())
            {
                String id=resultSet.getString(Const.TS_ID);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.mdbc.close(resultSet);
        }
        return count;
    }

    public void deleteAp (String username)
    {

        try {
            stmt.executeUpdate("DELETE FROM " + Const.APPOINTMENT_TABLE + " WHERE (" + Const.APPOINTMENT_ID + " LIKE '" + username+ "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
