import java.awt.Rectangle; // Import the appropriate classes
import java.awt.Color;

public class TankBullet
{
    //**********************************************************************PUBLIC FIELDS***************************************************************************

    public static final int BULLET_MAX = 100; // The maximum number of bullets there can be on the map
    public static final int LENGTH = 6, WIDTH = 6, SPEED = 4; // The dimensions and the speed of the bullets
    public static final Color bulletColour = Color.white; // The colour of the bullets

    //**********************************************************************PRIVATE FIELDS***************************************************************************

    private boolean heavy[], firedByEnemy[]; // Booleans for whether the bullet is a metal-penetrable one or one fired by an enemy
    private int count; // The counter that counts through the index to fire the bullets
    private int x[], y[], speed[]; // The (x, y) coordinates and the speed of the bullet
    private boolean firing[], moveY[]; // Booleans to check whether the bullet is being fired or whether it's supposed to move along the y axis

    //**********************************************************************INITIALIZATION METHODS***************************************************************************

    TankBullet () // Constructor
    {
	x = new int [BULLET_MAX]; // Initialize the arrays and the counter
	y = new int [BULLET_MAX];
	speed = new int [BULLET_MAX];
	moveY = new boolean [BULLET_MAX];
	firing = new boolean [BULLET_MAX];
	heavy = new boolean [BULLET_MAX];
	firedByEnemy = new boolean [BULLET_MAX];
	count = 0;
    }


    //**********************************************************************INSTANCE METHODS***************************************************************************

    public void periodic () // Periodic method to call
    {
	for (int i = 0 ; i != BULLET_MAX ; ++i) // For all of the bullets
	{
	    if (firing [i]) // If it is being fired
	    {
		if (moveY [i]) // If it is supposed to be moving along the y axis
		    y [i] += speed [i]; // add the speed along the y axis
		else
		    x [i] += speed [i]; // Otherwise add the speed along the x axis
	    }

	    for (int j = 0 ; j != BULLET_MAX ; ++j) // For each of the bullets again
		if (firing [i] && firing [j] && i != j && getRect (i).intersects (getRect (j))) // If both bullets are being fired, if they're not same and if they intersect
		    firing [i] = firing [j] = false; // Make both bullets dead
	}
    }


    public Rectangle getRect (int i) // Method to get the rectangle of a bullet with index i
    {
	Rectangle temp;
	if (moveY [i])
	    temp = new Rectangle (x [i], y [i], WIDTH, LENGTH); // The rectangle is different depending on how it moves
	else
	    temp = new Rectangle (x [i], y [i], LENGTH, WIDTH);
	return temp;
    }


    public void clear () // Method that clears the object
    {
	for (int i = 0 ; i != BULLET_MAX ; ++i)
	    firing [i] = false; // Just change the state of being fired to false
    }


    public void dead (int i) // Method to register a hit on a bullet
    {
	firing [i] = false;
    }


    public boolean tankHitCheck (Tank target) // Check a hit on a tank
    {
	for (int i = 0 ; i != BULLET_MAX ; ++i) // For all of the bullets
	{
	    if (firing [i]) // If the bullet is in the map
	    {
		if (firedByEnemy [i] && target.getStarNumber () == 0) // Don't do anything if enemies are firing at each other
		    ;
		else if (target.getBodyRect ().intersects (getRect (i)) || target.getGunRect ().intersects (getRect (i))) // If it intersects the gun or the body
		{
		    if (!firedByEnemy [i] && target.getStarNumber () > 0) // If the bullet is not fired by an enemy (the player), and if the target is a player
			target.stun (); // Stun the tank
		    else
			target.hit (); // Else register the hit (different targets)
		    firing [i] = false; // Make the firing state false
		    return true; // Notify of hit
		}
	    }
	}
	return false; // If it doesn't hit anything, return false
    }


    public void fire (Tank t) // Method to fire a bullet
    {
	if (t.bulletDelayReady () && !t.stunned ()) // If the firing tank is ready to fire and it is not stunned
	{
	    t.startCount (); // Start the delay count again
	    if (t.movingUp ()) // Fire the bullets in relation to the tank's direction
	    {
		speed [count] = -SPEED; // Set the speed by the speed constant and depending on the direction
		moveY [count] = true; // If the the bullet is supposed to move up, it's moving along the y axis
	    }
	    else if (t.movingDown ()) // Same thing
	    {
		speed [count] = SPEED;
		moveY [count] = true;
	    }
	    else if (t.movingLeft ()) // etc...
	    {
		speed [count] = -SPEED;
		moveY [count] = false;
	    }
	    else if (t.movingRight ())
	    {
		speed [count] = SPEED;
		moveY [count] = false;
	    }
	    firing [count] = true; // The bullet now is in the state of being fired
	    heavy [count] = t.heavyBullet (); // Whether or not the bullet is able to break metal depends on whether or not the tank fires them or not
	    firedByEnemy [count] = t.getStarNumber () == 0; // Whether or not the bullet is fired by an enemy depends on if the tank has any stars
	    x [count] = t.getBulletX (); // Get the positions of the bullets
	    y [count] = t.getBulletY ();
	    if (++count == BULLET_MAX) // Advance the counter, and if it reaches the total number of bullets, reset it
		count = 0;
	}
    }


    //**********************************************************************ACCESSOR METHODS***************************************************************************

    public int getX (int i) // Return the x value of the bullet
    {
	return x [i];
    }


    public int getY (int i) // The y
    {
	return y [i];
    }


    public boolean fired (int i) // Whether or not it's being fired
    {
	return firing [i];
    }


    public boolean heavyBullet (int i) // Whether or not it's a bullet capable of breaking though metal
    {
	return heavy [i];
    }
}
