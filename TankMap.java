import java.awt.Rectangle; // Import appropriate classes
import java.awt.Image;
import java.awt.Color;

public class TankMap
{
    //**********************************************************************PUBLIC FIELDS***************************************************************************

    public static final int ROW = MapCollection.ROW, COLUMN = MapCollection.COLUMN, WIDTH = 630, HEIGHT = 630; // Constants for the dimensions of the map
    public static final Color enemyColour = new Color (64, 64, 64); // Colour of the enemies

    //**********************************************************************PRIVATE FIELDS***************************************************************************

    // Map objects
    private Terrain map[] []; // The map is just a 2D array of Terrain types
    private String creator; // The name of the map's creator
    private static final int moveN = 11; // Constant value that the AI uses to check how many units around it

    // Enemy objects
    // Counters for the total enemy, the total of the ones left to spawn, the dead count, the cushion period after a random move, the direction of movement,
    // the counter for the random moves, and the counter for a regular move for the enemy tanks respectively
    private int totalEnemy, leftToSpawn, spawnDelayCount, deadCount, randomMoveCushionCount[], direction[], randomMoveCount[], moveCount[];
    private static final int spawnDelayLimit = 200, randomMoveCushionLimit = 30, randomMoveLimit = 50, randomDirectionChance = 500, moveLimit = 10; // Counter limits
    private static final double randomAttackChance = 1.2, randomMoveDownChance = 2.8; // The AI's random attack chance on the base and the random chance of moving down
    private Tank enemyTank[]; // Array of tank objects for the enemies
    private boolean spawned[]; // Array of booleans to see whether the enemy tank was spawned or not

    // Upgrade ojects
    // Integers for the total upgrade count, the total left to appear, the delay count in between upgrades, and the invulnerable count for the base
    private int totalUpgrade, leftToAppear, appearDelayCount, invulnerableCount;
    private static final int appearDelayLimit = 250, invulnerableLimit = 1000; // Counter limits
    private Upgrade upgrade[]; // Upgrade objects for the upgrades
    private boolean appeared[]; // Array of booleans to see whether the upgrade had appeared or not

    //**********************************************************************INITIALIZATION METHODS***************************************************************************

