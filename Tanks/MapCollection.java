// This class contains the maps
// Dimensions: 15 x 15
// All types are ensured to have 5 characters to help map-making
// Types available: empty, brick, metal, water, grass
public class MapCollection
{
    public static final int ROW = 15, COLUMN = 15, LEVEL_MAX = 7; // Constants for the set dimensions and the number of levels currently available

    // Please add your name to the collection by level
    public static String getCreator (int level)  // Returns the creator given a level
    {
	switch (level)
	{
	    case 0:
		return null;
	    case 1:
		return null;
	    case 2:
		return "Cory Sulpizi";
	    case 3:
		return "Kevin Xu";
	    case 4:
		return "Kevin Xu";
	    case 5:
		return "Kevin Xu";
	    case 6:
		return "Kevin Xu";
	    case 7:
		return "Kevin Xu";
	    default:
		System.out.println ("Level does not exist"); // Tell the user if the level specified does not exist
		return null;
	}
    }


    public static String[] [] getMap (int level)  // Returns a 2D array given a level
    {
	switch (level)
	{
	    case 0:
		return Level_0;
	    case 1:
		return Level_1;
	    case 2:
		return Level_2;
	    case 3:
		return Level_3;
	    case 4:
		return Level_4;
	    case 5:
		return Level_5;
	    case 6:
		return Level_6;
	    case 7:
		return Level_7;
	    default:
		System.out.println ("Level does not exist");
		return null;
	}
    }


    public static int[] getEnemyNumber (int level)  // Returns an array of integers for the number of each type of tank given a level
    {
	switch (level)
	{
	    case 0:
		return Level_0_Enemy;
	    case 1:
		return Level_1_Enemy;
	    case 2:
		return Level_2_Enemy;
	    case 3:
		return Level_3_Enemy;
	    case 4:
		return Level_4_Enemy;
	    case 5:
		return Level_5_Enemy;
	    case 6:
		return Level_6_Enemy;
	    case 7:
		return Level_7_Enemy;
	    default:
		System.out.println ("Level does not exist");
		return new int [7];
	}
    }


    public static int[] getUpgradeNumber (int level)  // Returns an array of numbers indicating how many of each kind of upgrades there are for a level
    {
	switch (level)
	{
	    case 0:
		return Level_0_Upgrade;
	    case 1:
		return Level_1_Upgrade;
	    case 2:
		return Level_2_Upgrade;
	    case 3:
		return Level_3_Upgrade;
	    case 4:
		return Level_4_Upgrade;
	    case 5:
		return Level_5_Upgrade;
	    case 6:
		return Level_6_Upgrade;
	    case 7:
		return Level_7_Upgrade;
	    default:
		System.out.println ("Level does not exist");
		return null;
	}
    }


    /* Template
    private static final String Level_X[] [] =  // Initialize terrain type of all blocks here, MAKE SURE TO MAKE THE MAP JUST LIKE HOW IT WOULD LOOK LIKE!
	{
	    {"empty", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "empty", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "empty"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "brick", "brick", "brick", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"},
	    {"xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "empty", "brick", "base_", "brick", "empty", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx"}
	};

    // The description is what the number that you enter entails
    private static final int Level_1_Enemy[] = {small 1hp, quick 1hp, quick 2hp, heavy 1hp, heavy 2hp, heavy 3hp, heavy 4hp};

    private static final int Level_1_Upgrade[] = {upgrade tank, add life, make tank invulnerable, fix base bricks, make base invulnerable};
    */

