import java.sql.*;
import java.util.*;
public class App {
    public static Database db;
    public static void main(String[] args) throws Exception {
        //Set up database
        db = Database.getDB();
        //connect to database
        try{
            db.connect();
            System.out.println("Connection successful!");
        }catch(SQLException e){
            System.out.println("Database connection failed :( shucks");
        }
        //do stuff (meat and potatos of program method calls)

        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.println("Type 't' to terminate");
            System.out.println("Type trainer id to get their pokemon");
            String input = scan.nextLine();
            int tID = -1;
            if(input.equals("t")){
                break;
            }
            try{
                tID = Integer.parseInt(input);
            }catch (Exception e){
                continue;
            }
            getTrainersPokemon(tID);
            System.out.println();

        }

        //Must show DELETES, INSERTS and UPDATES work.
        //demoInsertsDeletesPoke();
        //demoInsertsItem();
        //demoUpdates();

        //GROUP 3:
        // getTopTrainerTypes();
        // System.out.println();
        // getTrainerAttr();
        // System.out.println();
        
        //GROUP 2:
        // getTrainerExpItems(1);
        // System.out.println();
        // getTopGyms();
        // System.out.println();
        
        //GROUP 1:
        getNumPokemonPerTrainer();
        System.out.println();
        getNumItemsPerTrainer();
        System.out.println();
        getNumPokemonPerType();
        System.out.println();
        getTrainersPokemon(1);
        System.out.println();
        
        

