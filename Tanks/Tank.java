import java.awt.Rectangle; // Import appropriate classes
import java.awt.Color;
import java.awt.Image;

public class Tank
{
    //**********************************************************************PUBLIC FIELDS***************************************************************************

    public static final int LENGTH = 32; // Length of the tanks
    public static Image spawnImage, deadImage, starImage; // Images for spawning, dying and the stars
    public static final Color DARK_GREEN = new Color (0x00, 0x80, 0x00), GREEN = new Color (0x00, 0xC0, 0x00), GOLD = new Color (158, 141, 29); // Other colours
    public static final Color gunColour = Color.gray; // Gun colour
    public Color bodyColour, bottomCentreColour, bottomColour, topColour; // Colours for each of the tank objects

    //**********************************************************************PRIVATE FIELDS***************************************************************************

    private static final int WIDTH = 24, GUN_LENGTH = LENGTH - WIDTH; // constant measures. The gun length is the part that is not overlapping the tank body
    private int GUN_WIDTH, starNumber; // Gun width and the starnumber on the tank
    private static final double BASE_SPEED = 1.0; // Set the base speed for all the tanks
    private static int BASE_SHOOT_LIMIT = 48; // Set the base shoot limit
    private static final Color colourSet[] = new Color [4]; // Set the colours for enemies with different lives
    private int startX, startY, life, hp, upgrade; // Integers for the start x and y values, the life, the hitpoints and the upgrade rank
    private double x, y, speed; // Doubles for the x, y and speed of the tank
    private boolean living, heavy, sp, die, invul, st; // Booleans for living, heavy bullet, spawning, dying, invulnerable, and stunned
    private int bulletDelayCount, bulletDelayLimit, spawnCount, dieCount, invulnerableCount, stunCount; // Counters for bullet, spawning, dying, invulnerable and stun
    private static final int spawnLimit = 25, dieLimit = 25, invulnerableLimit = 500, stunLimit = 100; // Count limits for these counters
    private boolean up, down, left, right; // Booleans for which direction the tank is going

    //**********************************************************************INITIALIZATION METHODS***************************************************************************

    Tank (Color co, int xv, int yv, int lives)  // Initialization for players
    {
	// Colours for the tank
	bodyColour = co;
	topColour = GREEN;
	bottomCentreColour = (bodyColour.darker ()).darker ();
	bottomColour = bottomCentreColour.darker ();
	// Setting tank stats
	x = startX = xv + LENGTH / 2; // Starting position
	y = startY = yv + LENGTH / 2;
	life = lives; // Number of lives
	hp = 1; // Number of hp
	down = left = right = heavy = sp = die = living = !(up = true); // Set booleans
	bulletDelayCount = spawnCount = dieCount = invulnerableCount = 0; // Initialize the counters
	convert ((upgrade = 1)); // The tank always start with 1 upgrade
    }


    Tank (Color co, int xv, int yv, int lives, int hitp, int upg)  // Initialization for enemies
    {
	// Colours for the tank
	colourSet [0] = Color.white; // Set the colours for the different hp
	colourSet [1] = Color.yellow;
	colourSet [2] = Color.pink;
	colourSet [3] = Color.red;
	bodyColour = co;
	topColour = colourSet [hitp - 1];
	bottomCentreColour = (bodyColour.darker ()).darker ();
	bottomColour = bottomCentreColour.darker ();
	// Setting tank stats
	x = startX = xv + LENGTH / 2; // Starting position
	y = startY = yv + LENGTH / 2;
	hp = hitp;
	life = lives;
	down = left = right = sp = living = !(up = true);
	bulletDelayCount = spawnCount = dieCount = invulnerableCount = 0;
	convert ((upgrade = upg)); // The level of the tank depends on the parameter
    }


    //**********************************************************************TANK TYPE CONVERSION METHODS*********************************************************************

    private void convert (int upg)  // Switch case method for conversion
    {
	switch (upg)
	{
	    case 1: // Case 1 - 4 are for players
		toSmall ();
		break;
	    case 2:
		toLight ();
		break;
	    case 3:
		toMedium ();
		break;
	    case 4:
		toHeavy ();
		break;
	    case 5: // Case 5 - 7 are for enemies
		toEnemySmall ();
		break;
	    case 6:
		toEnemyLight ();
		break;
	    case 7:
		toEnemyHeavy ();
		break;
	    default:
		System.out.println ("No type of this tank"); // Tell the user if an invalid parameter was inputted
		break;
	}
    }


