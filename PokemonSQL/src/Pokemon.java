public class Pokemon {
    //get this from pokedex or name fk
    public static class Type{
        public int pokedex;
        public String type0;
        public String type1;
        //constructor
        public Type(int index, String t0, String t1){
            pokedex = index;
            type0 = t0;
            type1 = t1;
        }
        //getters:
        public int getDexID(){
            return pokedex;
        }
        public String getT0(){
            return type0;
        }
        public String getT1(){
            return type1;
        }
    }
    public int Pokemon_ID; //primary key
    public int level;
    public int HP;
    public Type types;
    public String Name; //could be fk
    //foreign keys:
    public int pokedex;
    public int Trainer_ID;
    //constructor
    public Pokemon(Type t, int lvl, int health, String pName, int dex, int tID){
        types = t;
        //Pokemon_ID = pID;
        level = lvl;
        HP = health;
        Name = pName;
        pokedex = dex;
        Trainer_ID = tID;
    }
    //getters:
    public int getPokeID(){
        return Pokemon_ID;
    }
    public int getLvl(){
        return level;
    }
    public int getHP(){
        return HP;
    }
    public Type getType(){
        return types;
    }
    public String getName(){
        return Name;
    }
    public int getDexID(){
        return pokedex;
    }
    public int getTrainerID(){
        return Trainer_ID;
    }
    //setters:
    public void setLvl(int lvl){
        level = lvl;
    }
    public void setHP(int health){
        HP = health;
    }
    public void setTrainerID(int id){
        Trainer_ID = id;
    }
    public void setPokedex(int pIndex){
        pokedex = pIndex;
    }
    public void setType(Type t){
        types = t;
    }
    public void setpID(int pID){
        Pokemon_ID = pID;
    }

}
