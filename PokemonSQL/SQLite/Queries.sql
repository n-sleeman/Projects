/*gets Name of Trainer_ID*/
SELECT Username 
FROM Trainer
WHERE Trainer_ID = 1;

/*gets all the pokemon a trainer is training */
SELECT *
FROM Pokemon
WHERE Trainer_ID = 2;

/*gets the number of pokemon per type (G1)*/
SELECT Type.Type0, count(*) AS numPokemon
FROM Pokemon NATURAL JOIN Type
GROUP BY Type.Type0;

/*gets pokemon and their primary type*/
SELECT Pokemon.Name, Type.Type0 
FROM Pokemon NATURAL JOIN Type;

/*gets username and numPokemon of each trainer (G1)*/
SELECT Trainer.Username, count(*) AS numPokemon
FROM Trainer JOIN Pokemon
ON Trainer.Trainer_ID = Pokemon.Trainer_ID
GROUP BY Trainer.Trainer_ID;

/*gets username and numItems of each trainer (G1)*/
SELECT Trainer.Username, count(*) AS numItems
FROM Trainer JOIN Item
ON Trainer.Trainer_ID = Item.Trainer_ID
GROUP BY Trainer.Trainer_ID;

/*gets trainers and their attributes as well as counts for items and pokemon (G3)*/
SELECT Trainer.Username, Trainer.Level, Trainer.Gym_ID, ItemCount.numItems, PokemonCount.numPokemon
FROM Trainer NATURAL JOIN (SELECT Trainer.Trainer_ID, count(*) AS numItems
                    FROM Trainer JOIN Item
                    ON Trainer.Trainer_ID = Item.Trainer_ID
                    GROUP BY Trainer.Trainer_ID) AS ItemCount NATURAL JOIN (SELECT Trainer.Trainer_ID, count(*) AS numPokemon
                                                                    FROM Trainer JOIN Pokemon
                                                                    ON Trainer.Trainer_ID = Pokemon.Trainer_ID
                                                                    GROUP BY Trainer.Trainer_ID) AS PokemonCount;



/*gets the trainer with the most pokemon and groups by primary type and returns how many pokemon of that type they have. (G3)*/
SELECT bestTrainer.alias, bestTrainer.numPokemon, Type.Type0, count(*) AS numTypePokemon 
FROM Type NATURAL JOIN Pokemon NATURAL JOIN (SELECT Trainer.Trainer_ID, Trainer.Username AS alias, count(*) AS numPokemon
                            FROM Trainer JOIN Pokemon
                            ON Trainer.Trainer_ID = Pokemon.Trainer_ID
                            GROUP BY Trainer.Trainer_ID
                            ORDER BY numPokemon DESC
                            LIMIT 1) AS bestTrainer
GROUP BY Type.Type0;
   

/* gets the trainer with a return of thier items and count of those that have a more expensive price than 50 pokebucks */
Select Trainer.Username, Item.name, count(*), Item.Price
From Trainer Join Item
On Trainer.Trainer_ID = Item.Trainer_ID
WHERE Trainer.Trainer_ID = 1
Group By Name 
Having Item.Price > 50
Order by count(*) DESC;

/* gets Gym Name, count of pokemon and average pokemon level at the specific gym that have five or more Pokemon, ordered in descending order */
Select Gym.Name, Count(*), Avg(Pokemon.Level)
From Trainer Join Pokemon Join Gym
Where Trainer.Trainer_ID = Pokemon.Trainer_ID AND
Trainer.Gym_ID = Gym.Gym_ID
Group By Gym.Name 
Having Count(*) >= 5
Order by Count(*) DESC



