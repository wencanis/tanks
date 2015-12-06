import java.awt.Rectangle;
import java.awt.Image;

public class Upgrade
{
    //**********************************************************************PUBLIC FIELDS***************************************************************************

    public static final int LENGTH = Terrain.LENGTH, WIDTH = Terrain.WIDTH; // Constants for the length and with of the upgrades
    public static Image upTankImage, upLifeImage, tankInvulnerableImage, fixBaseImage, baseInvulnerableImage; // Images

    //**********************************************************************PRIVATE FIELDS***************************************************************************

    private String type; // Type of the upgrade
    private Image image; // Image of the upgrade
    private int x, y; // X and y values
    private boolean avail; // Whether or not the upgrade is available for taking
    private int availableCount; // Counter to limit the time in which the upgrade can be gotten
    private static final int availableLimit = 500; // The number of iterations in the main function before the upgrade goes away

    //**********************************************************************INITIALIZATION METHODS***************************************************************************

    Upgrade (String t, int xv, int yv)  // Constructor
    {
	type = t.toLowerCase (); // Change the type to lower case to reduce coding
	x = xv; // Set the x and y values
	y = yv;
	availableCount = 0; // Initialize the available count
	if (type.equals ("uptank")) // Depening on the upgrade
	    image = upTankImage; // Give it the appropriate image
	else if (type.equals ("uplife"))
	    image = upLifeImage;
	else if (type.equals ("supertank"))
	    image = tankInvulnerableImage;
	else if (type.equals ("fixbase"))
	    image = fixBaseImage;
	else if (type.equals ("superbase"))
	    image = baseInvulnerableImage;
	else
	    System.out.println ("Type of upgrade does not exist"); // If a type given was not one of the above, notify the user of it
    }


    //**********************************************************************INSTANCE METHODS***************************************************************************

    public Rectangle getRect ()  // Get the rectangle for the upgrade
    {
	Rectangle temp = new Rectangle (x, y, LENGTH, WIDTH);
	return temp;
    }


    public void hit ()  // Method that registers a touch on the upgrade
    {
	avail = false; // It is now no longer available
    }


    public void appear ()  // Method that makes the upgrade appear
    {
	avail = true; // It is now available
	++availableCount; // Initialize the available count
    }


    public void periodic ()  // Periodic function to be called
    {
	if (avail) // If the upgrade is available
	    if (++availableCount == availableLimit) // Advance the counter, and if it reaches the limit
		avail = false; // It is not longer available
    }


    //**********************************************************************ACCESSOR METHODS***************************************************************************

    public String getType ()  // Get the type of the upgrade
    {
	return type;
    }


    public Image getImage ()  // Get the imagle of the upgrade
    {
	return image;
    }


    public boolean available ()  // Get whether it is available or not
    {
	return avail;
    }


    public int getX ()  // Get the x value of the upgrade
    {
	return x;
    }


    public int getY ()  // The y
    {
	return y;
    }
}