    private void toSmall ()  // Conversion methods
    {
	GUN_WIDTH = 2; // Set gun width
	starNumber = 1; // Set star number
	speed = BASE_SPEED; // Set speed
	bulletDelayLimit = BASE_SHOOT_LIMIT; // Set shoot speed
	heavy = false; // Heavy bullet or not
    }


    private void toLight ()  // Other conversion methods
    {
	GUN_WIDTH = 2;
	starNumber = 2;
	speed = BASE_SPEED * 1.6;
	bulletDelayLimit = BASE_SHOOT_LIMIT * 5 / 6;
	heavy = false;
    }


    private void toMedium ()
    {
	GUN_WIDTH = 4;
	starNumber = 3;
	speed = BASE_SPEED * 1.5;
	bulletDelayLimit = BASE_SHOOT_LIMIT / 2;
	heavy = false;
    }


    private void toHeavy ()
    {
	GUN_WIDTH = 6;
	starNumber = 4;
	speed = BASE_SPEED * 1.4;
	bulletDelayLimit = BASE_SHOOT_LIMIT / 2;
	heavy = true;
    }


    private void toEnemySmall ()
    {
	GUN_WIDTH = 2;
	starNumber = 0;
	speed = BASE_SPEED;
	bulletDelayLimit = BASE_SHOOT_LIMIT;
	heavy = false;
    }


    private void toEnemyLight ()
    {
	GUN_WIDTH = 2;
	starNumber = 0;
	speed = BASE_SPEED * 2;
	bulletDelayLimit = BASE_SHOOT_LIMIT * 3 / 4;
	heavy = false;
    }


    private void toEnemyHeavy ()
    {
	GUN_WIDTH = 4;
	starNumber = 0;
	speed = BASE_SPEED * 1.5;
	bulletDelayLimit = BASE_SHOOT_LIMIT / 2;
	heavy = false;
    }


    //**********************************************************************UPGRADE METHODS***************************************************************************

    public boolean promote ()  // This method promotes a player, but if the player is at maximum upgrades, it returns false to indicate that
    {
	if (upgrade < 4) // Only upgrade the tank if it is under 4
	{
	    convert (++upgrade); // Advance the upgrade and convert to that tank if necessary
	    return true;
	}
	return false;
    }


    public void addLife ()  // Adds life to the tank
    {
	++life;
	if (!living && !sp) // If the tank is not already living or spawning, spawn it
	    spawn ();
    }


    public void invulnerable ()  // Makes the tank become invulnerable
    {
	invul = true;
	topColour = GOLD; // Set the tank colour to gold
	++invulnerableCount; // Start the timer for invulnerability
    }


    public void shortInvulnerable ()  // This method should be called after the tank has been killed to ensure that the tank doesn't get easily killed again
    {
	invul = true;
	topColour = GOLD;
	invulnerableCount += invulnerableLimit - 100; // Set the timer to be shorter
    }


    //**********************************************************************POSITION METHODS***************************************************************************

    // SIDE SCREEN POSITIONS

    public Rectangle getBodyRect (int xv, int yv)  // Return rectangle with a given (x, y) coordinate
    {
	Rectangle temp = new Rectangle (xv - LENGTH / 2, yv - LENGTH / 2 + GUN_LENGTH, LENGTH, WIDTH);
	return temp;
    }


    public Rectangle getTopRect (int xv, int yv)  // Return for all body parts
    {
	Rectangle temp = new Rectangle (xv - (LENGTH / 2 - LENGTH / 4), yv, LENGTH - 2 * (LENGTH / 4), GUN_LENGTH);
	return temp;
    }


    public Rectangle getGunRect (int xv, int yv)
    {
	Rectangle temp = new Rectangle (xv - GUN_WIDTH / 2, yv - LENGTH / 2, GUN_WIDTH, LENGTH / 2);
	return temp;
    }


