package Server.Tables;


public class Clients {
    private String id;
    private String name;
    private String date;
    private String sex;

    public Clients(String id, String name, String date, String sex) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.sex=sex;
    }

    public Clients() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date= date;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
