package idema.michiel.lobby;

import javafx.beans.property.SimpleStringProperty;

public class TablePlayer {

    private final SimpleStringProperty name;

    public TablePlayer(String name){
        this.name = new SimpleStringProperty(name);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

}
