public class Gym {
    public int Gym_ID; //primary key
    public String Name;
    public String Type;
    public String Location; 
    //constructor
    public Gym( String gName, String gType, String loc){
       // Gym_ID = gID;
        Name = gName;
        Type = gType;
        Location = loc;
    }
    //getters:
    public int getGymID(){
       return Gym_ID;
    }
    public String getName(){
       return Name;
    }      
    public String getType(){
        return Type;
    }
    public String getLocation(){
        return Location;
    }
}