    TankMap (String maker, String[] [] terrainType, int tankType[], int upgradeType[])  // Constructor
    {
	// Initialize the map objects
	creator = maker; // Get the creator of the map
	map = new Terrain [terrainType.length] [terrainType [0].length]; // Create the 2D array
	createMap (terrainType); // Call auxiliary function

	// Initialize the enemies' objects
	leftToSpawn = totalEnemy = getArrayTotal (tankType, tankType.length); // The left to spawn and total enemy integers start with the total number of enemy
	spawned = new boolean [totalEnemy]; // Initialize arrays
	randomMoveCushionCount = new int [totalEnemy];
	moveCount = new int [totalEnemy];
	direction = new int [totalEnemy];
	randomMoveCount = new int [totalEnemy];
	spawnDelayCount = deadCount = 0; // Initialize counters
	// Initalize the tank objects
	enemyTank = new Tank [totalEnemy];
	for (int i = 0 ; i != totalEnemy ; ++i) // For each of the enemy tanks
	{
	    if (i < getArrayTotal (tankType, 1)) // For the number of the first kind of enemy specified
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 1, 5); // Give them these stats
	    else if (i < getArrayTotal (tankType, 2)) // For the number of second kind of enemies
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 1, 6);
	    else if (i < getArrayTotal (tankType, 3)) // Third
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 2, 6);
	    else if (i < getArrayTotal (tankType, 4))
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 1, 6);
	    else if (i < getArrayTotal (tankType, 5))
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 2, 6);
	    else if (i < getArrayTotal (tankType, 6))
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 3, 6);
	    else // Last
		enemyTank [i] = new Tank (enemyColour, randEnemyStartX (), 0, 1, 4, 7);
	}

	// Initialize upgrade objects
	leftToAppear = totalUpgrade = getArrayTotal (upgradeType, upgradeType.length); // The left to appear and total upgrade integers start with the total number of upgrades
	appeared = new boolean [totalUpgrade]; // Initialize the boolean
	upgrade = new Upgrade [totalUpgrade]; // Initialize the array of upgrades
	invulnerableCount = 0; // Initialize counters
	appearDelayCount = 1; // This counter starts at 1 so that an upgrade doesn't spawn right away
	for (int i = 0 ; i != totalUpgrade ; ++i) // For all of the upgrades
	{
	    int a, b; // Make to integers
	    do
	    {
		a = (int) (Math.random () * TankMap.ROW); // Get a random place on a map
		b = (int) (Math.random () * TankMap.COLUMN);
	    } // Loop until the upgrades get on an empty, a brick, or a grass square
	    while (!(this.getBlockType (a, b).equals ("empty") || this.getBlockType (a, b).equals ("brick") || this.getBlockType (a, b).equals ("grass")));

	    if (i < getArrayTotal (upgradeType, 1)) // For the number of the first kind of upgrade specified
		upgrade [i] = new Upgrade ("uptank", this.getBlockX (a, b), this.getBlockY (a, b)); // Give them these stats
	    else if (i < getArrayTotal (upgradeType, 2)) // For the number of second kind of upgrades
		upgrade [i] = new Upgrade ("uplife", this.getBlockX (a, b), this.getBlockY (a, b));
	    else if (i < getArrayTotal (upgradeType, 3)) // Third
		upgrade [i] = new Upgrade ("supertank", this.getBlockX (a, b), this.getBlockY (a, b));
	    else if (i < getArrayTotal (upgradeType, 4))
		upgrade [i] = new Upgrade ("fixbase", this.getBlockX (a, b), this.getBlockY (a, b));
	    else
		upgrade [i] = new Upgrade ("superbase", this.getBlockX (a, b), this.getBlockY (a, b));
	}
    }


    private void createMap (String[] [] type)  // Auxiliary function
    {
	for (int row = 0 ; row != type.length ; ++row) // For every column and every row of the map
	    for (int col = 0 ; col != type [0].length ; ++col)
		map [row] [col] = new Terrain (type [row] [col], col * Terrain.LENGTH, row * Terrain.WIDTH); // Call constructor for the terrain block
    }


    private static int getArrayTotal (int array[], int elements)  // Method that accumulates the sum of the values of each element of an array
    {
	int total = 0;
	for (int i = 0 ; i != elements ; ++i)
	    total += array [i];
	return total;
    }


    private static int randEnemyStartX ()  // Method that returns a random spawn location for an enemy tank
    {
	return ((int) (Math.random () * 3)) * (MapCollection.COLUMN / 2) * Terrain.LENGTH + (Terrain.LENGTH - Tank.LENGTH) / 2;
    }


    //**********************************************************************MAP METHODS*********************************************************************************

    // INSTANCE METHODS

    public static Rectangle getScreenRect ()  // Returns a rectangle of the playing screen
    {
	Rectangle temp = new Rectangle (0, 0, WIDTH, HEIGHT);
	return temp;
    }


    public Rectangle getLeftBaseRect ()  // Returns a rectangle to the left of the base, this helps the AI
    {
	Rectangle temp = new Rectangle (WIDTH / 3, HEIGHT - Terrain.LENGTH, WIDTH / 2 - Terrain.LENGTH / 2 - WIDTH / 3, Terrain.LENGTH);
	return temp;
    }


    public Rectangle getTopBaseRect ()  // Returns a rectangle to the top of the base, this helps the AI
    {
	Rectangle temp = new Rectangle (WIDTH / 2 - Terrain.LENGTH / 2, HEIGHT - HEIGHT / 3, Terrain.LENGTH, HEIGHT / 3);
	return temp;
    }


    public Rectangle getRightBaseRect ()  // Returns a rectangle to the right of the base, this helps the AI
    {
	Rectangle temp = new Rectangle (WIDTH / 2 + Terrain.LENGTH / 2, HEIGHT - Terrain.LENGTH, WIDTH / 3, Terrain.LENGTH);
	return temp;
    }


    public boolean baseDead ()  // Checks whether the base has been hit or not
    {
	return map [14] [7].bulletAccessible (); // If the base is accessible by bullets, it has been destroyed
    }


    public boolean validTankPosition (Tank tank)  // Returns whether a position held by this tank is valid or not
    {
	if (!getScreenRect ().intersects (tank.getTankRect ())) // If the tank doesn't even touch the screen
	{
	    tank.reset (); // Reset its position
	    return true; // After resetting, its position is valid
	}
	if (!getScreenRect ().contains (tank.getTankRect ())) // If the tank is not being contained by the screen, its position is invalid
	    return false;
	for (int i = 0 ; i != map.length ; ++i) // For all of the map
	    for (int j = 0 ; j != map [0].length ; ++j)
		if (map [i] [j].getRect ().intersects (tank.getTankRect ()) && !map [i] [j].tankAccessible ()) // If the tank is in a sqaure that is not accessible
		    return false; // The place is not valid
	return true; // If the tank passes all of these tests, its position is valid
    }


    private boolean canMoveUp (Tank tank)  // Checks if the tank is able to move up the screen
    {
	tank.moveUp (moveN); // Call the method to move by a constant number of units
	boolean ret = validTankPosition (tank); // Check if position is valid
	tank.moveUpBack (moveN); // Move back to the original position
	return ret; // Return whether the position is valid or not
    }


    private boolean canMoveDown (Tank tank)  // Checks if the tank is able to move down the screen
    {
	tank.moveDown (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveDownBack (moveN);
	return ret;
    }


    private boolean canMoveLeft (Tank tank)  // Left
    {
	tank.moveLeft (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveLeftBack (moveN);
	return ret;
    }


    private boolean canMoveRight (Tank tank)  // Right
    {
	tank.moveRight (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveRightBack (moveN);
	return ret;
    }


    private boolean canMoveLeftUp (Tank tank)  // Left and up
    {
	tank.moveLeft (moveN);
	tank.moveUp (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveLeftBack (moveN);
	tank.moveUpBack (moveN);
	return ret;
    }


    private boolean canMoveLeftDown (Tank tank)  // etc...
    {
	tank.moveLeft (moveN);
	tank.moveDown (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveLeftBack (moveN);
	tank.moveDownBack (moveN);
	return ret;
    }


    private boolean canMoveRightUp (Tank tank)
    {
	tank.moveRight (moveN);
	tank.moveUp (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveRightBack (moveN);
	tank.moveUpBack (moveN);
	return ret;
    }


    private boolean canMoveRightDown (Tank tank)
    {
	tank.moveRight (moveN);
	tank.moveDown (moveN);
	boolean ret = validTankPosition (tank);
	tank.moveRightBack (moveN);
	tank.moveDownBack (moveN);
	return ret;
    }


    public boolean bulletHitCheck (TankBullet bullet)  // Checks whether a part of the map is hitting one of the bullets
    {
	for (int i = 0 ; i != map.length ; ++i) // For all of the blocks of the map
	{
	    for (int j = 0 ; j != map [0].length ; ++j)
	    {
		for (int k = 0 ; k != TankBullet.BULLET_MAX ; ++k) // For all of the bullets
		{
		    if (bullet.fired (k)) // If the bullet is being fired
		    {
			if (!map [i] [j].bulletAccessible () && map [i] [j].getRect ().intersects (bullet.getRect (k))) // If the block is not accessible by bullets and intersects
			{
			    bullet.dead (k); // Call the method to kill the bullet
			    map [i] [j].hit (bullet.heavyBullet (k)); // And the method that registers a hit on the block
			    return true; // The bullet hit
			}
		    }
		}
	    }
	}
	return false; // If there was no hit, return false
    }


    private void fixBase ()  // Method that fixes a base with bricks
    {
	map [map.length - 2] [map.length / 2 - 1].fixBrick ();
	map [map.length - 2] [map.length / 2].fixBrick ();
	map [map.length - 2] [map.length / 2 + 1].fixBrick ();
	map [map.length - 1] [map.length / 2 - 1].fixBrick ();
	map [map.length - 1] [map.length / 2 + 1].fixBrick ();
    }


    private void superBase ()  // Method that makes a base invulnerable
    {
	map [map.length - 2] [map.length / 2 - 1].invulnerable ();
	map [map.length - 2] [map.length / 2].invulnerable ();
	map [map.length - 2] [map.length / 2 + 1].invulnerable ();
	map [map.length - 1] [map.length / 2 - 1].invulnerable ();
	map [map.length - 1] [map.length / 2 + 1].invulnerable ();
	++invulnerableCount;
    }


    private void brickBase ()  // Method that returns a base to brick form
    {
	map [map.length - 2] [map.length / 2 - 1].backToBrick ();
	map [map.length - 2] [map.length / 2].backToBrick ();
	map [map.length - 2] [map.length / 2 + 1].backToBrick ();
	map [map.length - 1] [map.length / 2 - 1].backToBrick ();
	map [map.length - 1] [map.length / 2 + 1].backToBrick ();
    }


    // ACCESSOR METHODS

    public String getCreator ()  // Returns the creator of the map
    {
	return creator;
    }


    public Rectangle getBlockRect (int i, int j)  // Returns the rectangle of the block
    {
	return map [i] [j].getRect ();
    }


    public int getBlockX (int i, int j)  // Returns the x coordinate
    {
	return map [i] [j].getX ();
    }


    public int getBlockY (int i, int j)  // The y
    {
	return map [i] [j].getY ();
    }


    public Image getBlockImage (int i, int j)  // The image
    {
	return map [i] [j].getImage ();
    }


    public String getBlockType (int i, int j)  // The type of the block
    {
	return map [i] [j].getType ();
    }


    //**********************************************************************ENEMY METHODS*********************************************************************************

    // INSTANCE METHODS

    public void enemyPeriodic ()  // Periodic function for the enemy
    {
	if (spawnDelayCount == 0 && leftToSpawn != 0) // If the delay counter has reached the end and there are more tanks to spawn
	    spawn (); // Call method to spawn
	spawnDelayCount = ++spawnDelayCount % spawnDelayLimit; // Advance the spawn delay counter
	for (int i = 0 ; i != totalEnemy ; ++i) // For all of the enemies
	{
	    enemyTank [i].periodic (); // Call their periodic functions
	    if (enemyTank [i].alive ()) // And if they're alive
		moveEnemy (i); // Move them by the AI function
	}
    }


    private void moveEnemy (int i)  // AI method
    {
	if (randomMoveCushionCount [i] == 0 && randomMoveCount [i] == 0) // If the random move period and the following cushion period is over, allow for random movements of tank
	{
	    ++randomMoveCount [i]; // and start random move count
	    if (getLeftBaseRect ().contains (enemyTank [i].getTankRect ()) && (int) (Math.random () * randomAttackChance) == 0) // If the tank is within one of the 3 zones
		randomMoveCount [i] = (direction [i] = 2) - 1;
	    else if (getTopBaseRect ().contains (enemyTank [i].getTankRect ()) && (int) (Math.random () * randomAttackChance) == 0) // around the base, depending on the chance
		randomMoveCount [i] = (direction [i] = 3) - 2;
	    else if (getRightBaseRect ().contains (enemyTank [i].getTankRect ()) && (int) (Math.random () * randomAttackChance) == 0) // of attack, attack the base
		randomMoveCount [i] = (direction [i] = 0) + 1;
	    else if ((int) (Math.random () * randomDirectionChance) == 0) // Randomly move by chance otherwise
	    {
		direction [i] = randDirection (4); // Get a random direction between 1 and 4
		randomMoveCount [i] = 1; // Initiate the random move counter
	    }
	    else // If no random move was made, make sure the random move count is not started
		--randomMoveCount [i];
	}

	if (randomMoveCushionCount [i] > 0) // If it is the cushioning period, advance this counter
	    randomMoveCushionCount [i] = ++randomMoveCushionCount [i] % randomMoveCushionLimit;
	if (randomMoveCount [i] > 0) // If the tank is being randomly moved, advance the counter
	    if ((randomMoveCount [i] = ++randomMoveCount [i] % randomMoveLimit) == 0) // If the random move counter finishes counting, start the cushion count
		++randomMoveCushionCount [i];

	if (randomMoveCount [i] == 0 && moveCount [i] == 0) // If the tank is not being moved randomly, move systematically
	{
	    ++moveCount [i]; // Initiate the movecount
	    if (enemyTank [i].movingUp ()) // Depending on the direction of the tank, pass in the parameters in a certain order to the auxiliary method
		direction [i] = moveEnemyAux (canMoveUp (enemyTank [i]), canMoveRight (enemyTank [i]), canMoveDown (enemyTank [i]), canMoveLeft (enemyTank [i]),
			canMoveLeftUp (enemyTank [i]), canMoveRightUp (enemyTank [i]), canMoveRightDown (enemyTank [i]), canMoveLeftDown (enemyTank [i]));
	    else if (enemyTank [i].movingRight ()) // These statements all have add and modulus operators to make sure the direction corresponds to the actual direction of the tank
		direction [i] = (moveEnemyAux (canMoveRight (enemyTank [i]), canMoveDown (enemyTank [i]), canMoveLeft (enemyTank [i]), canMoveUp (enemyTank [i]),
			    canMoveRightUp (enemyTank [i]), canMoveRightDown (enemyTank [i]), canMoveLeftDown (enemyTank [i]), canMoveLeftUp (enemyTank [i])) + 1) % 4;
	    else if (enemyTank [i].movingDown ()) // The auxiliary method returns the directions for the tank facing forward, but that forward doesn't always represent the actual direction
		direction [i] = (moveEnemyAux (canMoveDown (enemyTank [i]), canMoveLeft (enemyTank [i]), canMoveUp (enemyTank [i]), canMoveRight (enemyTank [i]),
			    canMoveRightDown (enemyTank [i]), canMoveLeftDown (enemyTank [i]), canMoveLeftUp (enemyTank [i]), canMoveRightUp (enemyTank [i])) + 2) % 4;
	    else if (enemyTank [i].movingLeft ()) // If the enemy was moving left, and the aux method decides it should move forward, it would give 1, but we want 0, and this operation finishes the job
		direction [i] = (moveEnemyAux (canMoveLeft (enemyTank [i]), canMoveUp (enemyTank [i]), canMoveRight (enemyTank [i]), canMoveDown (enemyTank [i]),
			    canMoveLeftDown (enemyTank [i]), canMoveLeftUp (enemyTank [i]), canMoveRightUp (enemyTank [i]), canMoveRightDown (enemyTank [i])) + 3) % 4;
	}
	if (moveCount [i] > 0) // If the move counter is initiated
	    moveCount [i] = ++moveCount [i] % moveLimit; // Advance it

	switch (direction [i]) // 0 is left, 1 is up, 2 is right, 3 is down
	{
	    case 0:
		enemyTank [i].moveLeft (); // Call the method to move the tank
		break;
	    case 1:
		enemyTank [i].moveUp ();
		break;
	    case 2:
		enemyTank [i].moveRight ();
		break;
	    case 3:
		enemyTank [i].moveDown ();
		break;
	    default:
		enemyTank [i].moveDown (); // If the direction is not one of the four, move down to move the tanks better around the map
		break;
	}
	if (!validTankPosition (enemyTank [i])) // If the enemy tank is in an invalid position
	    enemyTank [i].moveBack (); // Move back the tank
    }


    // Auxiliary method. This method takes in booleans as to whether a tank can move in that direction
    private static int moveEnemyAux (boolean up, boolean right, boolean down, boolean left, boolean upLeft, boolean upRight, boolean downRight, boolean downLeft)
    {
	int direction = 1; // Default direction is forward
	if (up) // If the tank is able to move up
	{
	    if (upLeft && upRight && downRight && downLeft && left && right) // If in open space, don't change direction
	    {
		if ((int) (Math.random () * randomMoveDownChance) == 0) // If the tank is in open space, give it a chance to move down
		    direction = -100; // by assigning a random number not in the switch case
	    }
	    else if (!upLeft && left && upRight) // up, left, and upright, but not upleft
		direction = randDirection (2); // Choose between left or up
	    else if (!upRight && right && upLeft) // up, right, upleft, but not upright
		direction = randDirection (2) + 1; // Choose between up or right
	    else if (left && right) // up, left and right
		direction = randDirection (3); // Choose between up, left and right
	    else if (right && !left) // up, right, but not left
		direction = randDirection (2) + 1; // Choose between up and right
	    else if (!right && left) // up, left, but not right
		direction = randDirection (2); // Choose between left and up
	}
	else // If not up
	{
	    if (left && right) // left and right
		direction = randDirection (2) * 2; // Choose between left and right
	    else if (right && !left) // Just right
		direction = 2; // Move right
	    else if (!right && left) // Just left
		direction = 0; // Move left
	    else
		direction = 3; // If not up, left, or right, move down
	}
	return direction; // Return the direction obtained
    }


    private static int randDirection (int n)  // Method that returns a number that corresponds to a direction
    {
	return (int) (Math.random () * n); // The parameter specifies how many directions it is considering
    }


    private void spawn ()  // Spawn method
    {
	if (leftToSpawn > 0) // If there is more to spawn
	{
	    int spawnNumber; // Declare a number
	    do
		spawnNumber = (int) (Math.random () * totalEnemy); // Get a random number based on the number of enemies there are
	    while (spawned [spawnNumber]); // Loop until an enemy that hasn't spawned is selected

	    enemyTank [spawnNumber].spawn (); // Call the spawn method on that enemy tank
	    spawned [spawnNumber] = true; // Spawned is true

	    --leftToSpawn; // Decrement the number left to spawn
	    ++spawnDelayCount; // Initiate the spawn delay count
	}
    }


    public void deadCheck (int i)  // Method to check if an enemy was killed
    {
	if (!enemyTank [i].alive ()) // If the tank is not alive
	    ++deadCount; // Advance the dead count
    }


    public boolean allEnemyDead ()  // Method to check if all of the enemy tanks have been killed
    {
	return deadCount == totalEnemy; // If the dead count is the same as the total number of enemies, this would yield true
    }


    // ACCESSOR METHODS

    public int getTotalEnemy ()  // Returns the total number of enemies
    {
	return totalEnemy;
    }


    public Tank getEnemyTank (int i)  // Returns an enemy tank of index i
    {
	return enemyTank [i];
    }


    //********************************************************************UPGRADE METHODS*******************************************************************************

    // INSTANCE METHODS

    public void upgradePeriodic ()  // Periodic function for the upgrades
    {
	appearDelayCount = ++appearDelayCount % appearDelayLimit; // Advance the appear delay count
	if (appearDelayCount == 0 && leftToAppear != 0) // If the delay count reaches the end and there are still more to appear
	    appear (); // Call the appear method

	if (invulnerableCount > 0) // If the base is invulnerable
	    if ((invulnerableCount = ++invulnerableCount % invulnerableLimit) == 0) // Advance the count, and if it reaches the end
		brickBase (); // Turn the base back to brick

	for (int i = 0 ; i != totalUpgrade ; ++i) // For all of the upgrades
	    upgrade [i].periodic (); // Call their periodic functions
    }


    private void appear ()  // Appear method
    {
	if (leftToAppear > 0) // If there are stlil more to appear
	{
	    int appearNumber; // Declare a number
	    do
		appearNumber = (int) (Math.random () * totalUpgrade); // Get a random index to appear
	    while (appeared [appearNumber]); // If that upgrade has already appeared, get another number

	    upgrade [appearNumber].appear (); // Call the appear function on that
	    appeared [appearNumber] = true; // It has now appeared

	    --leftToAppear; // Decrement the number left to appear
	    ++appearDelayCount; // Initiate the delay count
	}
    }


    public void hitUpgradeCheck (Tank player)  // Upgrade hit check on a player tank
    {
	for (int i = 0 ; i != totalUpgrade ; ++i) // For all of the upgrades
	{
	    if (upgrade [i].available () && player.getTankRect ().intersects (upgrade [i].getRect ())) // If the upgrade is available and if the player touches it
	    {
		final String type = upgrade [i].getType (); // Get the type of the upgrade
		if (type.equals ("uptank")) // If the upgrade is supposed to upgrade the tank
		{
		    if (!player.promote ()) // Promote the tank
			continue;
		}
		else if (type.equals ("uplife")) // Add life
		    player.addLife ();
		else if (type.equals ("supertank")) // Make tank invulnerable
		    player.invulnerable ();
		else if (type.equals ("fixbase")) // Fix the base
		    fixBase ();
		else if (type.equals ("superbase")) // Make the base invulnerable
		    superBase ();
		upgrade [i].hit (); // Register the hit on the upgrade
	    }
	}
    }


    // ACCESSOR METHODS

    public int getTotalUpgrade ()  // Get the total number of upgrades
    {
	return totalUpgrade;
    }


    public Upgrade getUpgrade (int i)  // Get the upgrade of index i
    {
	return upgrade [i];
    }
}
