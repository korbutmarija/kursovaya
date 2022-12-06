package Server.Tables;

import Server.Server;

public class Masters {
    private String name;

    private String experience;

    public Masters( String name,  String experience) {
        this.name = name;
        this.experience = experience;
    }

    public Masters() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
