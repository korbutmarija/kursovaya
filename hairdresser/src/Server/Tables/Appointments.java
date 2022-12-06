package Server.Tables;

import Server.Server;

public class Appointments  {
    private String id;
    private String name;
    private String appointments;
    private String master;
    private String date;
    private String time;
    private String price;

    public Appointments(String appointments, String master, String date, String time, String price) {
       this.appointments=appointments;
       this.master=master;
        this.date = date;
        this.time = time;
        this.price = price;
    }
    public Appointments(String id,String name,String appointments, String master, String date, String time, String price) {
        this.id=id;
        this.name=name;
        this.appointments=appointments;
        this.master=master;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public Appointments() {
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        this.name =name;
    }


}