        //disconnect database
        try{
            db.disconnect();
            System.out.println("Disconnection successful!");
        }catch(SQLException e){
            System.out.println("Database disconnection failed! LLLL");
        }
    }
    public static void demoInsertsDeletesPoke() throws SQLException{
        getTrainersPokemon(1);
        System.out.println();
        System.out.println("inserting charmander");
        System.out.println();
        db.insertPokemon(new Pokemon(null, 4, 90, "charmander", 4, 1));
        getTrainersPokemon(1);
        System.out.println();
        System.out.println("deleting charmander");
        System.out.println();
        db.deleteFromPokemon(79);  
        getTrainersPokemon(1);      
    }   

    public static void demoInsertsItem()throws SQLException {
        Item i31 = new Item("Revive",30.0,3);
        Item i32 = new Item("Full Heal", 100, 3);
        Item i33 = new Item("Full Restore", 200,3);

        Item i41 = new Item("Revive", 30.0, 4);
        Item i42 = new Item("Full Heal", 100, 4);
        Item i43 = new Item("Full Heal", 100, 4);
        Item i44 = new Item("Full Restore", 200,4);
        Item i45 = new Item("Pokeball", 25, 4);

        Item i51 = new Item("Full Restore", 200,4);
        Item i52 = new Item("Revive", 30, 5);
        Item i53 = new Item("Full Heal", 100, 5);

        Item i61 = new Item("Pokeball", 25,6);
        Item i62 = new Item("Full Restore", 200, 6);

        Item i71 = new Item("Revive", 30, 7);
        Item i72 = new Item("Pokeball", 25, 7);

        Item i81 = new Item("Pokeball", 25,8);
        Item i82 = new Item("Revive", 30, 8);

        getNumItemsPerTrainer();
        System.out.println();
        System.out.println("adding items");
        System.out.println();

        db.insertItem(i31);
        db.insertItem(i32);
        db.insertItem(i33);
        db.insertItem(i33);
        db.insertItem(i41);
        db.insertItem(i42);
        db.insertItem(i43);
        db.insertItem(i44);
        db.insertItem(i44);
        db.insertItem(i45);
        db.insertItem(i45);
        db.insertItem(i51);
        db.insertItem(i52);
        db.insertItem(i53);
        db.insertItem(i61);
        db.insertItem(i61);
        db.insertItem(i61);
        db.insertItem(i62);
        db.insertItem(i71);
        db.insertItem(i71);
        db.insertItem(i71);
        db.insertItem(i72);
        db.insertItem(i81);
        db.insertItem(i81);
        db.insertItem(i81);
        db.insertItem(i81);
        db.insertItem(i81);
        db.insertItem(i82);

        getNumItemsPerTrainer();
    }

    public static void demoUpdates() throws SQLException{   
        getTrainersPokemon(1);
        System.out.println();
        getTrainersPokemon(2);
        System.out.println();
        System.out.println("Trading Noah's Rattata to Brook");
        System.out.println();
        db.updateTrainerIDOnPokemon(39, 2);
        getTrainersPokemon(1);
        System.out.println();
        getTrainersPokemon(2);
        System.out.println();
        System.out.println("Trading Brook's Squirtle to Noah");
        System.out.println();
        db.updateTrainerIDOnPokemon(4, 1);
        getTrainersPokemon(1);
        System.out.println();
        getTrainersPokemon(2);
    }


    //Gets all the pokemon Trainer_ID is training (group 1).
    public static void getTrainersPokemon(int TrainerID) throws SQLException{
        ResultSet table = db.getTrainersPokemon(TrainerID);
        ResultSet name = db.getTrainerName(TrainerID);
        String tName = name.getString(1);
        System.out.println("--"+tName+"'s Pokemon--");
        String pName; //col 2
        int level; //col 4
        int pokedex; //col 3
        int hp; //col 5
        Pokemon p;
        while(table.next()){
            pName = table.getString(2);
            level = table.getInt(4);
            pokedex = table.getInt(3);
            hp = table.getInt(5);
            p = new Pokemon(null, level, hp, pName, pokedex, TrainerID);
            Pokemon.Type type = getPokemonType(p);
            System.out.print(pName+" "+" Level: "+level+" HP: "+hp+" Primary Type: "+type.type0 + " ");
            if(type.type1 != null){
                System.out.print("Secondar type: "+type.type1);
            }
            System.out.println();
        }
    }
    //Gets Trainers_ID most expensive Items and their count. (group 2 query)
    public static void getTrainerExpItems(int TrainerID)throws SQLException{
        ResultSet table = db.getTrainerRareItems(TrainerID);
        String trainerName = table.getString(1);
        int numItems;
        String itemName;
        double price;
        System.out.println("--"+trainerName+"'s Rare Items--");
        while(table.next()){
            numItems = table.getInt(3);
            itemName = table.getString(2);
            price = table.getDouble(4);
            System.out.println("Item: "+itemName+"    Price: "+price+"    Quanity: "+numItems);
        }

    }
    //gets Gym Name, count of pokemon and average pokemon level at the specific gym that have five or more Pokemon, ordered in descending order 
    public static void getTopGyms()throws SQLException{
        ResultSet table = executeStmt("Select Gym.Name, Count(*), Avg(Pokemon.Level) From Trainer Join Pokemon Join Gym Where Trainer.Trainer_ID = Pokemon.Trainer_ID AND Trainer.Gym_ID = Gym.Gym_ID Group By Gym.Name Having Count(*) >= 5 Order by Count(*) DESC");
        String gym;
        int numPokemon;
        double avgLevel;
        System.out.println("-- Top Gyms --");
        while(table.next()){
            gym = table.getString(1);
            numPokemon = table.getInt(2);
            avgLevel = table.getInt(3);
            System.out.println("Gym: "+gym+ "   Number of Pokemon: "+numPokemon+"   Avg. Pokemon Level: "+avgLevel);
        }
    }

    //Gets all Trainers with items and pokemon and display trainer attributes and their pokemon and item counts. (group 3 query)
    public static void getTrainerAttr()throws SQLException{
        ResultSet table = executeStmt("SELECT Trainer.Username, Trainer.Level, Trainer.Gym_ID, ItemCount.numItems, PokemonCount.numPokemon"
        +" FROM Trainer NATURAL JOIN (SELECT Trainer.Trainer_ID, count(*) AS numItems"
                            +" FROM Trainer JOIN Item"
                            +" ON Trainer.Trainer_ID = Item.Trainer_ID"
                            +" GROUP BY Trainer.Trainer_ID) AS ItemCount NATURAL JOIN (SELECT Trainer.Trainer_ID, count(*) AS numPokemon"
                                                                            +" FROM Trainer JOIN Pokemon"
                                                                            +" ON Trainer.Trainer_ID = Pokemon.Trainer_ID"
                                                                            +" GROUP BY Trainer.Trainer_ID) AS PokemonCount");
        String username;
        int level;
        int numItems;
        int numPokemon;
        System.out.println("--Trainers--");
        while(table.next()){
            username = table.getString(1);
            level = table.getInt(2);
            numItems = table.getInt(4);
            numPokemon = table.getInt(5);
            System.out.println("Name: "+ username+"    Level: "+level+ "    Number of Pokemon: "+ numPokemon+"    Number of Items: "+numItems);
        }
    }
    
    //Groups by pokemon type and gets their count and prints to consol. (group 1 query)
    public static void getNumPokemonPerType()throws SQLException{
        ResultSet table = executeStmt("SELECT Type.Type0, count(*) AS numPokemon FROM Pokemon NATURAL JOIN Type GROUP BY Type.Type0");
        String type;
        int numPokemon;
        System.out.println("--Pokemon Per Type--");
        while(table.next()){
            type = table.getString(1);
            numPokemon = table.getInt(2);
            System.out.println("Type: "+type + "    Number of Pokemon: "+numPokemon);
        }
    }
    //Groups by Trainer_ID and gets count of pokemon trained by each trainer. (group 1 query)
    public static void getNumPokemonPerTrainer()throws SQLException{
        ResultSet table = executeStmt("SELECT Trainer.Username, count(*) AS numPokemon FROM Trainer JOIN Pokemon ON Trainer.Trainer_ID = Pokemon.Trainer_ID GROUP BY Trainer.Trainer_ID");
        String username;
        int numPokemon;
        System.out.println("--Pokemon Per Trainer--");
        while(table.next()){
            username = table.getString(1);
            numPokemon = table.getInt(2);
            System.out.println("Trainer: "+username+"   Number of Pokemon: "+numPokemon);
        }
    }
    //Groups by Trainer_ID and gets count of items per trainer. (group 1 query)
    public static void  getNumItemsPerTrainer() throws SQLException{
        ResultSet table = executeStmt("SELECT Trainer.Username, count(*) AS numItems FROM Trainer JOIN Item ON Trainer.Trainer_ID = Item.Trainer_ID GROUP BY Trainer.Trainer_ID");
        String username;
        int numItems;
        System.out.println("--Items Per Trainer--");
        while(table.next()){
            username = table.getString(1);
            numItems = table.getInt(2);
            System.out.println("Trainer: "+username+"   Number of items: "+numItems);
        }
    }

    //Gets Trainer whos training the most pokemon and displays the primary types they train and how many pokemon of that type. (group 3 query)
    public static void getTopTrainerTypes()throws SQLException{
        ResultSet table = executeStmt("SELECT bestTrainer.alias, bestTrainer.numPokemon, Type.Type0, count(*) AS numTypePokemon FROM Type NATURAL JOIN Pokemon NATURAL JOIN (SELECT Trainer.Trainer_ID, Trainer.Username AS alias, count(*) AS numPokemon FROM Trainer JOIN Pokemon ON Trainer.Trainer_ID = Pokemon.Trainer_ID GROUP BY Trainer.Trainer_ID ORDER BY numPokemon DESC LIMIT 1) AS bestTrainer GROUP BY Type.Type0");
        String username = table.getString(1);
        int numPokemon = table.getInt(2);
        String type;
        int numPokePerType;
        System.out.println("Trainer with the most Pokemon: "+username);
        System.out.println("Number of Pokemon: "+numPokemon);
        while(table.next()){
            type = table.getString(3);
            numPokePerType = table.getInt(4);
            System.out.println("Type: "+type +"    Number of Pokemon: "+numPokePerType);
        }
    } 
    //Get table from stmt (executes query) 
    public static ResultSet executeStmt(String stmt) throws SQLException {
        ResultSet table = db.query(stmt);
        return table;
    }
    //gets Type of pokemon sets type to the object and returns it.
    public static Pokemon.Type getPokemonType(Pokemon p) throws SQLException{
        int dexID = p.getDexID();
        String script = "SELECT Type0, Type1 FROM Type WHERE Pokedex = ?";
        ResultSet typeTuple = db.getType(script, dexID);
        Pokemon.Type type = new Pokemon.Type(dexID, typeTuple.getString(1), typeTuple.getString(2));
        p.setType(type);
        return type;
    }
    //Passes trainer table and turns table of tuples to an arraylist of Trainer objects.
    public ArrayList getTrainerTableToArrayList(ResultSet trainerTable) throws SQLException{
        ArrayList<Trainer> resultList = new ArrayList<>();
        while(trainerTable.next()){
            int tID = trainerTable.getInt(1);
            String name = trainerTable.getString(2);
            int lvl = trainerTable.getInt(3);
            int gID = trainerTable.getInt(4);
            Trainer t = new Trainer(name, lvl, gID);
            t.setTrainerID(tID);
            resultList.add(t);
        }
        return resultList;
    }
    //Passes trainer table and turns table of tuples to arraylist of Pokemon objects.
    public ArrayList getPokemonTableToArrayList(ResultSet pokemonTable) throws SQLException{
        ArrayList<Pokemon> resultList = new ArrayList<>();
        while(pokemonTable.next()){
            int pID = pokemonTable.getInt(1);
            String name = pokemonTable.getString(2);
            int pokedex = pokemonTable.getInt(3);
            int lvl = pokemonTable.getInt(4);
            int hp = pokemonTable.getInt(5);
            int tID = pokemonTable.getInt(6);
            Pokemon p = new Pokemon(null, lvl, hp, name, pokedex, tID);
            p.setPokedex(pID);
            Pokemon.Type pType = getPokemonType(p);
            p.setType(pType);
        }
        return resultList;
    }
}