    public Rectangle getBottomCentreRect (int xv, int yv)
    {
	Rectangle temp = new Rectangle (xv - LENGTH / 2, yv - LENGTH / 2 + GUN_LENGTH / 2, LENGTH, GUN_LENGTH / 2);
	return temp;
    }


    public Rectangle getBottomRect (int xv, int yv)
    {
	Rectangle temp = new Rectangle (xv - LENGTH / 2, yv - LENGTH / 2, LENGTH, GUN_LENGTH / 2);
	return temp;
    }


    // GAME SCREEN POSITIONS

    public Rectangle getTankRect ()  // Return rectangle in actual position
    {
	Rectangle temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2, LENGTH, LENGTH);
	return temp;
    }


    public Rectangle getBodyRect ()  // The formulae can be found in the commentary
    {
	Rectangle temp;
	if (up)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2 + GUN_LENGTH, LENGTH, WIDTH);
	else if (down)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2, LENGTH, WIDTH);
	else if (left)
	    temp = new Rectangle ((int) x - LENGTH / 2 + GUN_LENGTH, (int) y - LENGTH / 2, WIDTH, LENGTH);
	else
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2, WIDTH, LENGTH);
	return temp;
    }


    public Rectangle getTopRect ()
    {
	Rectangle temp;
	if (up)
	    temp = new Rectangle ((int) x - (LENGTH / 2 - LENGTH / 4), (int) y, LENGTH - 2 * (LENGTH / 4), GUN_LENGTH);
	else if (down)
	    temp = new Rectangle ((int) x - (LENGTH / 2 - LENGTH / 4), (int) y - GUN_LENGTH, LENGTH - 2 * (LENGTH / 4), GUN_LENGTH);
	else if (left)
	    temp = new Rectangle ((int) x, (int) y - (LENGTH / 2 - LENGTH / 4), GUN_LENGTH, LENGTH - 2 * (LENGTH / 4));
	else
	    temp = new Rectangle ((int) x - GUN_LENGTH, (int) y - (LENGTH / 2 - LENGTH / 4), GUN_LENGTH, LENGTH - 2 * (LENGTH / 4));
	return temp;
    }


    public Rectangle getGunRect ()
    {
	Rectangle temp;
	if (up)
	    temp = new Rectangle ((int) x - GUN_WIDTH / 2, (int) y - LENGTH / 2, GUN_WIDTH, LENGTH / 2);
	else if (down)
	    temp = new Rectangle ((int) x - GUN_WIDTH / 2, (int) y, GUN_WIDTH, LENGTH / 2);
	else if (left)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - GUN_WIDTH / 2, LENGTH / 2, GUN_WIDTH);
	else
	    temp = new Rectangle ((int) x, (int) y - GUN_WIDTH / 2, LENGTH / 2, GUN_WIDTH);
	return temp;
    }


    public Rectangle getBottomCentreRect ()
    {
	Rectangle temp;
	if (up)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2 + GUN_LENGTH / 2, LENGTH, GUN_LENGTH / 2);
	else if (down)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y + LENGTH / 2 - GUN_LENGTH, LENGTH, GUN_LENGTH / 2);
	else if (left)
	    temp = new Rectangle ((int) x - LENGTH / 2 + GUN_LENGTH / 2, (int) y - LENGTH / 2, GUN_LENGTH / 2, LENGTH);
	else
	    temp = new Rectangle ((int) x + LENGTH / 2 - GUN_LENGTH, (int) y - LENGTH / 2, GUN_LENGTH / 2, LENGTH);
	return temp;
    }


    public Rectangle getBottomRect ()
    {
	Rectangle temp;
	if (up)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2, LENGTH, GUN_LENGTH / 2);
	else if (down)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y + LENGTH / 2 - GUN_LENGTH / 2, LENGTH, GUN_LENGTH / 2);
	else if (left)
	    temp = new Rectangle ((int) x - LENGTH / 2, (int) y - LENGTH / 2, GUN_LENGTH / 2, LENGTH);
	else
	    temp = new Rectangle ((int) x + LENGTH / 2 - GUN_LENGTH / 2, (int) y - LENGTH / 2, GUN_LENGTH / 2, LENGTH);
	return temp;
    }


    public int getStarX (int i)  // Get the x position for the star of number i for the tank
    {
	if (up)
	    return (int) x - LENGTH / 2 + LENGTH / 4 * i;
	else if (down)
	    return (int) x + LENGTH / 2 - LENGTH / 4 * (i + 1);
	else if (left)
	    return (int) x + LENGTH / 2 - LENGTH / 4;
	else
	    return (int) x - LENGTH / 2;
    }


    public int getStarY (int i)  // Get the y position
    {
	if (up)
	    return (int) y + LENGTH / 2 - LENGTH / 4;
	else if (down)
	    return (int) y - LENGTH / 2;
	else if (left)
	    return (int) y + LENGTH / 2 - LENGTH / 4 * (i + 1);
	else
	    return (int) y - LENGTH / 2 + LENGTH / 4 * i;
    }


    public int getBulletX ()  // Returns the x coordinate for where the bullet should go
    {
	if (living) // Only do so if the tank is living
	{
	    if (up || down)
		return (int) x - TankBullet.WIDTH / 2;
	    else if (left)
		return (int) x - LENGTH / 2 - TankBullet.LENGTH;
	    else if (right)
		return (int) x + LENGTH / 2;
	}
	return 0;
    }


    public int getBulletY ()  // Returns the y coordinate for where the bullet should go
    {
	if (living)
	{
	    if (up)
		return (int) y - LENGTH / 2 - TankBullet.LENGTH;
	    else if (down)
		return (int) y + LENGTH / 2;
	    else
		return (int) y - TankBullet.WIDTH / 2;
	}
	return 0;
    }


    //**********************************************************************MOVEMENT METHODS***************************************************************************

    public void moveUp ()  // Method to move the tank up
    {
	if (living && !st) // Only do so if the tank is living and not stunned
	{
	    down = left = right = !(up = true); // Set boolean up to be true and the rest false
	    y -= speed; // Move the tank up
	}
    }


    public void moveDown ()  // Other methods to move the tank is the specified direction
    {
	if (living && !st)
	{
	    up = left = right = !(down = true);
	    y += speed;
	}
    }


    public void moveLeft ()
    {
	if (living && !st)
	{
	    up = down = right = !(left = true);
	    x -= speed;
	}
    }


    public void moveRight ()
    {
	if (living && !st)
	{
	    up = down = left = !(right = true);
	    x += speed;
	}
    }


    public void moveBack ()  // Move back the tank
    {
	if (living && !st) // Only do so if the tank is living and not stunned
	{
	    if (up) // Moving back depends on which direction the tank is facing
		y += speed;
	    else if (down)
		y -= speed;
	    else if (left)
		x += speed;
	    else if (right)
		x -= speed;
	}
    }


    public void moveUp (int units)  // Method to move up the tank by a certain amount of units on the screen
    {
	if (living) // This only checks living because these methods are used exclusively by AI code
	    y -= units;
    }


    public void moveDown (int units)  // Other methods that move the tank by a certain number of units
    {
	if (living)
	    y += units;
    }


    public void moveLeft (int units)
    {
	if (living)
	    x -= units;
    }


    public void moveRight (int units)
    {
	if (living)
	    x += units;
    }


    public void moveUpBack (int units)  // Methods to move the tank back in a certain direction (the up denotes that the tank will move down)
    {
	if (living)
	    y += units;
    }


    public void moveDownBack (int units)
    {
	if (living)
	    y -= units;
    }


    public void moveLeftBack (int units)
    {
	if (living)
	    x += units;
    }


    public void moveRightBack (int units)
    {
	if (living)
	    x -= units;
    }


    //**********************************************************************OTHER INSTANCE METHODS***************************************************************************

    public void periodic ()  // Periodic function to be executed in the main function of the applet
    {
	if (living) // If the tank is living
	{
	    if (bulletDelayCount > 0) // Advance the bullet delay if it is 0
		bulletDelayCount = ++bulletDelayCount % bulletDelayLimit;
	    if (invulnerableCount > 0) // If the tank is invulnerable
	    {
		if ((invulnerableCount = ++invulnerableCount % invulnerableLimit) == 0) // Advance the invulnerable count, and if the count reaches the end
		{
		    invul = false; // Make the tank not invulnerable
		    topColour = GREEN; // And restore the top colour
		}
	    }
	    if (stunCount > 0) // If the tank is stunned
	    {
		if ((stunCount = ++stunCount % stunLimit) == 0) // Advance the stun count, and if the count reaches the end
		{
		    st = false; // The tank is no longer stunned
		    topColour = GREEN; // Restore the top colour
		}
	    }
	}
	else if (sp) // If the tank is spawned
	{
	    if ((spawnCount = ++spawnCount % spawnLimit) == 0) // Advance the spawn count, and if the count reaches the end
		living = !(sp = false); // The tank is no longer spawning, but is living
	}
	else if (die) // If the tank is dying
	{
	    if ((dieCount = ++dieCount % dieLimit) == 0) // Advance the die count, and if the count reaches the end
	    {
		die = false; // The tank is no longer dying
		if (life > 0) // If the tank has extra lives
		    respawn (); // Spawn it
	    }
	}
    }


    public void stun ()  // Method that stuns the tank
    {
	st = true;
	++stunCount; // Initiate the count
	topColour = Color.white; // Make the top of the tank white
    }


    public void startCount ()  // Method that initiates the count for the bullet delay
    {
	++bulletDelayCount;
    }


    public boolean bulletDelayReady ()  // Returns whether the tank can start firing
    {
	return bulletDelayCount == 0;
    }


    public void spawn ()  // Method that spawns the tank
    {
	if (!living && life > 0) // Only spawn when there is life and if the tank is not living
	{
	    sp = true;
	    ++spawnCount; // Initiate spawn counter
	    x = startX; // Reset the tank's position
	    y = startY;
	    down = left = right = !(up = true); // Make the tank face up
	}
    }


    public void respawn ()  // Method that respawns the tank
    {
	if (!living && life > 0) // Only respawn when there is life and if the tank is not living
	{
	    sp = true;
	    ++spawnCount; // Initiate spawn counter
	    shortInvulnerable (); // Make the tank invulnerable for a bit
	    x = startX; // Reset the tank's position
	    y = startY;
	    down = left = right = !(up = true); // Make the tank face up
	}
    }


    public void hit ()  // Method that registers a hit on the tank
    {
	if (living && !invul) // If the tank is living and it's not invulnerable
	{
	    if (--hp == 0) // lower the hp, and if it reaches 0
	    {
		convert ((upgrade = 1)); // Lower the upgrade of the tank
		hp = 1; // Give one hitpoint for when the tank respawns
		--life; // Lower the life
		living = !(die = true); // The tank is now dying and not living
		++dieCount; // Initiate the die counter
	    }
	    else
		topColour = colourSet [hp - 1]; // If the tank is not hit, give it the colour of that corresponding hp
	}
    }


    public void lifeSteal (Tank t)  // Method that does a life steal on another tank
    {
	if (!die) // If the tank is not dying
	{
	    --t.life; // Lower the life of the other tank
	    ++life; // Advance the life of this tank
	    respawn (); // Make this tank respawn
	}
    }


    public void reset ()  // Method that resets this tank
    {
	x = startX; // Return the tank to its original position
	y = startY;
	down = left = right = sp = die = living = !(up = true); // Reset all of the booleans
	bulletDelayCount = spawnCount = 0; // Reset the counters
    }


    //**********************************************************************ACCESSOR METHODS***************************************************************************

    public boolean movingUp ()  // Method that returns whether or not the tank is moving up
    {
	return up;
    }


    public boolean movingDown ()  // Same as above, except checks for whether the tank is moving down
    {
	return down;
    }


    public boolean movingLeft ()  // etc...
    {
	return left;
    }


    public boolean movingRight ()
    {
	return right;
    }


    public boolean spawning ()  // Accessor method that checks whether the tank is being spawned
    {
	return sp;
    }


    public boolean dying ()  // Or dying
    {
	return die;
    }


    public boolean stunned ()  // Or stunned
    {
	return st;
    }


    public boolean heavyBullet ()  // Or fires heavy bullets that penetrates metal
    {
	return heavy;
    }


    public int getStarNumber ()  // This method gets the number of starts the tank has
    {
	return starNumber;
    }


    public boolean alive ()  // Checks for whether the tank is living
    {
	return living;
    }


    public int getLife ()  // Returns the number of lives the tank has
    {
	return life;
    }
}
