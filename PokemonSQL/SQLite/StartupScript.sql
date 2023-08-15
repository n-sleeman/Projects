CREATE TABLE Pokemon (
Pokemon_ID INTEGER PRIMARY KEY AUTOINCREMENT,
Name text NOT NULL,
Pokedex INTEGER NOT NULL,
Level INTEGER NOT NULL,
HP INTEGER NOT NULL,
Trainer_ID INTEGER NOT NULL,
CONSTRAINT Trainer_ID FOREIGN KEY
(Trainer_ID) REFERENCES Trainer (Trainer_ID),
CONSTRAINT Pokedex FOREIGN KEY
(Pokedex) REFERENCES Type (Pokedex)
);

CREATE TABLE Type(
Pokedex INTEGER PRIMARY KEY AUTOINCREMENT,
Type0 text NOT NULL,
Type1 text
);

CREATE TABLE Trainer(
Trainer_ID INTEGER PRIMARY KEY AUTOINCREMENT,
Username text NOT NULL,
Level INTEGER NOT NULL,
Gym_ID INTEGER NOT NULL,
CONSTRAINT Gym_ID FOREIGN KEY
(Gym_ID) REFERENCES Gym (Gym_ID)
);

CREATE TABLE Item(
Item_ID INTEGER PRIMARY KEY AUTOINCREMENT,
Name text NOT NULL,
Price NUMERIC(12,2) NOT NULL,
Trainer_ID INTEGER NOT NULL,
CONSTRAINT Trainer_ID FOREIGN KEY
(Trainer_ID) REFERENCES Trainer (Trainer_ID)
);

CREATE TABLE Gym(
Gym_ID INTEGER PRIMARY KEY AUTOINCREMENT,
Name text NOT NULL,
Type text NOT NULL,
Location text NOT NULL
);


/*Gyms*/
INSERT INTO Gym (Name, Type, Location)
VALUES ("Pewter","Rock","Pewter");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Cerulean","Water","Cerulean");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Vermilion","Electric","Vermilion");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Celadon","Grass","Celadon");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Fuchsia","Poison","Fuchsia");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Saffron", "Psychic", "Saffron");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Cinnabar", "Fire", "Cinnabar");
INSERT INTO Gym (Name, Type, Location)
VALUES ("Viridian", "Ground", "Viridian");

/*Pokemon Types*/
INSERT INTO Type (Type0, Type1)
VALUES ("Grass","Poison");
INSERT INTO Type (Type0, Type1)
VALUES ("Grass","Poison");
INSERT INTO Type (Type0, Type1)
VALUES ("Grass","Poison");
INSERT INTO Type (Type0, Type1)
VALUES("Fire",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Fire",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Fire","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Water",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Water",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Water",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Bug",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Bug",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Bug","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Bug","Poison");
INSERT INTO Type (Type0, Type1)
VALUES("Bug","Poison");
INSERT INTO Type (Type0, Type1)
VALUES("Bug","Poison");
INSERT INTO Type (Type0, Type1)
VALUES("Normal","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Normal","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Normal","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Normal",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Normal",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Normal","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Normal","Flying");
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Electric",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Electric",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Ground",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Ground",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison","Ground");
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Poison","Ground");
/*more*/
INSERT INTO Type (Type0, Type1)
VALUES("Fairy",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Fairy",NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Fire", NULL);
INSERT INTO Type (Type0, Type1)
VALUES("Fire", NULL);


/*Test Trainer*/
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Noah",44,1);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Brooks",40,2);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Trey",51,3);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Liz",33,4);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Taylor",42,5);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Tyler",24,6);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Alexa",39,7);
INSERT INTO Trainer(Username, Level, Gym_ID)
VALUES ("Felix",53,8);

/*Test Pokemon*/
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charmander", 4, 12, 25, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charizard", 6, 65, 65, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Squirtle", 7, 8, 30, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Squirtle", 7, 10, 30, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ninetails", 38, 28, 120, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ninetails", 38, 24, 100, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Bulbasaur", 1, 19, 30, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ivysaur", 2, 25, 40, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Bulbasaur", 1, 19, 34, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ivysaur", 2, 22, 39, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Venusaur", 3, 55, 51, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Venusaur", 3, 51, 449, 4);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charmander", 4, 43, 37, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charizard", 6, 65, 60, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charmeleon", 5, 49, 40, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Charmeleon", 5, 51, 39, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Squirtle", 7, 9, 31, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Squirtle", 7, 10, 35, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Wartortle", 8, 19, 44, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Wartortle", 8, 17, 46, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Blastoise", 9, 45, 59, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Blastoise", 9, 40, 60, 2);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Caterpie", 10, 11, 30, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Caterpie", 10, 12, 33, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Metapod", 11, 19, 41, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Metapod", 11, 17, 43, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Butterfree", 12, 25, 42, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Butterfree", 12, 20, 45, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Weedle", 13, 9, 31, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Weedle", 13, 8, 32, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Kakuna", 14, 11, 29, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Kakuna", 14, 9, 30, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Beedrill", 15, 21, 42, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Beedrill", 15, 19, 48, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pidgey", 16, 12, 31, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pidgey", 16, 10, 34, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pidgeotto", 17, 31, 42, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pidgeotto", 17, 29, 44, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Rattata", 19, 31, 19, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Rattata", 19, 30, 21, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Raticate", 20, 21, 41, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Raticate", 20, 20, 43, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Spearow", 21, 12, 32, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Spearow", 20, 11, 35, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Fearow", 22, 36, 42, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Fearow", 22, 41, 46, 1);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ekans", 23, 12, 34, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Ekans", 23, 15, 33, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Arbok", 24, 23, 44, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Arbok", 20, 24, 48, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pikachu", 25, 9, 32, 3);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Pikachu", 25, 11, 37, 3);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Raichu", 26, 29, 48, 3);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Raichu", 26, 23, 42, 3);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Sandshrew", 27, 12, 35, 8);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Sandshrew", 27, 15, 32, 8);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Sandslash", 28, 29, 51, 8);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Sandslash", 28, 31, 54, 8);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nirodran", 29, 26, 43, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nirodan", 29, 21, 46, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidorina", 30, 36, 52, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidorina", 29, 31, 54, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidoqueen", 31, 45, 62, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidoqueen", 31, 43, 64, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nirodran", 32, 23, 33, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nirodan", 32, 24, 31, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidorino", 33, 27, 42, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidorino", 33, 29, 41, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidoking", 34, 41, 54, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Nidorino", 34, 39, 51, 5);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Clefairy", 35, 31, 51, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Clefairy", 35, 29, 54, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Clefable", 36, 41, 62, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Clefable", 36, 45, 67, 6);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Vulpuix", 37, 9, 31, 7);
INSERT INTO Pokemon(Name, Pokedex, Level, HP, Trainer_ID)
VALUES("Vulpuix", 37, 15, 37, 7);

/*Test Items*/
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Full Heal", 100, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Full Restore", 200, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Full Restore", 200, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Masterball", 50, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Masterball", 50, 1);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Revive", 30, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Full Heal", 100, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Full Restore", 200, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Pokeball", 25, 2);
INSERT INTO Item( Name, Price, Trainer_ID)
VALUES("Masterball", 50, 2);
                         


