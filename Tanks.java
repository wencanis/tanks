// The "Tanks" class.
// This program will run an arcade tanks game
// This program should be ran with dimensions 630x630
import java.applet.*; // Import the applet
import java.awt.*; // Import the graphics

public class Tanks extends Applet
{
    // Buffer objects
    Image Buffer; // Declare the image for the screen
    Graphics buff; // Declare the graphics object

    // Map objects
    static final Color screenColour = Color.black; // Set the screen colour
    static final Font gameFont = new Font ("Arial", Font.PLAIN, 50), sideFont = new Font ("Arial", Font.PLAIN, 20); // Set two fonts for the game
    TankMap map; // Create a tank map object
    TankBullet bullet; // Create a bullet object
    int level, selectedLevel, deadCount, interLevelCount; // Declare integers for the current level, the selected level, the dead count for game over, and the interlevel count
    static final int interLevelLimit = 100, firingChance = 20; // Set the amount of time for the interlevel and the chance for the enemies firing
    boolean pause, pressedPause, interLevel, start, levelSelect; // Booleans for pause, the pause key being pressed, whether or not it's interlevel, started or at level select

    // The players' objects
    Tank player[]; // Create the player tank object array
    // Booleans for firing key array, and whether the w, s, a, d, up, down, left right keys are pressed
    boolean fire[], pressedW, prevPressedW, pressedS, prevPressedS, pressedA, pressedD, pressedUp, pressedDown, pressedLeft, pressedRight;

    public void init ()  // Init method
    {
	// Setting values for buffer objects
	Buffer = createImage (size ().width, size ().height);
	buff = Buffer.getGraphics ();

	// Provide pictures for each type of terrain
	Terrain.emptyImage = getImage (getCodeBase (), "Empty 42x42.png");
	Terrain.brickImage = getImage (getCodeBase (), "Brick 42x42.jpg");
	Terrain.brickBrokenImage = getImage (getCodeBase (), "Crumbling Brick 42x42.jpg");
	Terrain.metalImage = getImage (getCodeBase (), "Metal 42x42.jpg");
	Terrain.metalBrokenImage = getImage (getCodeBase (), "Crumbling Metal 42x42.jpg");
	Terrain.waterImage = getImage (getCodeBase (), "Water 42x42.jpg");
	Terrain.baseImage = getImage (getCodeBase (), "Leaf 42x42.gif");
	Terrain.baseBrokenImage = getImage (getCodeBase (), "Burnt Leaf 42x42.jpg");
	Terrain.grassImage = getImage (getCodeBase (), "Grass 42x42.png");
	// Provide pictures for each type of upgrade
	Upgrade.upTankImage = getImage (getCodeBase (), "UpTank 42x42.png");
	Upgrade.upLifeImage = getImage (getCodeBase (), "UpLife 42x42.png");
	Upgrade.tankInvulnerableImage = getImage (getCodeBase (), "UpInvulnerable 42x42.png");
	Upgrade.fixBaseImage = getImage (getCodeBase (), "UpFixBase 42x42.png");
	Upgrade.baseInvulnerableImage = getImage (getCodeBase (), "UpProtectBase 42x42.png");
	// Provide pictures for the tanks
	Tank.spawnImage = getImage (getCodeBase (), "Lightbeam 32x32.png");
	Tank.deadImage = getImage (getCodeBase (), "Bam 32x32.gif");
	Tank.starImage = getImage (getCodeBase (), "Star 8x8.png");

	startScreenInit (); // Call initialization method for the start screen
    }


    public void startScreenInit ()  // Start screen initialization method
    {
	interLevelCount = level = 0; // Reset the interlevel count and level
	// Get the map for the startscreen
	map = new TankMap (MapCollection.getCreator (level), MapCollection.getMap (level), MapCollection.getEnemyNumber (level), MapCollection.getUpgradeNumber (level));
	player = new Tank [1]; // Tank object
	fire = new boolean [2]; // Fire check booleans
	player [0] = new Tank (Color.red, TankMap.WIDTH / 2 - Tank.LENGTH / 2, size ().height - 2 * Terrain.LENGTH - Tank.LENGTH, 1); // Create startscreen tank
	player [0].spawn (); // Spawn the tank

	selectedLevel = 1; // The selected level is default 1
	levelSelect = !(start = true); // It is not level select. Make start true so that the startscreen method can be called
    }