    private static final String Level_0[] [] =
	{
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "brick", "empty", "empty", "empty", "brick", "empty", "brick", "brick", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "brick", "empty", "empty", "empty", "brick", "empty", "brick", "empty", "empty", "brick", "empty", "brick", "brick", "empty"},
	    {"empty", "brick", "empty", "empty", "empty", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "empty"},
	    {"empty", "brick", "empty", "brick", "empty", "brick", "empty", "brick", "empty", "empty", "brick", "empty", "empty", "brick", "empty"},
	    {"empty", "empty", "brick", "empty", "brick", "empty", "empty", "brick", "brick", "empty", "brick", "empty", "empty", "brick", "empty"},
	    {"empty", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "empty"},
	    {"brick", "brick", "brick", "empty", "brick", "empty", "empty", "brick", "empty", "empty", "brick", "empty", "brick", "empty", "empty"},
	    {"empty", "brick", "empty", "brick", "empty", "brick", "empty", "brick", "brick", "empty", "brick", "empty", "brick", "empty", "brick"},
	    {"empty", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "empty"},
	    {"empty", "brick", "empty", "brick", "empty", "brick", "empty", "brick", "empty", "empty", "brick", "empty", "brick", "empty", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "brick", "base_", "brick", "empty", "empty", "empty", "empty", "empty", "empty"}
	};

    private static final int Level_0_Enemy[] = {0, 0, 0, 0, 0, 0, 0};

    private static final int Level_0_Upgrade[] = {0, 0, 0, 0, 0};

    private static final String Level_1[] [] =
	{
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "base_", "brick", "empty", "brick", "brick", "empty", "brick", "brick"}
	};

    private static final int Level_1_Enemy[] = {10, 0, 0, 0, 0, 0, 0};

    private static final int Level_1_Upgrade[] = {1, 0, 0, 0, 0};

    private static final String Level_2[] [] =
	{
	    {"empty", "empty", "empty", "brick", "brick", "empty", "empty", "empty", "empty", "empty", "brick", "brick", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "metal", "metal", "metal", "empty", "metal", "empty", "metal", "metal", "metal", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "metal", "metal", "grass", "empty", "empty", "empty", "grass", "metal", "metal", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "empty", "brick", "brick"},
	    {"brick", "brick", "empty", "metal", "metal", "empty", "brick", "base_", "brick", "empty", "metal", "metal", "empty", "brick", "brick"}
	};

    private static final int Level_2_Enemy[] = {15, 2, 0, 0, 0, 0, 0};

    private static final int Level_2_Upgrade[] = {1, 0, 1, 1, 1};

    private static final String Level_3[] [] =
	{
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "brick", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "brick", "brick"},
	    {"brick", "brick", "brick", "empty", "empty", "brick", "brick", "brick", "brick", "brick", "empty", "empty", "brick", "brick", "brick"},
	    {"brick", "metal", "brick", "empty", "empty", "brick", "metal", "metal", "metal", "brick", "empty", "empty", "brick", "metal", "metal"},
	    {"brick", "brick", "brick", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "brick", "brick", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water"},
	    {"water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water"},
	    {"water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water", "water", "water", "empty", "water", "water"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"brick", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "brick"},
	    {"brick", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "brick"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "metal", "empty", "metal", "metal", "empty", "brick", "brick", "brick", "empty", "metal", "metal", "empty", "metal", "metal"},
	    {"metal", "metal", "empty", "metal", "metal", "empty", "brick", "base_", "brick", "empty", "metal", "metal", "empty", "metal", "metal"}
	};

    private static final int Level_3_Enemy[] = {12, 4, 0, 2, 0, 0, 0};

    private static final int Level_3_Upgrade[] = {1, 1, 1, 1, 2};

    private static final String Level_4[] [] =
	{
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "water", "water", "water", "metal", "grass", "grass", "grass", "grass", "grass", "metal", "water", "water", "water", "empty"},
	    {"water", "water", "water", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "water", "water", "water"},
	    {"water", "water", "empty", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "empty", "water", "water"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "brick", "brick", "empty", "empty", "empty", "metal", "empty", "empty", "empty", "brick", "empty", "empty", "brick"},
	    {"empty", "empty", "brick", "brick", "brick", "empty", "empty", "metal", "empty", "empty", "brick", "brick", "empty", "empty", "brick"},
	    {"empty", "empty", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "empty", "empty"},
	    {"grass", "grass", "grass", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "grass", "grass", "grass"},
	    {"metal", "grass", "grass", "grass", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "grass", "grass", "grass", "metal"},
	    {"grass", "grass", "grass", "grass", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "grass", "grass", "grass", "grass"},
	    {"empty", "empty", "brick", "empty", "empty", "empty", "brick", "empty", "brick", "empty", "empty", "empty", "brick", "empty", "empty"},
	    {"empty", "empty", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "metal", "empty", "empty"},
	    {"grass", "grass", "empty", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "empty", "grass", "grass"},
	    {"grass", "grass", "empty", "empty", "empty", "empty", "brick", "base_", "brick", "empty", "empty", "empty", "empty", "grass", "grass"}
	};

    private static final int Level_4_Enemy[] = {10, 5, 0, 4, 2, 0, 0};

    private static final int Level_4_Upgrade[] = {2, 2, 2, 2, 2};

    private static final String Level_5[] [] =
	{
	    {"empty", "metal", "metal", "metal", "metal", "metal", "metal", "empty", "metal", "metal", "metal", "metal", "metal", "metal", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "metal", "empty", "empty", "empty", "water", "water", "water", "water", "water", "empty", "empty", "empty", "metal", "metal"},
	    {"metal", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "metal"},
	    {"empty", "empty", "empty", "metal", "metal", "water", "water", "water", "water", "water", "metal", "metal", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "metal", "metal", "water", "water", "water", "water", "water", "metal", "metal", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "water", "water", "water", "water", "water", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "grass", "metal", "metal", "grass", "water", "water", "water", "water", "water", "grass", "metal", "metal", "grass", "metal"},
	    {"metal", "grass", "metal", "metal", "grass", "water", "water", "water", "water", "water", "grass", "metal", "metal", "grass", "metal"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "grass", "metal", "empty", "empty", "empty", "metal", "metal", "metal", "empty", "empty", "empty", "metal", "grass", "metal"},
	    {"metal", "grass", "grass", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "empty", "grass", "grass", "metal"},
	    {"metal", "metal", "metal", "empty", "empty", "empty", "brick", "base_", "brick", "empty", "empty", "empty", "metal", "metal", "metal"}
	};

    private static final int Level_5_Enemy[] = {8, 6, 1, 4, 4, 1, 0};

    private static final int Level_5_Upgrade[] = {2, 2, 2, 1, 1};

    private static final String Level_6[] [] =
	{
	    {"empty", "empty", "empty", "empty", "brick", "brick", "grass", "grass", "grass", "brick", "brick", "empty", "empty", "empty", "empty"},
	    {"empty", "metal", "metal", "empty", "empty", "grass", "grass", "water", "grass", "grass", "empty", "empty", "metal", "metal", "empty"},
	    {"empty", "metal", "metal", "empty", "metal", "brick", "grass", "water", "grass", "brick", "metal", "empty", "metal", "metal", "empty"},
	    {"empty", "empty", "empty", "empty", "metal", "metal", "grass", "water", "grass", "metal", "metal", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "empty", "metal", "brick", "grass", "water", "grass", "brick", "metal", "empty", "empty", "empty", "empty"},
	    {"metal", "brick", "empty", "empty", "empty", "grass", "grass", "water", "grass", "grass", "empty", "empty", "empty", "brick", "metal"},
	    {"metal", "brick", "empty", "empty", "brick", "metal", "grass", "water", "grass", "metal", "brick", "empty", "empty", "brick", "metal"},
	    {"metal", "brick", "empty", "empty", "metal", "metal", "grass", "water", "grass", "metal", "metal", "empty", "empty", "brick", "metal"},
	    {"metal", "brick", "empty", "empty", "brick", "metal", "grass", "water", "grass", "metal", "brick", "empty", "empty", "brick", "metal"},
	    {"empty", "empty", "empty", "empty", "empty", "grass", "grass", "grass", "grass", "grass", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "metal", "brick", "brick", "empty", "metal", "brick", "grass", "brick", "metal", "empty", "brick", "brick", "metal", "metal"},
	    {"metal", "metal", "brick", "brick", "empty", "metal", "metal", "empty", "metal", "metal", "empty", "brick", "brick", "metal", "metal"},
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"metal", "brick", "empty", "brick", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "brick", "empty", "brick", "metal"},
	    {"metal", "metal", "empty", "metal", "metal", "empty", "brick", "base_", "brick", "empty", "metal", "metal", "empty", "metal", "metal"}
	};

    private static final int Level_6_Enemy[] = {10, 4, 2, 6, 4, 2, 0};

    private static final int Level_6_Upgrade[] = {2, 2, 2, 2, 1};

    private static final String Level_7[] [] =
	{
	    {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "empty", "grass", "grass", "grass", "empty", "empty", "grass", "grass", "grass", "empty", "empty", "empty", "grass"},
	    {"empty", "empty", "empty", "empty", "grass", "empty", "empty", "empty", "grass", "grass", "empty", "empty", "empty", "grass", "grass"},
	    {"empty", "water", "water", "empty", "empty", "empty", "grass", "empty", "grass", "grass", "grass", "empty", "empty", "grass", "grass"},
	    {"empty", "water", "water", "water", "empty", "grass", "grass", "grass", "grass", "grass", "empty", "empty", "empty", "empty", "grass"},
	    {"empty", "empty", "water", "empty", "empty", "grass", "grass", "empty", "empty", "empty", "empty", "water", "water", "empty", "empty"},
	    {"grass", "empty", "empty", "empty", "grass", "grass", "empty", "metal", "water", "water", "water", "water", "empty", "empty", "empty"},
	    {"grass", "empty", "grass", "grass", "empty", "empty", "empty", "water", "water", "water", "water", "empty", "empty", "empty", "empty"},
	    {"empty", "empty", "grass", "empty", "empty", "empty", "empty", "water", "water", "water", "water", "empty", "empty", "grass", "empty"},
	    {"empty", "empty", "grass", "grass", "empty", "grass", "empty", "water", "water", "water", "empty", "empty", "empty", "empty", "grass"},
	    {"grass", "grass", "grass", "grass", "grass", "empty", "empty", "empty", "empty", "empty", "grass", "grass", "empty", "empty", "grass"},
	    {"empty", "empty", "empty", "grass", "empty", "empty", "empty", "empty", "grass", "grass", "empty", "empty", "empty", "grass", "grass"},
	    {"water", "water", "empty", "grass", "grass", "empty", "empty", "grass", "grass", "grass", "empty", "water", "water", "grass", "empty"},
	    {"water", "water", "water", "empty", "empty", "empty", "brick", "brick", "brick", "empty", "empty", "grass", "water", "empty", "empty"},
	    {"water", "water", "water", "empty", "empty", "empty", "brick", "base_", "brick", "empty", "empty", "grass", "empty", "empty", "empty"}
	};

    private static final int Level_7_Enemy[] = {8, 2, 2, 6, 4, 2, 0};

    private static final int Level_7_Upgrade[] = {2, 1, 2, 2, 2};
}
