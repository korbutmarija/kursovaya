package Server;

import java.sql.*;


public class UsersTable extends DataTable implements ResultFromTable{
    public UsersTable (Statement stmt, DBConnection mdbc) {
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

    public String checkUserInDB(String username)
    {
        String var7 = "success";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()) {
                String Username = rs.getString(Const.USERS_USERNAME);
                if (Username.equals(username))
                {
                    var7 = "fail";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return var7;
    }


    public String CheckLoginClient(String username, String password)
    {
        String var7 = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next())
            {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if(tableLogin.equals(username)&&tablePassword.equals(password))
                {
                    int id = getIdUser(username);
                    ResultSet resultSet = this.getResultFromTable(Const.CLIENT_TABLE);
                    while (resultSet.next())
                    {
                        int Cid = resultSet.getInt(Const.CLIENT_ID);
                        if (Cid == id)
                        {
                            var7 = "successClient";
                            i++;
                        }
                    }
                }
            }if(i == 0)var7 = "fail";
        }catch (Exception var12)
        {
            System.out.println("Exception in Table of users");
        } finally {
            this.mdbc.close(rs);
        }
        return var7;
    }

    public String CheckLoginAdmin(String username, String password)
    {
        String var7 = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next())
            {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if(tableLogin.equals(username)&&tablePassword.equals(password))
                {
                    int id = getIdUser(username);
                    System.out.println(id);
                    ResultSet resultSet = this.getResultFromTable(Const.ADMIN_TABLE);
                    while (resultSet.next())
                    {
                        int Cid = resultSet.getInt(Const.ADMIN_ID);
                        System.out.println(Cid);
                        if (Cid == id)
                        {
                            var7 = "successAdmin";
                            i++;
                        }
                    }
                }
            }if(i == 0)var7 = "fail";
        }catch (Exception var12)
        {
            System.out.println("exception in Table of users");
        } finally {
            this.mdbc.close(rs);
        }
        return var7;

    }

    public void AddClient(String name, String birthdate, String sex, String username, String password)
    {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" +
                "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ")";

        try {
            this.stmt.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int id = getIdUser(username);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.CLIENT_TABLE + "(" + Const.CLIENT_ID + "," +
                    Const.CLIENT_NAME +  "," +
                    Const.CLIENT_BIRTHDATE + "," + Const.CLIENT_SEX + ")" +
                    "VALUES (" + id + "," + this.quotate(name)  + "," + this.quotate(birthdate) + "," + this.quotate(sex) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getIdUser(String username)
    {
        ResultSet resultSet;
        String select="SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';";
        int id = 0;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
                id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(id==0)
        {

        }
        return id;
    }
    public String getCountUsers()
    {
        String select = "SELECT * FROM hairdresser."+ Const.USER_TABLE;
        String select2="SELECT * FROM hairdresser."+Const.ADMIN_TABLE;
        ResultSet resultSet,rs;
        String count="";String idA="";
        int size=0,k=0;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            PreparedStatement pstmt2=connection.prepareStatement(select2);
            resultSet = pstmt.executeQuery();
            rs=pstmt2.executeQuery();
            while(rs.next())
            {
                idA+=rs.getString(Const.ADMIN_ID)+",";
                size++;
            }
            String messPart[]=idA.split(",");
            for(int i=0;i<size;i++){
                while (resultSet.next()) {
                    String id = resultSet.getString(Const.USERS_ID);
                    if(id.equals(messPart[i]))
                    {
                        i++;
                        if(i==size)
                            i--;
                    }
                    else
                       k++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        count=Integer.toString(k);
        return count;
    }
    public String CountAdmins()
    {
        String select="SELECT * FROM hairdresser."+Const.ADMIN_TABLE;
        ResultSet rs;
        String count="";
        int size=0,k=0;


        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs= pstmt.executeQuery();
            while(rs.next())
            {
                size++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        count=Integer.toString(size);
        return count;
    }
    public String CountClients()
    {
        String select="SELECT * FROM hairdresser."+Const.CLIENT_TABLE;
        ResultSet rs;
        String count="";
        int size=0;


        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs= pstmt.executeQuery();
            while(rs.next())
            {
                size++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        count=Integer.toString(size);
        return count;
    }
    public String getUser()
    {
        String select = "SELECT * FROM hairdresser."+ Const.USER_TABLE;
        String select2="SELECT * FROM hairdresser."+Const.ADMIN_TABLE;
        ResultSet resultSet,rs;
       String message="";String idA="";
       int size=0;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            PreparedStatement pstmt2=connection.prepareStatement(select2);
            resultSet = pstmt.executeQuery();
            rs=pstmt2.executeQuery();
            while(rs.next())
            {
                idA+=rs.getString(Const.ADMIN_ID)+",";
                size++;
            }
            String messPart[]=idA.split(",");
            for(int i=0;i<size;i++){
            while (resultSet.next()) {
                String id = resultSet.getString(Const.USERS_ID);
                String username=resultSet.getString(Const.USERS_USERNAME);
                String password=resultSet.getString(Const.USERS_PASSWORD);
                if(id.equals(messPart[i]))
                {
                    i++;
                    if(i==size)
                        i--;
                }
                else
                    message+=id+","+username+","+password+";";
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return message;
    }
    public String getClient()
    {
        String select = "SELECT * FROM hairdresser."+ Const.CLIENT_TABLE;
        ResultSet rs;
        String message="";
        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs=pstmt.executeQuery();

                while (rs.next()) {
                    String id = rs.getString(Const.CLIENT_ID);
                    String name=rs.getString(Const.CLIENT_NAME);
                    String date=rs.getString(Const.CLIENT_BIRTHDATE);
                    String sex=rs.getString(Const.CLIENT_SEX);
                        message+=id+","+name+","+date+","+sex+";";
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }return message;
    }
    public String getNameClient()
    {
        String select = "SELECT * FROM hairdresser."+ Const.CLIENT_TABLE;
        ResultSet rs;
        String message="";
        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs=pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(Const.CLIENT_ID);
                String name=rs.getString(Const.CLIENT_NAME);
                message+=id+","+name+";";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return message;
    }
    public String getAdmin()
    {
        String select = "SELECT * FROM hairdresser."+ Const.USER_TABLE;
        String select2="SELECT * FROM hairdresser."+Const.ADMIN_TABLE;
        ResultSet resultSet,rs;
        String message="";String idA="";String status="";
        int size=0;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            PreparedStatement pstmt2=connection.prepareStatement(select2);
            resultSet = pstmt.executeQuery();
            rs=pstmt2.executeQuery();
            while(rs.next())
            {
                idA+=rs.getString(Const.ADMIN_ID)+",";
                status+=rs.getString(Const.ADMIN_STATUS)+",";
                size++;
            }
            String messPart[]=idA.split(",");
            String statusPart[]=status.split(",");
            for(int i=0;i<size;i++){
                while (resultSet.next()) {
                    String id = resultSet.getString(Const.USERS_ID);
                    String username=resultSet.getString(Const.USERS_USERNAME);
                    String password=resultSet.getString(Const.USERS_PASSWORD);
                    if(id.equals(messPart[i]))
                    {
                        message+=id+","+username+","+password+","+statusPart[i]+";";
                        i++;
                        if (i==size)
                        i--;
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return message;
    }


    public void addAdmin (String username, String password)
    {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String idu = String.valueOf(getIdUser(username));
        try {
            stmt.executeUpdate("INSERT INTO " + Const.ADMIN_TABLE + "(" + Const.ADMIN_ID + "," + Const.ADMIN_STATUS + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate("ordinary") + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String deleteAdmin (String username)
    {
        String message="";
        String select="SELECT * FROM hairdresser."+Const.ADMIN_TABLE;
        ResultSet rs;
        int size=0, idU;
        idU=getIdUser(username);

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs = pstmt.executeQuery();

            while(rs.next())
            {
                String idA=rs.getString(Const.ADMIN_ID);
                String status=rs.getString(Const.ADMIN_STATUS);
                if(username.equals(idA))
                {
                    if(status.equals("super"))
                        message="Super админа удалить нельзя";

                }
                if(idU==Integer.parseInt(idA)) {
                    if(status.equals("super"))
                        message="Super админа удалить нельзя";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    if(message.equals("")) {
        String id = String.valueOf(getIdUser(username));
        try {
            stmt.executeUpdate("DELETE FROM " + Const.ADMIN_TABLE + " WHERE (" + Const.ADMIN_ID + " LIKE '" + id + "');");
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String uid = username;
        try {
            stmt.executeUpdate("DELETE FROM " + Const.ADMIN_TABLE + " WHERE (" + Const.ADMIN_ID + " LIKE '" + uid + "');");
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + uid + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return message;
    }
    public void deleteClient (String username)
    {
            try {

                stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + username + "');");
                stmt.executeUpdate("DELETE FROM " + Const.CLIENT_TABLE + " WHERE (" + Const.CLIENT_ID + " LIKE '" + username+ "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
    public void deleteUser (String id)
    {
        try {
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + id + "');");
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_USERNAME + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getDataPieS()
    {
        String data = "";
        String select = "SELECT * FROM hairdresser."+ Const.CLIENT_TABLE;
        ResultSet rs;
        int m=0,w=0;
        String message="";
        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs=pstmt.executeQuery();

            while (rs.next()) {
                String sex = rs.getString(Const.CLIENT_SEX);
                if(sex.equals("Мужской"))
                    m++;
                if(sex.equals("Женский"))
                    w++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        data=m+","+w;
        return data;
    }
    public String getDataPieA(String sex)
    {
        String data = "";
        String select = "SELECT * FROM hairdresser."+ Const.CLIENT_TABLE;
        ResultSet rs;
        int a12=0;
        int a17=0;
        int a29=0;
        int a64=0;
        int a65=0;

        try {
            PreparedStatement pstmt=connection.prepareStatement(select);
            rs=pstmt.executeQuery();

            while (rs.next())
            {
                String sexC = rs.getString(Const.CLIENT_SEX);
                String date=rs.getString(Const.CLIENT_BIRTHDATE);
                String year[]=date.split("-");
                if(sex.equals(sexC))
                {
                    if(Integer.parseInt(year[0])>=2008)
                        a12++;
                    if(Integer.parseInt(year[0])>=2003&&Integer.parseInt(year[0])<2008 )
                        a17++;
                    if(Integer.parseInt(year[0])>=1991&&Integer.parseInt(year[0])<2003)
                        a29++;
                    if(Integer.parseInt(year[0])>=1956&&Integer.parseInt(year[0])<1991)
                        a64++;
                    if(Integer.parseInt(year[0])<1956)
                        a65++;
                }
                if(sex.equals("all"))
                {
                    if(Integer.parseInt(year[0])>=2008)
                        a12++;
                    if(Integer.parseInt(year[0])>=2003&&Integer.parseInt(year[0])<2008 )
                        a17++;
                    if(Integer.parseInt(year[0])>=1991&&Integer.parseInt(year[0])<2003)
                        a29++;
                    if(Integer.parseInt(year[0])>=1956&&Integer.parseInt(year[0])<1991)
                        a64++;
                    if(Integer.parseInt(year[0])<1956)
                        a65++;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        data=a12+","+a17+","+a29+","+a64+","+a65;
        return data;
    }
}
