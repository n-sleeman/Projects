public class Item {
    public int Item_ID; //primary key
    public String Name;
    public double price;
    public int Trainer_ID; //fk
    //constructor
    public Item( String itemName, double itemPrice, int tID){
        //Item_ID = itemID;
        Name = itemName;
        price = itemPrice; 
        Trainer_ID = tID;
    }
    //getters:
    public int getItemID(){
        return Item_ID;
    }
    public String getName(){
        return Name;
    }
    public double getPrice(){
        return price;
    }
    public int getTrainerID(){
        return Trainer_ID; 
    }
    //setters:
    public void setTrainerID(int id){
        Trainer_ID = id;
    }
}
