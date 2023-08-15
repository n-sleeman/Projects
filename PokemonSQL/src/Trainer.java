public class Trainer {
    public int Trainer_ID; //primary key
    public String Username;
    public int level;
    public int Gym_ID; //fk
    public boolean isLeader;
    //constructor
    public Trainer( String name, int lvl, int gID){
        //Trainer_ID = tID;
        Username = name;
        level = lvl;
        Gym_ID = gID;
        isLeader = false;
    }
    //getters:
    public int getTrainerID(){
        return Trainer_ID;
    }
    public String getUsername(){
        return Username;
    }
    public int getLvl(){
        return level;
    }
    public int getGym(){
        return Gym_ID;
    }
    public boolean isLeader(){
        return isLeader;
    }
    //setters:
    public void setLeadership(boolean bool){
        isLeader = bool;
    }
    public void setLvl(int lvl){
        level = lvl;
    }
    public void setUsername(String str){
        Username = str;
    }
    public void setGymID(int id){
        Gym_ID = id;
    }
    public void setTrainerID(int id){
        Trainer_ID = id;
    }

}
