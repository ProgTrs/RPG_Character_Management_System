package rpg.manager;

import java.io.*;

public class DataManager {
    public static void saveGuildState(Guild guild, String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(guild);
            System.out.println("Server state save succesfully to " + filename);
        } catch (IOException e){
            System.err.println("Failed to save state "+ e.getMessage());
        }
    }

    public static Guild loadGuildState(String filename){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            System.out.println("Server state loaded succesfully from " +filename);
            return (Guild) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Failed to load state or file not found. Creating new Guild ");
            return new Guild();
        }
    }

}
