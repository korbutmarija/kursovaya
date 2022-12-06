package Server.Tables;

import Server.Server;

public class Admins {
    private String id;
    private String username;
    private String password;
    private String status;

    public Admins(String id, String username, String password, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status=status;
    }

    public Admins() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