    public void startScreen ()
    {
	final Rectangle startRect = new Rectangle (100, 515, 65, 15); // Set rectangles for the start, exit and level select so that the tank can go over them
	final Rectangle exitRect = new Rectangle (530, 515, 45, 15);
	final Rectangle selectRect = new Rectangle (0, 605, 107, 15);
	buff.setFont (sideFont); // Set the font to the side font
	buff.setColor (Color.white); // Set colour to white
	if (!levelSelect) // If it is not level select
	{
	    buff.drawString ("START", 100, 530); // Draw the words so that the user can select its options
	    buff.drawString ("EXIT", 530, 530);
	    buff.drawString ("Level Select", 0, 620);
	}

	if (player [0].getTankRect ().intersects (startRect)) // If player went on the start rectangle
	{
	    ++level; // Advance the level
	    start = !(interLevel = true); // It is in between levels and not at start screen
	    startGame (); // Call method to start game
	}
	else if (player [0].getTankRect ().intersects (exitRect)) // If payer went on the exit rectangle
	    System.exit (1); // Exit the program
	else if (!levelSelect && player [0].getTankRect ().intersects (selectRect)) // If the player went on the level select rectangle
	    levelSelect = true; // Make that boolean true
	else if (levelSelect) // If the player is on level select
	{
	    buff.drawString ("Select with w/s keys and press space to start", TankMap.WIDTH / 2 - 200, TankMap.HEIGHT / 2 - 50); // Draw instructions
	    buff.drawString ("Level: " + selectedLevel, TankMap.WIDTH / 2, TankMap.HEIGHT / 2); // Draw the level selected
	    if (pressedW && !prevPressedW && selectedLevel < MapCollection.LEVEL_MAX) // If the user pressed w
	    {
		++selectedLevel; // Advance the level
		prevPressedW = true; // The level was already advanced with the pressing of the w key
	    }
	    else if (pressedS && !prevPressedS && selectedLevel > 1) // If the user pressed s
	    {
		--selectedLevel; // Decrement the level
		prevPressedS = true; // The level was already lowered with the pressing of the s key
	    }
	    if (pause) // The pause boolean is synonymous with the space key
	    {
		level = selectedLevel; // Start the game at the selected level
		pause = start = !(interLevel = true); // interlevel is now true, but stop the pause so the game won't pause, and we no longer are at the start screen
		startGame (); // Call method to start the game
	    }
	}
	else // If no options were selected
	{
	    player [0].periodic (); // Call periodic function for player
	    if (pressedW) // Move the player with the keys
		player [0].moveUp ();
	    else if (pressedS)
		player [0].moveDown ();
	    else if (pressedA)
		player [0].moveLeft ();
	    else if (pressedD)
		player [0].moveRight ();
	    // Check if position is valid and move back if not
	    if (!map.validTankPosition (player [0]))
		player [0].moveBack ();

	    // Draw map
	    for (int i = 0 ; i != TankMap.ROW ; ++i)
		for (int j = 0 ; j != TankMap.COLUMN ; ++j)
		    buff.drawImage (map.getBlockImage (i, j), map.getBlockX (i, j), map.getBlockY (i, j), this);
	    if (player [0].alive ()) // If the player is alive, draw it
	    {
		fillRect (player [0].getBodyRect (), player [0].bodyColour); // Hull body
		fillRect (player [0].getBottomCentreRect (), player [0].bottomCentreColour); // Middle hull plate
		fillRect (player [0].getBottomRect (), player [0].bottomColour); // Lower hull plate
		fillRect (player [0].getGunRect (), player [0].gunColour); // Gun
		fillRect (player [0].getTopRect (), player [0].topColour); // Gun turret
	    }
	    else if (player [0].spawning ()) // If the player is instead being spawned, draw the spawning image
		buff.drawImage (Tank.spawnImage, player [0].getTankRect ().x, player [0].getTankRect ().y, this);
	}
    }


    public void startGame ()  // Start game method
    {
	// Initialize map to have the map for level 1
	map = new TankMap (MapCollection.getCreator (level), MapCollection.getMap (level), MapCollection.getEnemyNumber (level), MapCollection.getUpgradeNumber (level));
	deadCount = 100; // Count this many times and the program will exit

	bullet = new TankBullet (); // Initialization of bullet object

	// Declaration of players' array objects
	player = new Tank [2]; // Tank object
	// Initialization of players' array objects
	int playerStartX = (TankMap.ROW / 2 - 2) * Terrain.LENGTH + (Terrain.LENGTH - Tank.LENGTH) / 2; // Set starting positions
	int playerStartY = size ().height - Tank.LENGTH;
	player [0] = new Tank (Color.red, playerStartX, playerStartY, 3); // Parameters: colour, startx, starty, hitpoints, speed, fire delay
	player [0].spawn (); // Spawn the player

	playerStartX = (TankMap.ROW / 2 + 2) * Terrain.LENGTH + (Terrain.LENGTH - Tank.LENGTH) / 2; // Same as player 0
	playerStartY = size ().height - Tank.LENGTH;
	player [1] = new Tank (Color.blue, playerStartX, playerStartY, 3);
	player [1].spawn ();
    }


