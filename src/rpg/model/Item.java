package rpg.model;

import java.io.Serializable;

public abstract class Item implements Serializable {

    private final String name;

    protected Item(String name){
        this.name = name;
    }

    public String getName() {return  name;}
}
