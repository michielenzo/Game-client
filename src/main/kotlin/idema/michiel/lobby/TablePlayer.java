package idema.michiel.lobby;

import javafx.beans.property.SimpleStringProperty;

public class TablePlayer {

    private final SimpleStringProperty name;
    private final SimpleStringProperty status;

    public TablePlayer(String name, String status){
        this.name = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty(status);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public String getStatus(){ return status.get(); }

    public void setStatus(String status){ this.status.set(status); }



}