    public void nextLevel ()
    {
	// Get the new map for the new level
	map = new TankMap (MapCollection.getCreator (level), MapCollection.getMap (level), MapCollection.getEnemyNumber (level), MapCollection.getUpgradeNumber (level));

	// Clear the screen of any bullets
	bullet.clear ();

	// Reset and respawn players
	player [0].reset ();
	player [0].spawn ();
	player [1].reset ();
	player [1].spawn ();
    }


    // Method for detecting pressed keys
    public boolean keyDown (Event e, int key)
    {
	// These keys are for player 1
	if (key == 119 || key == 87) // These numbers represent the character values
	    pressedW = true;
	else if (key == 115 || key == 83)
	    pressedS = true;
	else if (key == 97 || key == 65)
	    pressedA = true;
	else if (key == 100 || key == 68)
	    pressedD = true;
	if (key == 96)
	    fire [0] = true;

	// These keys are for player 2
	if (key == Event.UP)
	    pressedUp = true; // Make the corresponding boolean true if a key is pressed
	else if (key == Event.DOWN)
	    pressedDown = true;
	else if (key == Event.LEFT)
	    pressedLeft = true;
	else if (key == Event.RIGHT)
	    pressedRight = true;
	if (key == 47)
	    fire [1] = true;

	if (key == 32 && !pressedPause) // Pause key (Space bar)
	{
	    pressedPause = true; // If the space bar is pressed, it WAS pressed
	    pause = !pause;
	}

	return true;
    }


    // Method for detecting released keys
    public boolean keyUp (Event e, int key)
    {
	// These keys are for player 1
	if (key == 119 || key == 87) // These numbers represent the character values
	    pressedW = prevPressedW = false;
	else if (key == 115 || key == 83)
	    pressedS = prevPressedS = false;
	else if (key == 97 || key == 65)
	    pressedA = false;
	else if (key == 100 || key == 68)
	    pressedD = false;
	if (key == 96)
	    fire [0] = false;

	// These keys are for player 2
	if (key == Event.UP)
	    pressedUp = false; // Make the corresponding boolean true if a key is pressed
	else if (key == Event.DOWN)
	    pressedDown = false;
	else if (key == Event.LEFT)
	    pressedLeft = false;
	else if (key == Event.RIGHT)
	    pressedRight = false;
	if (key == 47)
	    fire [1] = false;

	if (key == 32) // Pause key (Space bar)
	    pressedPause = false; // If the space bar is released, it is no longer being pressed

	return true;
    }


    public static void delay (int ms)  // Delay method
    {
	try // Try statement for delay
	{
	    Thread.sleep (ms);
	}
	catch (InterruptedException e)  // Catch clause for the interrupted exception
	{
	    System.out.println ("Error in sleeping"); // Notify the user of the problem
	}
    }


    public void fillRect (Rectangle rect, Color colour)  // Method to draw a rectangle object with a specific colour
    {
	buff.setColor (colour); // Set the colour
	buff.fillRect (rect.x, rect.y, rect.width, rect.height); // Draw the rectangle
    }


