import java.awt.Rectangle; // Import the appropriate classes
import java.awt.Image;

public class Terrain
{
    //**********************************************************************PUBLIC FIELDS***************************************************************************

    public static final int LENGTH = 42, WIDTH = 42, BLOCK_HEALTH = 2; // Constants for the length, width, and health for the blocks
    public static Image emptyImage, brickImage, brickBrokenImage, metalImage, metalBrokenImage, waterImage, baseImage, baseBrokenImage, grassImage; // The images

    //**********************************************************************PRIVATE FIELDS***************************************************************************

    private String type; // The type
    private Image image; // The image
    private boolean tankAccess, bulletAccess, destroyable; // Whether or not it's accessible by tanks, bullets or if it's destructible
    private int x, y, hp; // The (x, y) coordinates and the hp of the block

    //**********************************************************************INITIALIZATION METHODS***************************************************************************

    Terrain (String t, int xv, int yv)  // The constructor
    {
	hp = BLOCK_HEALTH; // Set the hp
	x = xv; // Set the x and y values from the parameters
	y = yv;
	t = t.toLowerCase (); // Get the type to lower case to reduce coding
	if (t.equals ("empty")) // Depending on the type, call the corresponding conversion function
	    toEmpty ();
	else if (t.equals ("brick"))
	    toBrick ();
	else if (t.equals ("metal"))
	    toMetal ();
	else if (t.equals ("water"))
	    toWater ();
	else if (t.equals ("base_"))
	    toBase ();
	else if (t.equals ("grass"))
	    toGrass ();
	else
	    System.out.println ("Type of terrain does not exist"); // If the given type does not exist, tell the user about it
    }


    //****************************************************************TERRAIN TYPE CONVERSION METHODS*******************************************************************

    private void toEmpty ()  // Method to turn a block to have empty stats
    {
	type = "empty"; // Change the type
	image = emptyImage; // Give it the correct image
	destroyable = !(tankAccess = bulletAccess = true); // An empty block is accessible by tanks, bullets, but not destructible
    }


    private void toBrick ()  // Method to change the block to brick type
    {
	type = "brick"; // Same things
	image = brickImage;
	tankAccess = bulletAccess = !(destroyable = true);
    }


    private void toBrokenBrick ()  // etc...
    {
	type = "brick";
	image = brickBrokenImage;
	tankAccess = bulletAccess = !(destroyable = true);
    }


    private void toMetal ()
    {
	type = "metal";
	image = metalImage;
	tankAccess = bulletAccess = destroyable = false;
    }


    private void toBrokenMetal ()
    {
	type = "metal";
	image = metalBrokenImage;
	tankAccess = bulletAccess = destroyable = false;
    }


    private void toWater ()
    {
	type = "water";
	image = waterImage;
	tankAccess = destroyable = !(bulletAccess = true);
    }


    private void toBase ()
    {
	type = "base";
	image = baseImage;
	tankAccess = bulletAccess = !(destroyable = true);
	hp = 1; // The base only has 1 hp
    }


    private void toBrokenBase ()
    {
	type = "base";
	image = baseBrokenImage;
	destroyable = !(tankAccess = bulletAccess = true);
    }


    private void toGrass ()
    {
	type = "grass";
	image = grassImage;
	destroyable = !(tankAccess = bulletAccess = true);
    }


    //****************************************************************OTHER INSTANCE METHODS*******************************************************************

    public Rectangle getRect ()  // Get the rectangle of the block
    {
	Rectangle temp = new Rectangle (x, y, LENGTH, WIDTH);
	return temp;
    }


    public void hit (boolean heavyBullet)  // Method to register a hit on a block
    {
	if (destroyable || heavyBullet) // If the block is destructible or if the bullet is heavy bullet
	{
	    if (--hp == 0) // Decrement the hp
	    {
		if (type.equals ("base")) // If the block was the base
		    toBrokenBase (); // Change the base to broken
		else
		    toEmpty (); // Or else change the terrain to empty
	    }
	    else // If the hp didn't reach 0
	    {
		if (type.equals ("brick")) // If the block was a brick
		    image = brickBrokenImage; // Change the image
		else if (type.equals ("metal")) // Same thing for metal
		    image = metalBrokenImage;
	    }
	}
    }


    public void fixBrick ()  // Method to fix this terrain to brick
    {
	hp = BLOCK_HEALTH; // Restore health
	toBrick (); // Change block to brick
    }


    public void backToBrick ()  // Method to change this metal terrain back to brick
    {
	if (hp == 2) // Depending on the health, change to the appropriate type
	    toBrick ();
	else if (hp == 1)
	    toBrokenBrick ();
    }


    public void invulnerable ()  // If method was called to make a block invulnerable
    {
	hp = BLOCK_HEALTH; // Restore health
	toMetal (); // Change everything to metal
    }


    //****************************************************************ACCESSOR METHODS*******************************************************************

    public int getX ()  // Get x of block
    {
	return x;
    }


    public int getY ()  // The y
    {
	return y;
    }


    public String getType ()  // The type
    {
	return type;
    }


    public Image getImage ()  // The image
    {
	return image;
    }


    public boolean tankAccessible ()  // Whether or not the block is accessible by tanks
    {
	return tankAccess;
    }


    public boolean bulletAccessible ()  // Accessibility by bullets
    {
	return bulletAccess;
    }


    public boolean destructible ()  // Whether or not the block is destructible
    {
	return destroyable;
    }
}
