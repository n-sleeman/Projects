import java.sql.*;
import java.util.*;

public class Database{
    
    private String dbPath = "jdbc:sqlite:./SQLite/PokemonDB.db";

    private Connection connect;
    
    private static final Database db = new Database();
    
    private Database(){}

    public static Database getDB(){
        return db;
    }

    public void connect() throws SQLException{
        connect = DriverManager.getConnection(dbPath);
    }

    public void disconnect() throws SQLException {
        connect.close();
    }

    //execute query
    public ResultSet query(String script) throws SQLException{
        PreparedStatement stmt = connect.prepareStatement(script);
        ResultSet ReTable = stmt.executeQuery();
        return ReTable;
    }
    //pokemon type query
    public ResultSet getType(String script, int dexID) throws SQLException{
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, dexID);
        return stmt.executeQuery();
    }
    //Trainer Pokemon
    public ResultSet getTrainersPokemon(int TrainerID)throws SQLException{
        PreparedStatement stmt = connect.prepareStatement("SELECT * FROM Pokemon WHERE Trainer_ID = ?");
        stmt.setInt(1, TrainerID);
        return stmt.executeQuery();
    }
    //Trainer Name
    public ResultSet getTrainerName(int TrainerID)throws SQLException{
        PreparedStatement stmt = connect.prepareStatement("SELECT Username FROM Trainer WHERE Trainer_ID = ?");
        stmt.setInt(1,TrainerID);
        return stmt.executeQuery();
    }
    //Trainer expensive Items
    public ResultSet getTrainerRareItems(int TrainerID)throws SQLException{
        PreparedStatement stmt = connect.prepareStatement("Select Trainer.Username, Item.name, count(*), Item.Price From Trainer Join Item On Trainer.Trainer_ID = Item.Trainer_ID WHERE Trainer.Trainer_ID = ? Group By Name Having Item.Price > 50 Order by count(*) DESC;");
        stmt.setInt(1,TrainerID);
        return stmt.executeQuery();
    }


    //inserts...
    //insert a pokemon object into database (Pokemon table)
    public void insertPokemon(Pokemon p) throws SQLException{
        String script = "INSERT INTO  Pokemon( Name, Pokedex, Level, HP, Trainer_ID) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = connect.prepareStatement(script);
        //stmt.setInt(1, p.getPokeID());
        stmt.setString(1, p.getName());
        stmt.setInt(2, p.getDexID());
        stmt.setInt(3, p.getLvl());
        stmt.setInt(4,p.getHP());
        stmt.setInt(5, p.getTrainerID());
        stmt.executeUpdate();
    } 
    //insert a trainer object into database (Trainer table)
    public void insertTrainer(Trainer t) throws SQLException{
        String script = "INSERT INTO Trainer( Username, Level, Gym_ID) VALUES (?,?,?)";
        PreparedStatement stmt = connect.prepareStatement(script);
        //stmt.setInt(1,t.getTrainerID());
        stmt.setString(1, t.getUsername());
        stmt.setInt(2, t.getLvl());
        stmt.setInt(3, t.getGym());
        stmt.executeUpdate();
    }
    //insert a gym object into database (Gym table)
    public void insertGym(Gym g) throws SQLException{
        String script = "INSERT INTO Gym( Name, Type, Location) VALUES (?,?,?)";
        PreparedStatement stmt = connect.prepareStatement(script);
       // stmt.setInt(1, g.getGymID());
        stmt.setString(1, g.getName());
        stmt.setString(2, g.getType());
        stmt.setString(3, g.getLocation());
        stmt.executeUpdate();
    }
    //insert a item object into database (Item table)
    public void insertItem(Item i) throws SQLException{
        String script = "INSERT INTO Item( Name, Price, Trainer_ID) VALUES (?,?,?)";
        PreparedStatement stmt = connect.prepareStatement(script);
        //stmt.setInt(1, i.getItemID());
        stmt.setString(1, i.getName());
        stmt.setDouble(2, i.getPrice());
        stmt.setInt(3, i.getTrainerID());
        stmt.executeUpdate();
    }
    //deletes...
    //delete from Pokemon table given Pokemon_ID for condision. 
    public void deleteFromPokemon(int PokemonID) throws SQLException{
        String script = "DELETE FROM Pokemon WHERE Pokemon_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, PokemonID);
        stmt.executeUpdate();
    }
    //delete from Trainer table given Trainer_ID for condision. 
    public void deleteFromTrainer(int TrainerID) throws SQLException{
        String script = "DELETE FROM Trainer WHERE Trainer_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, TrainerID);
        stmt.executeUpdate();
    }
    //delete from Item table given Item_ID for condision.
    public void deleteFromItem(int ItemID) throws SQLException{
        String script = "DELETE FROM Item WHERE Item_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1,ItemID);
        stmt.executeUpdate();
    }
    //delete from Gym table given Gym_ID for condision.
    public void deleteFromGym(int GymID) throws SQLException{
        String script = "DELETE FROM Gym WHERE ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1,GymID);
        stmt.executeUpdate();
    }
    //updates...
    //given the Pokemon_ID updates the Level with val on Pokemon_ID
    public void updateLevelOnPokemon(int PokemonID, int val) throws SQLException{
        String script = "UPDATE Pokemon SET Level = ? WHERE Pokemon_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, val);
        stmt.setInt(2, PokemonID);
        stmt.executeUpdate();
    }   
    //given the Pokemon_ID updates the HP with val on Pokemon_ID
    public void updateHPOnPokemon(int PokemonID, int val) throws SQLException{
        String script = "UPDATE Pokemon SET HP = ? WHERE Pokemon_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, val);
        stmt.setInt(2, PokemonID);
        stmt.executeUpdate();
    }  
    //given the Pokemon_ID, updates the Trainer_ID foreign key on Pokemon_ID
    public void updateTrainerIDOnPokemon(int PokemonID, int TrainerID) throws SQLException{
        String script = "UPDATE Pokemon SET Trainer_ID = ? WHERE Pokemon_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1,TrainerID);
        stmt.setInt(2, PokemonID);
        stmt.executeUpdate();
    }
    //given the Trainer_ID updates the Username with newName on Trainer_ID
    public void updateUsernameOnTrainer(int TrainerID, String newName) throws SQLException{
        String script = "UPDATE Trainer SET Username = ? WHERE Trainer_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setString(1, newName);
        stmt.setInt(2, TrainerID);
        stmt.executeUpdate();
    } 
    //given the Trainer_ID updates the level with val on Trainer_ID
    public void updateLevelOnTrainer(int TrainerID, int val) throws SQLException{
        String script = "UPDATE Trainer SET Username = ? WHERE Trainer_ID = ?";
        PreparedStatement stmt = connect.prepareStatement(script);
        stmt.setInt(1, val);
        stmt.setInt(2, TrainerID);
        stmt.executeUpdate();
    } 
    //given the Trainer_ID updates the Gym_ID foregin key with gymID on Trainer_ID
    public void updateGymidOnTrainer(int TrainerID, int gymID)throws SQLException{
        PreparedStatement stmt = connect.prepareStatement("UPDATE Trainer SET Gym_ID = ? WHERE Trainer_ID = ?");
        stmt.setInt(1,gymID);
        stmt.setInt(2, TrainerID);
        stmt.executeUpdate();
    }
    //given the Item_ID updates the Trainer_ID foreign key with TrainerID on Item_ID
    public void updateTrainerIDOnItem(int itemID, int TrainerID) throws SQLException{
        PreparedStatement stmt = connect.prepareStatement("UPDATE Item SET Trainer_ID = ? WHERE Item_ID = ?");
        stmt.setInt(1, TrainerID);
        stmt.setInt(2, itemID);
        stmt.executeUpdate();
    }


}