    public void main ()  // Main method
    {
	if (start) // If the game is at start
	    startScreen (); // Call the startscreen method
	else // If the game is not at the start
	{
	    if (pause) // If the user wants to pause, pause here
	    {
		buff.setFont (gameFont); // Set font to game font
		buff.setColor (Color.white); // Set colour to white
		buff.drawString ("GAME PAUSED", size ().width / 6, size ().height / 2); // Notify the user of the game being paused
	    }
	    else if (interLevel) // If the game is in between levels
	    {
		buff.setFont (gameFont); // Do the same font and colour settings
		buff.setColor (Color.white);
		buff.drawString ("Level " + level, size ().width / 4, size ().height / 2); // Notify
		if (++interLevelCount == interLevelLimit) // Advance the counter, and if it reached the limit
		{
		    interLevelCount = 0; // Reset the counter
		    interLevel = false; // It is no longer in between levels
		    nextLevel (); // Call the function to start the next level
		}
	    }
	    else
	    {
		//****************************************************************ACTION***********************************************************************************

		if (map.allEnemyDead ()) // If all the enemies are dead
		{
		    ++level; // Advance the level
		    interLevel = true; // It is now in between levels
		}

		// Player 1 actions
		player [0].periodic (); // Call the period function to be executed
		if (player [0].alive ()) // If the player is alive
		{
		    bullet.tankHitCheck (player [0]); // Bullet hit detection
		    map.hitUpgradeCheck (player [0]); // Upgrade detection
		    // Move player
		    if (pressedW)
			player [0].moveUp ();
		    else if (pressedS)
			player [0].moveDown ();
		    else if (pressedA)
			player [0].moveLeft ();
		    else if (pressedD)
			player [0].moveRight ();
		    // Check if position is valid and move back if not
		    if (!map.validTankPosition (player [0]))
			player [0].moveBack ();
		    // Fire check
		    if (fire [0])
			bullet.fire (player [0]);
		}
		else if (player [0].getLife () == 0 && player [1].getLife () > 1) // Life steal if necessary and possible
		    player [0].lifeSteal (player [1]);

		// Player 2 actions
		player [1].periodic (); // Call the period function to be executed
		if (player [1].alive ())
		{
		    bullet.tankHitCheck (player [1]); // Bullet hit detection
		    map.hitUpgradeCheck (player [1]); // Upgrade detection
		    // Move player
		    if (pressedUp)
			player [1].moveUp ();
		    else if (pressedDown)
			player [1].moveDown ();
		    else if (pressedLeft)
			player [1].moveLeft ();
		    else if (pressedRight)
			player [1].moveRight ();
		    // Check if position is valid and move back if not
		    if (!map.validTankPosition (player [1]))
			player [1].moveBack ();
		    // Fire check
		    if (fire [1])
			bullet.fire (player [1]);
		}
		else if (player [1].getLife () == 0 && player [0].getLife () > 1) // Life steal if necessary and possible
		    player [1].lifeSteal (player [0]);

		// Enemy actions
		map.enemyPeriodic (); // Call the periodic function to be executed
		for (int i = 0 ; i != map.getTotalEnemy () ; ++i) // Loop through all the enemies
		{
		    if (map.getEnemyTank (i).alive ())
		    {
			// Hit check
			if (bullet.tankHitCheck (map.getEnemyTank (i)))
			    map.deadCheck (i); // If hit, dead check
			// Fire by chance
			if ((int) (Math.random () * firingChance) == 0)
			    bullet.fire (map.getEnemyTank (i));
		    }
		}

		// Upgrade actions
		map.upgradePeriodic (); // Call the periodic function to be executed

		// Bullet actions
		bullet.periodic ();

		// Map actions
		map.bulletHitCheck (bullet); // Map hit check

		//****************************************************************DRAW***********************************************************************************

		// Draw enemies
		for (int i = 0 ; i != map.getTotalEnemy () ; ++i) // Looping through all of the enemies
		{
		    if (map.getEnemyTank (i).alive ()) // If the enemy is alive, draw it
		    {
			fillRect (map.getEnemyTank (i).getBodyRect (), map.getEnemyTank (i).bodyColour); // Hull body
			fillRect (map.getEnemyTank (i).getBottomCentreRect (), map.getEnemyTank (i).bottomCentreColour); // Middle hull plate
			fillRect (map.getEnemyTank (i).getBottomRect (), map.getEnemyTank (i).bottomColour); // Lower hull plate
			fillRect (map.getEnemyTank (i).getGunRect (), map.getEnemyTank (i).gunColour); // Gun
			fillRect (map.getEnemyTank (i).getTopRect (), map.getEnemyTank (i).topColour); // Gun turret
		    }
		}
		for (int i = 0 ; i != map.getTotalEnemy () ; ++i) // This for loops is after the one before to ensure that the spawn animation is always seen
		    if (map.getEnemyTank (i).spawning ()) // If the enemy is being spawned, draw the spawn image
			buff.drawImage (Tank.spawnImage, map.getEnemyTank (i).getTankRect ().x, map.getEnemyTank (i).getTankRect ().y, this);

		// Draw players
		for (int i = 0 ; i != player.length ; ++i) // Loop through all of the players
		{
		    if (player [i].alive ()) // Only draw if player is alive
		    {
			fillRect (player [i].getBodyRect (), player [i].bodyColour); // Hull body
			fillRect (player [i].getBottomCentreRect (), player [i].bottomCentreColour); // Middle hull plate
			fillRect (player [i].getBottomRect (), player [i].bottomColour); // Lower hull plate
			fillRect (player [i].getGunRect (), player [i].gunColour); // Gun
			fillRect (player [i].getTopRect (), player [i].topColour); // Gun turret
			for (int j = 0 ; j != player [i].getStarNumber () ; ++j) // Draw the star images
			    buff.drawImage (Tank.starImage, player [i].getStarX (j), player [i].getStarY (j), this);
		    }
		    else if (player [i].spawning ()) // Draw spawning or dying images if necessary
			buff.drawImage (Tank.spawnImage, player [i].getTankRect ().x, player [i].getTankRect ().y, this);
		    else if (player [i].dying ())
			buff.drawImage (Tank.deadImage, player [i].getTankRect ().x, player [i].getTankRect ().y, this);
		}

		// Draw map
		for (int i = 0 ; i != TankMap.ROW ; ++i)
		    for (int j = 0 ; j != TankMap.COLUMN ; ++j)
			buff.drawImage (map.getBlockImage (i, j), map.getBlockX (i, j), map.getBlockY (i, j), this);

		// Draw upgrades
		for (int i = 0 ; i != map.getTotalUpgrade () ; ++i)
		    if (map.getUpgrade (i).available ())
			buff.drawImage (map.getUpgrade (i).getImage (), map.getUpgrade (i).getX (), map.getUpgrade (i).getY (), this);

		// Draw bullets
		for (int i = 0 ; i != TankBullet.BULLET_MAX ; ++i)
		{
		    if (bullet.fired (i)) // Only draw if it is being fired
		    {
			if (!TankMap.getScreenRect ().contains (bullet.getRect (i))) // Out of bounds check
			    bullet.dead (i);
			fillRect (bullet.getRect (i), TankBullet.bulletColour);
		    }
		}
	    }
	    infoScreen (); // Draw the sidescreen
	}
    }


