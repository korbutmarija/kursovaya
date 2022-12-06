package Server.Tables;

import Server.Server;

public class Services {
    private String name;

    private String price;

    public Services( String name,  String price) {
        this.name = name;
        this.price = price;
    }

    public Services() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