    public void deadCheck (Graphics g)  // Check for if game is over
    {
	if (map.baseDead () || player [0].getLife () == 0 && player [1].getLife () == 0) // Game is over if base is hit or if both players are out of lives
	{
	    buff.setFont (gameFont); // Set font and colour
	    buff.setColor (Color.white);
	    buff.drawString ("GAME OVER", size ().width / 2 - 180, size ().height / 2); // Notify
	    if (--deadCount == 0) // If count reaches zero
		startScreenInit (); // Call start screen initiaization method
	}
    }


    public void clearScreen ()  // Clear screen method
    {
	buff.setColor (Tank.DARK_GREEN); // Set colour for side screen
	buff.fillRect (0, 0, size ().width, size ().height); // Fill background
	buff.setColor (screenColour); // Set colour for main screen
	buff.fillRect (0, 0, size ().width - 200, size ().height); // Fill the game screen
    }


    public void infoScreen ()  // Information screen method
    {
	// Draw general game information
	buff.setFont (sideFont);
	buff.setColor (Color.white);
	buff.drawString ("Level " + level, 700, 20); // The level
	if (map.getCreator () != null) // If there was a creator
	{
	    buff.drawString ("Map Created by: ", 650, 50); // Draw the name of the creator
	    buff.drawString (map.getCreator (), 650, 80);
	}

	// Draw players' information
	final int xPos = 630 + 50, yPos = 500; // integers for the placing of the players' tanks
	for (int i = 0 ; i != player.length ; ++i) // For all of the players
	{
	    fillRect (player [i].getBodyRect (xPos, yPos + i * 100), player [i].bodyColour); // Hull body
	    fillRect (player [i].getBottomCentreRect (xPos, yPos + i * 100), player [i].bottomCentreColour); // Middle hull plate
	    fillRect (player [i].getBottomRect (xPos, yPos + i * 100), player [i].bottomColour); // Lower hull plate
	    fillRect (player [i].getGunRect (xPos, yPos + i * 100), player [i].gunColour); // Gun
	    fillRect (player [i].getTopRect (xPos, yPos + i * 100), player [i].topColour); // Gun turret
	    buff.setColor (Color.white);
	    buff.drawString ("Life: " + player [i].getLife (), xPos + 40, yPos + i * 100 + 5); // Life
	}
    }


    public void update (Graphics g)  // Method that updates the screen
    {
	paint (g);
    }


    // Method that executes the program and paints the its instructions onto the screen
    public void paint (Graphics g)
    {
	clearScreen ();
	main (); // Main method
	deadCheck (g); // Check if it's game over
	g.drawImage (Buffer, 0, 0, this); // Draw the image
	repaint (); // Update the screen
	delay (10); // Delay for 10 miliseconds
    }
} // Tanks class
